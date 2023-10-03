package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class VendingMachineCLIDATATest {
    VendingMachineCLI vendingMachineCLI;
    Map<String, Product> mapOfProducts = new HashMap<>();

    @Before
    public void build(){
        vendingMachineCLI= new VendingMachineCLI();
        File file = new File("main.csv");
        vendingMachineCLI.mapCreator(file,mapOfProducts);
    }

    @Test
    public void mapCreator() {

        String actual = mapOfProducts.get("B2").getName();
        String expected = "Papsi";
        assertEquals("Map did not populate correctly.",expected,actual);

    }

    @Test
    public void didNotSplit(){
        boolean stringCheck = mapOfProducts.containsKey("A1,U-Chews,1.65,Gum");
        assertFalse("Check the separation of values. Expected result: A1, actual: A1,U-Chews,1.65,Gum" ,stringCheck);
    }

    @Test
    public void createAndGetClassMessages(){
        String candy = mapOfProducts.get("A3").getMessage();
        assertEquals("Yummy Yummy, So Sweet!", candy);
        String munchy = mapOfProducts.get("C4").getMessage();
        assertEquals("Crunch Crunch, Yum!", munchy);
        String drink = mapOfProducts.get("C2").getMessage();
        assertEquals("Glug Glug, Yum!", drink);
    }

    @Test
    public void testCorrectClassMessage(){
        String candy = mapOfProducts.get("A3").getMessage();
        boolean wrongClassMessage = candy.equals("Crunch Crunch, Yum!") || candy.equals("Glug Glug, Yum!") || candy.equals("Chew Chew, Yum!");
        assertFalse("Displayed incorrect class message. Expected: Yummy Yummy, So Sweet!",wrongClassMessage);
    }

}