package game.phases;

import game.GameState;
import game.apples.GreenApple;
import game.players.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DrawGreenApplePhase extends Phase {
    private GreenApple greenApple;
    /**
     * @param state  the game state
     */
    @Override
    public GameState execute(GameState state) throws IOException {
        greenApple = state.getCurrentGreenApple();
        state.addGreenAppleToDeck(state.getCurrentGreenApple());
        // notify all players of the current green apple
        for (Player player : state.getPlayers()) {
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
        System.out.println("The current green apple is: " + greenApple.getContent());
        return player;
    }
}
