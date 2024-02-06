package game;

import game.player.Player;
import game.apples.*;
import game.phases.*;

import java.util.ArrayList;

public class GameState {
    private ArrayList<Player> players;
    private ArrayList<GreenApple> greenAppleDeck;
    private ArrayList<RedApple> redAppleDeck;
    private ArrayList<Phase> phases;
    private GreenApple currentGreenApple;
    private ArrayList<RedApple> currentRedApples;

    public GameState(ArrayList<Player> players, ArrayList<GreenApple> greenAppleDeck, ArrayList<RedApple> redAppleDeck, ArrayList<Phase> phases) {
        this.players = players;
        this.greenAppleDeck = greenAppleDeck; // load from file
        this.redAppleDeck = redAppleDeck; // load from file
        this.phases = phases; // define either in file or with final config variables
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public Player getCurrentPlayer() {
        // return the player whose turn it is
    }

}
