/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import GameObjects.ID;
import static GameObjects.ID.Unit;
import Terrain.PathingLayer;
import java.util.ArrayList;
import pkg2dgame.*;
import Units.*;

/**
 * Class given to objects that allow them to pathfind
 *
 * @author Joseph
 */
public class Pathfinder {

    ///fields
    public GameObject host = null; ///what gameobject this pathfinder is controlling
    public int x, y, destX, destY;   ////coordinates of host object
    public Direction direction = Direction.stop;   ///direction we are moving
    private  ArrayList<Node> destField = new ArrayList<>();      //field around destination in which we can stop

    //constructor
    public Pathfinder(GameObject go) {
        this.host = go;
        this.x = go.x;
        this.y = go.y;
        this.destX = this.host.destX;
        this.destY = this.host.destY;
    }

    /**
     * sets the destination via x/y coordinates
     * @param x destination x
     * @param y destination y
     */
    public void setDestination(int x, int y) {
        destX = x;
        destY = y;
        this.setDestinationField();
    }

    /**
     * determines witch direction to go based on destination
     * @return direction to go
     */
    public Direction getDirection(int destX, int destY) {
        destX = Main.clamp(destX, 0, Game.width);
        destY = Main.clamp(destY, 0, Game.height);
        host.x = Main.clamp(host.x,0,Game.width);
        host.y = Main.clamp(host.y,0,Game.height);
        this.setDestinationField();
        this.x = host.x;
        this.y = host.y;
        boolean left = false, right = false, above = false, below = false;      ///possible directions
        if (this.destField.contains(Game.mainGrid.nodes[x][y])){
            host.isPathing=false;   
            return Direction.stop;  ///we are at the destination
            
        }
        

        if (!this.amIValid()) {
            return Direction.stop;  //ran into something
        }

        if (destX < host.x) {
            left = true;                //its left of us
        }
        if (destX > host.x) {
            right = true;               //its right of us
        }
        if (destY > host.y) {
            above = true;               //its above us
        }
        if (destY < host.y) {
            below = true;               //its below us
        }

        if (above && !right && !left) {
            return Direction.up;         ///if directly above
        }
        if (above && right) {
            return Direction.upRight;         ///if above and right
        }
        if (above && left) {
            return Direction.upLeft;         ///if above and left
        }

        if (below && !right && !left) {
            return Direction.down;         ///if directly below
        }
        if (below && right) {
            return Direction.downRight;         ///if below and right

        }
        if (below && left) {
            return Direction.downLeft;         ///if below and left
        }
        if (left && !above && !below) {
            return Direction.left;         ///if directly left
        }

        if (right && !above && !below) {     //if directly right
            return Direction.right;
        }
                System.out.println("idk");
                return Direction.stop;          //if we dont know what do to, dont do anything!
    }

    ///sets velocity based on direction
    public void move() {
        this.direction = this.getDirection(destX,destY);
        if (direction == Direction.stop) {   //if we are stopped, set velocity to 0
            host.setVelX(0);
            host.setVelY(0);
            return;
        }
        if (direction == Direction.up) {
            host.setVelX(0);
            host.setVelY(1);       //host.speed
            return;
        }
        if (direction == Direction.down) {
            host.setVelX(0);
            host.setVelY(-1);   //-host.speed
            return;
        }
        if (direction == Direction.right) {
            host.setVelX(1);
            host.setVelY(0);
            return;
        }
        if (direction == Direction.left) {
            host.setVelX(-1);
            host.setVelY(0);
            return;
        }
        if (direction == Direction.upLeft) {
            host.setVelX(-1);
            host.setVelY(1);
            return;
        }
        if (direction == Direction.downLeft) {
            host.setVelX(-1);
            host.setVelY(-1);
            return;
        }
        if (direction == Direction.downRight) {
            host.setVelX(1);
            host.setVelY(-1);
            return;
        }
        if (direction == Direction.upRight) {
            host.setVelX(1);
            host.setVelY(1);
            return;
        }
    }
    /**
     * checks to see if the pathing is valid. does not let you run towards an obstical
     * @return yes or no
     */
    
    public boolean amIValid(){
        if(host.isEthereal ){return true;} //etherial units never block pathing so are always valid
        Direction d = this.getPureDirection(destX, destY);
        int newX = host.x;
        int newY = host.y;
        switch (d){
            case right:
                newX += host.speed;
                break;
            case downRight:
                newX +=host.speed;
                newY -=host.speed;
                break;
            case upRight:
                newX += host.speed;
                newY += host.speed;
                break;
            case up:
                newY += host.speed;
                break;
            case down:
                newY -= host.speed;
                break;
            case downLeft:
                newX -= host.speed;
                newY -= host.speed;
                break;
            case left:
                newX -= host.speed;
                break;
            case upLeft:
                newX -= host.speed;
                newY += host.speed;
        }
         if(!this.isterrainValid(newX, newY)) return false;          //if terrain isnt valid, we are not vaild
        if (Game.mainGrid.isRangeValid(newX-host.width/2, newX+host.width/2, newY+host.height/2, newY-host.height/2, host)){
            //System.out.println("im Valid");
            return true;
        }
    
        //System.out.println("NOT VALID");
    return false;}
    
    
    /**
     * like get direction but it does not stop when it encounters an obstical
     * @param destX destination x
     * @param destY destination y
     * @return  direction. returns stop if we have arrived
     */ 
    public Direction getPureDirection(int destX, int destY){
        
        boolean left = false, right = false, above = false, below = false;      ///possible directions
        
        if (destX < host.x) {
            left = true;                //its left of us
        }
        if (destX > host.x) {
            right = true;               //its right of us
        }
        if (destY > host.y) {
            above = true;               //its above us
        }
        if (destY < host.y) {
            below = true;               //its below us
        }

        if (above && !right && !left) {
            return Direction.up;         ///if directly above
        }
        if (above && right) {
            return Direction.upRight;         ///if above and right
        }
        if (above && left) {
            return Direction.upLeft;         ///if above and left
        }

        if (below && !right && !left) {
            return Direction.down;         ///if directly below
        }
        if (below && right) {
            return Direction.downRight;         ///if below and right

        }
        if (below && left) {
            return Direction.downLeft;         ///if below and left
        }
        if (left && !above && !below) {
            return Direction.left;         ///if directly left
        }

        if (right && !above && !below) {     //if directly right
            return Direction.right;
        }
                return Direction.stop;          //if we dont know what do to, dont do anything!
    
    }
    /**
     * gets distance from another unit
     */
    public int getDistanceFrom(GameObject target) {
        int difX = Math.abs(x - target.x);
        int difY = Math.abs(y - target.y);
        int distance = 0;
        boolean done = false;
        while (!done) {
            if (difX > 0 && difY > 0) {   //going diagonal adds 14 distance
                distance+=14;
                difX--;
                difY--;
            }
            if (difX > 0) {   //going sideways adds 10 distance
                distance+=10;
                difX--;
            }

            if (difY > 0) {   //going up/down adds 10 distance
                distance+=10;
                difY--;
            }
            if (difY == difX && difX == 0) {   //if both are 0 then we are done and differance is determined
                done = true;
            }

        }
        
        return distance;
    }
    
    /**
     * gets distance from particular set of coordinates
     */
        public int getDistanceFrom(int targetX, int targetY) {
        int difX = Math.abs(x - targetX);
        int difY = Math.abs(y - targetY);
        int distance = 0;
        boolean done = false;
        while (!done) {
            if (difX > 0 && difY > 0) {   //going diagonal adds 14 distance
                distance+=14;
                difX--;
                difY--;
            }
            if (difX > 0) {   //going sideways adds 10 distance
                distance+=10;
                difX--;
            }

            if (difY > 0) {   //going up/down adds 10 distance
                distance+=10;
                difY--;
            }
            if (difY == difX && difX == 0) {   //if both are 0 then we are done and differance is determined
                done = true;
            }

        }
        
        return distance;
    }
    
    
    

    private ArrayList<Node> setDestinationField(){
        int size = 2;
        this.destField=Game.mainGrid.getRange(destX-size,destX+size,destY+size,destY-size);
        return Game.mainGrid.getRange(destX-size,destX+size,destY+size,destY-size);
    }
    
    /**
     * checks if the terrian is valid for the host at the given coordinates
     * @param x
     * @param y
     * @return 
     */
    
     public boolean isterrainValid(int x, int y){
         if(host.id != ID.Unit)return true;          //if its not a unit, we dont care
         Unit u = (Unit)host;
         switch(u.plane){
             case land:
                 if(Game.mainGrid.getTileAt(x, y).pathingLayer == PathingLayer.Water) return false;     //land cant go on water
                 break;
             case sea:
                 if(Game.mainGrid.getTileAt(x, y).pathingLayer != PathingLayer.Water) return false;     //sea can only move on water
             case air:
                 return true;                                                                           //air goes anywhere
         }
         return true;
     }
    
    
    
    
    
    
    
    
    
    
    
    ////BELOW IS COMPLICATED ATTEMPT TO DO A* PATHFINDING
    /* 
     public void go(int goX, int goY){
     ArrayList<Node> path = this.getPathTo(goX, goY);
     int place = 0;  ///place in the chain of destinations.
     if (path.get(0).x > host.x) {   ///if the next step is to the right
     host.setVelX(1);        //go to the right
     System.out.println("going right");
     }
     if (path.get(0).x < host.x) {   //same for left
     host.setVelX(-1);
     System.out.println("going left");
     }
     if (path.get(0).y < host.y) {   //if the next step is below
     host.setVelY(-1);
     System.out.println("going down");
     }
     if (path.get(0).y > host.y) {   //if the next step is above
     host.setVelY(1);
     System.out.println("going up");
     }
        
     }
    
    
    

     public ArrayList<Node> getPathTo(int destX, int destY) {
     this.destX = destX;
     this.destY = destY;
     ArrayList<Node> output = new ArrayList<>(); //output
     ArrayList<Node> OPEN = new ArrayList<>();   //set to be evaluated
     ArrayList<Node> CLOSED = new ArrayList<>(); //set of nodes already evaluated
     boolean found = false;                      //is weather or not we are done
     Node start = Game.mainGrid.nodes[this.x][this.y];
     Node current = null;    ///node being evaluated
     OPEN.add(start);

     while (!found) {
     current = this.getLowestFCost(OPEN);
     OPEN.remove(current);
     CLOSED.add(current);
     if (current == Game.mainGrid.nodes[destX][destY]) {
     found = true;   ///if current is the destination, we have found the path
     }
     for (Node n : current.getNeighbors()) {
     if(n.isValid(host) || CLOSED.contains(n)){
     ///if this node is closed or invalid, do nothing
     //  System.out.println("skipped node "+ n.toString());
     }
     else{
     ///if this node is valid and not closed
     //   System.out.println("took node "+n.toString());
     n.parent = current; //set their parent to current because that node birthed it
     OPEN.add(n);
     }
     }
     }///end of while loop

     boolean ready = false;
     while (!ready) {                         ///this loop sets compiles the path based on the chain of nodes. current begins as the last node in the sequence
     output.add(0, current);             ///ads each one to the start of the list
     if (current.parent != null) {
     current = current.parent;
     } else {
     ready = true;
     }
     }
     return output;
     }

     public Node getLowestFCost(ArrayList<Node> input) {
     //System.out.println("getting lowest FCost of arraylist length "+ input.SIZE());
     Node output = new Node(0,0,9999,9999);  //make a placeholder node with huge Fcost
     for (Node n : input) {
     n.setFcost(destX, destY, Game.mainGrid.nodes[x][y]);    /// (destination X, destination Y, where i am now[home])
     }
     for (Node n : input) {
     if (n.Fcost <= output.Fcost) {
     output = n;     ///if this is the lowest so far, make it the output
     }
     }

     return output;
     }
    
     */
}
