package com.match.apps.order.calculator.product;

public class Product {

    private String name;
    private Double price;
    private Boolean active;

    public Product(String name, Double price, Boolean active) {
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        StringBuilder sbr = new StringBuilder();
        sbr.append("Product:");
        sbr.append(name);
        sbr.append(" [Price:");
        sbr.append(price);
        sbr.append(", Active:");
        sbr.append(active);
        sbr.append("]");
        return sbr.toString();
    }
}
