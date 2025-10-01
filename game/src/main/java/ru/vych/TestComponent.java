package ru.vych;

import org.lwjgl.glfw.GLFW;
import ru.vych.input.KeyboardListener;
import ru.vych.logic.entity.Entity;
import ru.vych.logic.entity.components.Component;

public class TestComponent extends Component {
    @Override
    public void update(double deltaTime, Entity entity) {
        super.update(deltaTime, entity);

        float boost = KeyboardListener.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT) ? 8 : 1;

        if (KeyboardListener.isPressed(GLFW.GLFW_KEY_LEFT)) {
            entity.getPosition().x -= (float) (10 * deltaTime * boost);
        }
        if (KeyboardListener.isPressed(GLFW.GLFW_KEY_RIGHT)) {
            entity.getPosition().x += (float) (10 * deltaTime * boost);
        }
        if (KeyboardListener.isPressed(GLFW.GLFW_KEY_DOWN)) {
            entity.getPosition().y -= (float) (10 * deltaTime * boost);
        }
        if (KeyboardListener.isPressed(GLFW.GLFW_KEY_UP)) {
            entity.getPosition().y += (float) (10 * deltaTime * boost);
        }
    }
}
