package game.phases;

import game.GameState;
import game.apples.GreenApple;

public class DrawGreenApplePhase extends Phase {

    public void execute(GameState gameState) {
        GreenApple currentGreenApple = gameState.getGreenAppleDeck().remove(0);
        gameState.setCurrentGreenApple(currentGreenApple);
        gameState.addGreenAppleToDeck(currentGreenApple);
    }
}
