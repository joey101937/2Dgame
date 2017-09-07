/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import Pathfinding.Pathfinder;
import Units.Plane;
import Units.Type;
import Units.Unit;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.Game;
import pkg2dgame.Main;
import pkg2dgame.User;

/**
 *
 * @author Joseph
 */
public class IronMine extends Unit {

    //FIELDS

    public int interval = 50;
    private int currentTick;
    public int yield = 5;

    public IronMine(int x, int y, int team) {
        super(x, y, team);
        this.name = "Iron Mine";
        this.desc = "Produces Metal over time";
        this.plane = Plane.land;
        this.health = 200;
        this.maxHealth = 200;
        this.id = ID.Unit;
        this.speed = 0;
        this.width = 192;
        this.height = 99;
        this.unitTypes.add(Type.structure);
        this.setPathing(false);
        pf = new Pathfinder(this);

        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "DiamondMineSprite.png");
        }
        for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "DiamondMineSprite.png");
        }
        this.buildTimer = 200;
        this.loadSprites();
        this.loadMFSprites();
        this.mCost = 70;
        this.oCost = 0;
        
        /*
        //if it belongs to ai, make it etherial
        for (User user : Game.users) {
            if (user.team == this.team && user.isAI) {
                System.out.println("STRUCTURE BEING ETHERIAL");
                this.isEthereal = true; //AI strctures are etherial
            }
        }
        */
        this.isEthereal = true; //can walk through this
    }

    @Override
    protected void unitRender(Graphics g) {

    }

    @Override
    protected void unitTick() {
        if (this.buildTimer < 1 && this.stunTimer < 1) {    //if not building or stunned
            this.currentTick++;
            if (currentTick > interval) {
                currentTick = 0;
                this.produce();
            }
        }

    }

    @Override
    protected void unitFire() {

    }

    /**
     * gives owner resources
     */
    public void produce() {
        this.getUser().metal += yield;
    }

}
