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
    public GameState executeOnServer(GameState state) throws IOException, ClassNotFoundException {

        List<Thread> threads = new ArrayList<>();
        for (Player player : state.getPlayers()) {
            Thread thread = new Thread(() -> {
                try {
                        Socket socket = player.getSocket();
                        if (!player.isJudge() && !state.playerThatPlayedRedApple(player)) {
                            System.out.println("Waiting for " + player.getName() + " to submit a red apple"); // server side

                            super.notifyClient(socket); // notify player to submit red apple

                            // receive the red apple played by the player
                            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                            PlayerPlayedRedAppleModel playedRedApple = (PlayerPlayedRedAppleModel) inputStream.readObject();
                            state.PlayerPlayedRedApple(playedRedApple);

                            System.out.println(player.getName() + " submitted a red apple"); // server side
                        } else {
                            super.notifyClient(socket); // notify player that they are the judge
                        }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
            thread.start();
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
     * @param player the player
     * @return the player
     */
    @Override
    public Player executeOnClient(Player player) throws IOException {
        if (player.isJudge()) {
            System.out.println("You are the judge, please wait for the other players to submit their red apples");
        } else {
            System.out.println("Choose a red apple to play: ");
            player.printHand();
            RedApple redApple = player.chooseRedApple(player.getHand());

            PlayerPlayedRedAppleModel playRedApple = new PlayerPlayedRedAppleModel(player.getPlayerId(), redApple);

            ObjectOutputStream outputStream = new ObjectOutputStream(player.getSocket().getOutputStream());
            outputStream.writeObject(playRedApple);
            outputStream.flush();
        }

        return player;
    }
}
