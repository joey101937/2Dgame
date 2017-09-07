/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects.Projectiles;
import GameObjects.ID;
import Pathfinding.*;
import Units.*;
import java.awt.Graphics;
import pkg2dgame.*;

/**
 *
 * @author Joseph
 */
public class Cannonball extends Projectile{
    
    public Cannonball(int x, int y, int Damage, Unit target) {
        super(x, y, Damage, target);
        this.speed = 7;
        this.width = 6;
        this.height = 6;
    }

    
    /******************* GAME OBJECT FIELDS********************************
        public int x, y;           //coordinates
    public ID id;            //what kind of object it is
    protected int velX, velY;       //velocity, moves the object
    public int width = 0;
    public int height = 0 ;
    public int speed = 0;           
    public int destX = x, destY=y;    //destination coordinates for pathfinders
    public boolean isPathing = true;    //is pathing
    public boolean isSelected = false;  //is selected
    public boolean isEthereal = false;    //can it move through things? ///can trvel through other game objects
    public Pathfinder pf = null;        // the pathfinder
    public File[] spriteIcon = new File[8];         //the visual sprite icon. 8 for each diection
    public BufferedImage spriteImage= null;            //the buffered image with the spriteicon painted on it
    public Direction lastDir = Direction.up;            //Last non-stopped direction that the object was traveling in. defaults to up.
    
    public abstract void tick();
    
    
    
    
    
    
    */
    
    
    
    @Override
    public void ProjectileTick() {
        
    }
    
    @Override
        public void impact(boolean hit){
            
       //Unit u = (Unit)target;
       if(!this.detonated){target.health -= damage;this.detonated=true;}  //deals damage if it hasnt been already 
       if(explodes) Game.handler.addObject(new Blast(target.x,target.y));
       this.discard();
    }
    

    @Override
    public void render(Graphics g) {
         g.setColor(id.getColor());
        //g.fillRect(x - width / 2, y - height / 2, width, height);
        g.fillOval(x - width / 2, y - height / 2, width, height);
    }
    
}
