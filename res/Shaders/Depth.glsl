#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textureCoords;
layout (location = 2) in vec3 normals;

out vec2 outTextureCoords;

uniform mat4 lightSpaceMatrix;
uniform mat4 model = mat4(1.0);

void main() {
    gl_Position = lightSpaceMatrix * model * position;

    outTextureCoords = textureCoords;
}

#shader fragment
#version 330 core

in vec2 outTextureCoords;

out vec4 color;

uniform sampler2D depthMap;

void main() {
    float depthValue = texture(depthMap, outTextureCoords).r;
    color = vec4(vec3(depthValue), 1.0);
}