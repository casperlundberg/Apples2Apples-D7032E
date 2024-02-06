package apples;

public class Apple {
    private final String content; // The text on the Apple

    public Apple(String content) {
        System.out.println("Apple constructor");
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
