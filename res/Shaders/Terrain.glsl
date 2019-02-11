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
uniform sampler2D otherTexture;

void main() {
    vec4 blendMapColor = texture(blendMap, outTextCoord);
    //TRYING TO GET FUCKIN DEFAULT BLENDMAP BACKGROUND (BLACK)
    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    //FIXME!!!!!!!!!!!!!!!!INTERESTING!!!!!!!!!!   Try to * tiledCoords by 40    !!!!!!!!!!!!!!!
    vec2 tiledCoords = outTextCoord; //TILING:* 40.0;
    //SHOULD BE BLACK SO THE TERRAIN SHOULD BE TEXTURED IN GRASS
    vec4 groundTextureColor = texture(groundTexture, tiledCoords) * backTextureAmount;
    //JUST GETTING THE RED PIECE OF SHIT AKA. COLOR (TRYING)
    vec4 otherTextureColor = texture(otherTexture, tiledCoords) * blendMapColor.r;

    vec4 totalColor = groundTextureColor + otherTextureColor;

    //OLD PIECE OF TEXTURE CALCULATION CODE
    //vec4 texColor = texture(groundTexture, outTextCoord) * totalColor;

    color = totalColor;
}