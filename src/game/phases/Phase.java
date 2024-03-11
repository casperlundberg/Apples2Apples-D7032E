package game.phases;

import game.GameState;
import java.io.IOException;
import java.net.Socket;
import java.io.Serializable;


public abstract class Phase implements Serializable {

    /**
     * Executes the phase on the server
     * @param socket the socket to execute on
     * @param state the game state
     */
    public abstract void execute(Socket socket, GameState state) throws IOException;

    /**
     * Notifies the clients of the phase
     * @param socket the socket to notify
     * @param state the game state
     */
    public abstract void notifyClient(Socket socket, GameState state) throws IOException;

    /**
     * Executes the phase on the client
     * @param socket the socket to execute on
     */
    public abstract void executeOnClient(Socket socket) throws IOException;
}
