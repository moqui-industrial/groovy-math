/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.openfoam

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProvider

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@CompileStatic
class OpenFoamProvider implements MathProvider<OpenFoamPlan, OpenFoamResult> {
    final String mathModelId

    OpenFoamProvider(final String mathModelId) {
        if (!mathModelId) throw new IllegalArgumentException('mathModelId must not be empty')
        this.mathModelId = mathModelId
    }

    @Override
    String getProviderId() { 'openfoam' }

    @Override
    OpenFoamPlan compile(final MathMeta mathMeta) {
        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")

        String defId = model.get('mathModelDefId') as String
        String solvingMethod = null
        if (defId != null && mathMeta.hasEntity('MathModelDefPipeline')) {
            for (ModelValue step : mathMeta.entity('MathModelDefPipeline')) {
                if (step.get('mathModelDefId') == defId && step.get('solvingMethodEnumId') != null) {
                    solvingMethod = step.get('solvingMethodEnumId') as String
                    break
                }
            }
        }
        if (solvingMethod == null) {
            solvingMethod = (model.get('solvingMethodEnumId') ?: 'MmsmOpenFoamIcoFoam') as String
        }
        String solver = 'icoFoam'
        if (solvingMethod == 'MmsmOpenFoamSimpleFoam') {
            solver = 'simpleFoam'
        } else if (solvingMethod == 'MmsmFvm' || solvingMethod == 'Fvm') {
            solver = 'incompressibleFvm'
        }

        // 1. Resolve Parameters (SimScale Simulation Setup: Physical properties & Controls)
        double nu = 0.01d
        double rho = 1000.0d
        double startTime = 0.0d
        double endTime = 0.5d
        double deltaT = 0.005d
        double writeInterval = 0.1d
        double pTolerance = 1e-4d
        double uTolerance = 1e-4d

        // Check ParameterDef defaults first
        for (ModelValue paramDef : mathMeta.entity('ParameterDef')) {
            String code = (paramDef.get('parameterCode') ?: paramDef.get('parameterDefId')) as String
            Object defVal = paramDef.get('defaultValue')
            if (defVal instanceof Number) {
                double val = ((Number) defVal).doubleValue()
                switch (code) {
                    case 'nu':
                    case 'kinematicViscosity': nu = val; break
                    case 'rho':
                    case 'density': rho = val; break
                    case 'deltaT': deltaT = val; break
                    case 'startTime': startTime = val; break
                    case 'endTime': endTime = val; break
                    case 'writeInterval': writeInterval = val; break
                    case 'pTolerance':
                    case 'residualToleranceP': pTolerance = val; break
                    case 'uTolerance':
                    case 'residualToleranceU': uTolerance = val; break
                }
            }
        }

        for (ModelValue param : mathMeta.entity('Parameter')) {
            String mId = param.get('mathModelId') as String
            if (mId != mathModelId) continue
            String alias = (param.get('parameterAlias') ?: '') as String
            Number num = param.get('numericValue') as Number
            if (num == null) continue
            double val = num.doubleValue()

            switch (alias) {
                case 'nu':
                case 'kinematicViscosity':
                    nu = val; break
                case 'rho':
                case 'density':
                    rho = val; break
                case 'startTime':
                    startTime = val; break
                case 'endTime':
                    endTime = val; break
                case 'deltaT':
                    deltaT = val; break
                case 'writeInterval':
                    writeInterval = val; break
                case 'pTolerance':
                case 'residualToleranceP':
                    pTolerance = val; break
                case 'uTolerance':
                case 'residualToleranceU':
                    uTolerance = val; break
            }
        }

        if (deltaT <= 0.0d) {
            throw new IllegalArgumentException("deltaT must be strictly positive, got: ${deltaT}")
        }

        // 2. Resolve Mesh & Adaptation
        double xMin = 0.0, xMax = 0.1
        double yMin = 0.0, yMax = 0.1
        double zMin = 0.0, zMax = 0.01
        int nx = 20, ny = 20, nz = 1
        List<Double> grading = [1.0d, 1.0d, 1.0d]
        String adaptationType = 'MatNone'

        String meshId = model.get('meshId') as String
        ModelValue mesh = meshId ? mathMeta.entity('Mesh').findByName(meshId) : null
        if (mesh == null) {
            // Find first mesh if any
            for (ModelValue m : mathMeta.entity('Mesh')) {
                mesh = m
                break
            }
        }

        if (mesh != null) {
            adaptationType = (mesh.get('adaptationTypeEnumId') ?: mesh.get('meshAdaptationTypeEnumId') ?: 'MatNone') as String
        }

        // Read mesh parameters if set
        for (ModelValue param : mathMeta.entity('Parameter')) {
            String mId = param.get('mathModelId') as String
            if (mId != mathModelId) continue
            String alias = (param.get('parameterAlias') ?: '') as String
            Number num = param.get('numericValue') as Number
            if (num != null) {
                double val = num.doubleValue()
                if (alias == 'xMin') xMin = val
                else if (alias == 'xMax') xMax = val
                else if (alias == 'yMin') yMin = val
                else if (alias == 'yMax') yMax = val
                else if (alias == 'zMin') zMin = val
                else if (alias == 'zMax') zMax = val
                else if (alias == 'nx') nx = (int) val
                else if (alias == 'ny') ny = (int) val
                else if (alias == 'nz') nz = (int) val
                else if (alias == 'gradingX') grading[0] = val
                else if (alias == 'gradingY') grading[1] = val
                else if (alias == 'gradingZ') grading[2] = val
            }
        }

        // 3. Resolve Boundary Patches (MeshGroup)
        Map<String, Map<String, Object>> boundaryPatches = new LinkedHashMap<>()
        for (ModelValue group : mathMeta.entity('MeshGroup')) {
            String groupName = (group.get('groupName') ?: group.get('meshGroupId')) as String
            String groupType = (group.get('meshGroupTypeEnumId') ?: 'Patch') as String
            Map<String, Object> patchInfo = new LinkedHashMap<>()
            patchInfo.put('type', groupType)
            boundaryPatches.put(groupName, patchInfo)
        }

        if (boundaryPatches.isEmpty()) {
            // Standard OpenFOAM Cavity boundary configuration
            Map<String, Object> movingWall = new LinkedHashMap<>()
            movingWall.put('type', 'fixedValue')
            movingWall.put('velocity', [1.0d, 0.0d, 0.0d])
            movingWall.put('pressure', 'zeroGradient')
            boundaryPatches.put('movingWall', movingWall)

            Map<String, Object> fixedWalls = new LinkedHashMap<>()
            fixedWalls.put('type', 'noSlip')
            fixedWalls.put('velocity', [0.0d, 0.0d, 0.0d])
            fixedWalls.put('pressure', 'zeroGradient')
            boundaryPatches.put('fixedWalls', fixedWalls)

            Map<String, Object> frontAndBack = new LinkedHashMap<>()
            frontAndBack.put('type', 'empty')
            boundaryPatches.put('frontAndBack', frontAndBack)
        }

        String caseDir = "build/openfoam_cases/${mathModelId}"
        return new OpenFoamPlan(mathModelId, solver, caseDir,
            xMin, xMax, yMin, yMax, zMin, zMax,
            nx, ny, nz, grading, adaptationType,
            boundaryPatches, nu, rho,
            startTime, endTime, deltaT, writeInterval,
            pTolerance, uTolerance)
    }

    @Override
    OpenFoamResult execute(final OpenFoamPlan plan, final Map<String, ?> inputs) {
        long startNano = System.nanoTime()
        Path casePath = Paths.get(plan.caseDirectory)
        Files.createDirectories(casePath.resolve('system'))
        Files.createDirectories(casePath.resolve('constant'))
        Files.createDirectories(casePath.resolve('0'))

        // Write OpenFOAM Dictionaries
        writeControlDict(casePath.resolve('system/controlDict'), plan)
        writeFvSchemes(casePath.resolve('system/fvSchemes'))
        writeFvSolution(casePath.resolve('system/fvSolution'), plan)
        writeBlockMeshDict(casePath.resolve('system/blockMeshDict'), plan)
        writeTransportProperties(casePath.resolve('constant/transportProperties'), plan)
        writeUField(casePath.resolve('0/U'), plan)
        writePField(casePath.resolve('0/p'), plan)

        // Execution:
        // Try Panama C++ Bridge first if native solver requested
        if (plan.solver == 'icoFoam' || plan.solver == 'simpleFoam') {
            if (!OpenFoamPanama.INSTANCE.isAvailable()) {
                throw new org.moqui.math.spi.ProviderUnavailableException('openfoam',
                    "OpenFOAM native C++ runtime (libOpenFOAM / libfiniteVolume) is not installed on this system. The native solver '${plan.solver}' cannot be executed.",
                    "Install OpenFOAM or run './gradlew buildOpenFoamNative'.")
            }
            throw new UnsupportedOperationException("Native OpenFOAM solver '${plan.solver}' execution is not yet integrated with libfiniteVolume")
        } else if (plan.solver == 'incompressibleFvm') {
            // Execute Finite Volume Solver (Navier-Stokes FVM Incompressible 2D)
            Map<String, Object> fvmRun = solveIncompressibleFvm(plan)
            Map<String, Object> fieldResults = [U: fvmRun.get('U'), p: fvmRun.get('p')]
            @SuppressWarnings('unchecked')
            Map<String, Double> residuals = (Map<String, Double>) fvmRun.get('residuals')
            String executionStatus = fvmRun.get('status') as String

            double elapsedMs = (System.nanoTime() - startNano) / 1_000_000.0d
            int totalIters = (fvmRun.get('actualIters') ?: (int) Math.round((plan.endTime - plan.startTime) / plan.deltaT)) as int
            double simulatedTime = (fvmRun.get('actualTime') ?: plan.endTime) as double

            return new OpenFoamResult(
                plan.mathModelId,
                plan.solver,
                executionStatus,
                simulatedTime,
                totalIters,
                residuals,
                plan.cellCount,
                fieldResults,
                elapsedMs
            )
        } else {
            throw new IllegalArgumentException("Unsupported solver: " + plan.solver)
        }
    }

    private void writeControlDict(Path file, OpenFoamPlan plan) {
        int writeStep = Math.max(1, (int) Math.round(plan.writeInterval / plan.deltaT))
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       dictionary;
    location    "system";
    object      controlDict;
}
application     ${plan.solver};
startFrom       startTime;
startTime       ${plan.startTime};
stopAt          endTime;
endTime         ${plan.endTime};
deltaT          ${plan.deltaT};
writeControl    timeStep;
writeInterval   ${writeStep};
purgeWrite      0;
writeFormat     ascii;
writePrecision  6;
writeCompression off;
timeFormat      general;
timePrecision   6;
runTimeModifiable true;
"""
        Files.writeString(file, content)
    }

    private void writeFvSchemes(Path file) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       dictionary;
    location    "system";
    object      fvSchemes;
}
ddtSchemes
{
    default         Euler;
}
gradSchemes
{
    default         Gauss linear;
    grad(p)         Gauss linear;
}
divSchemes
{
    default         none;
    div(phi,U)      Gauss linear;
}
laplacianSchemes
{
    default         Gauss linear orthogonal;
}
interpolationSchemes
{
    default         linear;
}
snGradSchemes
{
    default         orthogonal;
}
"""
        Files.writeString(file, content)
    }

    private void writeFvSolution(Path file, OpenFoamPlan plan) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       dictionary;
    location    "system";
    object      fvSolution;
}
solvers
{
    p
    {
        solver          PCG;
        preconditioner  DIC;
        tolerance       ${plan.pTolerance};
        relTol          0.05;
    }
    pFinal
    {
        \$p;
        relTol          0;
    }
    U
    {
        solver          PBiCGStab;
        preconditioner  DILU;
        tolerance       ${plan.uTolerance};
        relTol          0;
    }
}
PISO
{
    nCorrectors     2;
    nNonOrthogonalCorrectors 0;
    pRefCell        0;
    pRefValue       0;
}
"""
        Files.writeString(file, content)
    }

    private void writeBlockMeshDict(Path file, OpenFoamPlan plan) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       dictionary;
    location    "system";
    object      blockMeshDict;
}
scale   1;

vertices
(
    (${plan.xMin} ${plan.yMin} ${plan.zMin})
    (${plan.xMax} ${plan.yMin} ${plan.zMin})
    (${plan.xMax} ${plan.yMax} ${plan.zMin})
    (${plan.xMin} ${plan.yMax} ${plan.zMin})
    (${plan.xMin} ${plan.yMin} ${plan.zMax})
    (${plan.xMax} ${plan.yMin} ${plan.zMax})
    (${plan.xMax} ${plan.yMax} ${plan.zMax})
    (${plan.xMin} ${plan.yMax} ${plan.zMax})
);

blocks
(
    hex (0 1 2 3 4 5 6 7) (${plan.nx} ${plan.ny} ${plan.nz}) simpleGrading (${plan.grading[0]} ${plan.grading[1]} ${plan.grading[2]})
);

edges
(
);

boundary
(
    movingWall
    {
        type wall;
        faces
        (
            (3 7 6 2)
        );
    }
    fixedWalls
    {
        type wall;
        faces
        (
            (0 4 7 3)
            (2 6 5 1)
            (1 5 4 0)
        );
    }
    frontAndBack
    {
        type empty;
        faces
        (
            (0 3 2 1)
            (4 5 6 7)
        );
    }
);

mergePatchPairs
(
);
"""
        Files.writeString(file, content)
    }

    private void writeTransportProperties(Path file, OpenFoamPlan plan) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       dictionary;
    location    "constant";
    object      transportProperties;
}
transportModel  Newtonian;
nu              [0 2 -1 0 0 0 0] ${plan.kinematicViscosity};
"""
        Files.writeString(file, content)
    }

    private void writeUField(Path file, OpenFoamPlan plan) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       volVectorField;
    location    "0";
    object      U;
}
dimensions      [0 1 -1 0 0 0 0];
internalField   uniform (0 0 0);

boundaryField
{
    movingWall
    {
        type            fixedValue;
        value           uniform (1 0 0);
    }
    fixedWalls
    {
        type            noSlip;
    }
    frontAndBack
    {
        type            empty;
    }
}
"""
        Files.writeString(file, content)
    }

    private void writePField(Path file, OpenFoamPlan plan) {
        String content = """/*--------------------------------*- C++ -*----------------------------------*\\
| =========                 |                                                 |
| \\\\      /  F ield         | OpenFOAM: The Open Source CFD Toolbox           |
|  \\\\    /   O peration     | Version:  v2406                                 |
|   \\\\  /    A nd           | Website:  www.openfoam.com                      |
|    \\\\/     M anipulation  |                                                 |
\\*---------------------------------------------------------------------------*/
FoamFile
{
    version     2.0;
    format      ascii;
    class       volScalarField;
    location    "0";
    object      p;
}
dimensions      [0 2 -2 0 0 0 0];
internalField   uniform 0;

boundaryField
{
    movingWall
    {
        type            zeroGradient;
    }
    fixedWalls
    {
        type            zeroGradient;
    }
    frontAndBack
    {
        type            empty;
    }
}
"""
        Files.writeString(file, content)
    }

    private Map<String, Object> solveIncompressibleFvm(OpenFoamPlan plan) {
        int nx = plan.nx
        int ny = plan.ny
        double dx = (plan.xMax - plan.xMin) / nx
        double dy = (plan.yMax - plan.yMin) / ny
        double dt = plan.deltaT
        double nu = plan.kinematicViscosity

        // Staggered / cell-centered discrete arrays
        double[][] u = new double[ny + 2][nx + 2]
        double[][] v = new double[ny + 2][nx + 2]
        double[][] p = new double[ny + 2][nx + 2]

        double lidVelocity = 1.0d
        Map<String, Object> moving = plan.boundaryPatches.get('movingWall')
        if (moving != null && moving.get('velocity') instanceof List) {
            List<?> velList = (List<?>) moving.get('velocity')
            if (!velList.isEmpty() && velList[0] instanceof Number) {
                lidVelocity = ((Number) velList[0]).doubleValue()
            }
        }

        int steps = Math.max(10, (int) Math.round((plan.endTime - plan.startTime) / dt))
        double maxVel = Math.max(Math.abs(lidVelocity), 1e-6d)
        double cfl = (maxVel / dx) * dt

        double fvmDt
        int subSteps
        if (cfl > 5.0) {
            // Severe CFL violation: user time-step violates explicit advection limit, do not suppress divergence
            fvmDt = dt
            subSteps = 1
        } else {
            double fvmDtLimit = Math.min(dt, 0.20 * dx * dx / Math.max(1e-6, nu))
            subSteps = Math.max(1, (int) Math.ceil(dt / fvmDtLimit))
            fvmDt = dt / subSteps
        }

        double[][] uPrev = new double[ny + 2][nx + 2]
        double[][] vPrev = new double[ny + 2][nx + 2]
        double resUx = 1.0d
        double resUy = 1.0d
        double resContinuity = 1.0d
        int actualIters = 0
        double actualTime = plan.startTime
        boolean diverged = false

        // Discrete FVM solver iterations for Cavity / Navier-Stokes flow
        for (int step = 0; step < steps; step++) {
            actualIters++
            actualTime = plan.startTime + (step + 1) * plan.deltaT

            for (int j = 0; j <= ny + 1; j++) {
                System.arraycopy(u[j], 0, uPrev[j], 0, nx + 2)
                System.arraycopy(v[j], 0, vPrev[j], 0, nx + 2)
            }

            for (int sub = 0; sub < subSteps; sub++) {
                // Apply boundary conditions:
                // Top wall (movingWall): u = lidVelocity, v = 0
                for (int i = 0; i <= nx + 1; i++) {
                    u[ny + 1][i] = lidVelocity
                    v[ny + 1][i] = 0.0d
                }
                // Bottom wall (fixedWalls): u = 0, v = 0
                for (int i = 0; i <= nx + 1; i++) {
                    u[0][i] = 0.0d
                    v[0][i] = 0.0d
                }
                // Left & right walls: u = 0, v = 0
                for (int j = 0; j <= ny + 1; j++) {
                    u[j][0] = 0.0d
                    v[j][0] = 0.0d
                    u[j][nx + 1] = 0.0d
                    v[j][nx + 1] = 0.0d
                }

                double[][] uStar = new double[ny + 2][nx + 2]
                double[][] vStar = new double[ny + 2][nx + 2]

                // Momentum predictor (Convection + Diffusion)
                for (int j = 1; j <= ny; j++) {
                    for (int i = 1; i <= nx; i++) {
                        double uDiff = nu * ((u[j][i + 1] - 2 * u[j][i] + u[j][i - 1]) / (dx * dx) +
                                             (u[j + 1][i] - 2 * u[j][i] + u[j - 1][i]) / (dy * dy))
                        double uConv = u[j][i] * (u[j][i + 1] - u[j][i - 1]) / (2 * dx) +
                                       v[j][i] * (u[j + 1][i] - u[j - 1][i]) / (2 * dy)
                        uStar[j][i] = u[j][i] + fvmDt * (uDiff - uConv)

                        double vDiff = nu * ((v[j][i + 1] - 2 * v[j][i] + v[j][i - 1]) / (dx * dx) +
                                             (v[j + 1][i] - 2 * v[j][i] + v[j - 1][i]) / (dy * dy))
                        double vConv = u[j][i] * (v[j][i + 1] - v[j][i - 1]) / (2 * dx) +
                                       v[j][i] * (v[j + 1][i] - v[j - 1][i]) / (2 * dy)
                        vStar[j][i] = v[j][i] + fvmDt * (vDiff - vConv)

                        if (Double.isNaN(uStar[j][i]) || Double.isInfinite(uStar[j][i]) ||
                            Double.isNaN(vStar[j][i]) || Double.isInfinite(vStar[j][i]) ||
                            Math.abs(uStar[j][i]) > 1e4 || Math.abs(vStar[j][i]) > 1e4) {
                            diverged = true
                            break
                        }
                    }
                    if (diverged) break
                }
                if (diverged) break

                // Pressure Poisson Equation Solver (Gauss-Seidel / DIC parity)
                for (int it = 0; it < 30; it++) {
                    for (int j = 1; j <= ny; j++) {
                        for (int i = 1; i <= nx; i++) {
                            double divU = ((uStar[j][i + 1] - uStar[j][i - 1]) / (2 * dx) +
                                           (vStar[j + 1][i] - vStar[j - 1][i]) / (2 * dy))
                            double rhs = (1.0d / fvmDt) * divU
                            p[j][i] = 0.25d * (p[j][i + 1] + p[j][i - 1] + p[j + 1][i] + p[j - 1][i] -
                                              dx * dy * rhs)
                        }
                    }
                    // Pressure zeroGradient boundary condition
                    for (int i = 1; i <= nx; i++) {
                        p[0][i] = p[1][i]
                        p[ny + 1][i] = p[ny][i]
                    }
                    for (int j = 1; j <= ny; j++) {
                        p[j][0] = p[j][1]
                        p[j][nx + 1] = p[j][nx]
                    }
                }

                // Velocity Corrector
                for (int j = 1; j <= ny; j++) {
                    for (int i = 1; i <= nx; i++) {
                        u[j][i] = uStar[j][i] - (fvmDt / (2 * dx)) * (p[j][i + 1] - p[j][i - 1])
                        v[j][i] = vStar[j][i] - (fvmDt / (2 * dy)) * (p[j + 1][i] - p[j - 1][i])
                    }
                }
            }

            if (diverged) break

            // Real physical residuals: L2 norm of velocity increment per step and divergence of velocity (continuity)
            double sumSqDu = 0.0d
            double sumSqDv = 0.0d
            double sumSqDivU = 0.0d
            for (int j = 1; j <= ny; j++) {
                for (int i = 1; i <= nx; i++) {
                    double du = u[j][i] - uPrev[j][i]
                    double dv = v[j][i] - vPrev[j][i]
                    sumSqDu += du * du
                    sumSqDv += dv * dv
                    double div = ((u[j][i + 1] - u[j][i - 1]) / (2 * dx) + (v[j + 1][i] - v[j - 1][i]) / (2 * dy))
                    sumSqDivU += div * div
                }
            }
            int totalCells = nx * ny
            resUx = Math.sqrt(sumSqDu / totalCells)
            resUy = Math.sqrt(sumSqDv / totalCells)
            resContinuity = Math.sqrt(sumSqDivU / totalCells)

            double steadyTol = 1e-4d
            if (resContinuity <= plan.pTolerance && resUx <= steadyTol && resUy <= steadyTol) {
                break
            }
        }

        // Pack results into 1D/2D lists matching cell indices
        List<List<Double>> velocityList = new ArrayList<>(nx * ny)
        List<Double> pressureList = new ArrayList<>(nx * ny)

        for (int j = 1; j <= ny; j++) {
            for (int i = 1; i <= nx; i++) {
                List<Double> vec = new ArrayList<>(3)
                vec.add(Double.valueOf(u[j][i]))
                vec.add(Double.valueOf(v[j][i]))
                vec.add(Double.valueOf(0.0d))
                velocityList.add(vec)
                pressureList.add(Double.valueOf(p[j][i]))
            }
        }

        double steadyTol = 1e-4d
        boolean isConverged = !diverged && (resContinuity <= plan.pTolerance && resUx <= steadyTol && resUy <= steadyTol)
        String computedStatus = isConverged ? 'CONVERGED' : 'NOT_CONVERGED'

        Map<String, Double> residualsMap = new LinkedHashMap<>()
        residualsMap.put('continuity', resContinuity)
        residualsMap.put('Ux', resUx)
        residualsMap.put('Uy', resUy)

        Map<String, Object> res = new LinkedHashMap<>()
        res.put('U', velocityList)
        res.put('p', pressureList)
        res.put('residuals', residualsMap)
        res.put('status', computedStatus)
        res.put('actualTime', actualTime)
        res.put('actualIters', actualIters)
        return res
    }
}
