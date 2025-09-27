package ru.vych.app;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vych.input.KeyboardListener;
import ru.vych.input.MouseListener;
import ru.vych.scene.SceneManager;
import ru.vych.util.Time;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Класс игрового окна.
 */
public class Window {
    private static final Logger log = LoggerFactory.getLogger(Window.class);

    private final WindowConfig config;

    private long window;

    protected Window(WindowConfig config) {
        this.config = config;
    }

    /**
     * Метод запуска игрового окна.
     * Запускает создание и инициализации игрового окна.
     * Начинает главный цикл приложения.
     * При закрытии приложения обеспечивает освобождение всех ресурсов.
     */
    public void run() {
        log.info("Starting LWJGL window. Version {}.", Version.getVersion());

        log.info("Init window");
        init();
        log.info(
                "Window \"{}\"({}) [{}x{}] initialised",
                config.getTitle(), window, config.getWidth(), config.getHeight()
        );

        log.info("Enter main loop");
        loop();

        log.info("Free resources");
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        SceneManager.unloadCurrentScene();

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    /**
     * Метод инициализации игрового окна.
     * Инициализирует контекст, создаёт и настраивает игровое окно,
     * загружает стартовую сцену. Отображает созданное окно на экран.
     */
    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, config.getResizable());
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, config.getDecorated());
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, config.getMaximized());
        GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, config.getAlwaysOnTop());
        GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, config.getTransparentFrameBuffer());

        window = GLFW.glfwCreateWindow(config.getWidth(), config.getHeight(), config.getTitle(), NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        GLFW.glfwSetCursorPosCallback(window, MouseListener::mousePositionCallback);
        GLFW.glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        GLFW.glfwSetKeyCallback(window, KeyboardListener::keyCallback);

        GLFW.glfwSetWindowSizeCallback(window, (_window, width, height) -> {
            glViewport(0, 0, width, height);
            SceneManager.getCurrentScene().adjustProjection(width, height);
        });

        try (MemoryStack stack = stackPush()) {
            if (config.isCenteredOnCreate()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);

                GLFW.glfwGetWindowSize(window, pWidth, pHeight);
                GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
                GLFW.glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
            }

            GLFW.glfwMakeContextCurrent(window);
            GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(window);
        }
    }

    /**
     * Главный цикл приложения.
     * Цикл состоит из следующих элементов (в порядке их вызова):
     * <ul>
     *     <li>Получение событий приложения их обработка</li>
     *     <li>Обновление активной сцены</li>
     *     <li>Отрисовка активной сцены</li>
     * </ul>
     */
    private void loop() {
        GL.createCapabilities();

        var beginTime = Time.getTimeSecond();
        float endTime;
        float deltaTime = -1;

        SceneManager.loadScene(config.getStartScene()).adjustProjection(config.getWidth(), config.getHeight());

        while (!GLFW.glfwWindowShouldClose(window)) {
            glfwPollEvents();
            var scene = SceneManager.getCurrentScene();
            scene.update(deltaTime);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            scene.draw(deltaTime);
            GLFW.glfwSwapBuffers(window);

            MouseListener.endFrame();

            endTime = Time.getTimeSecond();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }

}
