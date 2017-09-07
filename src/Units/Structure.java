/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import GameObjects.ID;
import Pathfinding.*;
import Units.GameUnits.*;
import java.awt.Graphics;
import pkg2dgame.Game;
import pkg2dgame.Main;
import pkg2dgame.User;

/**
 *
 * @author Joseph
 */
public abstract class Structure extends Unit implements Builder{
    
    public Structure(int x, int y, int team) {
        super(x, y, team);
        this.unitTypes.add(Type.structure); //all structures have the unit type structure
        this.speed = 0; //structures dont move
        this.pf = new Pathfinder(this);     //still have to give it a pathfinder for some reason
        pf.destX = x;
        pf.destY = y;
        this.id = ID.Unit;                  //structures are still units
        this.isPathing = false;             //our pather wont be used anyway
        this.weapon = weapon.none;          //structures default with the weapon of none
        System.out.println("structure constructor");
    }

    @Override
    protected void unitRender(Graphics g) {
           buildingRender(g);
           if(isProducing)this.drawBuildSprite(g)  ;//draw building sprite if we are making a unit
    }

    @Override
    protected void unitTick() {
           buildingTick();   
           if(lastProduced != null &&this.lastProduced.buildTimer > 0 && Game.handler.getUnits().contains(lastProduced))this.isProducing = true;
           else this.isProducing = false;
    }

    @Override
    protected void unitFire() {
        buildingFire();
    }

    /**
     * version of isOutputClear() for fieldTrucks. changes based on what we are
     * trying to build.
     *
     * @param i determines what we are trying to place. 0-2, one for each
     * production slot.
     * @return
     */
    public boolean isOutputClearFT(int i) {
        boolean output = false;
        if (i == 0) {//Basic factory
            //58, 30 and 128 increase to accomodate large buildings
            output = (Game.mainGrid.isRangeValid(this.x - this.width - 58, this.x + this.width + 128, y + height + 30, y - height - 30, this));
        } else if (i == 1) {   //oil rig
            output = (Game.mainGrid.isRangeValid(this.x - this.width, this.x + this.width, y + height, y - height, this));
        } else {
            return true;
        }
        if(!output)Main.display("CANNOT BUILD THERE, SOMETHING IN THE WAY");
        return output;
    }
    
        /**
     * checks to see if the location where it would spawn a unit is open
     * @return output
     */
    public boolean isOutputClear(){
        boolean output = (Game.mainGrid.isRangeValid(this.x+this.width/2, this.x+this.width/2+this.width, this.y+this.height/2, this.y-this.height/2, null));
        if(!output&&!isAIOwned){Main.display("CANNOT BUILD THERE, SOMTHING IN THE WAY");}
        return output;
    }
    
    
    ///STRUCTURE - SPECIFIC METHODS
    public abstract void buildingTick();
    public abstract void buildingRender(Graphics g);
    public abstract void buildingFire();
    
    ///structes will be able to produce 3 types of things
    
    @Override
    public abstract void Produce1();
    @Override
    public abstract void Produce2();
    @Override
    public abstract void Produce3();
    @Override
    public abstract Unit getProduct(int p);
    
    
    
}
