package game.phases;

import game.GameState;
import game.players.Player;

import java.io.IOException;

public class checkGameWinnerPhase extends Phase {
    boolean gameEnded = false;
    String winnerName;
    int winnerId;

    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        Player gameWinner = state.getGameWinner(); // currently must be the round winner or null cuz only rounds give points
        for (Player player : state.getPlayers()) {
            if (gameWinner != null) {
                this.gameEnded = true;
                this.winnerName = gameWinner.getName();
                this.winnerId = gameWinner.getPlayerId();
                super.notifyClient(player.getSocket());
            }
        }
        return state;
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
