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
public class AutoTurret extends Unit{

    public AutoTurret(int x, int y, int team) {
        super(x, y, team);
        this.name = "AutoTurret";
        this.desc = "Defensive Structure";
        this.health = 200;
        this.maxHealth = 200;
        this.weapon = Weapon.autocannon;
        this.id = ID.Unit;
        this.speed = 0;
        this.width = 40;
        this.height = 40;
        this.unitTypes.add(Type.Vehicle);
        this.isMilitary = true;
        this.setPathing(false);
        pf = new Pathfinder(this);
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets + "turret" + i + ".png");
        }
        for (int i = 0; i < 8; i++) {
            this.MFspriteIcon[i] = new File(Main.getDir() + Main.assets + "turretMF" + i + ".png");
        }
        this.loadSprites();
        this.loadMFSprites();

        this.mCost = 125;
        this.oCost = 0;
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
