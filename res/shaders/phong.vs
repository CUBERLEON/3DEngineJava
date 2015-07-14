#version 330

layout (location = 0) in vec3 v_position;
layout (location = 1) in vec2 v_textureCoord;
layout (location = 2) in vec3 v_normal;

out vec2 f_textureCoord;
out vec3 f_normalInterpolated;
out vec3 f_worldPosition;

uniform mat4 v_mTransform;
uniform mat4 v_mvpTransform;

void main() {
	f_textureCoord = v_textureCoord;
	f_normalInterpolated = normalize(v_mTransform * vec4(v_normal, 0)).xyz;
	f_worldPosition = (v_mTransform * vec4(v_position, 1)).xyz;
	gl_Position = v_mvpTransform * vec4(v_position, 1.0);
}