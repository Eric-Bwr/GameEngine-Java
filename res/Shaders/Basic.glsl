#shader vertex
#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textCoord;

out vec2 outTextCoord;
out vec3 outPos;

void main() {
    gl_Position = position;
    outTextCoord = textCoord;
    outPos = position.xyz;
}

#shader fragment
#version 330 core

out vec4 color;

in vec2 outTextCoord;
in vec3 outPos;

uniform vec2 mousePos;
uniform sampler2D texSampler;

void main() {
    float dis = length(mousePos.xy - outPos.xy);
    float b = 0.1 / dis * 2;
    vec4 texColor = texture(texSampler, outTextCoord);
    color = texColor * b;
}