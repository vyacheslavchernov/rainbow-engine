#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aUV;

uniform mat4 uProj;
uniform mat4 uView;

out vec4 fColor;
out vec2 fUV;

void main() {
    fColor = aColor;
    fUV = aUV;
    gl_Position = uProj * uView * vec4(aPos, 1.0);
}