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
     * @param socket the socket to execute on
     * @param state  the game state
     */
    @Override
    public GameState execute(Socket socket, GameState state) throws IOException {
        state.drawGreenApple();
        greenApple = state.getCurrentGreenApple();
        state.addGreenAppleToDeck(state.getCurrentGreenApple());
        // notify all players of the current green apple
        for (Player player : state.getPlayers()) {
            notifyClient(player.getSocket(), state);
        }
        return state;
    }

    /**
     * @param socket the socket to notify
     * @param state  the game state
     */
    @Override
    public void notifyClient(Socket socket, GameState state) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(this); // send the current phase
        outputStream.flush();
    }

    /**
     * @param socket the socket to execute on
     */
    @Override
    public void executeOnClient(Socket socket, Player player) {
        System.out.println("The current green apple is: " + greenApple.getContent());
    }
}
