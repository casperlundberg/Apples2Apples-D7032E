package game.player;

import game.apples.GreenApple;
import game.apples.RedApple;

import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<GreenApple> greenApplesWon;
    private ArrayList<RedApple> hand;
    private final int MAX_HAND_SIZE = 7;
    private boolean isJudge;
    private boolean isBot;
    private boolean playedRedApple;

    public Player(String name) {
        this.name = name;
        this.greenApplesWon = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void drawRedAppleUntilFullHand(RedApple redApple) {
        if (hand.size() < MAX_HAND_SIZE) {
            hand.add(redApple);
        }
    }

    public void playRedApple(RedApple redApple) {
        if (isJudge) {
            System.out.println("Judge cannot play red apple");
            return;
        }
        hand.remove(redApple);
        // Send red apple to the game server
    }

    public int getScore() {
        return greenApplesWon.size();
    }

    public void winGreenApple(GreenApple greenApple) {
        greenApplesWon.add(greenApple);
    }
}
