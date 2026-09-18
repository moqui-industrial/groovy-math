/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParameterPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParameterPurpose implements DslEnumValue {
    MathModel('PpMathModel', '', 'Math Model Parameter', ''),
    Mesh('PpMesh', '', 'Mesh Parameter', ''),
    MlHyperparameter('PpMlHyperparameter', '', 'Machine Learning Hyperparameter', ''),
    LearningRate('PpLearningRate', '', 'Learning Rate', 'PpMlHyperparameter'),
    WeightDecay('PpWeightDecay', '', 'Weight Decay', 'PpMlHyperparameter'),
    BatchSize('PpBatchSize', '', 'Batch Size', 'PpMlHyperparameter'),
    Epochs('PpEpochs', '', 'Epochs', 'PpMlHyperparameter'),
    Momentum('PpMomentum', '', 'Momentum', 'PpMlHyperparameter'),
    DropoutRate('PpDropoutRate', '', 'Dropout Rate', 'PpMlHyperparameter'),
    InitialCondition('PpInitialCondition', '', 'Initial Condition', ''),
    BoundaryCondition('PpBoundaryCondition', '', 'Boundary Patch Field Specification', 'PpControl'),
    DirichletBoundaryCondition('PpDirichletBoundaryCondition', '', 'Dirichlet Boundary Condition', 'PpBoundaryCondition'),
    NeumannBoundaryCondition('PpNeumannBoundaryCondition', '', 'Neumann Boundary Condition', 'PpBoundaryCondition'),
    MixedBoundaryCondition('PpMixedBoundaryCondition', '', 'Mixed Boundary Condition', 'PpBoundaryCondition'),
    Constraint('PpConstraint', '', 'Constraint', ''),
    DeviceConfiguration('PpDeviceConfiguration', '', 'Device Configuration', ''),
    DeviceTelemetry('PpDeviceTelemetry', '', 'Device Telemetry', ''),
    Control('PpControl', '', 'Control', ''),
    Monitoring('PpMonitoring', '', 'Monitoring', ''),
    Feedback('PpFeedback', '', 'Feedback', 'PpMonitoring'),
    Status('PpStatus', '', 'Status', 'PpMonitoring'),
    Condition('PpCondition', '', 'Condition', 'PpMonitoring'),
    Event('PpEvent', '', 'Event', 'PpMonitoring'),
    Sample('PpSample', '', 'Sample', 'PpMonitoring'),
    MeshMaterial('PpMeshMaterial', '', 'Mesh Material Property Assignment', ''),
    MeshRendering('PpMeshRendering', '', 'Mesh Visual/Rendering Property Assignment', ''),
    Physical('PpPhysical', '', 'Physical quantity', ''),
    FluidProperty('PpFluidProperty', '', 'Fluid Transport/Thermodynamic Property', 'PpPhysical'),
    SolverControl('PpSolverControl', '', 'Simulation Runtime & Convergence Control', 'PpControl'),
    NumericalScheme('PpNumericalScheme', '', 'FVM Discretization Scheme', 'PpControl'),
    Mechanical('PpMechanical', '', 'Mechanical property', ''),
    Thermal('PpThermal', '', 'Thermal property', ''),
    Electrical('PpElectrical', '', 'Electrical property', ''),
    Optical('PpOptical', '', 'Optical/visual property', ''),
    Rendering('PpRendering', '', 'Rendering/shading-related property', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParameterPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParameterPurpose fromId(final String id) {
        if (id == null) return null
        for (ParameterPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParameterPurpose fromCode(final String code) {
        if (code == null) return null
        for (ParameterPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ParameterPurpose fromName(final String name) {
        if (name == null) return null
        for (ParameterPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('MathModelParameter'.equalsIgnoreCase(name)) return MathModel
        if ('MeshParameter'.equalsIgnoreCase(name)) return Mesh
        if ('MachineLearningHyperparameter'.equalsIgnoreCase(name)) return MlHyperparameter
        if ('BoundaryPatchFieldSpecification'.equalsIgnoreCase(name)) return BoundaryCondition
        if ('MeshMaterialPropertyAssignment'.equalsIgnoreCase(name)) return MeshMaterial
        if ('MeshVisualRenderingPropertyAssignment'.equalsIgnoreCase(name)) return MeshRendering
        if ('PhysicalQuantity'.equalsIgnoreCase(name)) return Physical
        if ('FluidTransportThermodynamicProperty'.equalsIgnoreCase(name)) return FluidProperty
        if ('SimulationRuntimeConvergenceControl'.equalsIgnoreCase(name)) return SolverControl
        if ('FvmDiscretizationScheme'.equalsIgnoreCase(name)) return NumericalScheme
        if ('MechanicalProperty'.equalsIgnoreCase(name)) return Mechanical
        if ('ThermalProperty'.equalsIgnoreCase(name)) return Thermal
        if ('ElectricalProperty'.equalsIgnoreCase(name)) return Electrical
        if ('OpticalVisualProperty'.equalsIgnoreCase(name)) return Optical
        if ('RenderingShadingRelatedProperty'.equalsIgnoreCase(name)) return Rendering
        null
    }
}
