package ru.vych.render.shader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс аттрибутов шейдера.
 * Используется в {@link ShaderProgram} для бинда
 * аттрибутов шейдера перед началом их использования.
 */
@RequiredArgsConstructor
@Getter
public class ShaderAttribute {
    private final int attrSize;
    private final int attrType;
}
