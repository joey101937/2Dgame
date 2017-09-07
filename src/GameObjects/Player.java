/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Pathfinding.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.*;

/**
 *
 * @author Joseph
 */
public class Player extends GameObject {

    public Player(int x, int y, ID id) {
        super(x, y, id);
        this.width = 32;
        this.height = 32;
        this.speed = 2;
        this.pf  = new Pathfinder(this);
        this.setPathing(false);
        for (int i =0; i <8;i++){
            this.spriteIcon[i]= new File(Main.getDir()+ Main.assets + "PlayerSprite"+i+".png");
        }
        
    }
    ///fields
    boolean pathingOn   = false;

    @Override
    public void tick() {
        //moves it each tick based on its velocity
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());
        if(this.isPathing)this.pf.move();
       
        //this.x=this.clamp(x, Game.width-this.width/2, 5+this.width/2);
        //this.y=this.clamp(y, Game.height-this.height/2, 5+this.width/2);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(id.getColor());
        g.fillRect(x - width / 2, y - height / 2, width, height);
        if(this.isSelected){        //only displays if selected
        int boxW = this.width+10;                //selection box width
        int boxH = this.height+10;               //selection box height
        g.setColor(Color.red);
        g.drawRect(x - boxW/2 , y - boxH/2 , boxW, boxH);
        }
       this.drawSprite(g);
    }
    
    @Override
    public String toString(){
    
    return "Player object "+ "ID: " + this.id+ " etherial:" +this.isEthereal;
    }
    

}
