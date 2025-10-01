package ru.vych.resources.registry;

import ru.vych.render.shader.Shader;
import ru.vych.render.shader.ShaderProgram;
import ru.vych.render.shader.ShaderAttribute;
import ru.vych.resources.registry.entries.ShaderRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderRegistry {
    public static final String DEFAULT_SHADER_NAME = "DEFAULT_SHADER";
    private static final Map<String, ShaderProgram> registry = new HashMap<>();
    private static final List<ShaderRegistryEntry> availShaders = new ArrayList<>();

    static {
        availShaders.add(new ShaderRegistryEntry(
                DEFAULT_SHADER_NAME,
                ShaderAttribute.getDefault(),
                Shader.getDefaultVertex(),
                Shader.getDefaultFragment()
        ));
    }

    public static ShaderProgram getShaderProgram(String name) {
        if (!registry.containsKey(name)) {
            availShaders.forEach(avail -> {
                if (avail.name().equals(name)) {
                    registry.put(name, avail.load());
                }
            });
        }
        return registry.get(name);
    }

}
