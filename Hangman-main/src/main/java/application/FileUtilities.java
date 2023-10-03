package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;


public class FileUtilities {

    private int numOfWords = 0;

    private List<String> wordList = new ArrayList<>();
    public File thanksgivingList = new File("thanksgiving.txt");
    public File christmasList = new File("christmas.txt");
    public File normalMode = new File("defaultlist.txt");


    public static final String THANKSGIVING_FILE = "thanksgiving.txt";
    public static String themedList = "";


    public static String fileChooser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("~~~~~~~~~~~ WORD LISTS ~~~~~~~~~~~");
        System.out.println("~~~~~~~~~~~~ 1-Normal ~~~~~~~~~~~~");
        System.out.println("~~~~~~~~~ 2-Thanksgiving ~~~~~~~~~");
        System.out.println("~~~~~~~~~~~ 3-Christmas ~~~~~~~~~~");
        System.out.println("Please choose a word list: ");
        String fileChoice = scanner.nextLine();
            if(fileChoice.equals("1")){
                System.out.println("You have chosen: Normal");
                themedList = "defaultlist.txt";
            }else if (fileChoice.equals("2")) {
                System.out.println("You have chosen: Thanksgiving");
                themedList = "thanksgiving.txt";
            } else if (fileChoice.equals("3")) {
                System.out.println("You have chosen: Christmas");
                themedList = "christmas.txt";
            } else {
                System.out.println("Invalid Choice: Proceeding with default list");
                themedList = "defaultlist.txt";
            }

        return themedList;
    }


    public int getNumOfWords() {
        return numOfWords;
    }

    public List<String> getWordList() {
        return wordList;
    }

    /*
     * Use this to get a random word from the VOCAB_FILE
     */
    public String readRandomLineFromFile(File themedList) {
        try(Scanner wordReader = new Scanner(themedList)){
            while(wordReader.hasNext()){
                String word = wordReader.nextLine().toUpperCase();
                wordList.add(word);
                numOfWords++;
            }
        }catch (FileNotFoundException e){
            System.out.println("Problem reading file");
        }
        return wordList.get(getRandomNumber());
    }

    public int getRandomNumber(){
        return new Random().nextInt(getNumOfWords());
    }



}

