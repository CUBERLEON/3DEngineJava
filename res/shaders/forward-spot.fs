#version 330

in vec2 f_textureCoord;
in vec3 f_normalInterpolated; vec3 f_normal;
in vec3 f_worldPosition;

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct PointLight {
	vec3 color;
	float intensity;
	Attenuation attenuation;
	vec3 position;
	float range;
};

struct SpotLight {
	PointLight pointLight;
	vec3 direction;
	float cutoff;
};

//additional variables
uniform vec3 f_eyePosition;

//lights
uniform SpotLight f_spotLight;

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

vec4 calcPointLight(PointLight pointLight, vec3 normal) {
	vec3 lightDirection = f_worldPosition - pointLight.position;
	float distanceToPoint = length(lightDirection);

	if (distanceToPoint > pointLight.range)
		return vec4(0, 0, 0, 0);

	lightDirection = normalize(lightDirection);

	vec4 color = calcLight(pointLight.color, pointLight.intensity, lightDirection, normal);

	float attenuation = max(0.00001, pointLight.attenuation.constant +
									 pointLight.attenuation.linear * distanceToPoint +
									 pointLight.attenuation.exponent * distanceToPoint * distanceToPoint);

	return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotLight, vec3 normal) {
	vec3 lightDirection = normalize(f_worldPosition - spotLight.pointLight.position);
	float spotFactor = dot(lightDirection, spotLight.direction);

	vec4 color = vec4(0, 0, 0, 0);

	if (spotFactor > spotLight.cutoff && spotLight.cutoff != 1.0) {
		color = calcPointLight(spotLight.pointLight, normal) *
				(1.0 - (1.0 - spotFactor) / (1.0 - spotLight.cutoff));
	}

	return color;
}

void main() {
	f_normal = normalize(f_normalInterpolated);

	gl_FragColor = texture(f_texture, f_textureCoord) * calcSpotLight(f_spotLight, f_normal);
}