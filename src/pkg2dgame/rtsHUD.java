/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import java.awt.Graphics;
import java.awt.MouseInfo;

/**
 *  This is what draws the selection box when you drag the mouse
 * @author Joseph
 */
public class rtsHUD extends GameObject{
        /*
    REMEMBER, GAMEOBJECT HAS THE FOLLOWING FIELDS
    public int x, y;           //coordinates
    protected ID id;            //what kind of object it is
    protected int velX, velY;       //velocity, moves the object
    public int width = 0;
    public int height = 0 ;
    public int speed = 0;           
    public int destX = x, destY=y;    //destination coordinates for pathfinders
    public boolean isPathing = true;    //is pathing
    public boolean isSelected = false;  //is selected
    public boolean isEthereal = false;    //can it move through things? ///can trvel through other game objects
    public Pathfinder pf = null;        // the pathfinder
    */

    public rtsHUD(int x, int y, ID id) {
        super(x, y, id);
    }   
    
    @Override
    public void tick(){
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.id.getColor());
       if(Game.isMouseDown){
            drawSelectionBox(g);}
    }

    private void drawSelectionBox(Graphics g) {
        int mlX = Game.mouseLocX;
        int mlY = Game.mouseLocY;
        int width = Math.abs(mlX- Game.downx);
        int height = Math.abs(mlY - Game.downy);
        boolean down = false;
        boolean up = false;
        boolean right = false;
        boolean left = false;
        int buffer = 2;     //minimum distance the cursor must move before the selection box is drawn
        if (Game.downx > mlX+buffer) {
            left = true;
        }
        if (Game.downx < mlX-buffer) {
            right = true;
        }
        if (Game.downy < mlY-buffer) {
            down = true;
        }
        if (Game.downy > mlY+buffer) {
            up = true;
        }
         if(left && up) g.drawRect(mlX, mlY, width, height);
        if (left && down) g.drawRect(mlX, mlY-height, width, height);
        if (right && down) g.drawRect(Game.downx, Game.downy, width, height);
        if (right && up)g.drawRect(Game.downx, Game.downy-height, width, height);
        
    }
}
