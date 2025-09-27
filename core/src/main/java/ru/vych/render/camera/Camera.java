package ru.vych.render.camera;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    public static final float DEFAULT_NEAR_CLIP = 0;
    public static final float DEFAULT_FAR_CLIP = 100;

    @Getter
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    @Getter
    private final Vector2f position;

    @Getter
    private float viewWidth;
    @Getter
    private float viewHeight;

    public Camera(Vector2f position) {
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public void adjustProjection(float width, float height) {
        adjustProjection(width, height, DEFAULT_NEAR_CLIP, DEFAULT_FAR_CLIP);
    }

    public void adjustProjection(float width, float height, float nearClip, float farClip) {
        projectionMatrix.identity();
        projectionMatrix.ortho(0, width, 0, height, nearClip, farClip);
        viewWidth = width;
        viewHeight = height;
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0, 0, -1);
        Vector3f cameraUp = new Vector3f(0, 1, 0);

        viewMatrix.identity();
        viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20),
                cameraFront.add(position.x, position.y, 0),
                cameraUp
        );

        return viewMatrix;
    }

    public void centerCameraInViewOrigin() {
        position.x = -viewWidth/2;
        position.y = -viewHeight/2;
    }
}
