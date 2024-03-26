package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.player.Player;
import java.io.IOException;
import java.util.ArrayList;

public class SetupPhase extends Phase {
    private ArrayList<RedApple> newHand;
    private int playerId;
    /**
     * @param state the game state
     * @throws IOException if an I/O error occurs
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        state = fillPlayersHands(state);

        for (Player player : state.getPlayers()) {
            setNewHand(player.getHand());
            setPlayerId(player.getPlayerId());
            super.notifyClient(player.getSocket());
        }
        return state;
    }

    public GameState fillPlayersHands(GameState state) {
        state.fillPlayersHands();
        return state;
    }

    public void setNewHand(ArrayList<RedApple> newHand) {
        this.newHand = newHand;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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
