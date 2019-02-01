package com.match.apps.order.calculator.product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation Class of {@link ProductSource}
 * 
 * Reads the products from a txt file
 * 
 * @author kingma
 *
 */
public class FileProductSource implements ProductSource {

    private String confDirName;
    private String productFileName;

    public FileProductSource(String confDirName, String productFileName) {
        this.confDirName = confDirName;
        this.productFileName = productFileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        File productsFile = new File(confDirName + File.separator + productFileName);
        try (InputStream fis = new FileInputStream(productsFile)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                products.add(convertProduct(line));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return products;
    }

    private Product convertProduct(String line) {
        String[] parts = line.split(":");
        Product product = new Product();
        product.setName(parts[0]);
        product.setPrice(Double.parseDouble(parts[1]));
        product.setActive(Boolean.parseBoolean(parts[2]));

        return product;
    }

}
