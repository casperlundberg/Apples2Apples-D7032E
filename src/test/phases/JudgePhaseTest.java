package test.phases;

import game.GameState;
import game.apples.RedApple;
import game.phases.JudgePhase;
import game.player.Player;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JudgePhaseTest {

    @Test
    public void testExecuteOnClient() throws IOException {
        Player player = new Player("Player 1");
        JudgePhase judgePhase = new JudgePhase();
        ArrayList<RedApple> redApples = new ArrayList<>();
        redApples.add(new RedApple("Red Apple 1"));
        judgePhase.setRedApplesPlayed(redApples);

        player = judgePhase.executeOnClient(player);
        assertNotNull(player);
    }

    @Test
    public void testScoreUp() {
        GameState state = new GameState();

        JudgePhase judgePhase = new JudgePhase();
        Player player = new Player("Player 1");
        state.addPlayer(player);

        state = judgePhase.scoreUp(player, state);
        assertEquals(1, state.getPlayerById(player.getPlayerId()).getScore());
    }

    @Test
    public void testInvalidScoreUp() {
        GameState state = new GameState();

        JudgePhase judgePhase = new JudgePhase();

        state = judgePhase.scoreUp(null, state);
        assertNotNull(state);
    }

    @Test
    public void testPrintJudgeInfo() {
        JudgePhase judgePhase = new JudgePhase();
        Player player = new Player("Player 1");
        player.setJudge(true);

        judgePhase.printJudgeInfo(player);
    }
}
