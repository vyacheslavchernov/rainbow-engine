package ru.vych.logic.scene;

import lombok.Getter;
import org.joml.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.render.camera.Camera;
import ru.vych.render.shader.ShaderProgram;

/**
 * Абстрактная сцена.
 * От этого класса должны наследоваться все другие сцена.
 * <p>
 * Описывает все необходимые методы (инициализация, обновление, выгрузка),
 * создаёт логгер для сцены.
 */
public abstract class Scene {
    protected static final Logger log = LoggerFactory.getLogger(Scene.class);

    protected ShaderProgram shaderProgram;
    @Getter
    protected Camera camera;


    public Scene() {
    }

    /**
     * Метод инициализации сцены.
     * Этот метод вызывается после того,
     * как объект сцены будет создан в {@link SceneManager}
     *
     * @see SceneManager#loadScene(Class)
     */
    public void init() {
        log.info("Creating camera");
        camera = new Camera(new Vector2f());

        log.info("Scene {} initialised", this.getClass());
    }

    /**
     * Метод обновления сцены.
     * Вызывается в начале обработки каждого кадра, до начала отрисовки.
     * Используется для обновления логики сцены и объектов на ней.
     *
     * @param deltaTime время обработки предыдущего кадра
     */
    public void update(double deltaTime) {
    }

    /**
     * Метод отрисовки сцены.
     * Вызывается в середине обработки каждого кадра,
     * после метода обновления сцены {@link Scene#update(double)}.
     * Используется для отрисовки объектов сцены.
     * Может использоваться для отрисовки дополнительной графики.
     *
     * @param deltaTime время обработки предыдущего кадра
     */
    public void draw(double deltaTime) {
    }

    /**
     * Метод выгрузки ресурсов сцены.
     * Все ресурсы, специфичные для сцены,
     * которые были загружены при создании или во время жизни сцены
     * должны быть выгружены тут.
     * <p>
     * Вызывается у выгружаемой сцены во время смены сцены
     * и при закрытии приложения, если сцена активна.
     */
    public void unload() {
    }

    public void adjustProjection(float width, float height) {
        camera.adjustProjection(width, height);
    }

    public void adjustProjection(float width, float height, float nearClip, float farClip) {
        camera.adjustProjection(width, height, nearClip, farClip);
    }
}
