/**
 * Created by tonycheng on 5/17/17.
 */
import static org.junit.Assert.*;
import org.junit.Test;
public class testing {
    @Test
    public void testWinners() {
        boolean result;
        board game = new board();
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game.accessBoard()[i][j] = count;
                count += 1;
            }
        }
        result = game.checkGameOver();
        assertTrue(result);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                game.accessBoard()[i][j] = 2;
            }
        }
        result = game.checkGameOver();
        assertFalse(result);
    }
    @Test
    public void testAddRandomly() {
        board game = new board();
        game.printBoard();
        game.left();
        game.printBoard();
//        System.out.println(game.numberEmpty);
    }
}
