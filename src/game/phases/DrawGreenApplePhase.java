package game.phases;

import game.GameState;

public class DrawGreenApplePhase extends Phase {

    public void execute(GameState gameState) {
        gameState.setCurrentGreenApple(gameState.getGreenAppleDeck().remove(0));
    }
}
