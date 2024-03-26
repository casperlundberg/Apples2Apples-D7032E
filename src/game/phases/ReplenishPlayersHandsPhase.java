package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class ReplenishPlayersHandsPhase extends Phase {
    private ArrayList<RedApple> newHand;

    /**
     * @param state the current game state
     * @return the game state
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        state.fillPlayersHands();
        for (Player player : state.getPlayers()) {
            newHand = player.getHand();
            super.notifyClient(player.getSocket());
        }
        return state;
    }

    /**
     * @param player the current player
     * @return the player
     */
    @Override
    public Player executeOnClient(Player player) {
        player.setHand(newHand);
        return player;
    }
}
