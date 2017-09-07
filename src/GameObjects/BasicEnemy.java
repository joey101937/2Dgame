/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import pkg2dgame.Game;
import pkg2dgame.GameObject;

/**
 *
 * @author Joseph
 */
public class BasicEnemy extends GameObject {
    
    ////fields
    int height= 16;
    int width = 16;
    int cooldown = 0;
   

    public BasicEnemy(int x, int y, ID id) {
        super(x, y, id);
        this.velX = 5;
        this.velY = 5;
    }
    

    @Override
    public void tick() {
        //moves each frame based on velocity
        this.x+= this.velX;
        this.y+= this.velY;
        
        if (y <= 0 || y >= Game.height){
            velY *= -1;
        }
        if (x <= 0 || x >= Game.width){
            velX *= -1;
        }

                       
    }
                 

    @Override
    public void render(Graphics g) {
        g.setColor(id.getColor());
        g.fillRect(x-width/2, y-height/2, width, height);
    }
    
}
