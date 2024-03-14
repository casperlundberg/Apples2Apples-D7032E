package game.apples;

import java.io.Serializable;

public class Apple implements Serializable {
    private final String content; // The text on the Apple

    public Apple(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
