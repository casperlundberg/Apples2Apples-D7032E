package game.phases;

import game.GameState;
import game.players.Player;

import java.io.IOException;

public class checkWinnerPhase extends Phase {
    Player gameWinner;

    @Override
    public GameState execute(GameState state) throws IOException, ClassNotFoundException {
        Player gameWinner = state.getGameWinner(); // currently must be the round winner cuz only rounds give points
        for (Player player : state.getPlayers()) {
            if (gameWinner != null) {
                this.gameWinner = gameWinner;
                super.notifyClient(player.getSocket());
            }
        }

        return state;
    }

    @Override
    public Player executeOnClient(java.net.Socket socket, Player player) {
        if (gameWinner != null) {
            System.out.println("The game winner is: " + gameWinner.getName());
        }
        return player;
    }
}
