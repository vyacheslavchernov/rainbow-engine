package ru.vych.render.vertex;

import lombok.Getter;
import org.joml.Vector2f;
import ru.vych.render.shader.ShaderAttribute;

import java.util.ArrayList;
import java.util.List;

import static ru.vych.render.vertex.Vertex.VERTEX_ARRAY_SIZE;

public class VertexArray {
    @Getter
    private final List<Vertex> vertexList = new ArrayList<>();

    public VertexArray addVertex(Vertex vertex) {
        vertexList.add(vertex);
        return this;
    }

    public float[] build(List<ShaderAttribute> shaderAttributes, float width, float height, Vector2f position) {
        float[] vertexArray = new float[vertexList.size() * VERTEX_ARRAY_SIZE];
        final int[] offset = {0};
        vertexList.forEach(vertex -> {
            final int[] index = {0};
            vertex.asList(shaderAttributes, width, height, position).forEach(p -> {
                vertexArray[offset[0] * VERTEX_ARRAY_SIZE + index[0]] = p;
                index[0]++;
            });
            offset[0]++;
        });
        return vertexArray;
    }
}
