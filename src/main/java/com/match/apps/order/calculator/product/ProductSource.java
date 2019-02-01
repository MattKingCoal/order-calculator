package com.match.apps.order.calculator.product;

import java.util.List;

/**
 * Interface defining a source of Products
 */
public interface ProductSource {

    /**
     * @return A List of all {@link Product}s in this source.
     */
    public List<Product> loadProducts();

}
