package ru.vych.resources.registry.entries;

import ru.vych.render.shader.Shader;
import ru.vych.render.shader.ShaderProgram;
import ru.vych.render.shader.ShaderAttribute;

import java.util.List;

public record ShaderRegistryEntry(String name, List<ShaderAttribute> attributeList, Shader... shaders) implements RegistryEntry {
    public ShaderProgram load() {
        return new ShaderProgram(attributeList, shaders);
    }
}
