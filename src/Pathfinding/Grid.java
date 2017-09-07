/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import Terrain.*;
import java.util.ArrayList;
import pkg2dgame.Game;
import pkg2dgame.GameObject;
import pkg2dgame.Main;

/**
 * 2D representation of a grid of nodes
 * @author Joseph
 */
public class Grid {

    public Node[][] nodes;
    private int length, height;

    ///constructor
    //populates the grid with a new node for each coordinate
    public Grid(int x, int y) {
       // Main.display("Populating Main Grid...");
        x++;//add 1 to hieght and width so that we have one additional node to help keep track of bounds
        y++;
        nodes = new Node[x][y];
        for (int i = 0; i < nodes.length; i++) {
            for (int w = 0; w < nodes[i].length; w++) {
                nodes[i][w] = new Node(i, w);
                //System.out.println("populating...");
            }
        }

        this.length = x;
        this.height = y;

        ////prints the number of nodes
        int w = 0;
        for (Node[] a : nodes) {
            for (Node n : a) {
                w++;
            }
        }
        System.out.println("nodes " + w);
    }

    ///methods
    public Node[] getNeighbors(Node in) {
        //System.out.println("getting neightbors of "+in.toString());
        ArrayList<Node> n = new ArrayList<>();
        if (in.outOfBounds){
            System.out.println("getting neighbor for out of bounds node");
            return null;
        }
        //add surrounding nodes to grid
        //System.out.println(in.x+ " "+ in.y);
        n.add(nodes[in.x+1][in.y]);
        n.add(nodes[in.x-1][in.y]);
        n.add(nodes[in.x][in.y+1]);
        n.add(nodes[in.x][in.y-1]);
        n.add(nodes[in.x+1][in.y+1]);
        n.add(nodes[in.x-1][in.y-1]);
        n.add(nodes[in.x+1][in.y-1]);
        n.add(nodes[in.x-1][in.y+1]);
        
        
        Node[] output = new Node[n.size()];
        for (int i = 0; i < output.length;i++){
            output[i]=n.get(i);
        }
        return output;
    }

    ///tells each node to check weather or not it is valid
    public void update() {
        System.out.println("DO NOT CALL THIS UPDATE METHOD");
        for (Node n[] : nodes) {
            for (Node realNode : n) {
                realNode.checkIfValid();
            }
    }
}
    
    /**
     * Validates a range of nodes. returns false if any node returns as invalid.
     * nodes will not trigger invalid based on the occupancy of object "o"
     * @param startX    beginning x coordinate  
     * @param endX      ending x coordinate
     * @param startY    beginning y coordinate
     * @param endY      ending y coordinate
     * @param o         nodes occupied by this object will not return invalid unless out of bounds or occupied by another object
     * @return boolean verdict
     */
    public boolean isRangeValid(int startX, int endX, int startY, int endY, GameObject o) {
        startX = Main.clamp(startX, 0, Game.width);
        endX = Main.clamp(endX, 0, Game.width);
        startY = Main.clamp(startY, 0, Game.height);
        endY = Main.clamp(endY, 0, Game.height);
        for (int x = startX; x < endX; x++) {
            for (int y = endY; y < startY; y++) {
                if (!Game.mainGrid.nodes[x][y].isValid(o)) {
                    return false;
                }
            }
        }
    
    
        return true;
    }
    /**
     * NOTE START Y AND END Y NEED TO BE REVERSED
     * @param o object exception, will not return this object
     * @return 
     */
    public GameObject getOccupantOfRange(int startX, int endX, int startY, int endY, GameObject o) {
        startX = Main.clamp(startX, 0, Game.width);
        endX = Main.clamp(endX, 0, Game.width);
        startY = Main.clamp(startY, 0, Game.height);
        endY = Main.clamp(endY,0,Game.height);
        for (int x = startX; x < endX; x++) {
            for (int y = endY; y < startY; y++) {
                if (Game.mainGrid.nodes[x][y].getOccupant(o) != null) {
                    return Game.mainGrid.nodes[x][y].getOccupant(o);
                }
            }
        }

        return null;
    }
    
    
    
    
    
    /**
     * returns an arraylist of nodes in a given 2d range
     *  note Y start/end may need to be swapped
     * @param startX    beginning x coordinate  
     * @param endX      ending x coordinate
     * @param startY    beginning y coordinate
     * @param endY      ending y coordinate
     * @return          the arraylsit of nodes
     */
    public ArrayList<Node> getRange(int startX, int endX, int startY, int endY) {
        startX = Main.clamp(startX, 0, Game.width);
        endX = Main.clamp(endX, 0, Game.width);
        startY = Main.clamp(startY, 0, Game.height);
        endY = Main.clamp(endY,0,Game.height);
        ArrayList<Node> output = new ArrayList<>();
        try {
            for (int x = startX; x < endX; x++) {
                for (int y = endY; y < startY; y++) {
                    try{
                    output.add(Game.mainGrid.nodes[x][y]);
                    }catch(Exception e){e.printStackTrace();return null;}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * returns the object overlapping with a set of coordinates. is more than one object is there, it will return the first one it sees in the handler's object field
     * if no objects are found, return null.
     * @param X x coordinate
     * @param Y y coordinate
     * @return the object
     */
    public GameObject getObjectAt(int X, int Y) {
        for (GameObject go : Game.handler.getObjects()) {
            if ((X < go.x + (go.width / 2) && X > go.x - (go.width / 2)) && Y < go.y + (go.height / 2) && Y > go.y - (go.height / 2)) {
               // System.out.println(go);
                return go;  //go is at the impact site
            }
        }
        return null;    //there is no object there
    }

    public Tile getTileAt(int x, int y){
        if(x%Tile.SIZE == 0) x++;   //corrects if we are on the borderline between two tiles
        if(y%Tile.SIZE == 0) y++;
        
        for (Tile[] layer1: Game.myMap.tileSet){
            for(Tile t: layer1){
           if(x<t.gridx+Tile.SIZE && x > t.gridx && y< t.gridy+Tile.SIZE && y>t.gridy)  
             return t;
        }
    }
        System.out.println("no tile at those coordinates");
        return null;
    }
}//end of class
