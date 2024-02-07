package game.phases;
import game.players.Player;
import game.GameState;

import java.util.ArrayList;

public class JudgePhase extends Phase{
    public void execute(GameState gameState) {
        Player judge = gameState.getJudge();
    }
}
