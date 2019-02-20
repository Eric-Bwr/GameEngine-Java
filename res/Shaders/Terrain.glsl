#shader vertex
#version 400 core

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
#version 400 core

in vec2 outTextCoord;

out vec4 color;

uniform sampler2D blendMap;
uniform sampler2D groundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;

void main() {
    vec4 blendMapColor = texture(blendMap, outTextCoord);
    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    //* 40.0 == TILING THE TEXTURE; FOR GOOD LOKIN TEXTURE (Depends on terrain size)
    vec2 tiledCoords = outTextCoord * 40.0;
    vec4 backgroundTextureColor = texture(groundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;

    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

    //OLD PIECE OF TEXTURE CALCULATION CODE
    //vec4 totalColor = texture(blendMap, outTextCoord);

    color = totalColor;
}