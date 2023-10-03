package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
public class VendingMachineCLI{
    /////////////////////Files
    private File vendingFile = new File("main.csv");
    private File justDesserts = new File("dessert.csv");
    private File justDrinks  = new File("drinks.csv");
    private File file = new File("log.txt");
    /////////////////////Files

    /////////////////////Scanner/Maps/Sets/Classes
    private Logger logger = new Logger(file);
    private VendingGraphics vendingGraphics = new VendingGraphics();
    private Scanner userInput = new Scanner(System.in);
    private Map<String, Product> mapOfProducts = new HashMap<>();
    private Map<String, int[]> salesReport= new HashMap<>();
    private Set<String> saleKeys = salesReport.keySet();
    /////////////////////Scanner/Maps/Sets/Classes

    //////////////////Primitive/Strings
    private boolean toRun = true;
    private int vendCount = 1;
    private double cashOnHand = 0;
    private double totalItemCost = 0.0;
    private String type = "";
    /////////////////Primitive/Strings



//////////RUN THE PROGRAM
    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();

    }





    ////runs the initial menu and banner, then starts the vending machine.
    public void run() {
        vendingGraphics.platformBanner();

        vendingGraphics.fileMenu();

        String fileChoice = userInput.nextLine();

        File usedFile= productMenuChoice(fileChoice);

        runSnackAttackVending(usedFile);
    }










///Displays the main menu, and calls either a method to display the total current inventory-
// or a method to enter the purchase menu- or a method to create the secret sales report.
// Remaining option kills the operation.
    public void runSnackAttackVending(File usedFile){
        while (toRun) {
            vendingGraphics.mainMenu();
            String choice = userInput.nextLine();
            if (choice.equals("1")) {
                ///Prints the inventory from the file, through a map.
                printVendingInventory(usedFile, mapOfProducts);
            } else if (choice.equals("2")) {

                purchaseMenu(usedFile);

            } else if (choice.equals("3")) {
                System.out.println("You have chosen to exit the Vending Machine. Have a nice day!");
                toRun = false;
                vendingGraphics.platformBanner();
            } else if (choice.equals("4")) {

                secretSalesReportCreated();

            }
        }
    }


    //Run before any other method(sans a graphic) prompts the user to choose a vending menu,
    // and then creates the necessary maps for the background processes. Fall back is to the default menu!
    public File productMenuChoice(String fileChoice){

        if(fileChoice.equals("1")){

            mapCreator(vendingFile, mapOfProducts);
            salesReportMapCreator(vendingFile,salesReport);
            return vendingFile;

        }else if(fileChoice.equals("2")){

            mapCreator(justDesserts, mapOfProducts);
            salesReportMapCreator(justDesserts,salesReport);
            return justDesserts;

        }else if(fileChoice.equals("3")){

            mapCreator(justDrinks, mapOfProducts);
            salesReportMapCreator(justDrinks,salesReport);
            return justDrinks;

        }else{
            System.out.println("Not a valid choice. Proceeding with default menu.");
            mapCreator(vendingFile, mapOfProducts);
            salesReportMapCreator(vendingFile,salesReport);
            return vendingFile;

        }
    }


//Runs the purchase menu, and prompts the user to choose between adding money,
//selecting an item, or leaving the purchase menu-which dispenses the received money in coins
    public void purchaseMenu(File usedFile){
        boolean stay = true;
        while (stay) {
            System.out.println("Current money provided: $" + String.format("%.2f", cashOnHand));
            vendingGraphics.purchaseMenu();
            String userChoice = userInput.nextLine();
            if (userChoice.equals("1")) {
                vendingGraphics.moneyBanner();
                recieveMoney();
            } else if (userChoice.equals("2")) {

                printVendingInventory(usedFile, mapOfProducts);
                itemSelection();

            } else if (userChoice.equals("3")) {
                endTransaction();
                break;
            }
        }
    }
//Dispenses change to the user, and pops the user back to the main run menu. Appends the current item log.
    public void endTransaction(){
        String changeAmount = "GIVE CHANGE: $" + String.format("%.2f", cashOnHand) + " CUSTOMER MONEY TOTAL: $0.00";
        logger.write(changeAmount);
        String message = changeCounter(cashOnHand);
        System.out.println(message);
        cashOnHand=0;
    }
//One of the most important methods in the program. Creates the slotChoice variable,
// and proceeds based on product availability.
    public void itemSelection(){
        System.out.println("Please enter a slot choice: ");
        String slotChoice = userInput.nextLine().toUpperCase();
        if (mapOfProducts.containsKey(slotChoice)) {

            if (mapOfProducts.get(slotChoice).getProductCount() > 0) {

                itemPurchased(slotChoice);
            } else {
                System.out.println("Sorry, we're sold out of that item! Pick again?");
                vendingGraphics.soldOut();
            }
        }
    }

    //processes the correlated slotChoice item based on the current funds of the user,
    // and based on whether the user is eligible for a discount. Appends the item log.
    public void itemPurchased(String slotChoice){
        if (vendCount % 2 == 0 && vendCount!=0) {
            boGoDoSettings(slotChoice);
        }
        if (cashOnHand < mapOfProducts.get(slotChoice).getPrice()) {
            System.out.println("Uh Oh! You cant afford that!");
            vendingGraphics.angryNoAfford();
        } else {
            vendCount++;
            System.out.println("You chose the " + mapOfProducts.get(slotChoice).getName() + "! It costs: " + mapOfProducts.get(slotChoice).getPrice() + "\n" + mapOfProducts.get(slotChoice).getMessage());
            itemProcessed(slotChoice);

            appendItemLog(slotChoice);
        }
    }

    //Affects the background values, and updates the map.Customer money, received money, and item counts.
    public void itemProcessed(String slotChoice){
        cashOnHand -= mapOfProducts.get(slotChoice).getPrice();
        totalItemCost += mapOfProducts.get(slotChoice).getPrice();
        int currentCount = mapOfProducts.get(slotChoice).getProductCount();
        mapOfProducts.get(slotChoice).setProductCount(currentCount - 1);
    }

    //Modifies the sales report to account for the purchase, and logs the purchase itself.
    public void appendItemLog(String slotChoice){
        (salesReport.get(mapOfProducts.get(slotChoice).getName()))[0] = (salesReport.get(mapOfProducts.get(slotChoice).getName()))[0] +1;

        String productTransaction =  mapOfProducts.get(slotChoice).getName() + " " + mapOfProducts.get(slotChoice).getSlotIdentifier() + " ITEM PRICE: $" + mapOfProducts.get(slotChoice).getPrice() + " CUSTOMER MONEY TOTAL: $" + String.format("%.2f",cashOnHand);
        logger.write(productTransaction);
    }

///Receives money from the user, and rounds it down to the current whole dollar value. Eats the change :)
    //Appends the item log.
    public void recieveMoney(){
        System.out.println("Please enter the amount of money in whole dollars. All given change will be eaten by the machine as a donation. ");
        String cashAdded = userInput.nextLine();
        try {
            double preRoundedAmount = Double.parseDouble(cashAdded);
            double roundedAmount = Math.round(preRoundedAmount);
            if (roundedAmount > preRoundedAmount) {
                roundedAmount--;
            }
            cashOnHand += roundedAmount;
            totalItemCost += preRoundedAmount - roundedAmount;
            String message = "FEED MONEY: $" + cashAdded + " CUSTOMER MONEY TOTAL: $" + String.format("%.2f", cashOnHand);
            logger.write(message);
        }catch (NumberFormatException e){
            System.out.println("Please enter the dollar amount numerically.");
        }
    }

    ///processes the bogodo settings, giving a dollar to the user, and modifying the sales report map.
    public void boGoDoSettings(String slotChoice){
        cashOnHand += 1;
        (salesReport.get(mapOfProducts.get(slotChoice).getName()))[1] = (salesReport.get(mapOfProducts.get(slotChoice).getName()))[1] +1;

        System.out.println("Woo! It's August! BOGODO for you! Enjoy one dollar off your choice!");
        vendingGraphics.bogodo();
    }


    ///creates the sales report using a date format, makes sure its available, and then creates a sales report
    ///from a map and a set, by iterating through the set. Can create one report per minute without override.
    public void secretSalesReportCreated(){
        String fileSuffix = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        File folder = new File("SalesReports");
        folder.mkdir();
        if(!folder.exists()){
            folder.mkdir();
        }
        File saleFile = new File(folder,fileSuffix + "salesreport.txt");

        try {
            saleFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Problem creating new file");;
        }

        Logger saleLogger = new Logger(saleFile);
        for(String key: saleKeys){
            String message = salesReport.get(key)[0]+ "|" + salesReport.get(key)[1];
            saleLogger.addCreateSalesReport(key, message);
        }
        saleLogger.addCreateSalesReport("End Of Current Session Sales ", " $" + totalItemCost + "\n");
    }

///prints the vending inventory directly from the file
    // prints in alph and numeric order
    // requires the file, and the map to pull item counts from
    public void printVendingInventory(File file, Map<String, Product> map) {
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] productInfo = fileScanner.nextLine().split("\\,");

                System.out.println(productInfo[0] + " is: " + productInfo[1] + ", Costs: $" + productInfo[2] + " and is a " + productInfo[3] + "!");
                System.out.println("There are " + map.get(productInfo[0]).getProductCount() + " " + productInfo[1] + " left!");
                if (map.get(productInfo[0]).getProductCount() == 0) {
                    System.out.println("!SOLD OUT!\n");
                    vendingGraphics.soldOut();
                } else {
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Oops, something has gone wrong!");
        }
    }

//creates the product map by reading from the chosen file. Creates the items as class objects, but all are stored as parent class products.
    public void mapCreator (File file, Map <String,Product> mapOfProducts){

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] productInfo = fileScanner.nextLine().split("\\,");
                String productID = String.valueOf(productInfo[0]);
                String name = productInfo[1];
                double price = Double.parseDouble(productInfo[2]);
                type = productInfo[3];
                if (type.equals("Candy")) {
                    Candy candy = new Candy(productID, name, price);
                    mapOfProducts.put(productID, candy);
                } else if (type.equals("Drink")) {
                    Drink drink = new Drink(productID, name, price);
                    mapOfProducts.put(productID, drink);
                } else if (type.equals("Munchy")) {
                    Munchy munchy = new Munchy(productID, name, price);
                    mapOfProducts.put(productID, munchy);
                } else if (type.equals("Gum")) {
                    Gum gum = new Gum(productID, name, price);
                    mapOfProducts.put(productID, gum);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Oops, something has gone wrong!");
        }
    }


    //creates the sales report from the file, with all values set to zero. Values appendable
    //due to the nature of an array
    public void salesReportMapCreator(File file, Map<String, int[]> map){
        try(Scanner fileScanner = new Scanner(file)){
            while(fileScanner.hasNextLine()){
                String[] productInfo = fileScanner.nextLine().split("\\,");
                String name = productInfo[1];
                int[] salesReport = {0,0};
                map.put(name, salesReport);
            }

        }catch(FileNotFoundException e){
            System.out.println("Error. File not usable.");
        }
    }



    //the first method we pulled out. runs the change function in a series of logic
    //decisions from largest to smallest. Prints the change amount to the user.
    public String changeCounter (double cashOnHand){
        String message = "";
        if (cashOnHand != 0) {
            int quarters = 0;
            int dimes = 0;
            int nickels = 0;
            int pennies = 0;

            if (cashOnHand / 0.25 != 0) {
                quarters = (int) (cashOnHand / 0.25);
                cashOnHand -= quarters * 0.25;
            }
            if (cashOnHand / .1 != 0) {
                dimes = (int) (cashOnHand / 0.1);
                cashOnHand -= dimes * 0.1;
            }
            if (cashOnHand / 0.05 != 0) {
                nickels = (int) (cashOnHand / 0.05);
                cashOnHand -= nickels * 0.05;
            }
            if (cashOnHand / 0.01 != 0) {
                pennies = (int) (cashOnHand / 0.01);
                if (cashOnHand % 0.01 > 0) {
                    pennies++;
                }
                if( pennies == 5){
                    nickels++;
                    pennies = 0;
                }
            } message = "Your change is: " + quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels, and " + pennies + " pennies!";
        } return message;
    }
}