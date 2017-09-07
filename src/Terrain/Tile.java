
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

import java.awt.Graphics;
import pkg2dgame.Game;
import pkg2dgame.KeyInput;

/**
 * 20x20 unit of terrain
 * @author Joseph
 */
public class Tile {
    //Filds
    public TerrainType terrainType;
    public PathingLayer pathingLayer;
    public int x,y; ///coordinates of upper left corner reletive to number of tiles
    public int gridx,gridy; //coordinates of upper left corner reletive to pixel, ie grid
    public static final int SIZE = 50;
   /**0 means nothing, 1 means you can harvest oil here 2 means you can harvest metal here */ public int fertility = 0;
   /**Determines what sprite to use*/ public int varient = (int)(Math.random()*10);
    
    ///Constructor
    public Tile(TerrainType tt, int x, int y){
        this.terrainType = tt;
        this.pathingLayer = tt.getPathingLayer();
        this.x = x;
        this.y = y;
        this.gridx=x*SIZE;
        this.gridy=y*SIZE;
    }
    
    public void setFertility(int i){
        this.fertility = i;
        if(i == 1){
            this.varient = 11;
        }
        if(i == 2){
            this.varient = 12;
        }
    }
    
    public void setTerrainType(TerrainType tt){
        this.terrainType = tt;
        this.pathingLayer = tt.getPathingLayer();
    }

    public void render(Graphics g){
        if(isRenderValid()){
        g.drawImage(this.terrainType.getSprite(varient), x*SIZE-Game.myMap.camX*SIZE, y*SIZE-Game.myMap.camY*SIZE, null);
        }
    }
    /**
     * renders at specific location 
     */
        public void render(Graphics g, int x, int y){
        g.drawImage(this.terrainType.getSprite(varient), x, y, null);
    }
        /**
         * is this tile on screen?
         * @return 
         */
         public boolean isRenderValid() {
        
        if(x*Tile.SIZE<KeyInput.tx){return false;}
        if(x*Tile.SIZE>KeyInput.tx+Game.myMap.camWidth*Tile.SIZE){return false;}
        if(y*Tile.SIZE>KeyInput.ty+Game.myMap.camHeight*Tile.SIZE-Tile.SIZE){return false;}
        if(y*Tile.SIZE<KeyInput.ty){return false;}
        return true;
    }
        
        @Override
        public String toString(){
            return (this.terrainType +" tile at " + gridx + ","+gridy);
        }
}
