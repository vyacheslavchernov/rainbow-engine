package ru.vych.render.shapes;

import org.lwjgl.BufferUtils;
import ru.vych.render.shader.ShaderProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Класс представляющий прямоугольник,
 * который может быть отрисован на экране.
 */
public class Rectangle implements Drawable {
    private final ShaderProgram shaderProgram;

    private float[] vertexArray = {
            // position             //color
            0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f, // [0] bottom right
            -0.5f, 0.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f, // [1] top left
            0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f, // [2] top right
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f, // [3] bottom left
    };

    // индексы вершин элементов должны быть перечислены против часовой стрелки
    private int[] elementArray = {
            2, 1, 0, // top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoId, vboId, eboId;

    public Rectangle(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
    }

    @Override
    public void draw(double deltaTime) {
        glUseProgram(shaderProgram.getShaderProgram());
        glBindVertexArray(vaoId);

        shaderProgram.bindAttributes();

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        shaderProgram.unbindAttributes();

        glBindVertexArray(0);
        glUseProgram(0);
    }
}
