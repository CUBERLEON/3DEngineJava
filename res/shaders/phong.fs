#version 330

const int MAX_POINT_LIGHTS = 4;
const int MAX_SPOT_LIGHTS = 4;

in vec2 f_textureCoord;
in vec3 f_normalInterpolated; vec3 f_normal;
in vec3 f_worldPosition;

struct Attenuation {
	float constant;
	float linear;
	float exponent;
};

struct DirectionalLight {
	vec3 color;
	float intensity;
	vec3 direction;
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
uniform vec3 f_ambientLight;

uniform DirectionalLight f_directionalLight;

uniform PointLight f_pointLights[MAX_POINT_LIGHTS];
uniform int f_pointLightsCount;

uniform SpotLight f_spotLights[MAX_SPOT_LIGHTS];
uniform int f_spotLightsCount;

//material
uniform sampler2D f_sampler;
uniform vec3 f_baseColor;
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

/*		vec3 halfAngle = normalize(-direction + directionToEye);
		float blinnTerm = dot(normal, halfAngle);
		blinnTerm = clamp(blinnTerm, 0, 1);
		blinnTerm = cosAngleIncidence != 0.0 ? blinnTerm : 0.0;
		blinnTerm = pow(blinnTerm, f_specularPower);
		
		specularColor = vec4(color, 1) * f_specularIntensity * blinnTerm; //blinn-phong specular lighting
*/
		float specularFactor = pow(cosAngleIncidence, f_specularPower);
		specularColor = vec4(color, 1) * f_specularIntensity * specularFactor; //phong specular lighting
	}

    return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.color, directionalLight.intensity, directionalLight.direction, normal);
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

	vec4 color = vec4(f_baseColor, 1);

	vec4 textureColor = texture(f_sampler, f_textureCoord);
	if (textureColor != vec4(0, 0, 0, 0))
		color *= textureColor;

	vec4 totalLight = vec4(f_ambientLight, 1);
	
	totalLight += calcDirectionalLight(f_directionalLight, f_normal);

	for (int i = 0; i < f_pointLightsCount; i++)
		totalLight += calcPointLight(f_pointLights[i], f_normal);

	for (int i = 0; i < f_spotLightsCount; i++)
		totalLight += calcSpotLight(f_spotLights[i], f_normal);

	gl_FragColor = color * totalLight;
}