package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utility class to provide smooth scene transitions.
 */
public class Transition {

    /**
     * Applies a fade-in transition to a JavaFX node.
     * The node will fade from transparent to opaque.
     *
     * @param node The node to apply the transition to.
     * @param durationMillis The duration of the transition in milliseconds.
     */
    public static void applyFadeTransition(Node node, double durationMillis) {
        FadeTransition ft = new FadeTransition(Duration.millis(durationMillis), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}