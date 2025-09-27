package ru.vych.app;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.vych.scene.EmptyScene;
import ru.vych.scene.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;

/**
 * Класс описывающий конфигурацию игрового окна {@link Window}
 */
@Getter
@Setter
@Accessors(chain = true)
public class WindowConfig {
    //region defaults
    public static final int DEFAULT_WINDOW_WIDTH = 640;
    public static final int DEFAULT_WINDOW_HEIGHT = 480;
    public static final String DEFAULT_WINDOW_TITLE = "Rainbow";
    public static final int DEFAULT_WINDOW_RESIZABLE = GLFW_TRUE;
    public static final int DEFAULT_WINDOW_MAXIMIZED = GLFW_FALSE;
    public static final int DEFAULT_WINDOW_DECORATED = GLFW_TRUE;
    public static final int DEFAULT_WINDOW_ALWAYS_ON_TOP = GLFW_FALSE;
    public static final int DEFAULT_WINDOW_TRANSPARENT_FRAME_BUFFER = GLFW_FALSE;
    public static final boolean DEFAULT_WINDOW_CENTERED_ON_CREATE = true;
    public static final Class<? extends Scene> DEFAULT_WINDOW_START_SCENE = EmptyScene.class;
    //endregion defaults

    //region fields
    private int width;
    private int height;
    private String title;
    private int resizable;
    private int maximized;
    private int decorated;
    private int alwaysOnTop;
    private int transparentFrameBuffer;
    private boolean centeredOnCreate;
    private Class<? extends Scene> startScene;
    //endregion fields

    /**
     * @return конфиг окна по умолчанию
     */
    public static WindowConfig withDefault() {
        return new WindowConfig()
                .setWidth(DEFAULT_WINDOW_WIDTH)
                .setHeight(DEFAULT_WINDOW_HEIGHT)
                .setTitle(DEFAULT_WINDOW_TITLE)
                .setResizable(DEFAULT_WINDOW_RESIZABLE)
                .setMaximized(DEFAULT_WINDOW_MAXIMIZED)
                .setDecorated(DEFAULT_WINDOW_DECORATED)
                .setAlwaysOnTop(DEFAULT_WINDOW_ALWAYS_ON_TOP)
                .setTransparentFrameBuffer(DEFAULT_WINDOW_TRANSPARENT_FRAME_BUFFER)
                .setCenteredOnCreate(DEFAULT_WINDOW_CENTERED_ON_CREATE)
                .setStartScene(DEFAULT_WINDOW_START_SCENE);
    }
}
