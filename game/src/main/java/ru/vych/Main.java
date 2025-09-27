package ru.vych;

import ru.vych.app.Application;
import ru.vych.app.WindowConfig;

public class Main extends Application {
    public static void main(String[] args) {
        run(WindowConfig.withDefault()
                .setStartScene(MainScene.class)
//                .setMaximized(1)
        );
    }
}