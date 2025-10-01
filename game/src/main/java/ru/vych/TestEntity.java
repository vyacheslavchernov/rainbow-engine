package ru.vych;

import ru.vych.logic.entity.Entity;
import ru.vych.logic.entity.components.MaterialComponent;
import ru.vych.logic.entity.components.SpriteComponent;
import ru.vych.logic.scene.Scene;
import ru.vych.resources.registry.TexturesRegistry;

import static ru.vych.resources.registry.ShaderRegistry.DEFAULT_SHADER_NAME;
import static ru.vych.resources.registry.TexturesRegistry.DEFAULT_TEXTURE_NAME;

public class TestEntity extends Entity {
    public TestEntity(Scene scene) {
        super(scene);
    }

    @Override
    public void init() {
        super.init();

        components.add(new MaterialComponent(DEFAULT_SHADER_NAME));

        components.add(new SpriteComponent(TexturesRegistry.getTexture(DEFAULT_TEXTURE_NAME), this));

        components.add(new TestComponent());
    }
}
