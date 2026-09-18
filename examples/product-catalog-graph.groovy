/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// Pure Declarative Moqui Math Knowledge Graph: E-Commerce Product Catalog & Pricing Policies
Graph('ProductCatalogGraph', name: 'Moqui Mantle Product Catalog & Pricing Ontologies',
    description: 'E-Commerce Knowledge Graph of Categories, Products, and Unit Price Specifications') {

    // --- 1. Ontology Classes (Schema.org / Mantle Metamodel) ---
    vertex('Class_Product', label: 'Product')
    vertex('Class_ProductCategory', label: 'ProductCategory')
    vertex('Class_UnitPriceSpecification', label: 'UnitPriceSpecification')

    // --- 2. Product Categories ---
    vertex('CAT_DEMO_HARDWARE', label: 'Componenti Hardware Demo') {
        parameter('Param_Cat_Name', def: 'name', text: 'Componenti Hardware Demo')
    }

    // --- 3. Products and Price Specifications ---

    // Product 1: High-End Gaming Monitor (Price > 250 USD)
    vertex('DEMO_1_1', label: "Monitor Gaming Premium 27''")
    vertex('PriceSpec_DEMO_1_1', label: 'PriceSpec 299.99 USD') {
        parameter('Param_Price_1', def: 'price', value: 299.99)
        parameter('Param_Curr_1', def: 'priceCurrency', text: 'USD')
    }

    // Product 2: Budget Optical Mouse (Price < 250 USD)
    vertex('DEMO_1_2', label: 'Mouse Ottico Standard USB')
    vertex('PriceSpec_DEMO_1_2', label: 'PriceSpec 29.99 USD') {
        parameter('Param_Price_2', def: 'price', value: 29.99)
        parameter('Param_Curr_2', def: 'priceCurrency', text: 'USD')
    }

    // Product 3: AI Workstation Server (Price > 250 USD)
    vertex('DEMO_1_3', label: 'Workstation AI Dual GPU Pro')
    vertex('PriceSpec_DEMO_1_3', label: 'PriceSpec 3499.00 USD') {
        parameter('Param_Price_3', def: 'price', value: 3499.00)
        parameter('Param_Curr_3', def: 'priceCurrency', text: 'USD')
    }

    // --- 4. Class Instantiation Edges (RDF type) ---
    edge('Edge_T_Cat', from: 'CAT_DEMO_HARDWARE', to: 'Class_ProductCategory', label: 'type')
    edge('Edge_T_P1', from: 'DEMO_1_1', to: 'Class_Product', label: 'type')
    edge('Edge_T_P2', from: 'DEMO_1_2', to: 'Class_Product', label: 'type')
    edge('Edge_T_P3', from: 'DEMO_1_3', to: 'Class_Product', label: 'type')
    edge('Edge_T_S1', from: 'PriceSpec_DEMO_1_1', to: 'Class_UnitPriceSpecification', label: 'type')
    edge('Edge_T_S2', from: 'PriceSpec_DEMO_1_2', to: 'Class_UnitPriceSpecification', label: 'type')
    edge('Edge_T_S3', from: 'PriceSpec_DEMO_1_3', to: 'Class_UnitPriceSpecification', label: 'type')

    // --- 5. Semantic Relationships (hasCategory, hasPriceSpecification) ---
    edge('Edge_Cat_1', from: 'DEMO_1_1', to: 'CAT_DEMO_HARDWARE', label: 'hasCategory')
    edge('Edge_Cat_2', from: 'DEMO_1_2', to: 'CAT_DEMO_HARDWARE', label: 'hasCategory')
    edge('Edge_Cat_3', from: 'DEMO_1_3', to: 'CAT_DEMO_HARDWARE', label: 'hasCategory')

    edge('Edge_Spec_1', from: 'DEMO_1_1', to: 'PriceSpec_DEMO_1_1', label: 'priceSpecification')
    edge('Edge_Spec_2', from: 'DEMO_1_2', to: 'PriceSpec_DEMO_1_2', label: 'priceSpecification')
    edge('Edge_Spec_3', from: 'DEMO_1_3', to: 'PriceSpec_DEMO_1_3', label: 'priceSpecification')
}
