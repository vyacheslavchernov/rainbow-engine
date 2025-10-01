package ru.vych.render.shader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Класс аттрибутов шейдера.
 * Используется в {@link ShaderProgram} для бинда
 * аттрибутов шейдера перед началом их использования.
 */
@RequiredArgsConstructor
@Getter
public class ShaderAttribute {
    public final static String DEFAULT_SHADER_ATTRIBUTES = "core/assets/shaders/default/attributes.attr";

    public static final int
        POSITION_TYPE = 0,
        COLOR_TYPE = 1,
        UV_TYPE = 2;

    private final int attrSize;
    private final int attrType;
    private final int attrDataType;

    public static List<ShaderAttribute> getDefault() {
        return loadAttributes(DEFAULT_SHADER_ATTRIBUTES);
    }

    public static List<ShaderAttribute> loadAttributes(String source) {
        final StringBuilder sb = new StringBuilder();
        try (var lines = Files.lines(Paths.get(source))) {
            lines.forEach(line -> {
                if (!line.isEmpty() && line.charAt(0) != '#') {
                    sb.append(line);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final List<ShaderAttribute> attributeList = new ArrayList<>();
        Stream.of(sb.toString().split(";")).forEach(rawAttribute -> {
            String[] split = rawAttribute.split(",");
            attributeList.add(new ShaderAttribute(
                    Integer.decode(split[0].trim()),
                    Integer.decode(split[1].trim()),
                    Integer.decode(split[2].trim())
            ));
        });
        return attributeList;
    }
}
