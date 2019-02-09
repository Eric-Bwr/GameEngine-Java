#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;
layout (location = 2) in vec3 normals;

out vec2 outTextCoord;
out vec3 outLightPos;
out vec3 outNormals;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main() {
    vec4 pos = transformationMatrix * position;
    gl_Position = projectionMatrix * viewMatrix * pos;
    outTextCoord = textCoord;

    outNormals = (transformationMatrix * vec4(normals, 0.0)).xyz;
    outLightPos = lightPosition - pos.xyz;
}

#shader fragment
#version 330 core

out vec4 color;

in vec2 outTextCoord;
in vec3 outLightPos;
in vec3 outNormals;

uniform sampler2D texSampler;
uniform vec4 lightColor;
uniform float lightBrightness;
uniform float reflectivity;

void main() {
    float brightness = max(dot(normalize(outNormals), normalize(outLightPos)), 0.0);
    vec3 light = brightness * lightBrightness * lightColor.xyz;

    vec4 texColor = texture(texSampler, outTextCoord);
    color = vec4(light, 0.0) * texColor;
}