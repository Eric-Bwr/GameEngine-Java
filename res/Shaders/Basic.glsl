#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;

out vec2 outTextCoord;

void main() {
    gl_Position = position;
    outTextCoord = textCoord;
}

#shader fragment
#version 330 core

out vec4 color;

in vec2 outTextCoord;

uniform sampler2D texSampler;

void main() {
    color = texture(texSampler, outTextCoord);
}