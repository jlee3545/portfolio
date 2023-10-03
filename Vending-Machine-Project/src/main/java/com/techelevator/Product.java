package com.techelevator;

public abstract class Product {

    private String name;
    private int productCount;
    private  double price;
    private String message;
    private String slotIdentifier;

    public Product(String slotIdentifier,String name,double price,int productCount){
        this.slotIdentifier = slotIdentifier;
        this.name = name;
        this.price = price;
        this.productCount = productCount; ;

    }

    public Product(){

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public int getProductCount() {
        return productCount;
    }

    public double getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }
}
