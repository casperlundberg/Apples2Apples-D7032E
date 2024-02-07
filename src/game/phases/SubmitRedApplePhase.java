package game.phases;

import game.GameState;

import java.util.Collections;

public class SubmitRedApplePhase extends Phase {
    public void execute(GameState gameState){
        // randomize the order of the played red apples
        Collections.shuffle(gameState.getRedApplesToBeJudged());
    }
}
