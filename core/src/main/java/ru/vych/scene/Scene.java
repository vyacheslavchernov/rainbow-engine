package ru.vych.scene;

import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.render.shader.Shader;
import ru.vych.render.shader.ShaderAttribute;
import ru.vych.render.shader.ShaderProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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


    public Scene() {}

    /**
     * Метод инициализации сцены.
     * Этот метод вызывается после того,
     * как объект сцены будет создан в {@link SceneManager}
     *
     * @see SceneManager#loadScene(Class)
     */
    public void init() {
        log.info("Init shaders in scene - {}", this.getClass());
        shaderProgram = new ShaderProgram(
                List.of(
                        new ShaderAttribute(3, GL_FLOAT),
                        new ShaderAttribute(4, GL_FLOAT)
                ),
                new Shader(GL_VERTEX_SHADER, "core/assets/shaders/default_vertex.glsl"),
                new Shader(GL_FRAGMENT_SHADER, "core/assets/shaders/default_fragment.glsl")
        );
        log.info("Scene {} initialised", this.getClass());
    }

    /**
     * Метод обновления сцены.
     * Вызывается в начале обработки каждого кадра, до начала отрисовки.
     * Используется для обновления логики сцены и объектов на ней.
     *
     * @param deltaTime время обработки предыдущего кадра
     */
    public void update(double deltaTime) {}

    /**
     * Метод отрисовки сцены.
     * Вызывается в середине обработки каждого кадра,
     * после метода обновления сцены {@link Scene#update(double)}.
     * Используется для отрисовки объектов сцены.
     * Может использоваться для отрисовки дополнительной графики.
     *
     * @param deltaTime время обработки предыдущего кадра
     */
    public void draw(double deltaTime) {}

    /**
     * Метод выгрузки ресурсов сцены.
     * Все ресурсы, специфичные для сцены,
     * которые были загружены при создании или во время жизни сцены
     * должны быть выгружены тут.
     * <p>
     * Вызывается у выгружаемой сцены во время смены сцены
     * и при закрытии приложения, если сцена активна.
     */
    public void unload() {}
}
