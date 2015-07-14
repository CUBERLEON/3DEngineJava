#version 330

in vec2 f_textureCoord;

//lights
uniform vec3 f_ambientIntensity;

//material
uniform sampler2D f_texture;

void main() {
	gl_FragColor = texture(f_texture, f_textureCoord) * vec4(f_ambientIntensity, 1);
}