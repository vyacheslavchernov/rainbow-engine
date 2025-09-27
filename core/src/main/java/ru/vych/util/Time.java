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
    private final double timeStarted = System.nanoTime();

    /**
     * @return время прошедшее с момента запуска приложения в наносекундах
     */
    public double getTimeNano() {
        return System.nanoTime() - timeStarted;
    }

    /**
     * @return время прошедшее с момента запуска приложения в секундах
     */
    public double getTimeSecond() {
        return getTimeNano() * 1E-9;
    }
}
