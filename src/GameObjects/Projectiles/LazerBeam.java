/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects.Projectiles;

import Units.Unit;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import pkg2dgame.GameObject;

/**
 *
 * @author Joseph
 */
public class LazerBeam extends Projectile{

    public LazerBeam(int x, int y, int Damage, Unit target) {
        super(x, y, Damage, target);
        this.speed = 0;
        this.timeToLive = 5;
    }

    @Override
    public void impact(boolean hit) {
        target.health -= this.damage;
        this.discard();
    }

    @Override
    public void ProjectileTick() {
        if(this.timeToLive < 2){
            impact(true);   //after ttl ricks down, auto-hit
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x, y, target.x, target.y);
       
    }
    
}
