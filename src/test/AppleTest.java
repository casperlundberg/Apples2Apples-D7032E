package test;

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
}