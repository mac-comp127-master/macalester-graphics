package edu.macalester.graphics;

import static edu.macalester.graphics.testsupport.RenderingTestMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.macalester.graphics.testsupport.GraphicsObjectTestSuite;
import edu.macalester.graphics.testsupport.RenderingTest;

public class GraphicsTextTest implements GraphicsObjectTestSuite {
    private GraphicsText text;
    private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase().contains("mac os");

    @Override
    public GraphicsObject getGraphicsObject() {
        return text;
    }

    @RenderingTest(modes = { PLAIN, FILLED, HIT_TEST }, tolerance = 170)
    void plain() {
        text = new GraphicsText("plax");
        assertChangedAtEachStep(
            () -> text.setPosition(30, 60)
        );
    }

    @RenderingTest(modes = { PLAIN }, tolerance = 120)
    void styled() {
        text = new GraphicsText("zonk");
        assertChangedAtEachStep(
            () -> text.setFont("Arial", FontStyle.ITALIC, 16),
            () -> text.setCenter(30, 40),
            () -> text.setFontSize(32),
            () -> text.setFontStyle(FontStyle.BOLD)
        );
        assertEquals(IS_MAC ? 74 : 72, text.getWidth(), 0.5);
        assertEquals(37, text.getHeight(), 0.5);
    }

    @RenderingTest(modes = { PLAIN })
    void changed() {
        text = new GraphicsText("plobble", 10, 50);
        assertChangedAtEachStep(
            () -> text.setFont("Arial", FontStyle.BOLD, 9),
            () -> text.setFont(FontStyle.BOLD, 20),
            () -> text.setText("zeep")
        );
    }

    @RenderingTest(modes = { PLAIN })
    void unicode() {
        text = new GraphicsText("Φ̟̽");
        assertChangedAtEachStep(
            () -> text.setFont("Tahoma", FontStyle.PLAIN, 32),
            () -> text.setPosition(10, 60)
        );
        assertEquals("\u03a6\u033d\u031f", text.getText());
    }

    @RenderingTest
    void nullString() {
        text = new GraphicsText(null, 50, 50);
    }

    @RenderingTest(modes = { PLAIN })
    void emptyString() {
        text = new GraphicsText("", 50, 50);
    }

    @Test
    void intersects() {
        GraphicsText text0 = new GraphicsText("O", 30, 100);
        text0.setFont("Verdana", FontStyle.ITALIC, 96);

        GraphicsText text1 = new GraphicsText(".", 76, 86);
        text1.setFont("Arial", FontStyle.PLAIN, 16);

        assertFalse(text0.intersects(text1));
        assertFalse(text1.intersects(text0));

        text1.setPosition(87, 92);
        assertTrue(text0.intersects(text1));
        assertTrue(text1.intersects(text0));

        text1.setPosition(89, 93);
        assertFalse(text0.intersects(text1));
        assertFalse(text1.intersects(text0));
    }
}