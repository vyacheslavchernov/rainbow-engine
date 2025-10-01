package ru.vych.logic.entity.components;

import lombok.Getter;
import ru.vych.render.shader.ShaderProgram;
import ru.vych.resources.registry.ShaderRegistry;


@Getter
public class MaterialComponent extends Component {
    private final ShaderProgram shaderProgram;

    public MaterialComponent(String shaderProgramName) {
        shaderProgram = ShaderRegistry.getShaderProgram(shaderProgramName);
    }
}
