package game.models;
import game.apples.RedApple;
import game.players.Player;
import java.io.Serializable;

public class PlayerPlayedRedAppleModel implements Serializable {
    private final int playerId;
    private final RedApple redApple;

    public PlayerPlayedRedAppleModel(int playerId, RedApple redApple) {
        this.playerId = playerId;
        this.redApple = redApple;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public RedApple getRedApple(){
        return this.redApple;
    }
}
