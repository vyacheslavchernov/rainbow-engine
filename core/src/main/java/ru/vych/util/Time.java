package ru.vych.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Утилитный класс для работы с временем.
 * Ведёт отсчёт времени от момента запуска приложения.
 */
@UtilityClass
public class Time {
    @Getter
    private final float timeStarted = System.nanoTime();

    /**
     * @return время прошедшее с момента запуска приложения в наносекундах
     */
    public float getTimeNano() {
        return System.nanoTime() - timeStarted;
    }

    /**
     * @return время прошедшее с момента запуска приложения в секундах
     */
    public float getTimeSecond() {
        return (float) (getTimeNano() * 1E-9);
    }
}
