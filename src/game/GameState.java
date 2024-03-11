package game;

import game.models.PlayerPlayedRedAppleModel;
import game.players.Player;
import game.apples.*;
import game.phases.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class GameState {
    private final ArrayList<Player> players;
    private int amountOfHumanPlayers = 1; // Default to 1
    private int amountOfBots = 3; // Default to 3
    private final int MIN_PLAYERS_AND_BOTS_COMBINED = 4;
    private ArrayList<Player> playersWhoSubmittedRedApples;
    private ArrayList<GreenApple> greenAppleDeck;
    private ArrayList<RedApple> redAppleDeck;
    private ArrayList<Phase> phases;
    private ArrayList<PlayerPlayedRedAppleModel> submittedRedApplesModel; // Because we need to keep track of who played what red apple

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


    public GreenApple getCurrentGreenApple() {
        return greenAppleDeck.get(0);
    }

    public void addGreenAppleToDeck(GreenApple greenApple) {
        greenAppleDeck.add(greenApple);
    }

    public ArrayList<PlayerPlayedRedAppleModel> getSubmittedRedAppleModel() {
        return submittedRedApplesModel;
    }

    public void setCurrentGreenApple(GreenApple currentGreenApple) {
        greenAppleDeck.set(0, currentGreenApple);
    }

    public void drawGreenApple() {
        greenAppleDeck.remove(0);
    }

    public void setSubmittedRedAppleModel(ArrayList<PlayerPlayedRedAppleModel> submitted) {
        this.submittedRedApplesModel = submitted;
    }

    public void addPlayerPlayedRedAppleModel(PlayerPlayedRedAppleModel playerPlayedRedAppleModel) {
        submittedRedApplesModel.add(playerPlayedRedAppleModel);
    }

    public void removeRedAppleModelFromCurrentRedAppleModels(PlayerPlayedRedAppleModel redApple) {
        submittedRedApplesModel.remove(redApple);
    }

    public void playerPlayedRedApple(PlayerPlayedRedAppleModel playerPlayedRedAppleModel) {
        playerPlayedRedAppleModel.getPlayer().playRedApple(playerPlayedRedAppleModel.getRedApple());
        addPlayerWhoSubmittedRedApple(playerPlayedRedAppleModel.getPlayer());
    }

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public Phase getCurrentPhase() {
        return phases.get(0);
    }

    public void nextPhase() {
        phases.add(phases.remove(0));
    }

    public boolean allPlayersSubmittedRedApples() {
        return playersWhoSubmittedRedApples.size() == players.size() - 1;
    }

    public void newJudge() {
        int i = new Random().nextInt(players.size());
        players.get(i).setJudge(true);
    }

    public Player getJudge() {
        for (Player player : players) {
            if (player.isJudge()) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<RedApple> getRedApplesToBeJudged() {
        ArrayList<RedApple> redApples = new ArrayList<>();
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            redApples.add(playerPlayedRedAppleModel.getRedApple());
        }
        return redApples;
    }

    public Player getWinningPlayer(RedApple winningRedApple) {
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            if (playerPlayedRedAppleModel.getRedApple().equals(winningRedApple)) {
                return playerPlayedRedAppleModel.getPlayer();
            }
        }
        return null;
    }

    public boolean allPlayersJoined() {
        return players.size() == amountOfHumanPlayers + amountOfBots;
    }

    public void setAmountOfHumanPlayers(int amountOfHumanPlayers) {
        this.amountOfHumanPlayers = amountOfHumanPlayers;
    }

    public void setAmountOfBots(int amountOfBots) {
        this.amountOfBots = amountOfBots;
    }
}
