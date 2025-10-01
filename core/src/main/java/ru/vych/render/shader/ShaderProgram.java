package ru.vych.render.shader;

import lombok.Getter;
import org.joml.*;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.resources.Texture;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

/**
 * Шейдерная программа. Объединяет в себе несколько разных видов
 * шейдеров для дальнейшего их использования в процессе рендеринга.
 */
@Getter
public class ShaderProgram {
    public static final int FLOAT_SIZE_BYTES = Float.BYTES;

    private static final Logger log = LoggerFactory.getLogger(ShaderProgram.class);

    private final int shaderProgramId;
    private final List<ShaderAttribute> shaderAttributes;

    private final int shaderVertexSizeBytes;

    @Getter
    private boolean beingUse = false;

    public static ShaderProgram getDefault() {
        return new ShaderProgram(
                ShaderAttribute.getDefault(),
                Shader.getDefaultVertex(), Shader.getDefaultFragment()
        );
    }

    public ShaderProgram(List<ShaderAttribute> shaderAttributes, Shader... shaders) {
        this.shaderAttributes = shaderAttributes;
        shaderVertexSizeBytes = shaderAttributes.stream()
                .mapToInt(ShaderAttribute::getAttrSize).sum() * FLOAT_SIZE_BYTES;

        log.info("Linking shaders to program");
        shaderProgramId = glCreateProgram();
        Arrays.stream(shaders).forEach((shader -> {
            log.info("Link shader {} to program", shader.getShaderSource());
            glAttachShader(shaderProgramId, shader.getShaderId());
        }));
        glLinkProgram(shaderProgramId);

        var success = glGetProgrami(shaderProgramId, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            var len = glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException(
                    String.format("Error while linking shaders - %s",
                            glGetProgramInfoLog(shaderProgramId, len))
            );
        } else {
            log.info("Shaders linked in program");
        }
    }

    public void use(Matrix4f projection, Matrix4f view) {
        if (!beingUse) {
            beingUse = true;
            glUseProgram(shaderProgramId);
            uploadMat4f("uProj", projection);
            uploadMat4f("uView", view);
            bindAttributes();
        }
    }

    public void free() {
        unbindAttributes();
        glUseProgram(0);
        beingUse = false;
    }

    /**
     * Забиндить аттрибуты шейдеров.
     * Необходимо вызывать в начале метода отрисовки
     * {@link ru.vych.render.shapes.Drawable#draw(double, ru.vych.render.camera.Camera)}
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
     * Необходимо вызывать в конце метода отрисовки
     * {@link ru.vych.render.shapes.Drawable#draw(double, ru.vych.render.camera.Camera)}
     */
    public void unbindAttributes() {
        for (int i = 0; i < shaderAttributes.size(); i++) {
            glDisableVertexAttribArray(i);
        }
    }

    public void bindTexture(Texture texture, int slot) {
        uploadTexture("uTexSampler", slot);
        glActiveTexture(slot);
        texture.bind();
    }

    public void uploadMat4f(String varName, Matrix4f mat4f) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4f.get(matBuffer);
        glUniformMatrix4fv(location, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3f) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3f.get(matBuffer);
        glUniformMatrix3fv(location, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform4f(location, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform3f(location, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform2f(location, vec.x, vec.y);
    }

    public void uploadFloat(String varName, float val) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform1f(location, val);
    }

    public void uploadInt(String varName, int val) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform1i(location, val);
    }

    public void uploadTexture(String varName, int slot) {
        checkBeingUse();
        var location = glGetUniformLocation(shaderProgramId, varName);
        glUniform1i(location, slot);
    }

    private void checkBeingUse() {
        if (!beingUse) {
            throw new IllegalStateException(
                    "Shader program should be in use for upload variables"
            );
        }
    }
}
