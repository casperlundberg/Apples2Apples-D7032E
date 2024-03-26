package test.phases;

import game.GameState;
import game.apples.RedApple;
import game.phases.SetupPhase;
import game.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetupPhaseTest {
    @Test
    public void testExecuteOnClientWithNullHand() {
        Player player = new Player("Player 1");
        SetupPhase setupPhase = new SetupPhase();

        player = setupPhase.executeOnClient(player);
        assertNull(player.getHand());
    }

    @Test
    public void testExecuteOnClientWithHand() {
        Player player = new Player("Player 1");

        ArrayList<RedApple> hand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            hand.add(new RedApple("Red Apple " + i));
        }

        SetupPhase setupPhase = new SetupPhase();
        setupPhase.setNewHand(hand);
        setupPhase.setPlayerId(1);

        player = setupPhase.executeOnClient(player);

        assertEquals(hand, player.getHand());
        assertEquals(player.getPlayerId(), 1);
    }

    @Test
    public void testFillPlayersHands() {
        GameState state = new GameState();
        state.addPlayer(new Player("Player 1"));
        state.addPlayer(new Player("Player 2"));

        SetupPhase setupPhase = new SetupPhase();
        state = setupPhase.fillPlayersHands(state);
        for (Player player : state.getPlayers()) {
            assertNotNull(player.getHand());
        }
    }
}
