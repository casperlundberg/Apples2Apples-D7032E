package game.phases;

import game.GameState;
import game.player.Player;

import java.io.IOException;

public class CheckGameWinnerPhase extends Phase {
    boolean gameEnded = false;
    String winnerName;
    int winnerId;

    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        Player gameWinner = getGameWinner(state); // currently must be the round winner or null cuz only rounds give points
        for (Player player : state.getPlayers()) {
            if (gameWinner != null) {
                setGameEnded(true);
                setWinnerName(gameWinner.getName());
                setWinnerId(gameWinner.getPlayerId());
                super.notifyClient(player.getSocket());
            }
        }
        return state;
    }

    public Player getGameWinner(GameState state) {
        return state.getGameWinner();
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    @Override
    public Player executeOnClient(Player player) throws IOException {
        if (gameEnded) {
            player.setDonePlaying(true);

            if (winnerId == player.getPlayerId()) {
                System.out.println("Congratulations! You have won the game!");
            } else {
                System.out.println("The game winner is: " + winnerName);
            }

            player.getSocket().close();
        } else {
            System.out.println("The game has not ended yet, prepare for next round.");
        }
        return player;
    }
}
