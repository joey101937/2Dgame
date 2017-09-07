/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import GameObjects.ID;
import Pathfinding.Pathfinder;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.Game;
import pkg2dgame.Main;

/**
 *
 * @author Joseph
 */
public class Corpse extends Unit{
    
    /* 
     Pathfinder pathfinder = null;   //the unit's pathfinder
     int health = 1;                 //the unit's current health
     int maxHealth = 1;              //the units' maximum heath
     Weapon weapon = Weapon.none;    //the unit's weapon. effects the unitFire method.
   
    
     REMEMBER, GAMEOBJECT HAS THE FOLLOWING FIELDS
     public int x, y;           //coordinates
     protected ID id;            //what kind of object it is
     protected int velX, velY;       //controls Direction
     public int width = 0;           
     public int height = 0 ;
    public int speed = 0;        
    public int destX, destY;    //destination coordinates for pathfinders
    public boolean isPathing    //weather or not the unit is pathing
    */

    public Corpse(int x, int y) {
        super(x, y,0);  //0 in team parameter means its always neutral
        pf = new Pathfinder(this);
        width = 32;
        height = 32;
        speed = 0;
        isPathing = false;
        isEthereal = true;
        id = ID.UI;
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "deathSprite" + ".png");
        }
        this.loadSprites();

    }
    
    protected int lifeSpan = 15;

    @Override
    protected void unitRender(Graphics g) {
        this.drawSprite(g);
    }

    @Override
    protected void unitTick() {
        
        //remove this when lifespan hits 0
        if (lifeSpan <= 0){
            Game.handler.getObjects().remove(this);
        }else lifeSpan--;
    }

    @Override
    protected void unitFire() {
        //corpses dont need to unitFire.
    }
    
}
