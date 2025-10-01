package ru.vych.render.vertex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import ru.vych.render.shader.ShaderAttribute;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Vertex {
    public static final int VERTEX_ARRAY_SIZE = 9;

    private Vector3f position;
    private Color color;
    private Vector2f uv;

    public List<Float> asList(List<ShaderAttribute> shaderAttributes, float width, float height, Vector2f pos) {
        final List<Float> vertexList = new ArrayList<>();

        shaderAttributes.forEach(attr -> {
            switch (attr.getAttrDataType()) {
                case ShaderAttribute.POSITION_TYPE -> vertexList.addAll(List.of(
                        position.x * width + pos.x,
                        position.y * height + pos.y,
                        position.z
                ));
                case ShaderAttribute.COLOR_TYPE -> vertexList.addAll(color.asList());
                case ShaderAttribute.UV_TYPE -> vertexList.addAll(List.of(uv.x, uv.y));
            }
        });

        return vertexList;
    }
}
