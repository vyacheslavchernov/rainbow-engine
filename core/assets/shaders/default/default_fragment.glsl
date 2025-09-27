#version 330 core
uniform sampler2D uTexSampler;

in vec4 fColor;
in vec2 fUV;

out vec4 color;

void main() {
    color = texture(uTexSampler, fUV) * fColor;
}
