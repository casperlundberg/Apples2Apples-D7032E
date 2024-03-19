package test;

import game.apples.Apple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppleTest {
    @Test
    public void testGetContent() {
        String expectedContent = "Test Content";
        Apple apple = new Apple(expectedContent);
        String actualContent = apple.getContent();
        assertEquals(expectedContent, actualContent, "Content of the apple does not match the expected content");
    }
}