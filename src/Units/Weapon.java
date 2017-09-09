/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import GameObjects.Projectiles.*;
import pkg2dgame.AudioManager;
import pkg2dgame.Game;
import pkg2dgame.GameObject;

/**
 *  Will be used by Units to fire and do things
 * @author Joseph
 */
public enum Weapon {
    none() {
        @Override
        public void fire(Unit user, Unit Target) {
            return;         //units with a weapon of "none" does nothing when it fires
        }

        @Override
        public int getRange() {
            return 0;
        }

        @Override
        public boolean targetsGround() {
            return false;
        }

        @Override
        public boolean targetsAir() {
            return false;
        }
        /**
         * slow firing but high damage projectile
         */
    },cannon() { ///////////////////////////////////////////////////////////////////////////
        @Override
        public void fire(Unit user, Unit target) {
            if (user.pf.getDistanceFrom(target) <= this.getRange()) {

                    if (user.weaponCooldown == 0) {
                        Game.handler.getObjects().add(new Cannonball(user.x, user.y, 20, target));
                        user.muzzelFlashDuration = 10;  //crateds muzzel flash
                        user.weaponCooldown = 80; //30
                        AudioManager.Play("cannonLaunch3.mp3");
                    } else {
                        user.weaponCooldown--;
                    }

                    user.setVelX(0);
                    user.setVelY(0);
                } else {
                    user.pf.setDestination(target.x, target.y);     //if out of range, close in on target
                    user.pf.move();
                }
        }

        @Override
        public int getRange() {
            return 2000;
        }

        @Override
        public boolean targetsGround() {
             return true;
        }

        @Override
        public boolean targetsAir() {
            return false;
        }
        /**
         * fast firing, short range AG and AA
         */
    }, lazer(){ /////////////////////////////////////////////////////////////////////

        @Override
        public void fire(Unit user, Unit target) {
           if (user.pf.getDistanceFrom(target) <= this.getRange()) {
                    if (user.weaponCooldown == 0) {
                        Game.handler.getObjects().add(new LazerBeam(user.x, user.y, 5, target));
                        user.muzzelFlashDuration = 10;  //creats muzzel flash
                        user.weaponCooldown = 30;
                       AudioManager.Play("lazerFire.mp3");
                    } else {
                        user.weaponCooldown--;
                    }

                    user.setVelX(0);
                    user.setVelY(0);
                } else {
                    user.pf.setDestination(target.x, target.y);     //if out of range, close in on target
                    user.pf.move();
                }
        }

        @Override
        public int getRange() {
            return 1000;
        }

        @Override
        public boolean targetsGround() {
            return true;
        }

        @Override
        public boolean targetsAir() {
            return true;
        }
 }, 
    /**
     * fast firing instant dmg weapon with fair range, AG and AA
    */
    autocannon(){
        @Override
        public void fire(Unit user, Unit target) {
            if (user.pf.getDistanceFrom(target) <= this.getRange()) {
                if (user.weaponCooldown == 0) {
                    target.health -= 5;
                    user.muzzelFlashDuration = 15;  //creats muzzel flash
                    user.weaponCooldown = 30;
                    AudioManager.Play("machinegun.mp3");
                } else {
                    user.weaponCooldown--;
                }

                user.setVelX(0);
                user.setVelY(0);
            } else {
                user.pf.setDestination(target.x, target.y);     //if out of range, close in on target
                user.pf.move();
            }
        }

        @Override
        public int getRange() {
            return 2000;
        }

        @Override
        public boolean targetsGround() {
            return true;
        }

        @Override
        public boolean targetsAir() {
            return true;
        }
    };////////////////////////////////////////////////////////////////////////////////////////////
    
    public int x,y;       
    GameObject target = null;
    public abstract void fire(Unit user, Unit target);    ///fires the weapon
    public abstract int getRange();    //range of weapon
    public abstract boolean targetsGround();    //can it hit ground
    public abstract boolean targetsAir();       //can it hit air
           
    
    public void setX(int x) {
            this.x = x;
        }

    public void setY(int y) {
        this.y = y;
    }

   public void setTarget(Unit target) {
       this.target = target;
    }

}
