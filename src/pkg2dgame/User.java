/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import Units.*;
import Units.GameUnits.BasicFactory;
import Units.GameUnits.FieldTruck;

/**
 * Used for both plater and AI commands
 *
 * @author Joseph
 */
public class User {

    //FIELDS

    public Team team;           //team player is on
    public int metal, oil;      //amount of resources they have
    public boolean isAI;          //is the player AI controlled?
    public final int playerID;          //unique identifier for the player
    private static int playerIDs = 1;   //this is what determines that unique id. incremenets with each player that is added
    public UI ui;

    //constructor
    public User(Team team, boolean isAI) {
        this.team = team;
        this.isAI = isAI;
        this.metal = 200;
        this.oil = 100;
        this.playerID = playerIDs;
        playerIDs++;
    }

    /**
     * Orders the AI to have all units attack move to a specific coordinate
     *
     * @param x
     * @param y
     */
    public void allInAttack(int x, int y) {
        for (Unit u : Game.handler.getUnits()) {
            if (u.team == this.team) {
                u.issueAttackMove(x, y);
            }
        }
    }

    /**
     * orders all units in given unit array to attack move towards coordinate
     *
     * @param attackGroup the units to use
     * @param x
     * @param y
     */
    public void attackOrder(Unit[] attackGroup, int x, int y) {
        for (Unit u : attackGroup) {
            u.issueAttackMove(x, y);
        }
    }

    /**
     * makes the AI build an iron mine at a specified coordinate if able AI will
     * attempt to build a fieldtruck if none are available to construct the
     * building
     * @param x
     * @param y
     */
    public void buildIronMine(int x, int y) {
        boolean truckAvail = false;
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                truckAvail = true;
                FieldTruck ft = (FieldTruck) u;
                if(ft.queued)continue;
                ft.BuildAt(x, y, 3);
                return;
            }
        }
        //it it gets to here, there are no field trucks avail, so we will attempt to build one
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Factory" && u.team == team) {
                BasicFactory bf = (BasicFactory) u;
                if (bf.isProducing) {
                    continue;  //ignore the factory if it is already building somthing
                }
                bf.Produce2();
                if (bf.lastProduced.name == "Field Truck" && bf.lastProduced.isAlive) {
                    FieldTruck ft = (FieldTruck) bf.lastProduced; //if we successfully built a field truck, have it go straight to building our mine 
                    ft.BuildAt(x, y, 3);
                }
                return;   //return as we only need to build one
            }
        }
    }
    
     /**
     * makes the AI build an oilrig at a specified coordinate if able AI will
     * attempt to build a fieldtruck if none are available to construct the
     * building
     * @param x
     * @param y
     */
    public void buildOilrig(int x, int y){
        boolean truckAvail = false;
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                truckAvail = true;
                FieldTruck ft = (FieldTruck) u;
                if(ft.queued)continue;
                ft.BuildAt(x, y, 2);
                return;
            }
        }
        //it it gets to here, there are no field trucks avail, so we will attempt to build one
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Factory" && u.team == team) {
                BasicFactory bf = (BasicFactory) u;
                if (bf.isProducing) {
                    continue;  //ignore the factory if it is already building somthing
                }
                bf.Produce2();
                if (bf.lastProduced.name == "Field Truck" && bf.lastProduced.isAlive) {
                    FieldTruck ft = (FieldTruck) bf.lastProduced; //if we successfully built a field truck, have it go straight to building our mine 
                    ft.BuildAt(x, y, 2);
                }
                return;   //return as we only need to build one
            }
        }
    }
    
    /**
     * makes the AI build a factory at a specified coordinate if able AI will
     * attempt to build a fieldtruck if none are available to construct the
     * building
     * @param x
     * @param y
     */
    public void buildFactory(int x, int y){
         boolean truckAvail = false;
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                truckAvail = true;
                FieldTruck ft = (FieldTruck) u;
                if(ft.queued)continue;
                ft.BuildAt(x, y, 1);
                return;
            }
        }
        //it it gets to here, there are no field trucks avail, so we will attempt to build one
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Factory" && u.team == team) {
                BasicFactory bf = (BasicFactory) u;
                if (bf.isProducing) {
                    continue;  //ignore the factory if it is already building somthing
                }
                bf.Produce2();
                if (bf.lastProduced.name == "Field Truck" && bf.lastProduced.isAlive) {
                    FieldTruck ft = (FieldTruck) bf.lastProduced; //if we successfully built a field truck, have it go straight to building our mine 
                    ft.BuildAt(x, y, 1);
                }
                return;   //return as we only need to build one
            }
        }
    }
}
