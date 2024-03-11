package game.phases;

import game.GameState;
import game.players.Player;
import game.models.PlayerPlayedRedAppleModel;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;

public class SubmitRedApplePhase extends Phase {

    /**
     * @param socket the socket to execute on
     * @param state  the game state
     * @throws IOException
     */
    @Override
    public void execute(Socket socket, GameState state) throws IOException {
        for (Player player : state.getPlayers()) {
            notifyClient(player.getSocket(), state);
        }



        // randomize the order of the played red apples
        Collections.shuffle(state.getRedApplesToBeJudged());
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
    public void executeOnClient(Socket socket) throws IOException {
        System.out.println("Choose a red apple to play: ");
        PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player, redApple);

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(playRedApple);
        outputStream.flush();
    }
}
