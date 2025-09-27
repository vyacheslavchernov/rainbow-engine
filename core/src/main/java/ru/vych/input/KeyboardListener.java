package ru.vych.input;

import lombok.experimental.UtilityClass;
import org.lwjgl.glfw.GLFW;

/**
 * Слушатель событий клавиатуры
 */
@UtilityClass
public class KeyboardListener extends InputKeyListener {
    /**
     * Коллбэк события нажатия клавиш клавиатуры.
     * Обрабатывает нажатия и обновляет информацию по зажатым клавишам.
     *
     * @param window активное окно
     * @param key код клавиши
     * @param scancode scancode нажатой клавиши
     * @param action действие с клавишей
     * @param mods модификаторы нажатия
     */
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) {
            pressed.put(key, true);
        } else if (action == GLFW.GLFW_RELEASE) {
            pressed.put(key, false);
        }
    }
}
