/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshGroupPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshGroupPurpose implements DslEnumValue {
    BoundaryCondition('MgpBoundaryCondition', '', 'Boundary Condition Region (Dirichlet / Neumann)', ''),
    SolverRegion('MgpSolverRegion', '', 'Solver Computation Region', ''),
    MaterialRegion('MgpMaterialRegion', '', 'Material Domain for FEM equations', 'MgpSolverRegion'),
    ShadingRegion('MgpShadingRegion', '', 'Shading/Rendering Region', ''),
    ThermalDomain('MgpThermalDomain', '', 'Region of the mesh where thermal equations are applied (e.g. temperature simulation).', 'MgpSolverRegion'),
    StructuralDomain('MgpStructuralDomain', '', 'Region where structural (mechanical stress/strain) equations are applied.', 'MgpSolverRegion'),
    SupportStructure('MgpSupportStructure', '', 'Support or scaffold cells', ''),
    FluidDomain('MgpFluidDomain', '', 'Region used for fluid dynamics simulation (CFD).', 'MgpSolverRegion'),
    ElectromagneticDomain('MgpElectromagneticDomain', '', 'Region used for electromagnetic simulation (e.g. Maxwell equations).', 'MgpSolverRegion'),
    MultiphysicsDomain('MgpMultiphysicsDomain', '', 'Region supporting coupled physics models (e.g. thermal-structural).', 'MgpSolverRegion'),
    InitialConditionRegion('MgpInitialConditionRegion', '', 'Zone where initial values are applied (e.g. initial temperature, velocity, etc).', 'MgpSolverRegion'),
    ControlRegion('MgpControlRegion', '', 'Control domain for active feedback, model-predictive control, or sensor influence.', 'MgpSolverRegion'),
    SamplingZone('MgpSamplingZone', '', 'Region used to sample or extract data (e.g. for statistics or averaging).', ''),
    LoadRegion('MgpLoadRegion', '', 'Area where loads / sources are applied', ''),
    Interface('MgpInterface', '', 'Internal interface between sub-domains', ''),
    PathRegion('MgpPathRegion', '', 'Cells selected by proximity to a ParametricPath', ''),
    ToolContactBand('MgpToolContactBand', '', 'Tool-contact band for CAM operations', 'MgpPathRegion');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshGroupPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static MeshGroupPurpose fromId(final String id) {
        if (id == null) return null
        for (MeshGroupPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshGroupPurpose fromCode(final String code) {
        if (code == null) return null
        for (MeshGroupPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MeshGroupPurpose fromName(final String name) {
        if (name == null) return null
        for (MeshGroupPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('BoundaryConditionRegion'.equalsIgnoreCase(name)) return BoundaryCondition
        if ('SolverComputationRegion'.equalsIgnoreCase(name)) return SolverRegion
        if ('MaterialDomainForFemEquations'.equalsIgnoreCase(name)) return MaterialRegion
        if ('ShadingRenderingRegion'.equalsIgnoreCase(name)) return ShadingRegion
        if ('RegionOfTheMeshWhereThermalEquationsAreApplied'.equalsIgnoreCase(name)) return ThermalDomain
        if ('RegionWhereStructuralEquationsAreApplied'.equalsIgnoreCase(name)) return StructuralDomain
        if ('SupportOrScaffoldCells'.equalsIgnoreCase(name)) return SupportStructure
        if ('RegionUsedForFluidDynamicsSimulation'.equalsIgnoreCase(name)) return FluidDomain
        if ('RegionUsedForElectromagneticSimulation'.equalsIgnoreCase(name)) return ElectromagneticDomain
        if ('RegionSupportingCoupledPhysicsModels'.equalsIgnoreCase(name)) return MultiphysicsDomain
        if ('ZoneWhereInitialValuesAreApplied'.equalsIgnoreCase(name)) return InitialConditionRegion
        if ('ControlDomainForActiveFeedbackModelPredictiveControlOrSensorInfluence'.equalsIgnoreCase(name)) return ControlRegion
        if ('RegionUsedToSampleOrExtractData'.equalsIgnoreCase(name)) return SamplingZone
        if ('AreaWhereLoadsSourcesAreApplied'.equalsIgnoreCase(name)) return LoadRegion
        if ('InternalInterfaceBetweenSubDomains'.equalsIgnoreCase(name)) return Interface
        if ('CellsSelectedByProximityToAParametricpath'.equalsIgnoreCase(name)) return PathRegion
        if ('ToolContactBandForCamOperations'.equalsIgnoreCase(name)) return ToolContactBand
        null
    }
}
