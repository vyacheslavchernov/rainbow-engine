package ru.vych.render.shader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.resources.AbstractResource;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Класс шейдера.
 * Выполняет загрузку и компиляцию шейдера для последующего использования в {@link ShaderProgram}
 */
@RequiredArgsConstructor
public class Shader extends AbstractResource {
    public static final String DEFAULT_VERTEX_SHADER_SOURCE = "core/assets/shaders/default/default_vertex.glsl";
    public static final String DEFAULT_FRAGMENT_SHADER_SOURCE = "core/assets/shaders/default/default_fragment.glsl";

    private static final Logger log = LoggerFactory.getLogger(Shader.class);

    private final int shaderType;
    @Getter
    private final String shaderSource;

    private int shaderId;
    private boolean compiled = false;

    public static Shader getDefaultVertex() {
        return new Shader(GL_VERTEX_SHADER, DEFAULT_VERTEX_SHADER_SOURCE);
    }

    public static Shader getDefaultFragment() {
        return new Shader(GL_FRAGMENT_SHADER, DEFAULT_FRAGMENT_SHADER_SOURCE);
    }

    /**
     * Получение id шейдера.
     * Если шейдер ещё не был скомпилирован,
     * то компиляция произойдёт перед возвращением значения.
     *
     * @return id шейдера
     */
    public int getShaderId() {
        if (!compiled) {
            load();
        }
        return shaderId;
    }

    /**
     * Скомпилировать шейдер.
     */
    @SneakyThrows
    @Override
    public void load() {
        log.info("Try to compile shader \"{}\" of {} type", shaderSource, shaderType);

        shaderId = glCreateShader(shaderType);
        glShaderSource(shaderId, Files.readString(Paths.get(shaderSource)));
        glCompileShader(shaderId);

        var success = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            var len = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException(
                    String.format("Error in compilation of shader - %s",
                            glGetShaderInfoLog(shaderId, len))
            );
        } else {
            log.info("Shader {} compiled successful", shaderSource);
            compiled = true;
        }
    }
}
