package game.phases;

import game.GameState;
import game.apples.GreenApple;
import game.players.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DrawGreenApplePhase extends Phase {
    int judgeId;
    private GreenApple greenApple;
    /**
     * @param state the game state
     * @throws IOException if an I/O error occurs
     */
    @Override
    public GameState executeOnServer(GameState state) throws IOException {
        state.nextJudge(); // Set next player as judge if judge exist, otherwise randomize
        judgeId = state.getJudge().getPlayerId();

        greenApple = state.getCurrentGreenApple();
        state.addGreenAppleToDeck(state.getCurrentGreenApple());
        // notify all players of the current green apple
        for (Player player : state.getPlayers()) {
            super.notifyClient(player.getSocket());
        }

        return state;
    }

    /**
     * Executes the phase on the client
     * @param player clients player
     * @return the player
     */
    @Override
    public Player executeOnClient(Player player) {
        player.setJudge(player.getPlayerId() == judgeId);


        System.out.println();
        System.out.println("*****************************************************");
        if (player.isJudge()) {
            System.out.println("**                 NEW ROUND - JUDGE               **");
        } else {
            System.out.println("**                    NEW ROUND                    **");
        }
        System.out.println("*****************************************************");

        System.out.println("The current green apple is: " + greenApple.getContent());
        return player;
    }
}
