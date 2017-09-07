/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import static GameObjects.ID.Unit;
import Pathfinding.Pathfinder;
import Units.Plane;
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
public class OilRig extends Unit{
    //FIELDS
    public int interval = 50;
    private int currentTick;
    public int yield = 5;

    public OilRig(int x, int y, int team) {
        super(x, y, team);
        this.name = "Oil Rig";
        this.desc = "Produces oil over time";
        this.plane = Plane.land;
        this.health = 200;
        this.maxHealth = 200;
        this.id = ID.Unit;
        this.speed = 0;
        this.width = 120;
        this.height = 120;
        this.unitTypes.add(Type.structure);
        this.setPathing(false);
        pf = new Pathfinder(this);
        
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets +"OilPumpSprite.png");
        }
       for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "OilPumpSprite.png");
        }
       this.buildTimer = 200;
       this.loadSprites();
       this.loadMFSprites();
       this.mCost = 100;
       this.oCost = 0;

       /*
        for (User user : Game.users) {
            if (user.team == this.team && user.isAI) {
                System.out.println("STRUCTURE BEING ETHERIAL");
                this.isEthereal = true; //AI strctures are etherial
            }
        }
       */
       this.isEthereal = true;
    }

    @Override
    protected void unitRender(Graphics g) {
        //nothing fancy
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
        //nothing fancy
    }
    /**
     * gives owner resources
     */
    public void produce(){
        this.getUser().oil+= yield;
    }
    
}
