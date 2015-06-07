#version 330

in vec2 f_texCoord;

out vec4 fragmentColor;

uniform vec3 f_color;
uniform sampler2D f_sampler;

void main() {
	vec4 texColor = texture(f_sampler, f_texCoord);

	if (texColor == vec4(0, 0, 0, 0))
		fragmentColor = vec4(f_color, 1);
	else
		fragmentColor = texColor * vec4(f_color, 1);
}