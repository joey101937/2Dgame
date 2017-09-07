/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import java.io.File;
import pkg2dgame.Main;

/**
 *
 * @author Joseph
 */
public class Blast extends Corpse{

    public Blast(int x, int y) {
        super(x, y);        
        this.lifeSpan = 10;
        for (int i = 0; i < 8; i++) {
            this.spriteIcon[i] = new File(Main.getDir() + Main.assets+ "blastSprite32x27.png");
        }
        this.loadSprites();
    }
    
}
