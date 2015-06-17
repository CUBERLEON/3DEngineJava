#version 330

in vec2 f_texCoord;
in vec3 f_normal;

out vec4 fragColor;

struct BaseLight {
	vec3 color;
	float intensity;
};

struct DirectionalLight {
	BaseLight base;
	vec3 direction;
};

uniform vec3 f_baseColor;
uniform vec3 f_ambientLight;
uniform sampler2D f_sampler;
uniform DirectionalLight f_directionalLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal) {
	float diffuseFactor = max(dot(normal, -direction), 0);

	vec4 diffuseColor = vec4(base.color, 1) * base.intensity * diffuseFactor;

	return diffuseColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
	return calcLight(directionalLight.base, directionalLight.direction, normal);
}

void main() {
	vec4 totalLight = vec4(f_ambientLight, 1);
	totalLight += calcDirectionalLight(f_directionalLight, f_normal);

	vec4 color = vec4(f_baseColor, 1);

	vec4 texColor = texture(f_sampler, f_texCoord);
	if (texColor != vec4(0, 0, 0, 0))
		color *= texColor;

	fragColor = color * totalLight;
}