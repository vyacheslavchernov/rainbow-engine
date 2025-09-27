package ru.vych.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный слушатель ввода
 */
public abstract class InputKeyListener {
    protected final static Map<Integer, Boolean> pressed = new HashMap<>();

    /**
     * @return список всех нажатых клавиш\кнопок
     */
    public static List<Integer> getPressed() {
        List<Integer> pressedList = new ArrayList<>();
        pressed.forEach((key, val) -> {
            if (val) {
                pressedList.add(key);
            }
        });
        return pressedList;
    }

    /**
     * @return true, если нажата хотя бы одна клавиша\кнопка, иначе - false
     */
    public static boolean isAnyPressed() {
        return pressed.containsValue(true);
    }

    /**
     * Проверяет нажата ли конкретная клавиша\кнопка
     *
     * @param key код клавиши\кнопки
     * @return true, если нажата, иначе - false
     * @see org.lwjgl.glfw.GLFW класс с кодами клавиш\кнопок
     */
    public static boolean isPressed(int key) {
        return pressed.getOrDefault(key, false);
    }
}
