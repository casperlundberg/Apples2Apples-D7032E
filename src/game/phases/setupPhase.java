package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.players.Player;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class setupPhase extends Phase {
    private ArrayList<RedApple> newHand;
    private int playerId;
    /**
     * @param state the game state
     * @throws IOException if an I/O error occurs
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        state.fillPlayersHands();
        for (Player player : state.getPlayers()) {
            newHand = player.getHand();
            playerId = player.getPlayerId();
            super.notifyClient(player.getSocket());
        }
        return state;
    }

    /**
     * @param player the player
     * @return the player
     */
    @Override
    public Player executeOnClient(Player player) {
        player.setHand(newHand);
        player.setPlayerId(playerId);
        return player;
    }
}
