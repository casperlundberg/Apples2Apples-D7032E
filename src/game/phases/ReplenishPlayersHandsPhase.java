package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.players.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ReplenishPlayersHandsPhase extends Phase {
    private ArrayList<RedApple> newHand;

    /**
     * @param state the current game state
     * @return the game state
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException, ClassNotFoundException {
        state.fillPlayersHands();
        for (Player player : state.getPlayers()) {
            newHand = player.getHand();
            super.notifyClient(player.getSocket());
        }
        return state;
    }

    /**
     * @param socket the client's socket
     * @param player the current player
     * @return
     * @throws IOException
     */
    @Override
    public Player executeOnClient(Socket socket, Player player) throws IOException {
        player.setHand(newHand);
        return player;
    }
}
