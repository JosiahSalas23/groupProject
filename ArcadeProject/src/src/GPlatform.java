/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import Entity.Entity;
import Entity.Player;
import Entity.Transform;
import render.Texture;
import render.Animation;
import render.Camera;
import render.Shader;
import render.Model;
import io.Timer;
import io.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.*;

import world.Tile;
import world.TileRenderer;
import world.World;
/**
 * 
 * @author August B. Sandoval
 */
public class GPlatform {
    public static void main(String[] args){
        Window.setCallbacks();
        
        if( !glfwInit() ){
            throw new IllegalStateException("glfw failed to initalize!");
        }
        
        //glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        
        Window win = new Window();
        win.createWindow("Much game. So show off. wow!");
        
        
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        
        Camera camera = new Camera(win.getWidth(), win.getHeight());
        System.out.println(win.getWidth() + " " + win.getHeight() );
        
        int s = 256;
        float winW = (float)win.getWidth()/s;
        float winH = (float)win.getHeight()/(s*2);
        float[] BGverts = new float[]{
            -winW + winW*1.5f, winH - winH ,0,
             winW + winW*1.5f, winH - winH ,0,
             winW + winW*1.5f,-winH - winH ,0,
            -winW + winW*1.5f,-winH - winH ,0
        };


        TileRenderer tiles = new TileRenderer();
        
        Shader shader = new Shader("shader");
        
        
        float[] vertices1 = new float[]{
            -2f, 1f,0, //upper left x,y
             2f, 1f,0, //upper right x,y
             2f,-1f,0, //lower right x,y
            -2f,-1f,0  //lower left x,y
            };
        
        float[] texture1 = new float[]{
            0,0,
            1,0,
            1,1,
            0,1
        };
        
        int[] indices = new int[]{
            0,1,2,
            2,3,0
        };
        
        
        
        
        World world = new World();
        
        //Player player = new Player();
        Entity.addModel(BGverts, texture1, indices);
        Entity Title = new Entity( new Animation(1,1,"TestTitle"), new Transform(), 0 );
        //title.setTexture("TestTitle.jpg");
        //Player title = new Player();
        //title.setModel(vertices1, texture1, indices);
        //title.setTexture(new Texture("doge_1.jpg") );
       // world.setTile(Tile.text_tile2,  5, 0);k
        
        int xp=-720;
        int yp=260;
        camera.setPosition(new Vector3f(xp,yp,0));
        //projection.mul(scale, target);
        
        double frame_cap = 1.0/60.0; //limits to 60 FPS
        double time = Timer.getTime();
        double unprocessed = 0;
        double frames_time = 0;
        int frames = 0;
        
        while(!win.shouldClose()){
            boolean can_render = false;
            double time_2 = Timer.getTime();
            double passed = time_2 - time;
            unprocessed += passed;
            frames_time += passed;
            time = time_2;
            System.out.println( xp + " " + yp  );
            while(unprocessed >= frame_cap){
                if(win.hasResized()){
                    camera.setProjection(win.getWidth(), win.getHeight());
                    glViewport(0,0,win.getWidth(), win.getHeight());
                }
                
                unprocessed -= frame_cap;
                can_render = true;
                //target = scale;
                if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){             
                    glfwSetWindowShouldClose(win.getWindow(), true );
                }
                if(win.getInput().isKeyDown(GLFW_KEY_LEFT)){
                    //camera.getPosition().add(new Vector3f(1,0,0));
                    xp -= 5;
                }
                if(win.getInput().isKeyDown(GLFW_KEY_RIGHT)){
                    //camera.getPosition().add(new Vector3f(-1,0,0));
                    xp += 5;
                }
                if(win.getInput().isKeyDown(GLFW_KEY_UP)){
                   // camera.getPosition().add(new Vector3f(0,-1,0));
                    yp += 5;
                }
                if(win.getInput().isKeyDown(GLFW_KEY_DOWN)){
                    //camera.getPosition().add(new Vector3f(0,1,0));
                    yp -= 5;
                }
                //System.out.println("x: "+xp+" yp: "+yp);
                
                //player.update((float)frame_cap, win, camera, world);
                Title.setTransform(new Transform( camera.getPosition() , new Vector3f(1,1,1) ));
                Title.update((float)frame_cap, win, camera, world);
                
                
                
                world.correctCamera(camera, win);
                
                win.update();
                if(frames_time >= 1.0){
                    frames_time =0;
                        System.out.println("FPS: " + frames);
                        frames =0;
                }
            }
            if(can_render){
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                world.render(tiles, shader,camera, win);
                
                Title.render(shader, camera, world);
                win.swapBuffers();
                frames++;
            }

        }
        
        glfwTerminate();
    }
}