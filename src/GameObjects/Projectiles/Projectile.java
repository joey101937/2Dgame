/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects.Projectiles;

import GameObjects.ID;
import Pathfinding.Pathfinder;
import Units.Blast;
import Units.Unit;
import pkg2dgame.Game;
import pkg2dgame.GameObject;

/**
 *
 * @author Joseph
 */
public abstract class Projectile extends GameObject {

    //Fields
    public int damage;
    public Unit target;
    public boolean explodes = true;
    public int timeToLive = 100;
    public boolean detonated = false;  //has it detonated yet?

    public Projectile(int x, int y, int Damage, Unit target) {
        super(x, y, ID.Projectile);
        this.damage = Damage;
        this.isEthereal = true;
        this.pf = new Pathfinder(this);
        this.target = target;
        this.isPathing = true;
    }

    /**
     * removes this projectile from play
     */
    public void discard() {
        while (Game.handler.getObjects().contains(this)) {
            try {
                Game.handler.getObjects().remove(this);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void tick() {
        ProjectileTick();
        if (timeToLive < 0 || !target.isAlive) {
            //ttl ran out, time to delete the bullet
            this.discard();
            return;
        }
        
        //keep moving at the target
        this.setDestinaion(target.x, target.y);
        
        /*MOVEMENT LOOP*/
        for (int i = 0; i < speed; i++) {
            if (Game.mainGrid.getObjectAt(x, y) == this.target) {
                impact(true); //deal the damage
            }
            this.setX(this.getX() + this.getVelX());
            this.setY(this.getY() + this.getVelY());
            if (this.isPathing) {
                this.pf.move();
            }
        }
        /*END OF MOVEMENT LOOP */
        
        this.timeToLive--;
        if (Game.mainGrid.getObjectAt(x, y).id == ID.Wall) {
            this.discard();
        }
    }

    public abstract void impact(boolean hit);
    public abstract void ProjectileTick();
}
