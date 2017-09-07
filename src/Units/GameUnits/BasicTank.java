/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import Pathfinding.*;
import Units.Type;
import Units.Unit;
import Units.Weapon;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.Main;

/**
 *
 * @author Joseph
 */
public class BasicTank extends Unit{

    public BasicTank(int x, int y, int team) {
        super(x, y, team);
        this.name = "Tank";
        this.desc = "Core Armored Vehicle";
        this.health = 100;
        this.maxHealth = 100;
        this.weapon = Weapon.cannon;
        this.id = ID.Unit;
        this.speed = 1;
        this.width = 40;
        this.height = 40;
        this.unitTypes.add(Type.Vehicle);
        this.isMilitary = true;
        this.setPathing(false);
        pf = new Pathfinder(this);
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "TankSprite" + i + ".png");
        }
       for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "TankSpriteMF" + i + ".png");
        }
       this.loadSprites();
       this.loadMFSprites();
       
       this.mCost = 125;
       this.oCost = 0;
    }
 
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
    @Override
    protected void unitRender(Graphics g) {

    }

    @Override
    protected void unitTick() {

    }

    @Override
    protected void unitFire() {

    }
    
}
