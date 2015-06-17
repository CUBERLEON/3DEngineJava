#version 330

in vec2 f_texCoord;
in vec3 f_normalInterpolated;
vec3 f_normal;
in vec3 f_worldPosition;

out vec4 fragColor;

struct BaseLight {
	vec3 color;
	float intensity;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

uniform sampler2D f_sampler;

uniform vec3 f_baseColor;
uniform vec3 f_ambientLight;
uniform DirectionalLight f_directionalLight;

uniform float f_specularIntensity;
uniform float f_specularPower;
uniform vec3 f_eyePosition;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal) {
	float diffuseFactor = dot(normal, -direction);

	vec4 diffuseColor = vec4(0, 0, 0, 0);
	vec4 specularColor = vec4(0, 0, 0, 0);

	if (diffuseFactor > 0) {
		diffuseColor = vec4(base.color, 1) * base.intensity * diffuseFactor;

		vec3 directionToEye = normalize(f_eyePosition - f_worldPosition);
		vec3 reflectedDirection = normalize(reflect(direction, normal));

		float specularFactor = dot(directionToEye, reflectedDirection);

		if (specularFactor > 0) {
			specularFactor = pow(specularFactor, f_specularPower);
			specularColor = vec4(base.color, 1) * f_specularIntensity * specularFactor;
		}
	}

	return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, directionalLight.direction, normal);
}

void main() {
	f_normal = normalize(f_normalInterpolated);

	vec4 totalLight = vec4(f_ambientLight, 1);
	totalLight += calcDirectionalLight(f_directionalLight, f_normal);

	vec4 color = vec4(f_baseColor, 1);

	vec4 texColor = texture(f_sampler, f_texCoord);
	if (texColor != vec4(0, 0, 0, 0))
		color *= texColor;

	fragColor = color * totalLight;
}