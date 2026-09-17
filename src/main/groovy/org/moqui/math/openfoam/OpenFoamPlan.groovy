/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.openfoam

import groovy.transform.CompileStatic

@CompileStatic
class OpenFoamPlan {
    final String mathModelId
    final String solver
    final String caseDirectory

    // Mesh definition
    final double xMin, xMax, yMin, yMax, zMin, zMax
    final int nx, ny, nz
    final List<Double> grading
    final String meshAdaptationType

    // Boundary Patches: name -> [type, velocity, pressure]
    final Map<String, Map<String, Object>> boundaryPatches

    // Fluid & Physical properties (SimScale Material parameters)
    final double kinematicViscosity
    final double density

    // Simulation Controls (SimScale Simulation Control)
    final double startTime
    final double endTime
    final double deltaT
    final double writeInterval
    final double pTolerance
    final double uTolerance
    final double lidVelocity

    OpenFoamPlan(String mathModelId, String solver, String caseDirectory,
                 double xMin, double xMax, double yMin, double yMax, double zMin, double zMax,
                 int nx, int ny, int nz, List<Double> grading, String meshAdaptationType,
                 Map<String, Map<String, Object>> boundaryPatches,
                 double kinematicViscosity, double density,
                 double startTime, double endTime, double deltaT, double writeInterval,
                 double pTolerance, double uTolerance, double lidVelocity = 1.0d) {
        this.mathModelId = mathModelId
        this.solver = solver
        this.caseDirectory = caseDirectory
        this.xMin = xMin
        this.xMax = xMax
        this.yMin = yMin
        this.yMax = yMax
        this.zMin = zMin
        this.zMax = zMax
        this.nx = nx
        this.ny = ny
        this.nz = nz
        this.grading = grading ?: [1.0d, 1.0d, 1.0d]
        this.meshAdaptationType = meshAdaptationType ?: 'MatNone'
        this.boundaryPatches = boundaryPatches ?: Collections.emptyMap()
        this.kinematicViscosity = kinematicViscosity
        this.density = density
        this.startTime = startTime
        this.endTime = endTime
        this.deltaT = deltaT
        this.writeInterval = writeInterval
        this.pTolerance = pTolerance
        this.uTolerance = uTolerance
        this.lidVelocity = lidVelocity
    }

    int getCellCount() {
        nx * ny * nz
    }
}
