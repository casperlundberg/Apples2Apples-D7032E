package game.phases;
import game.apples.RedApple;
import game.players.Player;
import game.GameState;
import handlers.InputHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class JudgePhase extends Phase {
    ArrayList<RedApple> redApplesPlayed;

    /**
     * @param state  the game state
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     * @return the game state
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException, ClassNotFoundException {
        redApplesPlayed = state.getRedApplesToBeJudged();
        System.out.println(state.getJudge().getName() + " is the judge.");

        for (Player player : state.getPlayers()) {
            super.notifyClient(player.getSocket());

            if (player.isJudge()) {
                System.out.println("Waiting for the judge (username: "+player.getName()+") to decide winning red apple.");
                ObjectInputStream inputStream = new ObjectInputStream(player.getSocket().getInputStream());
                RedApple redApple = (RedApple) inputStream.readObject();
                Player roundWinner = state.getRoundWinner(redApple);

                if (roundWinner != null) {
                    state.addGreenAppleToPlayer(roundWinner);
                    System.out.println("Judge's decision is made. The winner of this round is: " + roundWinner.getName());
                } else {
                    System.out.println("No round winner found for the selected red apple.");
                }
            }
        }

        // print the players data
        state.printPlayerData();

        state.clearPlayersWhoSubmittedRedApples();
        return state;
    }

    /**
     * Executes the phase on the client
     * @param player clients player
     * @throws IOException if an I/O error occurs
     * @return the player
     */
    @Override
    public Player executeOnClient(Player player) throws IOException {
        // print the red apples played by the players

        if (player.isJudge()) {
            System.out.println("You are the judge, please select the red apple that you think is the best match win the green apple. The red apples played by the players are:");
        } else {
            System.out.println("You are not the judge, please wait for the judge to make a decision. The red apples played by the players are: ");
        }
        int i = 0;
        for (RedApple redApple : redApplesPlayed) {
            System.out.println("["+i+"] " + redApple.getContent());
            i++;
        }

        if (player.isJudge()) {
            InputHandler inputHandler = new InputHandler();
            RedApple winningRedApple = inputHandler.chooseRedApple(player, redApplesPlayed);

            ObjectOutputStream outputStream = new ObjectOutputStream(player.getSocket().getOutputStream());
            outputStream.writeObject(winningRedApple);
            outputStream.flush();
        }

        return player;
    }
}
