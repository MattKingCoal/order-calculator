package com.match.apps.milk.repo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.match.apps.order.calculator.product.FileProductSource;
import com.match.apps.order.calculator.product.Product;
import com.match.apps.order.calculator.product.ProductSource;

public class FileSourceTest {

    @Test
    public void fileSourceTest() {
        ProductSource source = new FileProductSource("conf", "products.txt");
        List<Product> products = source.loadProducts();

        Assert.assertFalse(products.isEmpty());
    }

}
