package ru.vych.render.shader;

import ru.vych.resources.Resource;

public class ShaderData implements Resource {
    private boolean loaded;

    @Override
    public void load() {

    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }
}
