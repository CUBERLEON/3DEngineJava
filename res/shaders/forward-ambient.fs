#version 330

in vec2 f_textureCoord;

//material
uniform sampler2D m_diffuse;

//lights
uniform vec3 r_ambientLight;

void main() {
	gl_FragColor = texture(m_diffuse, f_textureCoord) * vec4(r_ambientLight, 1);
}