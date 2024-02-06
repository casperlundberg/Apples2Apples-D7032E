package game;

import game.players.Player;
import game.apples.*;
import game.phases.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class GameState {
    private ArrayList<Player> players;
    private ArrayList<Player> playersWhoSubmittedRedApples;
    private ArrayList<GreenApple> greenAppleDeck;
    private ArrayList<RedApple> redAppleDeck;
    private ArrayList<Phase> phases;
    private GreenApple currentGreenApple;
    private ArrayList<RedApple> currentRedApples;

    public GameState() {
        this.players = null;
        loadGreenApples();
        loadRedApples();
        setupPhases();
    }

    private void setupPhases() {
        this.phases = new ArrayList<>();
        this.phases.add(new DrawGreenApplePhase());
        this.phases.add(new SubmitRedApplePhase());
    }
    private void loadGreenApples() {
        this.greenAppleDeck = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("green_apples.txt"))) {
                this.greenAppleDeck.add(new GreenApple(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(this.greenAppleDeck);
    }

    private void loadRedApples() {
        this.redAppleDeck = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("red_apples.txt"))) {
                this.redAppleDeck.add(new RedApple(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(this.redAppleDeck);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int addPlayer(Player player) {
        player.drawRedAppleUntilFullHand(redAppleDeck);
        players.add(player);
        return player.getPlayerId();
    }

    public int addPlayer(String name) {
        Player player = new Player(name);
        player.drawRedAppleUntilFullHand(redAppleDeck);
        players.add(player);
        return player.getPlayerId();
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayersWhoSubmittedRedApples() {
        return playersWhoSubmittedRedApples;
    }

    public void addPlayerWhoSubmittedRedApple(Player player) {
        playersWhoSubmittedRedApples.add(player);
    }

    public ArrayList<GreenApple> getGreenAppleDeck() {
        return greenAppleDeck;
    }

    public ArrayList<RedApple> getRedAppleDeck() {
        return redAppleDeck;
    }

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public GreenApple getCurrentGreenApple() {
        return currentGreenApple;
    }

    public void addGreenAppleToDeck(GreenApple greenApple) {
        greenAppleDeck.add(greenApple);
    }

    public ArrayList<RedApple> getCurrentRedApples() {
        return currentRedApples;
    }

    public void setCurrentGreenApple(GreenApple currentGreenApple) {
        this.currentGreenApple = currentGreenApple;
    }

    public void setCurrentRedApples(ArrayList<RedApple> currentRedApples) {
        this.currentRedApples = currentRedApples;
    }

    public void addRedAppleToCurrentRedApples(RedApple redApple) {
        currentRedApples.add(redApple);
    }

    public void removeRedAppleFromCurrentRedApples(RedApple redApple) {
        currentRedApples.remove(redApple);
    }

    public void executePhase(Phase phase) {
        phase.execute(this);
    }
}
