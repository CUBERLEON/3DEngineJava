#version 400

layout (location = 0) in vec3 v_position;
layout (location = 1) in vec2 v_textureCoord;

out vec2 f_textureCoord;

//transform
uniform mat4 t_mvpTransform;

void main() {
	f_textureCoord = v_textureCoord;
	gl_Position = t_mvpTransform * vec4(v_position, 1.0);
}