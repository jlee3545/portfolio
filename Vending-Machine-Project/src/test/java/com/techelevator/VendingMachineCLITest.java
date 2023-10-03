package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendingMachineCLITest {
    VendingMachineCLI vendingMachineCLI;

    @Before
    public void setUp() {
        vendingMachineCLI =new VendingMachineCLI();
    }

    @Test
    public void changeCounter() {
        String actualMessage = vendingMachineCLI.changeCounter(5.25);
        String expectedMessage = "Your change is: 21 quarters, 0 dimes, 0 nickels, and 0 pennies!";
        assertEquals("Change not calculated correctly.",expectedMessage,actualMessage);

    }

    @Test
    public void incorrectChange(){
        String actualMessage = vendingMachineCLI.changeCounter(5.25);
        String incorrectResponse = "Your change is: 20 quarters, 2 dimes, 0 nickels, and 5 pennies!";
        boolean shouldBeFalse = actualMessage.equals(incorrectResponse);
        assertFalse("Check the logic in the change calculation.",shouldBeFalse);
    }

    @Test
    public void noChangeZeroCheck(){
        String actualMessage = vendingMachineCLI.changeCounter(0.00);
        String incorrectResponse = "Your change is: 0 quarters, 0 dimes, 0 nickels, and 0 pennies!";
        boolean shouldBeFalse = actualMessage.equals(incorrectResponse);
        assertFalse("Zero should not display a message.",shouldBeFalse);
    }
}