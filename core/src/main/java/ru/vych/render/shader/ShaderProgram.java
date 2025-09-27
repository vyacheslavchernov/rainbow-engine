package ru.vych.render.shader;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

/**
 * Шейдерная программа. Объединяет в себе несколько разных видов
 * шейдеров для дальнейшего их использования в процессе рендеринга.
 */
@Getter
public class ShaderProgram {
    public static final int FLOAT_SIZE_BYTES = 4;

    private static final Logger log = LoggerFactory.getLogger(ShaderProgram.class);

    private final int shaderProgram;
    private final List<ShaderAttribute> shaderAttributes;

    private final int shaderVertexSizeBytes;

    public ShaderProgram(List<ShaderAttribute> shaderAttributes, Shader... shaders) {
        this.shaderAttributes = shaderAttributes;
        shaderVertexSizeBytes = shaderAttributes.stream()
                .mapToInt(ShaderAttribute::getAttrSize).sum() * FLOAT_SIZE_BYTES;

        log.info("Linking shaders to program");
        shaderProgram = glCreateProgram();
        Arrays.stream(shaders).forEach((shader -> {
            log.info("Link shader {} to program", shader.getShaderSource());
            glAttachShader(shaderProgram, shader.getShaderId());
        }));
        glLinkProgram(shaderProgram);

        var success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            var len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException(
                    String.format("Error while linking shaders - %s",
                            glGetProgramInfoLog(shaderProgram, len))
            );
        } else {
            log.info("Shaders linked in program");
        }
    }

    /**
     * Забиндить аттрибуты шейдеров.
     * Необходимо вызывать в начале метода отрисовки {@link ru.vych.render.shapes.Drawable#draw(double)}
     */
    public void bindAttributes() {
        int index = 0;
        int offset = 0;
        for (var attr : shaderAttributes) {
            glVertexAttribPointer(
                    index, attr.getAttrSize(), attr.getAttrType(),
                    false, shaderVertexSizeBytes, (long) offset * FLOAT_SIZE_BYTES
            );
            glEnableVertexAttribArray(index);

            index++;
            offset += attr.getAttrSize();
        }
    }

    /**
     * Разбиндить аттрибуты шейдеров.
     * Необходимо вызывать в конце метода отрисовки {@link ru.vych.render.shapes.Drawable#draw(double)}
     */
    public void unbindAttributes() {
        for (int i = 0; i < shaderAttributes.size(); i++) {
            glDisableVertexAttribArray(i);
        }
    }
}
