package game.phases;

import game.GameState;

public class SubmitRedApplePhase {
    public void execute(GameState gameState) {
        for (int i = 0; i < gameState.getPlayers().size(); i++) {
            // wait for client to submit red apple
        }
    }
}
