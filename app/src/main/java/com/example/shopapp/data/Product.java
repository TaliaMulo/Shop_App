package com.example.shopapp.data;

public class Product {
    private String nameProduct;
    private String amount;
    private String key;

    public Product(){}
    public Product(String nameProduct, String amount) {
        this.nameProduct = nameProduct;
        this.amount = amount;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
