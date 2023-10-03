import application.FileUtilities;
import application.Hangman;

import java.io.FileNotFoundException;
import java.io.IOException;


public class HangmanCLI {
    public static void main(String[] args) {

        try {
            new Hangman().run();
        } catch (NullPointerException e) {
            System.out.println("Something went wrong");;
        }
    }
}
