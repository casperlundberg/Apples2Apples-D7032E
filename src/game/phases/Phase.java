package game.phases;

import game.GameState;
import game.players.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.Serializable;

/**
 * This abstract class represents a phase in the game.
 * Each phase has its own implementation of the execute and executeOnClient methods.
 * The notifyClient method is common for all phases and is used to notify the client about the current phase.
 */
public abstract class Phase implements Serializable {

    /**
     * This method is used to execute the current phase on the server side.
     * It takes the current game state as a parameter and returns the updated game state.
     *
     * @param state the current game state
     * @return the updated game state after executing the phase
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public abstract GameState execute(GameState state) throws IOException, ClassNotFoundException;

    /**
     * This method is used to notify the client about the current phase.
     * It sends the current phase object to the client through the provided socket.
     *
     * @param socket the socket to send the phase object to
     * @throws IOException if an I/O error occurs
     */
    public void notifyClient(Socket socket) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(this); // send the current phase
        outputStream.flush();
    }

    /**
     * This method is used to execute the current phase on the client side.
     * It takes the client's socket and player as parameters and returns the updated player after executing the phase.
     *
     * @param socket the client's socket
     * @param player the current player
     * @return the updated player after executing the phase
     * @throws IOException if an I/O error occurs
     */
    public abstract Player executeOnClient(Socket socket, Player player) throws IOException;
}