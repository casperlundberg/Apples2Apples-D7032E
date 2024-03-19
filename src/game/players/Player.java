package game.players;

import game.apples.GreenApple;
import game.apples.RedApple;
import java.net.Socket;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a Player in the game.
 * A Player can be a bot or a human player and has a hand of RedApple cards and a score of GreenApple cards won.
 */
public class Player {
    private int playerId;
    private final String name;
    private transient Socket socket;
    private final ArrayList<GreenApple> greenApplesWon;
    private ArrayList<RedApple> hand;
    private final int MAX_HAND_SIZE = 7;
    private boolean isJudge;
    private boolean isBot;
    private boolean donePlaying = false;

    /**
     * Constructor for a Player object.
     * @param name The name of the player.
     */
    public Player(String name ) {
        this(name ,  false);
    }

    /**
     * Constructor for a Player object.
     * @param name The name of the player.
     * @param isBot A boolean indicating if the player is a bot.
     */
    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
        this.hand = new ArrayList<>();
        this.greenApplesWon = new ArrayList<>();
        generatePlayerId();
    }

    /**
     * Constructor for a Player object.
     * @param name The name of the player.
     * @param socket The socket associated with the player.
     */
    public Player(String name, Socket socket) {
        this.name = name;
        this.isBot = false;
        this.hand = new ArrayList<>();
        this.greenApplesWon = new ArrayList<>();
        this.socket = socket;
    }

    /**
     * Generates a unique player id for each player.
     */
    public void generatePlayerId() {
        SecureRandom random = new SecureRandom();
        this.playerId = random.nextInt();
    }

    /**
     * @return The player's id.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the player's id.
     * @param playerId The id to set.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return A boolean indicating if the player is a bot.
     */
    public boolean isBot() {
        return isBot;
    }

    /**
     * Sets if the player is a bot.
     * @param isBot The boolean to set.
     */
    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

    /**
     * Draws RedApple cards until the player's hand is full.
     * @param redApples The list of RedApple cards to draw from.
     * @return The list of RedApple cards after drawing.
     */
    public ArrayList<RedApple> drawRedAppleUntilFullHand(ArrayList<RedApple> redApples) {
        while (hand.size() < MAX_HAND_SIZE && !redApples.isEmpty()) {
            hand.add(redApples.remove(0));
        }
        return redApples;
    }

    /**
     * Removes a RedApple card from the player's hand.
     * @param redApple The RedApple card to remove.
     */
    public void removeRedApple(RedApple redApple) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getContent().equals(redApple.getContent())) {
                hand.remove(i);
                break;
            }
        }
    }

    /**
     * @return The player's score, which is the number of GreenApple cards won.
     */
    public int getScore() {
        return greenApplesWon.size();
    }

    /**
     * Adds a GreenApple card to the player's score.
     * @param greenApple The GreenApple card to add.
     */
    public void winGreenApple(GreenApple greenApple) {
        greenApplesWon.add(greenApple);
    }

    /**
     * Prints the player's hand of RedApple cards.
     */
    public void printHand() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("[" + i + "]   " + hand.get(i).getContent());
        }
    }

    /**
     * @return The player's hand of RedApple cards.
     */
    public ArrayList<RedApple> getHand() {
        return hand;
    }

    /**
     * Sets the player's hand of RedApple cards.
     * @param hand The hand to set.
     */
    public void setHand(ArrayList<RedApple> hand) {
        this.hand = hand;
    }

    /**
     * Choose a RedApple from the given list of RedApple cards.
     * Currently, in use for players to choose from hand and judge from the played red apples.
     * If the player is a bot, a random RedApple is chosen.
     * @param redApples The list of RedApple cards to choose from.
     * @return The chosen RedApple card.
     */
    public RedApple chooseRedApple(ArrayList<RedApple> redApples) {
        // If the player is a bot, choose a random red apple
        if (isBot) {
            return redApples.get(new SecureRandom().nextInt(redApples.size()));
        }

        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        int choice = 0;
        while (!valid) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 0 && choice < redApples.size()) {
                    valid = true;
                    System.out.println("You chose: " + redApples.get(choice).getContent());
                } else {
                    System.out.println("Invalid index, please enter a valid index: ");
                    scanner.nextLine(); // clear scanner buffer so the next input can be read
                }
            } else {
                System.out.println("No input available. Please enter an integer.");
                scanner.nextLine(); // clear scanner buffer so the next input can be read
            }
        }

        // Do not close the scanner here if it's System.in as it will close System.in as well
        return redApples.get(choice);
    }

    /**
     * Sets if the player is the judge.
     * @param b The boolean to set.
     */
    public void setJudge(boolean b) {
        isJudge = b;
    }

    /**
     * @return A boolean indicating if the player is the judge.
     */
    public boolean isJudge() {
        return isJudge;
    }

    /**
     * Sets the player's socket.
     * @param socket The socket to set.
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * @return The player's socket.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return A boolean indicating if the player is done playing.
     */
    public boolean getDonePlaying() {
        return donePlaying;
    }

    /**
     * Sets if the player is done playing.
     * @param donePlaying The boolean to set.
     */
    public void setDonePlaying(boolean donePlaying) {
        this.donePlaying = donePlaying;
    }
}