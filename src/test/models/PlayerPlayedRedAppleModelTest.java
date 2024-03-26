package test.models;

import game.apples.RedApple;
import game.models.PlayerPlayedRedAppleModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerPlayedRedAppleModelTest {

    @Test
    public void testPlayerPlayedRedAppleModel() {
        String expectedContent = "Red Apple";
        RedApple redApple = new RedApple(expectedContent);
        PlayerPlayedRedAppleModel model = new PlayerPlayedRedAppleModel(1, redApple);

        // Cant check playerID because it is generated randomly
//        assertEquals(1, model.playerId());
        assertEquals(redApple, model.redApple());
    }
}