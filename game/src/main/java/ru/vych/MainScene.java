package ru.vych;

import ru.vych.render.shapes.Rectangle;
import ru.vych.scene.Scene;

public class MainScene extends Scene {
    private Rectangle rectangle;

    @Override
    public void init() {
        super.init();
        rectangle = new Rectangle(shaderProgram);
    }

    @Override
    public void draw(double deltaTime) {
        super.draw(deltaTime);
        rectangle.draw(deltaTime);
    }
}
