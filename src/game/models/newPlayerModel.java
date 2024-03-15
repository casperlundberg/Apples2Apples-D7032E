package game.models;

import java.io.Serializable;

public class newPlayerModel implements Serializable {
    private final String name;

    public newPlayerModel(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
