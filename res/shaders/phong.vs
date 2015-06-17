#version 330

layout (location = 0) in vec3 v_position;
layout (location = 1) in vec2 v_texCoord;
layout (location = 2) in vec3 v_normal;

out vec2 f_texCoord;
out vec3 f_normalInterpolated;
out vec3 f_worldPosition;

uniform mat4 v_transform;
uniform mat4 v_projectedTransform;

void main() {
	f_texCoord = v_texCoord;
	f_normalInterpolated = normalize(v_transform * vec4(v_normal, 0)).xyz;
	f_worldPosition = (v_transform * vec4(v_position, 1)).xyz;
	gl_Position = v_projectedTransform * vec4(v_position, 1.0);
}