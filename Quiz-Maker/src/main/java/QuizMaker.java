
import dao.Leaderboard;
import dao.NeonDbLeaderboard;
import dao.Player;
import quiz.QuizQuestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * QuizMaker (with leaderboard database feature)
 *
 * 1. Add your connection string to the NeonDbLeaderboard class (see README.md)
 * 2. Run QuizMaker and verify in the console the leaderboard is displayed
 * 3. Complete the askQuizQuestions method below.
 * 4. Add more questions in "test_quiz.txt"
 * 5. Test game and do challenge features in the README.md
 */
public class QuizMaker {

    private final Scanner userInput = new Scanner(System.in);
    private final File quizFileObject = new File("test_quiz.txt");
    private final Leaderboard leaderboard = new NeonDbLeaderboard();

    public static void main(String[] args) {
        QuizMaker quizMaker = new QuizMaker();
        quizMaker.run();
    }

    public void run() {

        displayLeaderboard();

//        List<QuizQuestion> quizQuestions = loadQuiz();

        int numberCorrect = askQuizQuestions();

        if( numberCorrect > 0){
            askAddLeaderboard(numberCorrect);
        }
    }

    /**
     * Ask all passed in questions
     * @param
     * @return number of correct responses
     */
    private int askQuizQuestions() {

//        System.out.println("Enter the fully qualified name of the file to read in for quiz questions");
//        String fileNameInput = userInput.nextLine();
//
//        File file = new File(fileNameInput);
        int correct = 0;
        int questionsAsked = 0;

        try {
            Scanner fileScanner = new Scanner(quizFileObject);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] answers = line.split("\\|");
                String question = answers[0];
                questionsAsked++;
                System.out.println(question);
                System.out.println("1) " + answers[1].replace("*", ""));
                System.out.println("2) " + answers[2].replace("*", ""));
                System.out.println("3) " + answers[3].replace("*", ""));
                System.out.println("4) " + answers[4].replace("*", ""));
                System.out.println();
                System.out.println("Type a number to choose your answer:");
                Scanner answerInput = new Scanner(System.in);
                String numberInput = answerInput.nextLine();
                int number = Integer.parseInt(String.valueOf(numberInput));
                if (answers[number].contains("*")) {
                    System.out.println("Your answer: " + number);
                    System.out.println("Correct!");
                    System.out.println();
                    correct++;
                } else {
                    System.out.println("Your answer: " + number);
                    System.out.println("Wrong :(");
                    System.out.println();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not working");
        } catch (NumberFormatException e) {
            System.out.println("Number not correct format");
        }
        System.out.println("You got " + correct + " answer(s) correct out of the " + questionsAsked + " questions asked.");

        return correct;
    }

    /**
     * Display leaderboard
     */
    private void displayLeaderboard(){
        List<Player> players = this.leaderboard.getLeaderboard();

        System.out.println("Leaderboard");
        System.out.println("----------------------");

        for(int i = 0; i < players.size(); i++){
            Player eachPlayer = players.get(i);

            System.out.println((i + 1) + ". " + eachPlayer.getName() + " - " + eachPlayer.getScore());
        }
    }

    /**
     * Ask if user wants to add their name and score to the leaderboard
     * @param numberCorrect
     */
    private void askAddLeaderboard(int numberCorrect) {
        System.out.println("Add name to leaderboard? (y/n)");
        String userAnswer = userInput.nextLine();

        if(userAnswer.equalsIgnoreCase("y")) {
            System.out.println("What is your name?");
            String name = userInput.nextLine();
            leaderboard.addLeaderboard(new Player(name, numberCorrect));

            displayLeaderboard();
        }
    }

    /**
     * Load questions from source
     * @return list of quiz questions
     */
    private List<QuizQuestion> loadQuiz() {
        // Read through the input file, and build of list of questions, one line at a time.
        List<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();

        try (Scanner fileScanner = new Scanner(quizFileObject)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                quizQuestions.add(new QuizQuestion(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return quizQuestions;
    }
}
