#version 330

in vec2 f_texCoord;

out vec4 fragColor;

uniform vec3 f_baseColor;
uniform vec3 f_ambientLight;
uniform sampler2D f_sampler;

void main() {
	vec4 totalLight = vec4(f_ambientLight, 1.0);
	vec4 color = vec4(f_baseColor, 1);

	vec4 texColor = texture(f_sampler, f_texCoord);
	if (texColor != vec4(0, 0, 0, 0))
		color *= texColor;

	fragColor = color * totalLight;
}