package ru.vych.resources.registry;

import ru.vych.resources.Texture;
import ru.vych.resources.registry.entries.TextureRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TexturesRegistry {
    public static final String DEFAULT_TEXTURE_NAME = "DEFAULT_TEXTURE";
    private static final Map<String, Texture> registry = new HashMap<>();
    private static final List<TextureRegistryEntry> availTextures = new ArrayList<>();

    static {
        availTextures.add(new TextureRegistryEntry(
                DEFAULT_TEXTURE_NAME,
                Texture.DEFAULT_TEXTURE_SOURCE
        ));
    }

    public static Texture getTexture(String name) {
        if (!registry.containsKey(name)) {
            availTextures.forEach(avail -> {
                if (avail.name().equals(name)) {
                    registry.put(name, avail.build());
                }
            });
        }
        return registry.get(name);
    }
}
