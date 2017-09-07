/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

import java.awt.Graphics;
import java.util.ArrayList;
import pkg2dgame.Game;

/**
 * Contains a 2D array of tiles used in the game
 *
 * @author Joseph
 */
public class Map {

    //Fields
    public static int camX, camY;           //location of the camera
    /**in tiles*/public int camWidth = 14, camHeight = 14;         //how many tiles can be displayed at once  -usually 30x21
    public Tile[][] tileSet;                //the main grid of tiles
    public String name = "not yet named";   //name of map
    int width, height;                      ///in tiles
    public boolean isTerrainMoving = false; //if we are moving the terrain or the camera
    public ArrayList<Coordinate> coordinates = new ArrayList<>();
    
    //Constructors
    /**
     * Creates a new map of all grass tiles. tiles are 20x20
     * @param width how wide the map is
     * @param height how tall the map is
     */
    public Map(int width, int height) {
        this.setUp(width, height);
    }

    /**
     * Crates a prebuilt map based on number entered
     * @param seed
     */
    public Map(int seed) {
        switch (seed) {
            case 1:
                //blank map on the smaller side
                this.setUp(35, 21);
                this.populateCoordinates();
                return;
            case 2:
                this.setUp(60, 20);   //a very shot and long map filled with symetrical resource zones used for pve or ai fighting
                for(Tile[] ta: this.getField(4,4,4,8)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(16,4,4,4)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(56,4,4,8)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(44,4,4,4)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(25,16,10,4)){
                     for(Tile t : ta){
                       t.setFertility(1);
                    }
                }
                this.populateCoordinates();
                return;
            case 3:
                this.setUp(50, 50); // very lare map with one water tile in the center
                this.tileSet[20][20].setTerrainType(TerrainType.Water);
                this.populateCoordinates();
                return;
            case 4:
                this.setUp(35, 35); //current test map
                for(Tile[] ta: this.getField(10, 5, 6, 4)){
                    for(Tile t : ta){
                        t.setTerrainType(TerrainType.Water);
                    }
                }
                  for(Tile[] ta: this.getField(10, 10, 2, 2)){
                     for(Tile t : ta){
                       t.setFertility(1);
                    }
                 }
                  for(Tile[] ta: this.getField(20, 14, 2, 2)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                 }
                  this.populateCoordinates();
                return;
            case 5:
                this.setUp(35, 20);
                Game.localUser.metal += 500;
                for(Tile[] ta: this.getField(19, 5, 8, 8)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(12, 10, 8, 4)){
                     for(Tile t : ta){
                       t.setFertility(1);
                    }
                }
                this.populateCoordinates();
                return;
            case 6: //main PvE gameplay map
                this.setUp(45, 20);
                for(Tile[] ta: this.getField(8, 5, 8, 7)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(32, 5, 7, 8)){
                     for(Tile t : ta){
                       t.setFertility(2);
                    }
                }
                for(Tile[] ta: this.getField(8, 1, 5, 5)){
                     for(Tile t : ta){
                       t.setFertility(1);
                    }
                }
                for(Tile[] ta: this.getField(30, 1, 5, 5)){
                     for(Tile t : ta){
                       t.setFertility(1);
                    }
                }
                this.populateCoordinates();
                return;
            default:
                System.out.println("Unknown Map Seed: " + seed);
        }
    }

    /**
     * sets all tiles on the map to grass
     */
    public void reset() {
        tileSet = new Tile[width][height];
        for (int i = 0; i < tileSet.length; i++) {
            for (int w = 0; w < tileSet[i].length; w++) {
                tileSet[i][w] = new Tile(TerrainType.Grass, i, w);
            }
        }
        this.populateCoordinates();
    }

    /**
     * sets the map to a blank map of given width and height
     * @param width
     * @param height
     */
    public void setUp(int width, int height) {
        if (!TerrainType.isSet) {
            TerrainType.setSprites();
        }
        tileSet = new Tile[width][height];
        for (int i = 0; i < tileSet.length; i++) {
            for (int w = 0; w < tileSet[i].length; w++) {
                tileSet[i][w] = new Tile(TerrainType.Grass, i, w);
            }
        }
        this.width = tileSet.length;
        this.height = this.tileSet[0].length;
    }

    /**
     * renders all tiles in the map
     * @param g using this graphics
     */
    public void render(Graphics g) {
        for (Tile[] t : tileSet) {
            for (Tile tile : t) {
                tile.render(g);
            }
        }
    }

    /**
     * renders the tiles on screen
     */
    public void renderCamera(Graphics g) {
        for (int i = camX; i < camX + camWidth; i++) {                   //render <cam wdith> number of tiles sideways
            for (int h = camY; h < camY + camHeight; h++) //render <cam height> number of tiles up and down)
            {
                try{
            tileSet[i][h].render(g);
                }catch(Exception e){}
              
            }
        }
    }

    public void camUp() {
        if (camY > 0) {
            camY--;    
        }

    }

    public void camDown() {
      //  if (camY + camHeight < height) {
            camY++;
            
     //   }
    }

    public void camRight() {
       //if(camX+camWidth < width){
        camX++;
     //  }
    }

    public void camLeft() {
        if(camX>0){
            camX--;
        }
    }
    
    public Tile[][] getField(int startx, int startY, int w, int h){
        Tile[][] output = new Tile[w][h];
        for(int i = startx; i< startx+w;i++){
            for(int i2 = startY; i2 < startY + h;i2++){
                output[i-startx][i2-startY] = tileSet[i][i2];
            }
        }
        return output;
    }

    /**
     * moves camera to specific X,Y coordinate
     * @param x
     * @param y
     */
    public void jumpCamTo(int x, int y) {
        camX = x;
        camY = y;
    }
    
    /**
     * fills the coordinate arraylist with the current map layout
     */
    public void populateCoordinates(){
        coordinates.clear();
        for(Tile[] t1 : this.tileSet){
            for(Tile t : t1){
                this.coordinates.add(new Coordinate(t.x,t.y,t.fertility));
            }
        }
    }
}
