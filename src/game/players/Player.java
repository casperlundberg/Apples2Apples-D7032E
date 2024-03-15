package game.players;

import game.apples.GreenApple;
import game.apples.RedApple;
import java.net.Socket;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private int playerId;
    private final String name;
    private transient Socket socket;
    private final ArrayList<GreenApple> greenApplesWon;
    private ArrayList<RedApple> hand;
    private final int MAX_HAND_SIZE = 7;
    private boolean isJudge;
    private boolean isBot;
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
        SecureRandom random = new SecureRandom();
        this.playerId = random.nextInt();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

    public ArrayList<RedApple> drawRedAppleUntilFullHand(ArrayList<RedApple> redApples) {
        while (hand.size() < MAX_HAND_SIZE && !redApples.isEmpty()) {
            hand.add(redApples.remove(0));
        }
        return redApples;
    }

    public void removeRedApple(RedApple redApple) {
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
        return hand.get(choice);
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
