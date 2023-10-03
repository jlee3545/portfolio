package ui;

public class UserOutput {

    /*
     * https://patorjk.com/software/taag
     */
    public void displayWelcome(){
        System.out.print(ConsoleColors.YELLOW_BOLD);
        System.out.println(" o         o                                                                               ");
        System.out.println("<|>       <|>                                                                              ");
        System.out.println("< >       < >                                                                              ");
        System.out.println(" |         |      o__ __o/  \\o__ __o     o__ __o/  \\o__ __o__ __o      o__ __o/  \\o__ __o  ");
        System.out.println(" o__/_ _\\__o     /v     |    |     |>   /v     |    |     |     |>    /v     |    |     |> ");
        System.out.println(" |         |    />     / \\  / \\   / \\  />     / \\  / \\   / \\   / \\   />     / \\  / \\   / \\ ");
        System.out.println("<o>       <o>   \\      \\o/  \\o/   \\o/  \\      \\o/  \\o/   \\o/   \\o/   \\      \\o/  \\o/   \\o/ ");
        System.out.println(" |         |     o      |    |     |    o      |    |     |     |     o      |    |     |  ");
        System.out.println("/ \\       / \\    <\\__  / \\  / \\   / \\   <\\__  < >  / \\   / \\   / \\    <\\__  / \\  / \\   / \\ ");
        System.out.println("                                               |                                           ");
        System.out.println("                                       o__     o                                           ");
        System.out.println("                                       <\\__ __/>                                           ");
        System.out.println();
        System.out.print(ConsoleColors.RESET);
        System.out.println("*** Welcome to Hangman Classic ***");
    }
}
