/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import Pathfinding.Pathfinder;
import Units.Builder;
import Units.Type;
import Units.Unit;
import Units.Weapon;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.Game;
import pkg2dgame.Main;
import pkg2dgame.User;

/**
 *
 * @author Joseph
 */
public class FieldTruck extends Unit implements Builder {

    //FIELDS
   public int queueX, queueY;     //used to queue production, makes a thing when it gets to these coordinates
   public int queueBuild;         //int 1-3, determines what product to produce when it hits the queue coordinates
    public boolean queued = false; //weather or not we are using the queue system
    Unit[] Products = new Unit[3];

    public FieldTruck(int x, int y, int team) {
        super(x, y, team);
        this.name = "Field Truck";
        this.desc = "Builder Unit";
        this.health = 50;
        this.maxHealth = 50;
        this.weapon = Weapon.none;
        this.id = ID.Unit;
        this.speed = 2;
        this.width = 50;
        this.height = 50;
        this.unitTypes.add(Type.Vehicle);
        this.unitTypes.add(Type.builder);
        this.setPathing(false);
        pf = new Pathfinder(this);

        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "TruckSprite" + i + ".png");
        }
        for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "TruckSprite" + i + ".png");
        }
        this.loadSprites();
        this.loadMFSprites();
        this.mCost = 50;
        this.oCost = 0;

    }

    @Override
    protected void unitRender(Graphics g) {
    }

    @Override
    protected void unitTick() {
        int margin = 2;
        if(this.queued){
            if(this.x >= queueX-margin && this.x <= queueX+margin && this.y >= queueY-margin && this.y <= queueY+margin){
                this.executeQueue();
            }
        }
    }

    @Override
    protected void unitFire() {
    }

    @Override
    public void Produce1() {
        this.getProduct(0);
        if (isOutputClearFT(0) && this.buildTimer < 1 && this.stunTimer < 1) {//not building or stunned
            if (Game.mainGrid.getTileAt(x, y).fertility == 0) {             //make sure we are on plain terrain
                if (this.getUser().metal >= this.getProduct(0).mCost && this.getUser().oil >= this.getProduct(0).oCost) {
                    this.getUser().metal -= this.getProduct(0).mCost;
                    this.getUser().oil -= this.getProduct(0).oCost;
                    BasicFactory bf = new BasicFactory(x, y, team.getNumber());
                    bf.buildTimer = Products[0].buildTimer;
                    Game.handler.addObject(bf);
                    this.destroy(false);
                } else if (!(getUser().metal >= this.Products[0].mCost) || !(getUser().oil >= this.Products[0].oCost)) {
                    if(!isAIOwned)Main.display("You require more resources");
                }
            } else {
                 if(!isAIOwned)Main.display("Must be placed on open terrain");
            }
        }
    }

    @Override
    public void Produce2() {
        this.getProduct(1);
        if (isOutputClearFT(1) && this.buildTimer < 1 && this.stunTimer < 1) {
            if (Game.mainGrid.getTileAt(x, y).fertility == 1) {             //make sure we are on oil-rich terrain
                if (this.getUser().metal >= this.getProduct(1).mCost && this.getUser().oil >= this.getProduct(1).oCost) {
                    this.getUser().metal -= this.getProduct(1).mCost;
                    this.getUser().oil -= this.getProduct(1).oCost;
                    OilRig or = new OilRig(x, y, team.getNumber());
                    or.buildTimer = Products[1].buildTimer;
                    Game.handler.addObject(or);
                    this.destroy(false);
                } else if (!(getUser().metal >= this.Products[1].mCost) || !(getUser().oil >= this.Products[1].oCost)) {
                    if(!isAIOwned)Main.display("You require more resources");
                }
            } else {
                 if(team == Game.localUser.team && !isAIOwned)Main.display("Must place that on oil-rich terrain");
            }
        }
    }

    @Override
    public void Produce3() {
        this.getProduct(2);
        if (isOutputClearFT(1) && this.buildTimer < 1 && this.stunTimer < 1) {
            if (Game.mainGrid.getTileAt(x, y).fertility == 2) {             //make sure we are on Iron-rich terrain
                if (this.getUser().metal >= this.getProduct(2).mCost && this.getUser().oil >= this.getProduct(2).oCost) {
                    this.getUser().metal -= this.getProduct(2).mCost;
                    this.getUser().oil -= this.getProduct(2).oCost;
                    IronMine or = new IronMine(x, y, team.getNumber());
                    or.buildTimer = Products[2].buildTimer;
                    Game.handler.addObject(or);
                    this.destroy(false);
                }
            } else {
                if(team == Game.localUser.team && !isAIOwned)Main.display("Must place that on Iron-rich terrain");
            }
        }
    }

    @Override
    public Unit getProduct(int p) {

        switch (p) {
            case 0:
                if (this.Products[0] != null) {
                    return Products[0];
                } else {
                    return Products[0] = new BasicFactory(0, 0, 0);
                }
            case 1:
                if (this.Products[1] != null) {
                    return Products[1];
                } else {
                    return Products[1] = new OilRig(0, 0, 0);
                }
            case 2:
                if (this.Products[2] != null) {
                    return Products[2];
                } else {
                    return Products[2] = new IronMine(0, 0, 0);
                }
            default:
                return null;
        }
    }

    @Override
    public boolean isOutputClear() {
       if(this.isAIOwned)return true;   //ai can build whereever it wants
        boolean output = (Game.mainGrid.isRangeValid(this.x + this.width / 2, this.x + this.width / 2 + this.width, this.y + this.height / 2, this.y - this.height / 2, null));
        if(!output)Main.display("CANNOT BUILD THERE, SOMETHING IN THE WAY");
        return output;
    }

    @Override
    public boolean isOutputClearFT(int i) {
        boolean output = false;
        if(this.isAIOwned) return true; //ai can build whereever it wants
        if (i == 0) {//Basic factory
            //add constants like 58 and 30 to increase the seach area to make sure it is clear to build
            output = (Game.mainGrid.isRangeValid(this.x - this.width - 58, this.x + this.width + 128, y + height + 30, y - height - 30, this));
        } else if (i == 1) {  
            output = (Game.mainGrid.isRangeValid(this.x - this.width - 40, this.x + this.width + 0, y + height + 25, y - height - 25, this));
        } else if (i == 2) {
            output = (Game.mainGrid.isRangeValid(this.x - this.width - 100, this.x + this.width + 100, y + height + 20, y - height - 20, this));
        } else {
            return true;
        }
        if(!output)Main.display("CANNOT BUILD THERE, SOMETHING IN THE WAY");
        return output;
        
    }
    /**
     * orders the truck to move to a set of coordinates and then execute a produce command when it gets there.
     * @param x
     * @param y
     * @param item which item to produce
     */
    public void BuildAt(int x, int y, int item){
        queued = true;
        this.queueX = x;
        this.queueY = y;
        this.queueBuild = item;
        this.MoveTo(x, y);
    }
    
    /**
     * builds the queued item
     */
    public void executeQueue(){
        this.queued = false;
        switch(this.queueBuild){
            case 1: this.Produce1();
                return;
            case 2: this.Produce2();
                return;
            case 3: this.Produce3();
                return;
            default: System.out.println("ATTEMPTED TO QUEUE UNKNOWN PRODUCTION: " + queueBuild);
                return;
        }
    }
    
    
}
