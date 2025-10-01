package ru.vych.resources.registry.entries;

import ru.vych.resources.Texture;

public record TextureRegistryEntry(String name, String source) {
    public Texture build() {
        return new Texture(source);
    }
}
