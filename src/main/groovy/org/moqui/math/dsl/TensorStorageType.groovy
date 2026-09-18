/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorStorageType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorStorageType implements DslEnumValue {
    RowElement('TstRowElement', '', 'Row-per-element', ''),
    ArrayField('TstArrayField', '', 'Elements stored as an array field', ''),
    BlobField('TstBlobField', '', 'Elements stored as a BLOB field', ''),
    Content('TstContent', '', 'Elements stored as an external content', ''),
    DenseContig('TstDenseContig', '', 'Dense Contiguous Buffer (torch.strided default)', 'TstArrayField'),
    DenseRowMajor('TstDenseRowMajor', '', 'Dense Row-Major (C)', 'TstArrayField'),
    DenseColMajor('TstDenseColMajor', '', 'Dense Column-Major (Fortran)', 'TstArrayField'),
    DenseChLast2D('TstDenseChLast2D', '', 'Dense Channels-Last 2-D (NHWC)', 'TstDenseContig'),
    DenseChLast3D('TstDenseChLast3D', '', 'Dense Channels-Last 3-D (NDHWC)', 'TstDenseContig'),
    SparseCOO('TstSparseCOO', '', 'Sparse COO (indices, value)', 'TstArrayField'),
    SparseCSR('TstSparseCSR', '', 'Sparse CSR / CSC', 'TstArrayField'),
    SparseBSC('TstSparseBSC', '', 'Sparse BSC (Block-Sparse Column)', 'TstArrayField'),
    SparseBSR('TstSparseBSR', '', 'Sparse BSR (Block-Sparse Row)', 'TstArrayField'),
    SparseCSC('TstSparseCSC', '', 'Sparse CSC (Compressed Sparse Column)', 'TstArrayField'),
    SparseNested('TstSparseNested', '', 'Sparse Nested (Hybrid COO-CSR)', 'TstArrayField'),
    Zarr('TstZarr', '', 'Zarr Directory (Chunked, Compressed)', 'TstContent'),
    NPY('TstNPY', '', 'NumPy .npy File', 'TstContent'),
    SafeTensor('TstSafeTensor', '', 'Safetensors Binary (Zero-Copy)', 'TstContent'),
    ArrowIPC('TstArrowIPC', '', 'Apache Arrow IPC Stream', 'TstContent'),
    HDF5('TstHDF5', '', 'HDF5 Dataset', 'TstContent'),
    NetCDF4('TstNetCDF4', '', 'netCDF-4 (HDF5-based scientific data)', 'TstContent'),
    TileDBDense('TstTileDBDense', '', 'TileDB Dense Array', 'TstContent'),
    TileDBSparse('TstTileDBSparse', '', 'TileDB Sparse Array', 'TstContent'),
    N5('TstN5', '', 'N5 (chunked, HDF5-like, used in bio-imaging)', 'TstContent'),
    ParquetArrow('TstParquetArrow', '', 'Apache Parquet (Arrow-columnar)', 'TstContent'),
    MemMap('TstMemMap', '', 'POSIX/Win Memory-Mapped File', 'TstContent'),
    DLPack('TstDLPack', '', 'DLPack Capsule', 'TstContent'),
    TorchPT('TstTorchPT', '', 'PyTorch .pt / .pth serialized tensor file', 'TstContent'),
    TensorStoreSpec('TstTensorStoreSpec', '', 'TensorStore JSON / Kv Spec', 'TstContent');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorStorageType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorStorageType fromId(final String id) {
        if (id == null) return null
        for (TensorStorageType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorStorageType fromCode(final String code) {
        if (code == null) return null
        for (TensorStorageType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
