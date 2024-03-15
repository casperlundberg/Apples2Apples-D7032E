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
import java.util.List;

public class SubmitRedApplePhase extends Phase {

    /**
     * Executes the phase on the server
     * @param state  the game state
     * @throws IOException
     */
    @Override
    public GameState execute(GameState state) throws IOException, ClassNotFoundException {
        state.nextJudge(); // Set next player as judge if judge exist, otherwise randomize

        List<Thread> threads = new ArrayList<>();
        for (Player player : state.getPlayers()) {
            if (!player.isJudge() && !state.playerThatPlayedRedApple(player)) {
                Thread thread = new Thread(() -> {
                    try {
                        System.out.println("Waiting for " + player.getName() + " to submit a red apple");
                        Socket socket = player.getSocket();
                        super.notifyClient(socket); // notify player to submit red apple

                        // receive the red apple played by the player
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        PlayerPlayedRedAppleModel playRedApple = (PlayerPlayedRedAppleModel) inputStream.readObject();
                        state.addPlayerPlayedRedAppleModel(playRedApple);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                threads.add(thread);
                thread.start();
            }
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Shuffle the submitted playerPlayedRedApple models
        Collections.shuffle(state.getSubmittedRedAppleModel());

        return state;
    }

    /**
     * Executes the phase on the client
     *
     * @param socket the socket to execute on
     * @return
     */
    @Override
    public Player executeOnClient(Socket socket, Player player) throws IOException {
        if (!player.isJudge()) {
            System.out.println("Choose a red apple to play: ");
            player.printHand();
            RedApple redApple = player.chooseRedApple();

            PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player.getPlayerId(), redApple);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(playRedApple);
            outputStream.flush();
        } else {
            System.out.println("You are the JUDGE, please wait for the other players to submit their red apples");
        }
        return player;
    }
}
