/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import GameObjects.ID;
import static GameObjects.ID.Unit;
import pkg2dgame.*;
import Units.*;

/**
 * a single point on the grid
 *
 * @author Joseph
 */
public class Node {

    //fields
    public int x, y; //coordinates
    public int Fcost, Hcost, Gcost; //values used for pathfinding. g is distance from user, h is distance from target. Fcost is their sum
    public boolean validity = false;    //can it be walked on?
    public boolean outOfBounds = false; //is it outside the playable area
    public Node parent = null;          //the node it came from

    //Constructors
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, int G, int H) {
        this.x = x;
        this.y = y;
        this.Gcost = G;
        this.Hcost = H;
        this.Fcost = G + H;
    }

    public Node(Node n) {
        this.x = n.x;
        this.y = n.y;
        this.Fcost = n.Fcost;
        this.Hcost = n.Hcost;
        this.Gcost = n.Gcost;
    }

    /**
     * returns false if the node is out of bounds or is occupied by anu object
     *
     * @return  boolean verdict
     */
    public boolean isValid() {
        ////if out of bounds, return flase.
        if (this.x >= Game.width) {
            this.outOfBounds = true;
            return false;
        }
        if (this.x < 0) {
            this.outOfBounds = true;
            return false;
        }
        if (this.y >= Game.height) {
            this.outOfBounds = true;
            return false;
        }
        if (this.y < 0) {
            this.outOfBounds = true;
            return false;
        }

        ///if colliding with an object, return false.
        for (GameObject go : Game.handler.getObjects()) { //deviding by more than  will make a smaller collision field
            if ((this.x < go.x + go.width / 1 && this.x > go.x - go.width / 1) && this.y < go.y + go.height / 2 && this.y > go.y - go.height / 2) {
                // System.out.println(x + "OBJECT COLLISION" + y);
                if(!go.isEthereal)return false;
            }
        }

        return true;
    }

    /**
     * returns false if the node is out of bounds or occupied by any game
     * object, excluding the "exception"
     * @param exception the object to be excluded
     * @return result
     */
    public boolean isValid(GameObject exception) {
        ////if out of bounds, return flase.
        if (this.x >= Game.width) {
            this.outOfBounds = true;
            return false;
        }
        if (this.x < 0) {
            this.outOfBounds = true;
            return false;
        }
        if (this.y >= Game.height) {
            this.outOfBounds = true;
            return false;
        }
        if (this.y < 0) {
            this.outOfBounds = true;
            return false;
        }
try{
        
        ///if colliding with an object, return false.
        for (GameObject go : Game.handler.getObjects()) {
            if ((this.x < go.x + (go.width / 2) && this.x > go.x - (go.width / 2)) && this.y < go.y + (go.height / 2) && this.y > go.y - (go.height / 2)) {
                // System.out.println(x + "OBJECT COLLISION" + y);
                if (go != exception) {
                    if(go.id == ID.Unit){
                        Unit u = (Unit)go;
                        Unit e = (Unit)exception;
                        if (go != null && (!go.isEthereal && u.plane == e.plane))return false;
                    
                    }else{
                        if(!go.isEthereal)return false;
                    }
                }///if the collision is with the exception, ignore it
            }
        }   
}catch(Exception e){
    e.printStackTrace();
    return false;
}
return true;
    }

    /**
     * returns the object occupying a node. 
     * @param exception this object will not be returned by the method. set as null for all objects to be returnable.
     * @return the occupant
     */
    public GameObject getOccupant(GameObject exception) {

        for (GameObject go : Game.handler.getObjects()) {
            if ((this.x < go.x + (go.width / 2) && this.x > go.x - (go.width / 2)) && this.y < go.y + (go.height / 2) && this.y > go.y - (go.height / 2)) {
                if (go != exception) {
                    return go;
                }
            }
        }

        return null;
    }

    /**
     * sets Hcost based on the distance from target
     * @param x target X
     * @param y target Y
     */
    public void setHcost(int x, int y) {
        int value = 0;  //H value
        int yDif = Math.abs(this.y - y);   ///differnce in Y values (absilute value)
        int xDif = Math.abs(this.x - x);//differnece in x values (absolute value)
        boolean done = false;
        while (!done) {
            if (yDif != 0 && xDif != 0) {        //going diagonal
                yDif--;                          //decrement distance from both 
                xDif--;                          //decrement distance
                value += 14;                     //incrase Hcost
            }
            if (!(yDif != 0 && xDif != 0) && xDif > 0) {       //going sideways. check to make sure we cant go diagonal first
                xDif--;
                value += 10;
            }
            if (!(yDif != 0 && xDif != 0) && yDif > 0) {      //going up/down .check to make sure we cant go diagonal first
                yDif--;
                value = +10;
            }
            if (yDif == 0 && xDif == 0) {
                done = true;         ///if we arrived, we are done
            }

        }
        this.Hcost = value;
    }

    /**
     * sets Gcost based on the distance from user
     *
     * @param home user
     */
    public void setGcost(Node home) {
        int value = 0;
        int yDif = Math.abs(this.y - home.y);   ///differnce in Y values (absilute value)
        int xDif = Math.abs(this.x - home.x);//differnece in x values (absolute value)
        boolean done = false;
        while (!done) {
            if (yDif != 0 && xDif != 0) {        //going diagonal
                yDif--;                          //decrement distance from both 
                xDif--;                          //decrement distance
                value += 14;                     //incrase Hcost
            }
            if (!(yDif != 0 && xDif != 0) && xDif > 0) {       //going sideways. check to make sure we cant go diagonal first
                xDif--;
                value += 10;
            }
            if (!(yDif != 0 && xDif != 0) && yDif > 0) {      //going up/down .check to make sure we cant go diagonal first
                yDif--;
                value = +10;
            }
            if (yDif == 0 && xDif == 0) {
                done = true;         ///if we arrived, we are done
            }

        }
        this.Gcost = value;
    }

    /**
     * sets the F cost based on distance from user and distance from target
     * @param destX destination X
     * @param destY destination Y
     * @param home user
     */
    public void setFcost(int destX, int destY, Node home) {
        this.setGcost(home);    ///sets G
        this.setHcost(destX, destY);    //sets H
        this.Fcost = this.Gcost + this.Hcost; //F is sum of other two
    }

    /**
     *runs the isValid method and sets this node's boolean validity field to the result 
     */
    
    public void checkIfValid() {
        this.validity = this.isValid();
    }

    
    /**
     *runs the isValid(object exception) method and sets this node's boolean validity field to the result 
     * @param exception the object exception of the isValid method
     */
    
    public void checkIfValid(GameObject exception) {
        this.validity = this.isValid(exception);
    }

    /**
     * sets this node's cost fields to 0, parent to null, and runs the checkIfValid() method
     */
    
    public void reset() {
        this.Gcost = 0;
        this.Fcost = 0;
        this.Hcost = 0;
        this.parent = null;
        this.checkIfValid();
    }

    /**
     * returns this node's surrounding nodes
     * @return array of neighbors
     */
    public Node[] getNeighbors() {
        return Game.mainGrid.getNeighbors(this);
    }
    

    @Override
    public String toString() {
        if (this.isValid()) {
            return "Node at " + x + "," + y + " valid";
        } else {
            return "Node at " + x + "," + y + " invalid";
        }
    }

}
