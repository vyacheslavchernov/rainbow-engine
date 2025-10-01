package ru.vych.logic.entity.components;

import ru.vych.logic.entity.Entity;

public abstract class Component {
    public void init(Entity entity) {}
    public void update(double deltaTime, Entity entity) {}
    public void draw(double deltaTime, Entity entity) {}
}
