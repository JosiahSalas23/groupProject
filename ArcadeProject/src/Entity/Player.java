/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import render.Animation;
/**
 *
 * @author August's PC
 */
public class Player extends Entity{
	
	public Player(Transform transform, int ID) {
		super(new Animation(5,15,"doge"), transform, ID);
	}
	
	
}
