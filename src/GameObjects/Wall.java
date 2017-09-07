/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Pathfinding.Direction;
import Pathfinding.Pathfinder;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.*;

/**
 *
 * @author Joseph
 */
public class Wall extends GameObject {

    public Wall(int x, int y, ID id) {
        super(x, y, id);
        this.width = 20;
        this.height = 100;
        this.speed = 0; ///walls dont move
        this.pf = new Pathfinder(this);
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() +  Main.assets + "WallVerticle.png");
        }
        
    }

    @Override
    public void tick() {
        //does nothing .-.

    }

    @Override
    public void render(Graphics g) {
        g.setColor(id.getColor());
        g.fillRect(x - width / 2, y - height / 2, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(x,y,1,1);
        //this.drawSprite(g);
    }

}
