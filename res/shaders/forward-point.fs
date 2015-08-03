#version 330

in vec2 f_textureCoord;
in vec3 f_normalInterpolated; vec3 f_normal;
in vec3 f_worldPosition;

//additional variables
uniform vec3 f_eyePosition;

//material
uniform sampler2D f_diffuse;
uniform float f_specularIntensity;
uniform float f_specularPower;

include "lighting.glh"

//lights
uniform PointLight f_pointLight;

void main() {
	f_normal = normalize(f_normalInterpolated);

	gl_FragColor = texture(f_diffuse, f_textureCoord) * calcPointLight(f_pointLight, f_normal, f_worldPosition);
}