/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorContentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorContentType implements DslEnumValue {
    Custom('TCntCustom', 'Custom', 'Custom', ''),
    Npy('TCntNpy', 'Npy', 'NumPy Binary (.npy)', ''),
    Zarr('TCntZarr', 'Zarr', 'Zarr Chunked Array', ''),
    SafTen('TCntSafTen', 'Safe', 'Safetensors Binary', ''),
    ArrowIpc('TCntArrowIpc', 'ArrowIpc', 'Arrow IPC Stream', ''),
    CBOR('TCntCBOR', 'Cbor', 'CBOR Encoded Dense Tensor', ''),
    Manifest('TCntManifest', 'Manifest', 'External Manifest / Metadata File', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorContentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorContentType fromId(final String id) {
        if (id == null) return null
        for (TensorContentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorContentType fromCode(final String code) {
        if (code == null) return null
        for (TensorContentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TensorContentType fromName(final String name) {
        if (name == null) return null
        for (TensorContentType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('NumpyBinary'.equalsIgnoreCase(name)) return Npy
        if ('ZarrChunkedArray'.equalsIgnoreCase(name)) return Zarr
        if ('SafetensorsBinary'.equalsIgnoreCase(name)) return SafTen
        if ('ArrowIpcStream'.equalsIgnoreCase(name)) return ArrowIpc
        if ('CborEncodedDenseTensor'.equalsIgnoreCase(name)) return CBOR
        if ('ExternalManifestMetadataFile'.equalsIgnoreCase(name)) return Manifest
        null
    }
}
