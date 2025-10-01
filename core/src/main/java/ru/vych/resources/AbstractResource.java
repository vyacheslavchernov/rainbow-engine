package ru.vych.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.logic.scene.Scene;

public abstract class AbstractResource implements Resource {
    protected static final Logger log = LoggerFactory.getLogger(AbstractResource.class);
    protected boolean loaded = false;

    @Override
    public boolean isLoaded() {
        return loaded;
    }
}
