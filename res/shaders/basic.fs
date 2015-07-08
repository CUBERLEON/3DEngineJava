#version 330

in vec2 f_textureCoord;

out vec4 fragmentColor;

uniform vec3 f_color;
uniform sampler2D f_sampler;

void main() {
	vec4 textureColor = texture(f_sampler, f_textureCoord);

	if (textureColor == vec4(0, 0, 0, 0))
		fragmentColor = vec4(f_color, 1);
	else
		fragmentColor = textureColor * vec4(f_color, 1);
}