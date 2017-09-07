/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import GameObjects.Projectiles.Cannonball;
import GameObjects.ID;
import Pathfinding.*;
import Terrain.Map;
import Terrain.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import pkg2dgame.*;
import static pkg2dgame.Game.handler;

/**
 * Special Game objects that will be used as units and have additional function
 *
 * @author Joseph
 */
public abstract class Unit extends GameObject {

    //Unit Fields 
    public Pathfinder pathfinder = null;   //the unit's pathfinder
    public int health = 1;                 //the unit's current health
    public int maxHealth = 1;              //the units' maximum heath
    public Weapon weapon = Weapon.none;    //the unit's weapon. effects the unitFire method.
    public boolean leavesCorpse = true;        //weather or not the unit leaves a corpse when it dies
    public Unit target = null;              //the unit that this unit is trying to attack
    public int scanRange = 3000;             //how far away an enemy can be for a unit to automatically attack it
    public boolean manuallyTargeted = false;    //if the unit was told to target something manually
    public boolean isFiring = false;            //weather or not to use the muzzelFlash Sprite. breifly becomes true when the unit fires
    public int weaponCooldown;                  //how long you have to wait to unitFire again
    public Plane plane = Plane.land;            //land air or sea unit
    public ArrayList<Type> unitTypes = new ArrayList<Type>();   //types of unit there are. ie Vehicle, infantry
    public int stunTimer = 0;                           //if more than 0; the unit is stunned for a tick. the number is how long
    public int muzzelFlashDuration = 0;                 //how long the to use the shooting sprite
    public int buildTimer = 0;                          ///how much longer its building for
    public int mCost, oCost;                            //how many resources they cost (metal,oil)
    public boolean isAmoving = false;                   //are we attack-moving?
    public boolean isAlive = true;                      //is it alive?
    public Unit lastProduced = null;                  //most recent unit spawned by this building
    public boolean isProducing = false;          //if this building is currently building a unit
    public boolean isAIOwned = false;           //if the unit is owned by an AI
    public boolean isMilitary = false;          //if the unit is a combat oriented unit (as opposed to economy)
    /*
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

    public Unit(int x, int y, int team) {
        super(x, y, ID.Unit);
        this.team = Team.getTeam(team);
        this.id = ID.Unit;
        for(User user : Game.users){
            if(user.team == this.team && user.isAI){
                this.isAIOwned = true;
            }
        }
    }

    @Override
    public void tick() {

        if (!this.isPostionValid()) {
            // this.destroy(leavesCorpse);             //destroy out of bounds units
        }
        if (buildTimer > 0) {
            buildTimer--;
        }
        if (this.stunTimer <= 0 && this.buildTimer <= 0) {    //either stunned or building
            this.unitTick();

            if (this.health <= 0) {
                this.destroy(leavesCorpse);    //dies if it runs out of health
            }        //movement
            for (int i = 0; i < speed; i++) {
                this.setX(this.getX() + this.getVelX());
                this.setY(this.getY() + this.getVelY());
                if (this.isPathing) {
                    this.pf.move();
                }
            }

            //firing
            if (target == null){
            this.SearchForTarget(); //if stopped and no current target, aquire the closest unit as new target
            }
            if ((this.target != null && !isPathing) || (this.target != null && isAmoving)) {
                this.fire(); //fires when it has a target and is stopped or is Amoving
            }
            if (target != null && pf.getDistanceFrom(target) > scanRange * 1.2 | target.health <= 0) {
                disengage(); //if the target is too far away, disengage
            }
        } else {
            stunTimer--;//if the unit is stunned

        }

    }

    @Override
    public void render(Graphics g) {
        if (!this.isRenderValidI()) {
          // System.out.println("off screen");
            return;          //dont render off screen units

        }
        int renderX = x;//-= Game.Camx;
        int renderY = y;//-= Game.camy;

        this.unitRender(g);                         //unit-specific method
        int boxW = this.width + 10;                //selection box width
        int boxH = this.height + 10;               //selection box height
        double HP = (double) health / maxHealth;    //% of HP the unit has
        g.setColor(this.team.getColor());
        if (this.id == ID.Unit) {
            g.fillRect(renderX - boxW / 2, renderY + boxH / 2, (int) (boxW * HP), 5);
        }

        if (this.isSelected) {        //only displays if selected
            g.drawRect(renderX - boxW / 2, renderY - boxH / 2, boxW, boxH);
            if (this.isAmoving) {
                //make the path red if we are attack moving
                g.setColor(Color.RED);
            }
            if (this.isPathing) {
                if((this.unitTypes.contains(Type.builder) && this.unitTypes.contains(Type.structure))||!this.unitTypes.contains(Type.structure)){
                    //draw the path only if it is a non-structure unit or a structure unit with builder
                g.drawLine(renderX, renderY, this.pf.destX, this.pf.destY);
                g.drawRect(pf.destX - 3, pf.destY - 3, 6, 6);
                }
            }    
            g.setColor(this.team.getColor());
        }
        if (this.muzzelFlashDuration == 0) {
            if (!this.unitTypes.contains(Type.structure) || this.name == "Field Truck") {
                this.drawSprite(g);    //if no muzzel flash and is not a structure
            }
            if (this.unitTypes.contains(Type.structure) && this.name!="Field Truck") {
                this.drawSpriteS(g);   //if its a structure dont center the sprite
            }
        } else {
            this.drawMFSprite(g);
            this.muzzelFlashDuration--;
        }
        if (this.stunTimer > 0) {
            this.drawStunSprite(g);      //if its stunned, display stun animation
        }
        if (this.buildTimer > 0 || this.isProducing) {
            this.drawBuildSprite(g);            ///if its still building
        }

       // x += Game.Camx;
      //  y += Game.camy;
    }

    /**
     * properly removes the unit from the game.
     * @param leavesCorpse weather or not we should leave a corpse in the unit's former position 
     */
    public void destroy(boolean leavesCorpse) {
        this.isAlive = false;
        Iterator<GameObject> it = Game.handler.iterator;
        if (leavesCorpse) {
            Game.handler.addObject(new Corpse(x, y));
        }
    
        while (Game.handler.getObjects().contains(this)) {
            try {
                Game.handler.getObjects().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //since its dead we need to take it away
        this.x = -100;
        this.y = -100;
        
        if(Game.window.ui.selected == this){
            Game.ki.selected.clear();   //deselects the unit after death
        }
        
        
    }

    @Override
    public String toString() {
        String output = team + " " + name + " HP: " + health + "/" + maxHealth;
        return output;
    }
    /**
     * stops movement and clears the current target
     */
    public void disengage() {
        System.out.println("disengage");
        this.isPathing = false;
        this.pf.setDestination(x, y);
        pf.move();
        this.target = null;
    }
    
    /**
     * checks for nearby enemy units and assigns the closest one to be its target
     */
    public void SearchForTarget() {
        ArrayList<GameObject> unitsInScan = new ArrayList<>();
        GameObject closest = null;
        int lowestDist = 99999999;
        if (this.target == null) { //only aquires no current target
            for (GameObject go : Game.handler.getUnits()) {
                if (go.id == ID.Unit) { ///for every unit
                    if (pf.getDistanceFrom(go) <= this.scanRange && go != this && go.id == ID.Unit) {       //if the unit is in scan range and is not this same unit
                        Unit u = (Unit) go;
                        if (!this.team.getAllies().contains(u.team.getNumber()) && this.canTarget(go)) {
                            unitsInScan.add(go);        //exempt units whose team is allied with our own
                        }
                    }
                }
            }
        }
        if (unitsInScan.size() > 0) {
            for (GameObject go : unitsInScan) {
                if (pf.getDistanceFrom(go) < lowestDist) {
                    lowestDist = pf.getDistanceFrom(go);
                    closest = go;
                }
            }

        }
        target = (Unit) closest;
        manuallyTargeted = false;   //the unit targeted on its own so not manually targeted
    }

    /**
     * fires the user's weapon at its target, this.target
     */
    public void fire() {
        unitFire();
        this.isPathing = false;
        switch (weapon) {
            case none:
                return;
            case lazer:    
            case cannon:
                this.faceTarget(); //face the target  
                this.weapon.fire(this, target);
                break;
        }
    }
    /**
     * faces the current target, target being this.target
     */
    private void faceTarget() {
        this.pf.direction = pf.getPureDirection(this.target.x, this.target.y);
        if (target.x < x + width / 2 && target.x > this.x - width / 2 && target.y > y) {
            pf.direction = Direction.up;
        } ///target is directly above
        if (target.x < x + width / 2 && target.x > this.x - width / 2 && target.y < y) {
            pf.direction = Direction.down;
        } //targetis directly below
        if (target.y < y + height / 2 && target.y > this.y - height / 2 && target.x < x) {
            pf.direction = Direction.left;
        } //target is directly left
        if (target.y < y + height / 2 && target.y > this.y - height / 2 && target.x > x) {
            pf.direction = Direction.right;
        } //target is firectly right
    }
    
    /**
     * returns true if the user is able to target the passed unit with their gun
     * @param u target
     * @return result
     */
    public boolean canTarget(GameObject u) {
        if (u.id == ID.Unit) {
            Unit target = (Unit) u;
            if (target.plane == Plane.air && this.weapon.targetsAir()) {
                return true;
            }
            if (target.plane == Plane.land && this.weapon.targetsGround()) {
                return true;
            }
            if (target.plane == Plane.sea && this.weapon.targetsGround()) {
                return true;
            }
            return false;

        } else {
            return false;   ///u isnt a unit and therefore cannot be targeted
        }
    }
    
    /**
     * Stops movement of the unit
     */
    public void issueStopCommand() {
        this.setVelX(0);
        this.setVelY(0);
        this.isPathing = false;
    }
    /**
     * attempts to attack the passed unit, following the constraints of its weapon
     * @param newTarget unit to attack
     */
    public void issueAttackCommand(Unit newTarget){
        if(pf.getDistanceFrom(newTarget) > this.scanRange){
            this.issueAttackMove(newTarget.x, newTarget.y);
            return;
        }
         if((newTarget.plane == Plane.land && this.weapon.targetsGround()) || (newTarget.plane == Plane.air && this.weapon.targetsAir())){
         this.issueStopCommand();
         this.target = newTarget;
         this.manuallyTargeted = true;
         this.isAmoving = true; //so we get a red line.
         }
    }
    
    /**
     * moves towards a point attacking all enemies on the way
     */
    public void issueAttackMove(int targX, int targY) {
        targX = Main.clamp(targX, 0, Game.width);
        targY = Main.clamp(targY, 0, Game.height);
        if(this.weapon != Weapon.none){
        this.setDestinaion(targX, targY);
        this.isAmoving = true;
        this.manuallyTargeted = false;
         //System.out.println("move command");
        }
    }
    
    /**
     * basic move command
     */
    public void MoveTo(int x, int y){
        x = Main.clamp(x, 0, Game.width);
        y = Main.clamp(y, 0, Game.height);
        this.setDestinaion(x, y);
        this.isPathing = true;
        if(manuallyTargeted) target = null; //if we were manually targeting somthing, this should cancel that order
        this.manuallyTargeted = false;
    }

    /**
     * checks to see if the unit is on the map
     *
     * @return
     */
    public boolean isPostionValid() {
        if (this.x > Game.width) {
            return false;
        }
        if (this.x < 0) {
            return false;
        }
        if (this.y > Game.height) {
            return false;
        }
        if (this.y < 0) {
            return false;
        }
        return true;
    }
    /**
     * checks if the unit is on screen
     * @return 
     */
    public boolean isRenderValidI() {
        if(x<KeyInput.tx){return false;}
        if(x>KeyInput.tx+Game.myMap.camWidth*Tile.SIZE){return false;}
        if(y>KeyInput.ty+Game.myMap.camHeight*Tile.SIZE-Tile.SIZE){return false;}
        if(y<KeyInput.ty){return false;}
        return true;
    }
    
    /**
     * returns the user corresponding to the unit's team
     * @return 
     */
    public User getUser(){
        for(User u : Game.users){
            if(u.team == this.team){
                return u;
            }
        }
        System.out.println("no owner: team " + this.team);
        return null;
    }
    
    //we are overriding this to be identical to the "move to" command
    @Override
    public void setDestinaion(int x, int y){
        x = Main.clamp(x, width, Game.width - this.width);
        y = Main.clamp(y, height, Game.height - this.height);
        this.setPathing(true);
        if (pf != null) {
            this.pf.setDestination(x, y);
        }
        this.isPathing = true;
        if(manuallyTargeted) target = null; //if we were manually targeting somthing, this should cancel that order
        this.manuallyTargeted = false;
    }


    /* UNIT EXCLUSIVE METHODS   */
    protected abstract void unitRender(Graphics g);       ///the method units will run as their rendering method

    protected abstract void unitTick();         ///the method untis will run as their ticking method

    protected abstract void unitFire();             //method for firing weapon. dependent on weapon parameter
}
