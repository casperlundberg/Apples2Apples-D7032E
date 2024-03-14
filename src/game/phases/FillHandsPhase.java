package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.players.Player;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class FillHandsPhase extends Phase {
    private ArrayList<RedApple> newHand;
    /**
     * @param state  the game state
     */
    @Override
    public GameState execute(GameState state) throws IOException {
        state.fillPlayersHands();
        for (Player player : state.getPlayers()) {
            newHand = player.getHand();
            super.notifyClient(player.getSocket());
        }
        return state;
    }


    /**
     * @param socket the socket to execute on
     * @return
     */
    @Override
    public Player executeOnClient(Socket socket, Player player) {
        player.setHand(newHand);
        return player;
    }
}
