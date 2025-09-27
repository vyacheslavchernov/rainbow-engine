package ru.vych.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс приложения
 */
public abstract class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    protected static Window window;

    /**
     * Запуск приложения.
     * Создаёт и инициализирует игровое окно.
     * Вход в основной цикл приложения.
     *
     * @param config конфигурация для игрового окна
     */
    protected static void run(WindowConfig config) {
        log.info("Starting application");
        window = new Window(config);
        window.run();
        log.info("Closing application");
    }
}
