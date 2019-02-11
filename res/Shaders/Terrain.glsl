#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;
layout (location = 2) in vec3 normals;

out vec2 outTextCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main() {
    vec4 pos = position * transformationMatrix;
    gl_Position = projectionMatrix * viewMatrix * pos;
    outTextCoord = textCoord;
}

#shader fragment
#version 330 core

in vec2 outTextCoord;

out vec4 color;

uniform sampler2D blendMap;
uniform sampler2D otherTexture;
uniform sampler2D groundTexture;

void main() {
    vec4 blendMapColor = texture(blendMap, outTextCoord);
    float backTextureAmount = 1 - (blendMapColor.r);
    vec2 tiledCoords = outTextCoord * 40.0;
    vec4 groundTextureColor = texture(groundTexture, tiledCoords) * backTextureAmount;
    vec4 otherTextureColor = texture(otherTexture, tiledCoords) * blendMapColor.r;
    vec4 totalColor = groundTextureColor + otherTextureColor;

  //vec4 texColor = texture(groundTexture, outTextCoord) * totalColor;
    vec4 texColor = totalColor;
    color = texColor;
}