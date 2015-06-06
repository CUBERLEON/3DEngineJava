#version 330

layout (location = 1) in vec3 pos;

out vec4 color;

uniform mat4 transform;

void main() {
	color = clamp(abs(vec4(pos, 1.0)), 0.0, 1.0);
	gl_Position = transform * vec4(0.25*pos, 1.0);
}