package ru.vych.input;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.lwjgl.glfw.GLFW;


/**
 * Слушатель событий клавиатуры.
 * <p>
 * В конце каждого фрейма обязательно вызывать
 * {@link MouseListener#endFrame()} для корректной работы этого класса.
 */
@UtilityClass
public class MouseListener extends InputKeyListener {
    static {
        scrollX = 0;
        scrollY = 0;
        xPos = 0;
        yPos = 0;
        lastX = 0;
        lastY = 0;
    }

    @Getter
    private double scrollX, scrollY;
    @Getter
    private double xPos, yPos, lastX, lastY;
    @Getter
    private boolean isDragging;

    /**
     * Коллбэк обрабатывающий перемещение курсора внутри окна.
     * Обновляет информацию о позиции курсора.
     *
     * @param window активное окно
     * @param xpos позиция курсора по x
     * @param ypos позиция курсора по y
     */
    public void mousePositionCallback(long window, double xpos, double ypos) {
        lastX = xPos;
        lastY = yPos;
        xPos = xpos;
        yPos = ypos;
        isDragging = isAnyPressed();
    }

    /**
     * Коллбэк обрабатывающий нажатия кнопок мыши.
     * Обрабатывает нажатия и обновляет информацию по зажатым кнопкам.
     *
     * @param window активное окно
     * @param button код кнопки
     * @param action действие с кнопкой
     * @param mods модификаторы нажатия
     */
    public void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) {
            pressed.put(button, true);
        } else if (action == GLFW.GLFW_RELEASE) {
            pressed.put(button, false);
            isDragging = false;
        }
    }

    /**
     * Коллбэк обрабатывающий скролл колёсиком мыши.
     * Обновляет информацию о скролле.
     *
     * @param window активное окно
     * @param xOffset сдвиг по x
     * @param yOffset сдвиг по y
     */
    public void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    /**
     * Обработка конца фрейма.
     * Обязательно вызывать в конце каждого фрейма для корректной работы слушателя.
     */
    public void endFrame() {
        scrollX = 0;
        scrollY = 0;
        lastX = xPos;
        lastY = yPos;
    }

    /**
     * @return дельта позиции мыши за фрейм по x
     */
    public double getDx() {
        return lastX - xPos;
    }

    /**
     * @return дельта позиции мыши за фрейм по y
     */
    public double getDy() {
        return lastY - yPos;
    }
}
