package game.phases;

import game.GameState;
import game.apples.RedApple;
import game.players.Player;
import game.models.PlayerPlayedRedAppleModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class SubmitRedApplePhase extends Phase {
    ArrayList<PlayerPlayedRedAppleModel> redApplesPlayed;

    /**
     * Executes the phase on the server
     * @param socket the socket to execute on
     * @param state  the game state
     * @throws IOException
     */
    @Override
    public GameState execute(Socket socket, GameState state) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        state.newJudge(); // Set a new judge
        for (Player player : state.getPlayers()) {
            if (!player.isJudge()) {
                notifyClient(player.getSocket(), state); // notify all players to submit red apples

                // how to wait for clients to send their red apples?

                // receive the red apple played by the player
                PlayerPlayedRedAppleModel playRedApple = (PlayerPlayedRedAppleModel) inputStream.readObject();
                state.addPlayerPlayedRedAppleModel(playRedApple);
            }
        }

        if (state.allPlayersSubmittedRedApples()) {
            // randomize the order of the submitted red apples while keeping track of who submitted them
            ArrayList<PlayerPlayedRedAppleModel> redApplesPlayed = state.getSubmittedRedAppleModel();
            Collections.shuffle(redApplesPlayed);
            state.setSubmittedRedAppleModel(redApplesPlayed);
            return state;
        }

        System.out.println("Not all players have submitted red apples yet");
        return state;
    }

    /**
     * Sends the current phase to the client
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
     * Executes the phase on the client
     * @param socket the socket to execute on
     */
    @Override
    public void executeOnClient(Socket socket, Player player) throws IOException {
        if (!player.isJudge()) {
            System.out.println("Choose a red apple to play: ");
            player.printHand();
            RedApple redApple = player.chooseRedApple();

            PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player, redApple);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(playRedApple);
            outputStream.flush();
        }
    }
}
