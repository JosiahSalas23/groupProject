/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author August's PC
 */
public class Transform {
    public Vector3f pos;
    public Vector3f scale;
    
    public Transform(){
        pos = new Vector3f();
        scale = new Vector3f(1,1,1);
    }
    public Transform(Vector3f pos, Vector3f scale) {
    	this.pos = pos;
    	this.scale = scale;
    }
    
    public Matrix4f getProjection(Matrix4f target){
        target.scale(scale);
        target.translate(pos);
        return target;
    }
    public void setTransform(Vector3f position, Vector3f scale) {
    	pos = position;
    	this.scale = scale;
    }
}
