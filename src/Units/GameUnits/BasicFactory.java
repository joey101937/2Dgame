/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import Pathfinding.Pathfinder;
import Units.Structure;
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
public class BasicFactory extends Structure {

    //FIELDS
    Unit[] Products = new Unit[3];

    public BasicFactory(int x, int y, int team) {
        super(x, y, team);
        this.name = "Factory";
        this.width = 130;
        this.height = 128;
        // this.Products[0] = new BasicTank(0,0,0);        //the kind of unit it makes in produce 1 is BasicTank
        // this.Products[1] = new FieldTruck(0,0,0);       //produce 2 makes FieldTrucks
        this.name = "Factory";
        this.unitTypes.add(Type.builder);
        this.unitTypes.add(Type.structure);
        this.health = 500;
        this.maxHealth = 500;
        //  this.unitTypes.add(Type.builder);
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "FactorySprite.png");
        }
        for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "FactorySprite.png");
        }
        this.loadSprites();
        this.loadMFSprites();
        this.buildTimer = 200;
        this.oCost = 0;
        this.mCost = 100;

        for (User user : Game.users) {
            if (user.team == this.team && user.isAI) {
                System.out.println("STRUCTURE BEING ETHERIAL");
                this.isEthereal = true; //AI strctures are etherial
            }
        }
    }

    @Override
    public void buildingTick() {

    }

    @Override
    public void buildingRender(Graphics g) {
        // g.drawRect(this.x+this.width/2,this.y-this.height/2,this.width,this.height);
    }

    @Override
    public void buildingFire() {

    }

    @Override
    public void Produce1() {
        for (int i = 0; i < 3; i++) {
            this.getProduct(i);
        }
        if (this.buildTimer < 1 && this.stunTimer < 1) {
            if (this.isOutputClear() && getUser().metal >= this.Products[0].mCost && getUser().oil >= this.Products[0].oCost && this.getUser().hasSupply) {
                BasicTank t = new BasicTank(this.x + this.width, this.y, this.team.getNumber());
                this.lastProduced = t;                                      //sets this tank to the last produced unit
                Game.handler.addObject(t);
                if (isPathing) {
                    t.setDestinaion(this.pf.destX, this.pf.destY); //if we have a rally set, go to it
                } else {
                    t.setDestinaion(x + width, y);
                }                          //if we do not have a rally set, then stay where you are
                t.isPathing = true;
                t.buildTimer = 160;                                        //build time
                getUser().metal -= this.Products[0].mCost;   //pay metal price
                getUser().oil -= this.Products[0].oCost;   //pay oil price
            } else if (!(getUser().metal >= this.Products[0].mCost) || !(getUser().oil >= this.Products[0].oCost)) {
                if (!isAIOwned) {
                    Main.display("You require more resources");
                }
            } else if (!this.getUser().hasSupply && !this.isAIOwned) {
                Main.display("You are using all " + User.SUPPLYCAP + " of your unit slots.");
            }
        }
    }

    @Override
    public void Produce2() {
        for (int i = 0; i < 3; i++) {
            this.getProduct(i);
        }
        if (this.buildTimer < 1 && this.stunTimer < 1) {
            if (this.isOutputClear() && getUser().metal >= this.Products[1].mCost && getUser().oil >= this.Products[1].oCost && this.getUser().hasSupply) {
                FieldTruck t = new FieldTruck(this.x + this.width, this.y, this.team.getNumber());
                this.lastProduced = t;                                      //sets this Product to the last produced unit
                Game.handler.addObject(t);
                if (isPathing) {
                    t.setDestinaion(this.pf.destX, this.pf.destY); //if we have a rally set, go to it
                } else {
                    t.setDestinaion(x + width, y);
                }                          //if we do not have a rally set, then stay where you are
                t.isPathing = true;
                t.buildTimer = 160;                                        //build time
                getUser().metal -= this.Products[1].mCost;   //pay metal price
                getUser().oil -= this.Products[1].oCost;   //pay oil price
            } else if (!(getUser().metal >= this.Products[1].mCost) || !(getUser().oil >= this.Products[1].oCost)) {
                if (!isAIOwned) {
                    Main.display("You require more resources");
                }
            } else if (!this.getUser().hasSupply && !this.isAIOwned) {
                Main.display("You are using all " + User.SUPPLYCAP + " of your unit slots.");
            }
        }

    }

    @Override
    public void Produce3() {
        for (int i = 0; i < 3; i++) {
            this.getProduct(i);
        }
        if (this.buildTimer < 1 && this.stunTimer < 1) {
            if (this.isOutputClear() && getUser().metal >= this.Products[2].mCost && getUser().oil >= this.Products[2].oCost && this.getUser().hasSupply) {
                Helicopter t = new Helicopter(this.x + this.width, this.y, this.team.getNumber());
                this.lastProduced = t;                                      //sets this Product to the last produced unit
                Game.handler.addObject(t);
                if (isPathing) {
                    t.setDestinaion(this.pf.destX, this.pf.destY); //if we have a rally set, go to it
                } else {
                    t.setDestinaion(x + width, y);
                }                          //if we do not have a rally set, then stay where you are
                t.isPathing = true;
                t.buildTimer = 160;                                        //build time
                getUser().metal -= this.Products[2].mCost;   //pay metal price
                getUser().oil -= this.Products[2].oCost;   //pay oil price
            } else if (!(getUser().metal >= this.Products[2].mCost) || !(getUser().oil >= this.Products[2].oCost)) {
                if (!isAIOwned) {
                    Main.display("You require more resources");
                }
            } else if (!this.getUser().hasSupply && !this.isAIOwned) {
                Main.display("You are using all " + User.SUPPLYCAP + " of your unit slots.");
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
                    return Products[0] = new BasicTank(0, 0, 0);
                }
            case 1:
                if (this.Products[1] != null) {
                    return Products[1];
                } else {
                    return Products[1] = new FieldTruck(0, 0, 0);
                }
            case 2:
                if (this.Products[2] != null) {
                    return Products[2];
                } else {
                    return Products[2] = new Helicopter(0, 0, 0);
                }
            default:
                return null;
        }
    }

}
