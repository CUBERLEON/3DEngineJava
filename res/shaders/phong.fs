#version 330

const int MAX_POINT_LIGHTS = 4;

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

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct PointLight {
	BaseLight base;
	Attenuation attenuation;
	vec3 position;
	float range;
};

uniform sampler2D f_sampler;

uniform vec3 f_baseColor;
uniform vec3 f_ambientLight;
uniform DirectionalLight f_directionalLight;
uniform PointLight f_pointLights[MAX_POINT_LIGHTS];
uniform int f_pointLightsCount; 

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

		float cosAngleIncidence = dot(directionToEye, reflectedDirection);
		cosAngleIncidence = clamp(cosAngleIncidence, 0, 1);

/*		vec3 halfAngle = normalize(-direction + directionToEye);
		float blinnTerm = dot(normal, halfAngle);
		blinnTerm = clamp(blinnTerm, 0, 1);
		blinnTerm = cosAngleIncidence != 0.0 ? blinnTerm : 0.0;
		blinnTerm = pow(blinnTerm, f_specularPower);
		
		specularColor = vec4(base.color, 1) * f_specularIntensity * blinnTerm; //blinn-phong specular lighting
*/
		float specularFactor = pow(cosAngleIncidence, f_specularPower);
		specularColor = vec4(base.color, 1) * f_specularIntensity * specularFactor; //phong specular lighting
	}

	return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, directionalLight.direction, normal);
}

vec4 calcPointLight(PointLight pointLight, vec3 normal) {
	vec3 lightDirection = f_worldPosition - pointLight.position;
	float distanceToPoint = length(lightDirection);

	if (distanceToPoint > pointLight.range)
		return vec4(0, 0, 0, 0);

	lightDirection = normalize(lightDirection);

	vec4 color = calcLight(pointLight.base, lightDirection, normal);

	float attenuation = max(0.00001, pointLight.attenuation.constant +
									 pointLight.attenuation.linear * distanceToPoint +
									 pointLight.attenuation.exponent * distanceToPoint * distanceToPoint);

	return color / attenuation;
}

void main() {
	f_normal = normalize(f_normalInterpolated);

	vec4 totalLight = vec4(f_ambientLight, 1);
	totalLight += calcDirectionalLight(f_directionalLight, f_normal);

	for (int i = 0; i < f_pointLightsCount; i++)
		totalLight += calcPointLight(f_pointLights[i], f_normal);

	vec4 color = vec4(f_baseColor, 1);

	vec4 texColor = texture(f_sampler, f_texCoord);
	if (texColor != vec4(0, 0, 0, 0))
		color *= texColor;

	fragColor = color * totalLight;
}