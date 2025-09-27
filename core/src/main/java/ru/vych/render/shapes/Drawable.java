package ru.vych.render.shapes;

/**
 * Интерфейс для всех объектов,
 * которые могут быть отрисованы в игровом окне.
 */
public interface Drawable {
    /**
     * Метод отрисовки объекта.
     *
     * @param deltaTime время обработки предыдущего кадра
     */
    void draw(double deltaTime);
}
