/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import Entity.Entity;
import Entity.GUI;
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
        World world = new World("Test");
        
        int s = world.getScale()/4;
        float winW = (float)win.getWidth()/(s);
        float winH = (float)win.getHeight()/(s);
        float[] BGverts = new float[]{
            -winW + winW*1.5f, winH - winH ,0,
             winW + winW*1.5f, winH - winH ,0,
             winW + winW*1.5f,-winH - winH ,0,
            -winW + winW*1.5f,-winH - winH ,0
        };


        TileRenderer tiles = new TileRenderer();
        
        Shader shader = new Shader("shader");
        
        System.out.println( camera.getPosition() );
        float[] vertices1 = new float[]{
              -1f + winW/s + 1f ,0.5f - winH/s ,0,
               1f + winW/s + 1f , 0.5f - winH/s ,0,
               1f + winW/s + 1f ,-0.5f - winH/s ,0,
              -1f + winW/s + 1f ,-0.5f - winH/s ,0
            };
        
        float[] vertices2 = new float[]{
                -1f + winW/(s*0.5f) + 1f , 0.5f - winH/(s*0.5f) ,0,
                 1f + winW/(s*0.5f) + 1f , 0.5f - winH/(s*0.5f) ,0,
                 1f + winW/(s*0.5f) + 1f ,-0.5f - winH/(s*0.5f) ,0,
                -1f + winW/(s*0.5f) + 1f ,-0.5f - winH/(s*0.5f) ,0
              };
        
        float[] vertices3 = new float[]{
                -1.5f + winW/(s*0.5f) + 1f , 0.5f - winH/(s*0.5f) ,0,
                 1.5f + winW/(s*0.5f) + 1f , 0.5f - winH/(s*0.5f) ,0,
                 1.5f + winW/(s*0.5f) + 1f ,-0.5f - winH/(s*0.5f) ,0,
                -1.5f + winW/(s*0.5f) + 1f ,-0.5f - winH/(s*0.5f) ,0
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
        
        
        
        

        Entity.addModel( vertices1,texture1, indices );
        Entity.addModel( vertices2,texture1, indices );
        Entity.addModel( vertices3,texture1, indices );
        Entity emp = new Entity( new Animation(1,1,"doge"), new Transform(), 1);
        //Player player = new Player();
        GUI Title = new GUI( "TestTitle", new Transform(), 2 );
        GUI butt1 = new GUI( "Button1", new Transform(), 0 );
        GUI butt2 = new GUI( "Button1", new Transform(), 0 );
        emp.getTransform().pos.add( new Vector3f(.15f, 0,0) );
        Title.getTransform().pos.add( new Vector3f(-2f, 1f,0) );
        butt1.getTransform().pos.add( new Vector3f(-1.5f, -.8f ,0) );
        butt2.getTransform().pos.add( new Vector3f(-1.5f,-2.0f,0) );

        
        int xp=-720;
        int yp=260;
        //camera.setPosition(new Vector3f(xp,yp,0));
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
                emp.getTransform().pos.add( new Vector3f(  (float)(1* frame_cap),0,0)  );
                Title.getTransform().pos.add( new Vector3f(  (float)(1* frame_cap),0,0)  );
                butt1.getTransform().pos.add( new Vector3f(  (float)(1* frame_cap),0,0)  );
                butt2.getTransform().pos.add( new Vector3f(  (float)(1* frame_cap),0,0)  );

                
                emp.update((float)frame_cap, win, camera, world);
                world.correctCamera(camera, win);
                //Title.update(win, camera, world);
                
                
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
                butt1.render(shader, camera, world);
                butt2.render(shader, camera, world);
                //emp.render(shader, camera, world);
                win.swapBuffers();
                frames++;
            }

        }
        
        glfwTerminate();
    }
}