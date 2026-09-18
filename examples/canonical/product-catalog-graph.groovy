/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// Pure Declarative Moqui Math Knowledge Graph: E-Commerce Product Catalog & Pricing Policies
Graph('ProductCatalogGraph', name: 'Moqui Mantle Product Catalog & Pricing Ontologies',
    description: 'E-Commerce Knowledge Graph of Categories, Products, and Unit Price Specifications') {

    // --- 1. Ontology Classes (Schema.org / Mantle Metamodel) ---
    GraphVertex('Class_Product', label: 'Product')
    GraphVertex('Class_ProductCategory', label: 'ProductCategory')
    GraphVertex('Class_UnitPriceSpecification', label: 'UnitPriceSpecification')

    // --- 2. Product Categories ---
    GraphVertex('CAT_DEMO_HARDWARE', label: 'Componenti Hardware Demo') {
        Parameter('Param_Cat_Name', parameterDefId: 'name', textValue: 'Componenti Hardware Demo')
    }

    // --- 3. Products and Price Specifications ---

    // Product 1: High-End Gaming Monitor (Price > 250 USD)
    GraphVertex('DEMO_1_1', label: "Monitor Gaming Premium 27''")
    GraphVertex('PriceSpec_DEMO_1_1', label: 'PriceSpec 299.99 USD') {
        Parameter('Param_Price_1', parameterDefId: 'price', numericValue: 299.99)
        Parameter('Param_Curr_1', parameterDefId: 'priceCurrency', textValue: 'USD')
    }

    // Product 2: Budget Optical Mouse (Price < 250 USD)
    GraphVertex('DEMO_1_2', label: 'Mouse Ottico Standard USB')
    GraphVertex('PriceSpec_DEMO_1_2', label: 'PriceSpec 29.99 USD') {
        Parameter('Param_Price_2', parameterDefId: 'price', numericValue: 29.99)
        Parameter('Param_Curr_2', parameterDefId: 'priceCurrency', textValue: 'USD')
    }

    // Product 3: AI Workstation Server (Price > 250 USD)
    GraphVertex('DEMO_1_3', label: 'Workstation AI Dual GPU Pro')
    GraphVertex('PriceSpec_DEMO_1_3', label: 'PriceSpec 3499.00 USD') {
        Parameter('Param_Price_3', parameterDefId: 'price', numericValue: 3499.00)
        Parameter('Param_Curr_3', parameterDefId: 'priceCurrency', textValue: 'USD')
    }

    // --- 4. Class Instantiation Edges (RDF type) ---
    GraphEdge('Edge_T_Cat', fromVertexId: 'CAT_DEMO_HARDWARE', toVertexId: 'Class_ProductCategory', label: 'type')
    GraphEdge('Edge_T_P1', fromVertexId: 'DEMO_1_1', toVertexId: 'Class_Product', label: 'type')
    GraphEdge('Edge_T_P2', fromVertexId: 'DEMO_1_2', toVertexId: 'Class_Product', label: 'type')
    GraphEdge('Edge_T_P3', fromVertexId: 'DEMO_1_3', toVertexId: 'Class_Product', label: 'type')
    GraphEdge('Edge_T_S1', fromVertexId: 'PriceSpec_DEMO_1_1', toVertexId: 'Class_UnitPriceSpecification', label: 'type')
    GraphEdge('Edge_T_S2', fromVertexId: 'PriceSpec_DEMO_1_2', toVertexId: 'Class_UnitPriceSpecification', label: 'type')
    GraphEdge('Edge_T_S3', fromVertexId: 'PriceSpec_DEMO_1_3', toVertexId: 'Class_UnitPriceSpecification', label: 'type')

    // --- 5. Semantic Relationships (hasCategory, hasPriceSpecification) ---
    GraphEdge('Edge_Cat_1', fromVertexId: 'DEMO_1_1', toVertexId: 'CAT_DEMO_HARDWARE', label: 'hasCategory')
    GraphEdge('Edge_Cat_2', fromVertexId: 'DEMO_1_2', toVertexId: 'CAT_DEMO_HARDWARE', label: 'hasCategory')
    GraphEdge('Edge_Cat_3', fromVertexId: 'DEMO_1_3', toVertexId: 'CAT_DEMO_HARDWARE', label: 'hasCategory')

    GraphEdge('Edge_Spec_1', fromVertexId: 'DEMO_1_1', toVertexId: 'PriceSpec_DEMO_1_1', label: 'priceSpecification')
    GraphEdge('Edge_Spec_2', fromVertexId: 'DEMO_1_2', toVertexId: 'PriceSpec_DEMO_1_2', label: 'priceSpecification')
    GraphEdge('Edge_Spec_3', fromVertexId: 'DEMO_1_3', toVertexId: 'PriceSpec_DEMO_1_3', label: 'priceSpecification')
}
