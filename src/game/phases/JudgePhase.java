package game.phases;
import game.apples.RedApple;
import game.models.PlayerPlayedRedAppleModel;
import game.players.Player;
import game.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class JudgePhase extends Phase{
    ArrayList<PlayerPlayedRedAppleModel> redApplesPlayed;

    /**
     * @param state  the game state
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     * @return the game state
     */
    @Override
    public GameState execute(GameState state) throws IOException, ClassNotFoundException {
        redApplesPlayed = state.getSubmittedRedAppleModel();


        for (Player player : state.getPlayers()) {
            super.notifyClient(player.getSocket());

            if (player.isJudge()) {
                ObjectInputStream inputStream = new ObjectInputStream(player.getSocket().getInputStream());
                RedApple redApple = (RedApple) inputStream.readObject();
                Player roundWinner = state.getRoundWinner(redApple);
                state.addGreenAppleToPlayer(roundWinner);
            }
        }
        return state;
    }

    /**
     * Sends the current phase to the client
     * @param socket the socket to notify
     * @param player clients player
     * @throws IOException if an I/O error occurs
     * @return the player
     */
    @Override
    public Player executeOnClient(Socket socket, Player player) throws IOException {
        // print the red apples played by the players
        if (!player.isJudge()) {
            System.out.println("You are not the judge, please wait for the judge to make a decision. The red apples played by the players are: ");
        } else {
            System.out.println("You are the judge, please select the red apple that you think is the best. The red apples played by the players are:");
        }
        int i = 0;
        for (PlayerPlayedRedAppleModel redAppleModel : redApplesPlayed) {
            System.out.println("["+i+"] " + redAppleModel.getRedApple().getContent());
            i++;
        }

        if (player.isJudge()) {
            System.out.println("Please enter the index of the red apple that you think is the best: ");
            // scanner to get the index of the red apple that the judge thinks is the best
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            RedApple winningRedApple = redApplesPlayed.get(index).getRedApple();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(winningRedApple);
            outputStream.flush();
        }

        return player;
    }
}
