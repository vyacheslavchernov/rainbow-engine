package ru.vych;

import ru.vych.logic.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class MainScene extends Scene {
    private TestEntity testEntity;


    @Override
    public void init() {
        super.init();

        testEntity = new TestEntity(this);
        testEntity.init();

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        testEntity.update(deltaTime);
    }

    @Override
    public void draw(double deltaTime) {
        super.draw(deltaTime);

        testEntity.draw(deltaTime);
    }
}
