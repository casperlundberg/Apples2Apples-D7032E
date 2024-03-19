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
    private boolean gameEnded = false;
    private ArrayList<GreenApple> greenAppleDeck;
    private ArrayList<RedApple> redAppleDeck;
    private ArrayList<Phase> phases;
    private final ArrayList<PlayerPlayedRedAppleModel> submittedRedApplesModel; // Because we need to keep track of who played what red apple

    public GameState() {
        this.players = new ArrayList<>();
        this.submittedRedApplesModel = new ArrayList<>();
        loadGreenApples();
        loadRedApples();
        setupPhases();
        setupScoring();
    }

    private void setupPhases() {
        this.phases = new ArrayList<>();
        this.phases.add(new setupPhase());
        this.phases.add(new DrawGreenApplePhase());
        this.phases.add(new SubmitRedApplePhase());
        this.phases.add(new JudgePhase());
        this.phases.add(new checkGameWinnerPhase());
        this.phases.add(new ReplenishPlayersHandsPhase());
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
            System.out.println("Error occurred while loading green apples");
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
            System.out.println("Error occurred while loading red apples");
        }
        Collections.shuffle(this.redAppleDeck);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        player.drawRedAppleUntilFullHand(redAppleDeck);
        players.add(player);
    }

    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }
        return null;
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

    public void PlayerPlayedRedApple(PlayerPlayedRedAppleModel playerPlayedRedAppleModel) {
        submittedRedApplesModel.add(playerPlayedRedAppleModel);
        // remove played red apple from player's hand
        for (Player player : players) {
            if (player.getPlayerId() == playerPlayedRedAppleModel.playerId()) {
                player.removeRedApple(playerPlayedRedAppleModel.redApple());
            }
        }
    }

    public Phase getCurrentPhase() {
        return phases.get(0);
    }

    public void nextPhase() {
        if (phases.get(0) instanceof setupPhase) {
            phases.remove(0);
        } else {
            phases.add(phases.remove(0));
        }
    }

    public void nextJudge() {
        boolean judgeExists = false;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isJudge()) {
                judgeExists = true;
                players.get(i).setJudge(false);
                if (i == players.size() - 1) {
                    players.get(0).setJudge(true);
                } else {
                    players.get(i + 1).setJudge(true);
                }
                break;
            }
        }
        if (!judgeExists) {
            randomizeJudge();
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
            redApples.add(playerPlayedRedAppleModel.redApple());
        }
        return redApples;
    }

    public Player getRoundWinner(RedApple winningRedApple) {
        System.out.println("\n*** ROUND WINNER ***");
        System.out.println("The winning red apple is: " + winningRedApple.getContent());
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            System.out.println("--------------------");
            System.out.println("Player: " + playerPlayedRedAppleModel.playerId() + " played: " + playerPlayedRedAppleModel.redApple().getContent());
            if (playerPlayedRedAppleModel.redApple().getContent().equals(winningRedApple.getContent())) {
                return getPlayerById(playerPlayedRedAppleModel.playerId());
            }
        }
        return null;
    }

    public void clearPlayersWhoSubmittedRedApples() {
        submittedRedApplesModel.clear();
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
            if (player.getScore() >= winningScore){
                gameEnded = true;
                return player;
            }
        }
        return null;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public boolean playerThatPlayedRedApple(Player player) {
        for (PlayerPlayedRedAppleModel playerPlayedRedAppleModel : submittedRedApplesModel) {
            if (getPlayerById(playerPlayedRedAppleModel.playerId()).equals(player)) {
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

    public void printPlayerData() {
        System.out.println();
        System.out.println("PLAYER DATA:");
        System.out.println("====================================");
        for (Player player : players) {
            System.out.println("Player: " + player.getName());
            System.out.println("Player ID: " + player.getPlayerId());
            System.out.println("Player Hand: ");
            player.printHand();
            System.out.println("Player Score: " + player.getScore());
            System.out.println("Player Judge: " + player.isJudge());
            System.out.println();
        }
        System.out.println("====================================");
        System.out.println();
    }
}
