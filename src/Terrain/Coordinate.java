/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrain;

/**
 * class that holds coordinate
 * @author Joseph
 */
public class Coordinate {
    /**
     * where it is
     */
    public int x, y;    //where it is on the map
    /**what it has on it(nothing, iron, oil)*/
    public int fertility;
    
    /**
     * @param x x coordinate
     * @param y y coordinate
     * @param f what fertility is here
     */
    public Coordinate(int x, int y, int f){
    this.x= x;
    this.y=y;
    this.fertility=f;
    }
    
    @Override
    public String toString(){
        return "COORDINATE: " + x+"," +y+ " F:"+fertility;
    }
}
