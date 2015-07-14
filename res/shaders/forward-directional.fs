#version 330

in vec2 f_textureCoord;
in vec3 f_normalInterpolated; vec3 f_normal;
in vec3 f_worldPosition;

struct DirectionalLight {
	vec3 color;
	float intensity;
	vec3 direction;
};

//additional variables
uniform vec3 f_eyePosition;

//lights
uniform DirectionalLight f_directionalLight;

//material
uniform sampler2D f_texture;
uniform float f_specularIntensity;
uniform float f_specularPower;

vec4 calcLight(vec3 color, float intensity, vec3 direction, vec3 normal) {
	float diffuseFactor = dot(normal, -direction);

	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);

	if (diffuseFactor > 0 && intensity > 0) {
		diffuseColor = vec4(color, 1) * intensity * diffuseFactor;

		vec3 directionToEye = normalize(f_eyePosition - f_worldPosition);
		vec3 reflectedDirection = normalize(reflect(direction, normal));

		float cosAngleIncidence = dot(directionToEye, reflectedDirection);
		cosAngleIncidence = clamp(cosAngleIncidence, 0, 1);

		float specularFactor = pow(cosAngleIncidence, f_specularPower);
		specularColor = vec4(color, 1) * f_specularIntensity * specularFactor; //phong specular lighting
	}

    return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.color, directionalLight.intensity, directionalLight.direction, normal);
}

void main() {
	f_normal = normalize(f_normalInterpolated);

	gl_FragColor = texture(f_texture, f_textureCoord) * calcDirectionalLight(f_directionalLight, f_normal);
}