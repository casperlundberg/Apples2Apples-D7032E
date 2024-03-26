package test.apples;

import game.apples.Apple;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AppleTest {
    @Test
    public void testGetContent() {
        String expectedContent = "Test Content";
        Apple apple = new Apple(expectedContent);
        String actualContent = apple.getContent();
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testGetContentEmpty() {
        String expectedContent = "";
        Apple apple = new Apple(expectedContent);
        String actualContent = apple.getContent();
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testGreenApple() {
        String expectedContent = "Green Apple";
        Apple apple = new Apple(expectedContent);
        String actualContent = apple.getContent();
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testRedApple() {
        String expectedContent = "Red Apple";
        Apple apple = new Apple(expectedContent);
        String actualContent = apple.getContent();
        assertEquals(expectedContent, actualContent);
    }
}