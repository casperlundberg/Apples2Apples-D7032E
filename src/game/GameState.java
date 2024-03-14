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
    private int winningScore = 8; // Default to 8
    private final int MIN_PLAYERS_AND_BOTS_COMBINED = 4;
    private final ArrayList<Player> playersWhoSubmittedRedApples;
    private ArrayList<GreenApple> greenAppleDeck;
    private ArrayList<RedApple> redAppleDeck;
    private ArrayList<Phase> phases;
    private ArrayList<PlayerPlayedRedAppleModel> submittedRedApplesModel; // Because we need to keep track of who played what red apple

    public GameState() {
        this.players = new ArrayList<>();
        this.playersWhoSubmittedRedApples = new ArrayList<>();
        this.submittedRedApplesModel = new ArrayList<>();
        loadGreenApples();
        loadRedApples();
        setupPhases();
        setupScoring();
    }

    private void setupPhases() {
        this.phases = new ArrayList<>();
        this.phases.add(new FillHandsPhase());
        this.phases.add(new DrawGreenApplePhase());
        this.phases.add(new SubmitRedApplePhase());
        this.phases.add(new JudgePhase());
        this.phases.add(new checkWinnerPhase());
    }

    private void setupScoring() {
        int playersAndBots = amountOfHumanPlayers + amountOfBots;
        if (playersAndBots >= 8) {
            winningScore = 4;
        } else {
            winningScore = 8;
            for (int i = 4; i < playersAndBots; i++) {
                winningScore--;
            }
        }
    }
    private void loadGreenApples() {
        this.greenAppleDeck = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("src/game/resources","green_apples.txt"))) {
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
            for (String line : Files.readAllLines(Paths.get("src/game/resources","red_apples.txt"))) {
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

    public void fillPlayersHands() {
        for (Player player : players) {
            redAppleDeck = player.drawRedAppleUntilFullHand(redAppleDeck);
        }
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

    public void setSubmittedRedAppleModel(ArrayList<PlayerPlayedRedAppleModel> submitted) {
        this.submittedRedApplesModel = submitted;
    }

    public void addPlayerPlayedRedAppleModel(PlayerPlayedRedAppleModel playerPlayedRedAppleModel) {
        submittedRedApplesModel.add(playerPlayedRedAppleModel);
    }

    public void removeRedAppleModelFromCurrentRedAppleModels(PlayerPlayedRedAppleModel redApple) {
        submittedRedApplesModel.remove(redApple);
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

    public void nextJudge() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isJudge()) {
                players.get(i).setJudge(false);
                if (i == players.size() - 1) {
                    players.get(0).setJudge(true);
                } else {
                    players.get(i + 1).setJudge(true);
                }
                break;
            } else {
                randomizeJudge();
            }
        }
    }

    public void randomizeJudge() {
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

    public Player getRoundWinner(RedApple winningRedApple) {
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            if (playerPlayedRedAppleModel.getRedApple().equals(winningRedApple)) {
                return playerPlayedRedAppleModel.getPlayer();
            }
        }
        return null;
    }

    public void addGreenAppleToPlayer(Player player) {
        player.winGreenApple(greenAppleDeck.remove(0));
        // update the player in the players list
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).equals(player)) {
                players.set(i, player);
            }
        }
    }

    public Player getGameWinner() {
        for (Player player : players) {
            if (player.getScore() == winningScore){
                return player;
            }
        }
        return null;
    }

    public boolean playerThatPlayedRedApple(Player player) {
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            if (playerPlayedRedAppleModel.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;

    }

    public boolean allPlayersJoined() {
        if (players == null) {
            return false;
        }
        return players.size() == amountOfHumanPlayers + amountOfBots;
    }

    public void setAmountOfHumanPlayers(int amountOfHumanPlayers) {
        this.amountOfHumanPlayers = amountOfHumanPlayers;
    }

    public void setAmountOfBots(int amountOfBots) {
        this.amountOfBots = amountOfBots;
    }
}
