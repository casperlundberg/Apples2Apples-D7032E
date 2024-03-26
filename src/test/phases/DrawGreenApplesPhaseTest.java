package test.phases;

import game.GameState;
import game.phases.DrawGreenApplePhase;
import game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DrawGreenApplesPhaseTest {

    @Test
    public void testExecuteOnClientAsJudge() {
        Player player = new Player("Player 1");

        GameState state = new GameState();
        state.addPlayer(player);

        DrawGreenApplePhase drawGreenApplePhase = new DrawGreenApplePhase();
        drawGreenApplePhase.nextJudge(state);
        drawGreenApplePhase.nextGreenApple(state);

        player = drawGreenApplePhase.executeOnClient(player);
        assertTrue(player.isJudge());
    }

    @Test
    public void testExecuteOnClientAsNotJudge() {
        Player player = new Player("Player 1");
        Player player2 = new Player("Player 2");

        GameState state = new GameState();
        state.addPlayer(player);

        DrawGreenApplePhase drawGreenApplePhase = new DrawGreenApplePhase();
        drawGreenApplePhase.nextJudge(state);
        drawGreenApplePhase.nextGreenApple(state);

        state.addPlayer(player2);

        player2 = drawGreenApplePhase.executeOnClient(player2);
        assertFalse(player2.isJudge());
    }
}
