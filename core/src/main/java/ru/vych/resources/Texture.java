package ru.vych.resources;

import lombok.Getter;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;


public class Texture extends AbstractResource implements Bindable {
    public static final String DEFAULT_TEXTURE_SOURCE = "core/assets/textures/grid.png";

    private final String source;
    private int texId;
    @Getter
    private IntBuffer width;
    @Getter
    private IntBuffer height;
    @Getter
    private IntBuffer channels;
    @Getter
    private ByteBuffer image;

    private boolean loaded = false;

    public static Texture getDefault() {
        return new Texture(DEFAULT_TEXTURE_SOURCE);
    }

    public Texture(String source) {
        this.source = source;
    }

    @Override
    public void load() {
        if (loaded) {
            log.info("Texture \"{}\" is already loaded", source);
            return;
        }

        texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        channels = BufferUtils.createIntBuffer(1);
        image = stbi_load(source, width, height, channels, 0);

        if (image != null) {
            var channelsCount = channels.get(0);
            if (channelsCount != 3 && channelsCount != 4) {
                throw new IllegalStateException(String.format(
                        "Unsupported image. There not 3 or 4 channels. Altual channels count - %d",
                        channelsCount
                ));
            }

            var channel = channelsCount == 3 ? GL_RGB : GL_RGBA;
            glTexImage2D(
                    GL_TEXTURE_2D, 0, channel,
                    width.get(0), height.get(0), 0,
                    channel, GL_UNSIGNED_BYTE, image
            );
        } else {
            throw new IllegalStateException(String.format(
                    "Couldn't load texture - \"%s\"", source
            ));
        }

        stbi_image_free(image);

        loaded = true;
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texId);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
