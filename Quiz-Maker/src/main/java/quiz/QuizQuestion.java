package quiz;

public class QuizQuestion {

    private String question;
    private String[] answers;
    private String correctAnswer;

    /**
     * Parses the bar-delimited line into its constituent parts:
     *  question, answers, and correctAnswer
     *
     * @param line - A question and answer String in the following format:
     *               <question>|<answer-1>|<answer-2>|<correct-answer-3>*|<answer-4>
     *               What color is the sky?|yellow|blue*|green|red
     */
    public QuizQuestion(String line) {

        if ((line != null) && (!line.isEmpty())) {

            String[] parts = line.split("\\|");
            this.question = parts[0];
            this.answers = new String[parts.length-1];

            for(int i = 1; i < parts.length; i++) {
                String answer = parts[i].trim();

                if (answer.endsWith("*")) {
                    answer = answer.substring(0, answer.length() - 1);
                    this.correctAnswer = Integer.toString(i);
                }

                answers[i - 1] = answer;
            }
        }
    }

    /**
     * Returns a copy of the original answers,
     * so caller never gains access to the private array.
     *
     * @return
     */
    public String[] getAnswers() {
        return this.answers.clone();
    }

    public String getQuestion() {
        return this.question;
    }

    public boolean isCorrectAnswer(String selectedAnswer) {
        return this.correctAnswer.equals(selectedAnswer);
    }
}
