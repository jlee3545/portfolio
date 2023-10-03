package com.techelevator;

public class Gum extends Product{
    public Gum(String slotIdentifier, String name, double price) {
        super(slotIdentifier, name, price, 5);
        setMessage("Chew Chew, Yum!");
    }
}
