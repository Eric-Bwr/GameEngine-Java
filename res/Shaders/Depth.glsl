#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 0) in vec2 textureCoords;

out vec2 outTextureCoords;

void main() {
    gl_Position = position;
    outTextureCoords = textureCoords;
}

#shader fragment
#version 330 core

in vec2 outTextureCoords;

out vec4 color;

uniform sampler2D texSampler;

void main() {
    vec4 texColor = texture(texSampler, outTextureCoords);
    color = texColor;
}