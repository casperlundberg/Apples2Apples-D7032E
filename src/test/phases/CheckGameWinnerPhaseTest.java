package test.phases;

import game.GameState;
import game.phases.CheckGameWinnerPhase;
import game.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
public class CheckGameWinnerPhaseTest {

    @Test
    public void testGetGameWinner() {
        GameState state = new GameState();
        state.setAmountOfBots(4);
        state.setAmountOfHumanPlayers(1);
        state.addBots();

        Player player = new Player("Player 1");
        state.addPlayer(player);
        state.setupScoring();

        for (int i = 0; i < 7; i++) {
            state.addGreenAppleToPlayer(player);
            if (state.getGameWinner() != null) {
                break;
            }
        }


        CheckGameWinnerPhase checkGameWinnerPhase = new CheckGameWinnerPhase();
        Player winner = checkGameWinnerPhase.getGameWinner(state);
        assertEquals(player, winner);

    }

    @Test
    public void testGameEndedNotWinner() throws IOException {
        Player player = new Player("Player 1");
        player.setSocket(new Socket());

        CheckGameWinnerPhase checkGameWinnerPhase = new CheckGameWinnerPhase();
        checkGameWinnerPhase.setGameEnded(true);

        player = checkGameWinnerPhase.executeOnClient(player);

        assertTrue(player.getDonePlaying());
    }

    @Test
    public void testGameEndedWinner() throws IOException {
        Player player = new Player("Player 1");
        player.setSocket(new Socket());


        CheckGameWinnerPhase checkGameWinnerPhase = new CheckGameWinnerPhase();
        checkGameWinnerPhase.setGameEnded(true);
        checkGameWinnerPhase.setWinnerName(player.getName());
        checkGameWinnerPhase.setWinnerId(player.getPlayerId());

        player = checkGameWinnerPhase.executeOnClient(player);

        assertTrue(player.getDonePlaying());
    }

    @Test
    public void testGameNotEnded() throws IOException {
        Player player = new Player("Player 1");
        player.setSocket(new Socket());

        CheckGameWinnerPhase checkGameWinnerPhase = new CheckGameWinnerPhase();
        checkGameWinnerPhase.setGameEnded(false);

        player = checkGameWinnerPhase.executeOnClient(player);

        assertFalse(player.getDonePlaying());
    }
}
