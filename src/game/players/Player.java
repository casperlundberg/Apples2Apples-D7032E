package game.players;

import game.apples.GreenApple;
import game.apples.RedApple;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

public class Player implements Serializable {
    private int playerId;
    private final String name;
    private Socket socket;
    private final ArrayList<GreenApple> greenApplesWon;
    private ArrayList<RedApple> hand;
    private final int MAX_HAND_SIZE = 7;
    private boolean isJudge;
    private final boolean isBot;
    private boolean playedRedApple; // can be used instead of playerPlayedAppleModel probably

    public Player(String name ) {
        this(name ,  false);
    }

    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
        this.hand = new ArrayList<>();
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

    public ArrayList<RedApple> drawRedAppleUntilFullHand(ArrayList<RedApple> redApples) {
        while (hand.size() < MAX_HAND_SIZE && !redApples.isEmpty()) {
            hand.add(redApples.remove(0));
        }
        return redApples;
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

    public ArrayList<RedApple> getHand() {
        return hand;
    }

    public void setHand(ArrayList<RedApple> hand) {
        this.hand = hand;
    }

    public RedApple chooseRedApple() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return hand.remove(choice);
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
