package com.techelevator;

public class Drink extends Product {
    public Drink(String slotIdentifier, String name, double price) {
        super(slotIdentifier, name, price, 5);
        setMessage("Glug Glug, Yum!");

    }

}
