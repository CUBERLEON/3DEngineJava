#version 330

layout (location = 0) in vec3 position;

out vec4 color;

uniform float clampValue;
uniform mat4 transform;

void main() {
	color = vec4(clamp(position, 0.0, clampValue), 1.0);
	gl_Position = transform * vec4(0.25 * position, 1.0);
}