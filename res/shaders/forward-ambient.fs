#version 400

in vec2 f_textureCoord;

//material
uniform sampler2D m_diffuse;

//lights
uniform vec3 l_ambient;

void main() {
	gl_FragColor = texture(m_diffuse, f_textureCoord) * vec4(l_ambient, 1);
}