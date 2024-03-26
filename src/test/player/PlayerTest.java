package test.player;

import game.apples.GreenApple;
import game.apples.RedApple;
import game.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player("Test Player");
    }

    @Test
    public void testPlayer() {
        String expectedName = "Test Player";
        assertEquals(expectedName, player.getName());
        assertEquals(0, player.getScore());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void testPlayerIsBot() {
        String expectedName = "Test Bot";
        Player player = new Player(expectedName, true);

        assertEquals(expectedName, player.getName());
        assertTrue(player.isBot());
    }

    @Test
    public void testPlayerWithSocket() {
        String expectedName = "Test Player";
        Player player = new Player(expectedName, null);

        assertEquals(expectedName, player.getName());
        assertEquals(0, player.getScore());
        assertNull(player.getSocket());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void testGetPlayerId() {
        int id = player.getPlayerId();

        assertEquals(id, player.getPlayerId());
    }

    @Test
    public void testSetPlayerId() {
        int id = 1;
        player.setPlayerId(id);

        assertEquals(id, player.getPlayerId());
    }

    @Test
    public void testSetBot() {
        player.setBot(true);

        assertTrue(player.isBot());
    }

    @Test
    public void testDrawRedAppleUntilFullHand() {
        ArrayList<RedApple> redApples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            redApples.add(new RedApple("Apple " + i));
        }

        player.drawRedAppleUntilFullHand(redApples);

        assertEquals(7, player.getHand().size());
        assertEquals(3, redApples.size());
    }

    @Test
    public void testRemoveRedApple() {
        RedApple redApple = new RedApple("Test Apple");
        player.getHand().add(redApple);

        player.removeRedApple(redApple);

        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void testWinGreenApple() {
        GreenApple greenApple = new GreenApple("Test Apple");

        player.winGreenApple(greenApple);

        assertEquals(1, player.getScore());
    }

    @Test
    public void testSetJudge() {
        player.setJudge(true);
        assertTrue(player.isJudge());
    }

    @Test
    public void testSetSocket() {
        player.setSocket(null);
        assertNull(player.getSocket());
    }

    @Test
    public void testGetDonePlaying() {
        assertFalse(player.getDonePlaying());
    }

    @Test
    public void testSetDonePlaying() {
        player.setDonePlaying(true);
        assertTrue(player.getDonePlaying());
    }

    @Test
    public void testPrintHand() {
        ArrayList<RedApple> redApples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            redApples.add(new RedApple("Apple " + i));
        }
        player.drawRedAppleUntilFullHand(redApples);
        player.printHand();
    }

    @Test
    public void testSetHand() {
        ArrayList<RedApple> redApples = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            redApples.add(new RedApple("Apple " + i));
        }
        player.setHand(redApples);
        assertEquals(redApples, player.getHand());
    }
}