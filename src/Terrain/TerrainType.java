/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pkg2dgame.Game;
import pkg2dgame.Main;
import pkg2dgame.Sprite;

/**
 * Kind of terrain. terrain may have attributes above pathing layer, which is only used for pathing.
 * @author Joseph
 */




public enum TerrainType {
    Grass {

        @Override
        public PathingLayer getPathingLayer() {
            return PathingLayer.Ground;
        }

        @Override
        public BufferedImage getSprite(int varient) {
            switch(varient){
                case 1: return grassSprite;
                case 2: return grassSprite2;
                case 11: return grassSprite11;
                case 12: return grassSprite12;
                default: return grassSprite;
            }
        }
        
    },Sand {

        @Override
        public PathingLayer getPathingLayer() {
            return PathingLayer.Beach;
        }

        @Override
        public BufferedImage getSprite(int varient) {
        return sandSprite;
        }
    },Water {

        @Override
        public PathingLayer getPathingLayer() {
            return PathingLayer.Water;
        }

        @Override
        public BufferedImage getSprite(int varient) {
            return waterSprite;
        }
    },;
    
    /**
     * Pathing layer determines what units can move there
     * @return 
     */
    public abstract PathingLayer getPathingLayer();
    /**
     * what the ground looks like
     * @param varient this decides what verison of the sprite to present
     * @return 
     */
    public abstract BufferedImage getSprite(int varient);
    
    /**
     * loads the terrain sprites and stores them in variables.
     * called in map constructor if not already done so
     */
    public static void setSprites(){
        try {
            
            waterSprite = ImageIO.read(new File(Main.getDir() +  Main.assets + "waterTile.png"));
            grassSprite = ImageIO.read(new File(Main.getDir() +  Main.assets + "grassTile.png"));
            grassSprite2 = ImageIO.read(new File(Main.getDir() +  Main.assets + "grassTile2.png"));
            grassSprite11 = ImageIO.read(new File(Main.getDir() +  Main.assets + "grassTile11.png"));
            grassSprite12 = ImageIO.read(new File(Main.getDir() +  Main.assets + "grassTile12.png"));
            sandSprite = ImageIO.read(new File(Main.getDir() +  Main.assets + "sandTile.png"));
                    
            /*
            waterSprite = ImageIO.read(new Sprite(TerrainType.class.getResource("/Assets/waterTile.png")).url);
            grassSprite = ImageIO.read(new Sprite(TerrainType.class.getResource("/Assets/grassTile.png")).url);
            grassSprite2 = ImageIO.read(new Sprite(TerrainType.class.getResource("/Assets/grassTile2.png")).url);
            sandSprite = ImageIO.read(new Sprite(TerrainType.class.getResource("/Assets/sandTile.png")).url);
            */
            isSet = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    ///FIELDS
    public static boolean isSet = false;
    public static BufferedImage grassSprite = null;
    public static BufferedImage grassSprite2 = null;
    public static BufferedImage grassSprite11 = null;   //oil
    public static BufferedImage grassSprite12 = null;   //metal
    public static BufferedImage sandSprite = null;
    public static BufferedImage waterSprite = null;
}
