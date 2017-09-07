/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Pathfinding.*;
import Terrain.Tile;
import Units.Team;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The basis of all in-game objects
 *
 * @author Joseph
 */
public abstract class GameObject {

    ///fields

    public int x, y;           //coordinates
    public ID id;            //what kind of object it is
    protected int velX, velY;       //velocity, moves the object
    public int width = 0;
    public int height = 0;
    public int speed = 0;
    public int destX = x, destY = y;    //destination coordinates for pathfinders
    public boolean isPathing = true;    //is pathing
    public boolean isSelected = false;  //is selected
    public boolean isEthereal = false;    //can it move through things? ///can trvel through other game objects
    public Pathfinder pf = null;        // the pathfinder
    public File[] spriteIcon = new File[8];         //the visual sprite icon. 8 for each diection
    public File[] MFspriteIcon = new File[8];       //the visual sprtie icon to be displayed during firing for units
    public BufferedImage spriteImage = null;            //the buffered image with the spriteicon painted on it
    public Direction lastDir = Direction.up;            //Last non-stopped direction that the object was traveling in. defaults to up.
    public Team team = Team.neutral;        //the unit's team 
    public abstract void tick();
    public String name = "Unnamed";         //name of the object
    public String desc = "undescript" ;     //description of unit
    public BufferedImage[] sprites = new BufferedImage[8];
    public BufferedImage[] spritesMF = new BufferedImage[8];

    public abstract void render(Graphics g);

    /// getters and setters
    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int clamp(int input, int max, int min) {
        if (input > max) {
            return input = max;
        }
        if (input < min) {
            return input = min;
        }
        return input;
    }

    public void setPathing(Boolean b) {
        this.isPathing = b;
    }    //method for turning on/off the pathfinder

    public void setDestinaion(int x, int y) {
        this.setPathing(true);
        if (pf != null) {
            this.pf.setDestination(x, y);
        }
    }

    public BufferedImage createSprite() {
        /*
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.setSprite(pf.direction));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
        */
        return this.setSprite(pf.direction);
    }

    public BufferedImage createMFSprite() {
        /*
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.setMFSprite(pf.direction));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
        */
        return this.setMFSprite(pf.direction);
    }

    public void drawStunSprite(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Main.getDir() + Main.assets + "StunSprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, -5 + x - img.getWidth() / 2, -5 + y - img.getHeight() / 2, null);
    }
    
        public void drawBuildSprite(Graphics g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Main.getDir() + Main.assets + "WrenchSprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, -5 + x - img.getWidth() / 2, -5 + y - img.getHeight() / 2, null);
    }
    
    
    /**
     * draws the sprite centered on the object
     * @param g 
     */

    public void drawSprite(Graphics g) {
        g.drawImage(this.createSprite(), x - this.createSprite().getWidth() / 2, y - this.createSprite().getHeight() / 2, null);
    }

    /**
     * draws sprite with the sprite's top left corner being in the same place as the object's topleft corner
     * @param g 
     */
        public void drawSpriteS(Graphics g) {
        g.drawImage(this.createSprite(), x - this.width / 2, y - this.height / 2, null);
    }
    
    public void drawMFSprite(Graphics g) {
        g.drawImage(this.createMFSprite(), x - this.createMFSprite().getWidth() / 2, y - this.createMFSprite().getHeight() / 2, null);
    }

    /**
     * returns the appropriate MUZZEL FLASH sprite depending on the facing
     * direction. if stopped it returns the last non stopped direction
     *
     * @param d direction we measure
     * @return proper sprite icon
     */
    private BufferedImage setMFSprite(Direction d) {
        //note some of these are backwards intentionally because 0,0 is at the top
        switch (d) {
            case up:
                lastDir = Direction.up;
                return spritesMF[1];
                //return MFspriteIcon[1];

            case down:
                lastDir = Direction.down;
                 return spritesMF[0];
                //return MFspriteIcon[0];

            case right:
                lastDir = Direction.right;
                 return spritesMF[2];
              //  return MFspriteIcon[2];

            case left:
                lastDir = Direction.left;
                 return spritesMF[3];
                //return MFspriteIcon[3];

            case upRight:
                lastDir = Direction.upRight;
                 return spritesMF[5];
                //return MFspriteIcon[5];

            case downRight:
                lastDir = Direction.downRight;
                 return spritesMF[4];
               // return MFspriteIcon[4];

            case downLeft:
                lastDir = Direction.downLeft;
                 return spritesMF[7];
                //return MFspriteIcon[7];

            case upLeft:
                lastDir = Direction.upLeft;
                 return spritesMF[6];
              //  return MFspriteIcon[6];

            case stop:
                return setSprite(lastDir);

        }
        return null;
    }
    
    private BufferedImage setSprite(Direction d) {
        //note some of these are backwards intentionally because 0,0 is at the top
        switch (d) {
            case up:
                lastDir = Direction.up;
               // return spriteIcon[1];
                return sprites[1];

            case down:
                lastDir = Direction.down;
                return sprites[0];
               // return spriteIcon[0];

            case right:
                lastDir = Direction.right;
                return sprites[2];
                //return spriteIcon[2];

            case left:
                lastDir = Direction.left;
                return sprites[3];
               // return spriteIcon[3];

            case upRight:
                lastDir = Direction.upRight;
                //return spriteIcon[5];
                return sprites[5];

            case downRight:
                lastDir = Direction.downRight;
                return sprites[4];
                //return spriteIcon[4];

            case downLeft:
                lastDir = Direction.downLeft;
                return sprites[7];
                //return spriteIcon[7];

            case upLeft:
                lastDir = Direction.upLeft;
                return sprites[6];
             //   return spriteIcon[6];

            case stop:
                return setSprite(lastDir);

        }
        return null;
    }
    /**
     * Loads the bufferedImage sprites array using the files in the spriteIcon arrays
     */
    public void loadSprites(){
        for(int i = 0; i< 8; i++){
            try{
            this.sprites[i] = ImageIO.read(this.spriteIcon[i]);
            }catch(Exception e){
            e.printStackTrace();
            }
        }
    }
    
    public void loadMFSprites(){
         for(int i = 0; i< 8; i++){
            try{
            this.spritesMF[i] = ImageIO.read(this.MFspriteIcon[i]);
            }catch(Exception e){
            e.printStackTrace();
            }
        }
    }
    
    
    

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ID getId() {
        return id;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

}
