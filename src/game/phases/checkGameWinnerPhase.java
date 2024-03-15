package game.phases;

import game.GameState;
import game.players.Player;

import java.io.IOException;

public class checkGameWinnerPhase extends Phase {
    boolean gameEnded = false;
    String winnerName;

    @Override
    public GameState executeOnServer(GameState state) throws IOException, ClassNotFoundException {
        Player gameWinner = state.getGameWinner(); // currently must be the round winner or null cuz only rounds give points
        for (Player player : state.getPlayers()) {
            if (gameWinner != null) {
                this.gameEnded = true;
                this.winnerName = gameWinner.getName();
                super.notifyClient(player.getSocket());
            }
        }
        return state;
    }

    @Override
    public Player executeOnClient(java.net.Socket socket, Player player) {
        if (gameEnded) {
            System.out.println("The game winner is: " + winnerName);
        } else {
            System.out.println("The game has not ended yet.");
        }
        return player;
    }
}
