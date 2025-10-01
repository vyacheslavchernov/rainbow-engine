package ru.vych.logic.scene;

import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Менеджер сцен.
 * Управляет жизненным циклом сцен - выгрузка, загрузка,
 * смена активной сцены; Предоставляет доступа к активной сцене
 */
public class SceneManager {
    protected static final Logger log = LoggerFactory.getLogger(SceneManager.class);

    @Getter
    private static Scene currentScene = null;

    /**
     * Загружает сцену и делает её активной.
     * Предыдущая активная сцена выгружается.
     *
     * @param sceneClass сцена для загрузки
     * @return объект новой активной сцены, которая была загружена
     */
    @SneakyThrows
    public static Scene loadScene(Class<? extends Scene> sceneClass) {
        if (currentScene != null) {
            log.info("Unload current scene - {}", currentScene);
            currentScene.unload();
        }

        log.info("Trying to load scene - {}", sceneClass);
        currentScene = sceneClass.getDeclaredConstructor().newInstance();
        log.info("Trying to init scene - {}", sceneClass);
        currentScene.init();
        log.info("Scene {} ready", sceneClass);
        return currentScene;
    }

    /**
     * Ручная выгрузка активной сцены.
     */
    public static void unloadCurrentScene() {
        if (currentScene != null) {
            log.info("Manually unload current scene - {}", currentScene);
            currentScene.unload();
            currentScene = null;
        }
    }
}
