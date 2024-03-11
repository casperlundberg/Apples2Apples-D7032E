package game.players;

import game.apples.GreenApple;
import game.apples.RedApple;
import java.net.Socket;

import java.util.ArrayList;

public class Player {
    private int playerId;
    private final String name;
    private Socket socket;
    private ArrayList<GreenApple> greenApplesWon;
    private ArrayList<RedApple> hand;
    private final int MAX_HAND_SIZE = 7;
    private boolean isJudge;
    private boolean isBot;
    private boolean playedRedApple;

    public Player(String name ) {
        this(name ,  false);
    }

    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
        this.greenApplesWon = new ArrayList<>();
        generatePlayerId();
    }

    // function that generates a unique player id for each player
    public void generatePlayerId() {
        this.playerId = Math.abs(name.hashCode());
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public void drawRedAppleUntilFullHand(ArrayList<RedApple> redApples) {
        while (hand.size() < MAX_HAND_SIZE && !redApples.isEmpty()) {
            hand.add(redApples.remove(0));
        }
    }

    public void playRedApple(RedApple redApple) {
        if (isJudge) {
            System.out.println("Judge cannot play red apple");
            return;
        }
        hand.remove(redApple);
    }

    public int getScore() {
        return greenApplesWon.size();
    }

    public void winGreenApple(GreenApple greenApple) {
        greenApplesWon.add(greenApple);
    }

    public void printHand() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("[" + i + "]   " + hand.get(i).getContent());
        }
    }

    public void setJudge(boolean b) {
        isJudge = b;
    }

    public boolean isJudge() {
        return isJudge;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
