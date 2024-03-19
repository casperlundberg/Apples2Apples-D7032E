package game.models;
import game.apples.RedApple;

import java.io.Serializable;

/**
 * This class represents a model for when a player plays a RedApple card.
 * It implements Serializable, allowing it to be converted into a byte stream.
 * The class is a record, a special type of class in Java that is a transparent carrier for immutable data.
 */
public record PlayerPlayedRedAppleModel(int playerId, RedApple redApple) implements Serializable {
}