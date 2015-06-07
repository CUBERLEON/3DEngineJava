#version 330

layout (location = 0) in vec3 v_position;
layout (location = 1) in vec2 v_texCoord;

out vec2 f_texCoord;

uniform mat4 v_transform;

void main() {
	f_texCoord = v_texCoord;
	gl_Position = v_transform * vec4(v_position, 1.0);
}