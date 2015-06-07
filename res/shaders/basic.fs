#version 330

in vec2 f_texCoord;

out vec4 fragmentColor;

uniform sampler2D f_sampler;

void main() {
	fragmentColor = texture2D(f_sampler, f_texCoord);
}