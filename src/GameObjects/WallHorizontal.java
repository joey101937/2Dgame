/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Pathfinding.Pathfinder;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import pkg2dgame.*;

/**
 *
 * @author Joseph
 */
public class WallHorizontal extends GameObject {
    public WallHorizontal(int x, int y, ID id) {
        super(x, y, id);
        this.width = 100;
        this.height = 20;
        this.speed = 0; ///walls dont move

        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() +  Main.assets + "WallHorrizontal.png");
        }
        pf = new Pathfinder(this);
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
