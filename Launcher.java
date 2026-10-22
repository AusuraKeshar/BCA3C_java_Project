package com.devforge;

import javafx.application.Application;

/** Entry point used by the packaged launcher so JavaFX can start from a bundled classpath. */
public final class Launcher {
    private Launcher() { }
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
