/**
 * Created by tonycheng on 5/14/17.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class main {
    private static final String EXIT   = "exit";
    private static final String PROMPT = "> ";
    private static int highestScore = 0;
    private static board currentGame = new board();
    private static final String startingText = "Welcome to 2584! This is a game very similar to the popular mobile game 2048, just with the Fibonnaci sequence.\n Enter 'help' for a list of commands to get you started";
    private static String commands = "Currently supported commands: \na - shift board left\ns -shift board down\nd - shift board right" +
            "\nw - shift board up\nnew game - start a new game\nscore - get your current score\nhighest score - get the highest score";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        board game = new board();
        System.out.print(PROMPT);
        System.out.println(startingText);
        String line = "";
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }

            if (!line.trim().isEmpty()) {
                if (line.equals("new game")) {
                    if (currentGame.getScore() > highestScore) {
                        highestScore = currentGame.getScore();
                    }
                    currentGame = new board();
                    currentGame.printBoard();
                } else if (line.equals("help")) {
                    System.out.println(commands);
                } else if (line.equals("score") || line.equals("highest score")) {
                    if (currentGame.getScore() > highestScore) {
                        highestScore = currentGame.getScore();
                    }
                    System.out.println("Your Score: " + currentGame.getScore());
                    System.out.println("Highest Score: " + highestScore);
                } else {
                    currentGame.transact(line);
                }
            }
            System.out.print(PROMPT);
        }

        in.close();
    }
}