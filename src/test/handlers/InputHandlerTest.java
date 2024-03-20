package test;

import game.apples.RedApple;
import game.players.Player;
import handlers.InputHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputHandlerTest {
    private InputHandler inputHandler;
    private Player player;
    private ArrayList<RedApple> redApples;
    private final InputStream sysInBackup = System.in; // Backup System.in to restore it later

    @BeforeEach
    public void setUp() {
        inputHandler = new InputHandler();
        player = new Player("Test Player");
        redApples = new ArrayList<>();
        redApples.add(new RedApple("Apple 1"));
        redApples.add(new RedApple("Apple 2"));
        redApples.add(new RedApple("Apple 3"));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(sysInBackup); // Reset System.in to its original
    }

    @Test
    public void testBotChooseRedApple() {
        player.setBot(true);
        RedApple chosenApple = inputHandler.chooseRedApple(player, redApples);
        assertTrue(redApples.contains(chosenApple), "Chosen apple should be in the list of red apples");
    }

//    @Test
//    public void testPlayerChooseRedApple() {
//        provideInput("0\n");
//        RedApple chosenApple = inputHandler.chooseRedApple(player, redApples);
//        assertEquals("Apple 1", chosenApple.getContent(), "Chosen apple should be 'Apple 1'");
//    }
//
//    @Test
//    public void testGetPlayerNameInput() {
//        provideInput("Test Player\n");
//        String playerName = inputHandler.getPlayerNameInput();
//        assertEquals("Test Player", playerName, "Player name should be 'Test Player'");
//
//    }

    // Tests with input currently not working, maybe needs some mockup or framework that handles that.
    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}