/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units.GameUnits;

import GameObjects.ID;
import Pathfinding.Pathfinder;
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
public class Helicopter extends Unit{

    public Helicopter(int x, int y, int team) {
        super(x, y, team);
        this.name = "Helicopter";
        this.desc = "Light Air-to-Ground Flier";
        this.health = 80;
        this.maxHealth = 80;
        this.weapon = Weapon.lazer;
        this.id = ID.Unit;
        this.speed = 2;
        this.width = 55;
        this.height = 55;
        this.unitTypes.add(Type.Vehicle);
        this.plane = plane.air;
        this.isMilitary = true;
        this.setPathing(false);
        pf = new Pathfinder(this);
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "Helicopter" + i + ".png");
        }
       for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "Helicopter" + i + ".png");
        }
       this.loadSprites();
       this.loadMFSprites();
       
       this.mCost = 100;
       this.oCost = 200;
    }

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
