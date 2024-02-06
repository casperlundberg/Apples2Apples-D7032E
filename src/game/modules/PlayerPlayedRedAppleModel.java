package game.modules;
import game.apples.RedApple;
import game.players.Player;
import java.io.Serializable;

public class PlayerPlayedRedAppleModel implements Serializable {
    private Player player;
    private RedApple redApple;

    public PlayerPlayedRedAppleModel(Player player, RedApple redApple) {
        this.player = player;
        this.redApple = redApple;
    }
}
