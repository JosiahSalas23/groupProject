#version 300 es
precision mediump float;
in vec3 colour;
precision mediump float;
out vec4 out_Color;

void main(void) {

	out_Color = vec4(colour, 1.0);

} // end main