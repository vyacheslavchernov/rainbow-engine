package ru.vych.logic.entity;

import lombok.Getter;
import org.joml.Vector2f;
import ru.vych.logic.entity.components.Component;
import ru.vych.logic.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    @Getter
    protected final Scene scene;
    protected final List<Component> components = new ArrayList<>();

    @Getter
    protected Vector2f position = new Vector2f();

    public Entity(Scene scene) {
        this.scene = scene;
    }

    public void init() {
        components.forEach(component -> component.init(this));
    }

    public void update(double deltaTime) {
        components.forEach(component -> component.update(deltaTime, this));
    }

    public void draw(double deltaTime) {
        components.forEach(component -> component.draw(deltaTime, this));
    }

    public <T extends Component> T getComponent(Class<T> component) {
        for (Component c : components) {
            if (component.isInstance(c)) {
                //noinspection unchecked
                return (T) c;
            }
        }
        return null;
    }
}
