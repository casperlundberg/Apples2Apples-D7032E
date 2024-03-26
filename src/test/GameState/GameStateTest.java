package test.GameState;

import game.GameState;
import game.apples.RedApple;
import game.models.PlayerPlayedRedAppleModel;
import game.phases.DrawGreenApplePhase;
import game.phases.SetupPhase;
import game.phases.SubmitRedApplePhase;
import game.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class GameStateTest {

    @Test
    public void testGameState() {
        GameState state = new GameState();
        assertNotNull(state);
    }

    @Test
    public void testGetPlayers() {
        GameState state = new GameState();
        state.addPlayer(new Player("Player 1"));
        state.addPlayer(new Player("Player 2"));
        assertEquals(2, state.getPlayers().size());
    }

    @Test
    public void testGetPlayerById() {
        GameState state = new GameState();
        Player player = new Player("Player 1");
        state.addPlayer(player);
        assertEquals(player, state.getPlayerById(player.getPlayerId()));
    }

    @Test
    public void testGetPlayerByIdWithInvalidId() {
        GameState state = new GameState();
        assertNull(state.getPlayerById(1));
    }

    @Test
    public void testGetCurrentGreenApple() {
        GameState state = new GameState();
        assertNotNull(state.getCurrentGreenApple());
    }

    @Test
    public void testAddGreenAppleToDeck() {
        GameState state = new GameState();
        state.addGreenAppleToDeck(state.getCurrentGreenApple());
        assertNotNull(state);
    }

    @Test
    public void testFillPlayersHands() {
        GameState state = new GameState();
        state.addPlayer(new Player("Player 1"));
        state.addPlayer(new Player("Player 2"));
        state.fillPlayersHands();
        for (Player player : state.getPlayers()) {
            assertEquals(7, player.getHand().size());
        }
    }

    @Test
    public void testSubmittedRedAppleModel() {
        GameState state = new GameState();

        Player player = new Player("Player 1");
        player.setPlayerId(1);
        state.addPlayer(player);

        RedApple toBeSubmitted = player.getHand().get(0);

        ArrayList<RedApple> redApples = new ArrayList<>();
        redApples.add(toBeSubmitted);

        PlayerPlayedRedAppleModel model = new PlayerPlayedRedAppleModel(player.getPlayerId(), toBeSubmitted);
        state.PlayerPlayedRedApple(model);

        // current hand size is 7 in Gamestate.java Constants
        assertEquals(6, player.getHand().size());
        assertEquals(1, state.getSubmittedRedAppleModel().size());

        // check that the red apples shown to judge is the same as the submitted red apples
        assertEquals(redApples, state.getRedApplesToBeJudged());

        // check that the round winner is not null, aka found in gamestate
        Player roundWinner = state.getRoundWinner(toBeSubmitted);
        assertNotNull(roundWinner);
    }

    @Test
    public void testCurrentPhase() {
        GameState state = new GameState();
        assertNotNull(state.getCurrentPhase());
        assertInstanceOf(SetupPhase.class, state.getCurrentPhase());
    }

    @Test
    public void testNextPhase() {
        GameState state = new GameState();
        state.nextPhase();
        assertNotNull(state.getCurrentPhase());
        assertInstanceOf(DrawGreenApplePhase.class, state.getCurrentPhase());

        state.nextPhase();
        state.nextPhase();
        state.nextPhase();
        state.nextPhase();
        state.nextPhase();
        state.nextPhase();

        assertNotNull(state.getCurrentPhase());
        assertInstanceOf(SubmitRedApplePhase.class, state.getCurrentPhase());
    }

    @Test
    public void testNextJudge() {
        GameState state = new GameState();
        Player player = new Player("Player 1");
        player.setPlayerId(1);
        Player player2 = new Player("Player 2");
        player.setPlayerId(2);
        state.addPlayer(player);


        state.nextJudge();
        assertNotNull(state.getJudge());
        assertEquals(player, state.getJudge());

        state.addPlayer(player2);

        // since first player is made judge before player2 is added we know that player2 will be judge when a new one is selected
        state.nextJudge();
        assertNotNull(state.getJudge());
        assertEquals(player2, state.getJudge());

        state.nextJudge();
        assertNotNull(state.getJudge());
        assertEquals(player, state.getJudge());
    }

    @Test
    public void testSetUpScoring() {
        GameState state = new GameState();

        state.setAmountOfHumanPlayers(1);
        state.addPlayer(new Player("Player 1"));

        state.setAmountOfBots(1);
        state.setAmountOfHumanPlayers(1);
        state.addBots();
        state.setupScoring();
        assertEquals(4, state.getPlayers().size());

        state.setAmountOfBots(4);
        state.addBots();
        state.setupScoring();
        assertEquals(5, state.getPlayers().size());

        state.setAmountOfBots(8);
        state.addBots();
        state.setupScoring();
        assertEquals(9, state.getPlayers().size());
    }

    @Test
    public void testGetInvalidPlayer() {
        GameState state = new GameState();
        assertNull(state.getPlayerById(1));
    }

    @Test
    public void testGetInvalidJudge() {
        GameState state = new GameState();
        state.addPlayer(new Player("Player 1"));
        assertNull(state.getJudge());
    }

    @Test
    public void getInvalidRoundWinner() {
        GameState state = new GameState();
        assertNull(state.getRoundWinner(new RedApple("Test")));
    }

    @Test
    public void testClearSubmittedRedApples() {
        GameState state = new GameState();

        Player player = new Player("Player 1");
        player.setPlayerId(1);
        state.addPlayer(player);

        RedApple toBeSubmitted = player.getHand().get(0);

        PlayerPlayedRedAppleModel model = new PlayerPlayedRedAppleModel(player.getPlayerId(), toBeSubmitted);
        state.PlayerPlayedRedApple(model);

        state.clearPlayersWhoSubmittedRedApples();
        assertEquals(6, player.getHand().size());
        assertEquals(0, state.getSubmittedRedAppleModel().size());
    }

    @Test
    public void testAddGreenAppleToPlayer() {
        GameState state = new GameState();
        Player player = new Player("Player 1");
        state.addPlayer(player);
        state.addGreenAppleToPlayer(player);
        assertEquals(1, state.getPlayers().get(0).getScore());
    }

    @Test
    public void testAddGreenAppleToInvalidPlayer() {
        GameState state = new GameState();
        Player player = new Player("Player 1");

        // Player is not added to the gamestate
        // state.addPlayer(player);

        // Dummy player
        Player player2 = new Player("Player 2");
        state.addPlayer(player2);

        // Player 2 is the only one in the gamestate and therefore the only one that can have a green apple added to it
        // The below line does not change the score of player 2
        state.addGreenAppleToPlayer(player);
        assertEquals(0, state.getPlayers().get(0).getScore());
    }

    @Test
    public void testIsGameEnded() {
        GameState state = new GameState();
        assertFalse(state.isGameEnded());
    }

    @Test
    public void testInvalidGetGameWinner() {
        GameState state = new GameState();
        assertNull(state.getGameWinner());
    }

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

        assertTrue(state.isGameEnded());
    }

    @Test
    public void testInvalidPlayerThatPlayedRedApple() {
        GameState state = new GameState();
        Player player = new Player("Player 1");
        state.addPlayer(player);
        assertFalse(state.playerThatPlayedRedApple(player));
    }

    @Test
    public void testPlayerThatPlayedRedApple() {
        GameState state = new GameState();
        Player player = new Player("Player 1");
        state.addPlayer(player);

        RedApple toBeSubmitted = player.getHand().get(0);

        PlayerPlayedRedAppleModel model = new PlayerPlayedRedAppleModel(player.getPlayerId(), toBeSubmitted);
        state.PlayerPlayedRedApple(model);

        assertTrue(state.playerThatPlayedRedApple(player));
    }

    @Test
    public void testAllPlayersJoined() {
        GameState state = new GameState();
        assertFalse(state.allPlayersJoined());
    }

    @Test
    public void testPrintPlayers() {
        GameState state = new GameState();
        state.addPlayer(new Player("Player 1"));
        state.addPlayer(new Player("Player 2"));
        state.printPlayerData();
    }
}
