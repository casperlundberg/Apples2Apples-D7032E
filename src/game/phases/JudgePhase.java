package game.phases;
import game.players.Player;
import game.GameState;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class JudgePhase extends Phase{

    /**
     * @param socket the socket to execute on
     * @param state  the game state
     * @throws IOException
     */
    @Override
    public void execute(Socket socket, GameState state) throws IOException {
        Player judge = state.getJudge();
    }

    @Override
    public void notifyClient(Socket socket, GameState state) {

    }

    @Override
    public void executeOnClient(Socket socket) {

    }
}
