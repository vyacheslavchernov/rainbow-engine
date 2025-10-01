package ru.vych.logic.entity.components;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import ru.vych.logic.entity.Entity;
import ru.vych.resources.Texture;
import ru.vych.render.vertex.Color;
import ru.vych.render.vertex.Vertex;
import ru.vych.render.vertex.VertexArray;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteComponent extends Component {
    @Getter
    @Setter
    private Texture texture;

    @Getter
    @Setter
    private float width = 150;
    @Getter
    @Setter
    private float height = 150;

    private final VertexArray vertexArrayObject = new VertexArray()
            .addVertex(new Vertex(new Vector3f(1, -1, 0), Color.WHITE(), new Vector2f(1, 1)))
            .addVertex(new Vertex(new Vector3f(-1, 1, 0), Color.WHITE(), new Vector2f(0, 0)))
            .addVertex(new Vertex(new Vector3f(1, 1, 0), Color.WHITE(), new Vector2f(1, 0)))
            .addVertex(new Vertex(new Vector3f(-1, -1, 0), Color.WHITE(), new Vector2f(0, 1)));

    private final int[] elementArray = {
            2, 1, 0, // top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoId = -1;
    private int vboId = -1;
    private int eboId = -1;

    public SpriteComponent(Texture texture, Entity entity) {
        this.texture = texture;
        init(entity);
    }

    @Override
    public void init(Entity entity) {
        super.init(entity);
        buildVaoVboEbo(entity);
    }

    @Override
    public void draw(double deltaTime, Entity entity) {
        super.draw(deltaTime, entity);

        buildVaoVboEbo(entity);

        var shaderProgram = entity.getComponent(MaterialComponent.class).getShaderProgram();
        shaderProgram.use(entity.getScene().getCamera().getProjectionMatrix(), entity.getScene().getCamera().getViewMatrix());

        shaderProgram.bindTexture(texture, 0);

        glBindVertexArray(vaoId);
        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        shaderProgram.free();
    }

    private void buildVaoVboEbo(Entity entity) {
        if (vaoId == -1) {
            vaoId = glGenVertexArrays();
        }

        glBindVertexArray(vaoId);
        float[] vertexArray = vertexArrayObject.build(
                entity.getComponent(MaterialComponent.class).getShaderProgram().getShaderAttributes(),
                width, height, entity.getPosition()
        );

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        if (vboId == -1) {
            vboId = glGenBuffers();
        }
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        if (eboId == -1) {
            eboId = glGenBuffers();
        }
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
    }
}
