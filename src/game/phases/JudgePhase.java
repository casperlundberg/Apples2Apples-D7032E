package game.phases;
import game.apples.RedApple;
import game.player.Player;
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
        setRedApplesPlayed(state.getRedApplesToBeJudged());
        System.out.println(state.getJudge().getName() + " is the judge.");

        for (Player player : state.getPlayers()) {
            super.notifyClient(player.getSocket());

            if (player.isJudge()) {
                Player roundWinner = waitForJudgeDecision(player, state);
                state = scoreUp(roundWinner, state);
            }
        }

        // print the players data on the server
        state.printPlayerData();

        state.clearPlayersWhoSubmittedRedApples();
        return state;
    }

    public void setRedApplesPlayed(ArrayList<RedApple> redApplesPlayed) {
        this.redApplesPlayed = redApplesPlayed;
    }

    public Player waitForJudgeDecision(Player player, GameState state) throws IOException, ClassNotFoundException {
        System.out.println("Waiting for the judge (username: "+player.getName()+") to decide winning red apple.");
        ObjectInputStream inputStream = new ObjectInputStream(player.getSocket().getInputStream());
        RedApple redApple = (RedApple) inputStream.readObject();
        return state.getRoundWinner(redApple);
    }

    public GameState scoreUp(Player roundWinner, GameState state) {
        if (roundWinner != null) {
            state.addGreenAppleToPlayer(roundWinner);
            System.out.println("Judge's decision is made. The winner of this round is: " + roundWinner.getName());
        } else {
            System.out.println("No round winner found for the selected red apple.");
        }
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
        printJudgeInfo(player);
        printRedApples(redApplesPlayed);

        if (player.isJudge()) {
            InputHandler inputHandler = new InputHandler();
            RedApple winningRedApple = inputHandler.chooseRedApple(player, redApplesPlayed);

            ObjectOutputStream outputStream = new ObjectOutputStream(player.getSocket().getOutputStream());
            outputStream.writeObject(winningRedApple);
            outputStream.flush();
        }

        return player;
    }

    public void printJudgeInfo(Player player) {
        if (player.isJudge()) {
            System.out.println("You are the judge, please select the red apple that you think is the best match win the green apple. The red apples played by the players are:");
        } else {
            System.out.println("You are not the judge, please wait for the judge to make a decision. The red apples played by the players are: ");
        }
    }

    public void printRedApples(ArrayList<RedApple> redApples) {
        int i = 0;
        for (RedApple redApple : redApples) {
            System.out.println("["+i+"] " + redApple.getContent());
            i++;
        }
    }
}
