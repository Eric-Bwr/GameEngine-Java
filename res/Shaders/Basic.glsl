#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;
layout (location = 2) in vec3 normals;

out vec2 outTextCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    vec4 pos = transformationMatrix * position;
    gl_Position = projectionMatrix * viewMatrix * pos;
    outTextCoord = textCoord;
}

#shader fragment
#version 330 core

out vec4 color;

in vec2 outTextCoord;

uniform sampler2D texSampler;

void main() {
    vec4 texColor = texture(texSampler, outTextCoord);
    color = texColor;
}