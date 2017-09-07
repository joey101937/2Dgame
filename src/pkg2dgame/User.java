/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import Terrain.Coordinate;
import Terrain.Tile;
import Units.*;
import Units.GameUnits.*;
import java.util.ArrayList;

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
    public static final int SUPPLYCAP = 15;//how many units you can have at once
    public int currentSupply = 0;       //how many units we currently have
    public boolean hasSupply = true;
    //AI ATTRIBUTES
    public int timidness = 3;              //how many more units the ai must have in order to attack early
    public int playSpeed = 100;          //how often it makes plays. ie every 20 ticks, make a play
    private int currentTick = 0;        //keeps track of current tick to know when to make a play
    //AI FLAGS
    private boolean needAA = false;
    private boolean underAttack = false; 
    
    
//constructor

    public User(Team team, boolean isAI) {
        this.team = team;
        this.isAI = isAI;
        this.metal = 200;
        this.oil = 0;
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
                if (u.weapon == Weapon.none) {
                    continue;   //dont use units with no weapon
                }
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
     * runs every game tick
     */
    public void tick() {
        int currentUnitCount =0;
        for(Unit u : Game.handler.getUnits()){
            if(u.team == team){
                currentUnitCount++;
            }
        }
        this.currentSupply = currentUnitCount;
        if(currentSupply >= User.SUPPLYCAP) hasSupply = false;
        else hasSupply = true;
        currentTick++;
        if (currentTick >= playSpeed) {
            try {
               if(isAI) makePlay();
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentTick = 0;
        }
    }

    /**
     * determines what the AI should do based on its situation
     */
    public void makePlay() {
        System.out.println("MAKING PLAY");
        boolean hasIronMine = false;
        boolean hasOilRig = false;
        ArrayList<FieldTruck> trucks = new ArrayList<>();
        ArrayList<BasicFactory> factorys = new ArrayList<>();
        int distanceFromClosestPatch = 9999999;
        Coordinate closestPatch = null;
        int numMines = 0, numRigs = 0;

        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Iron Mine" && u.team == team) {
                hasIronMine = true;
                numMines++;
            } 
            if (u.name == "Oil Rig" && u.team == team) {
                hasOilRig = true;
                numRigs++;
            }
        }

        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                FieldTruck ft = (FieldTruck)u;
               // if(ft.queued)continue;
                trucks.add(ft);
            }
            if (u.name == "Factory" && u.team == team) {
                factorys.add((BasicFactory) u);
            }
        }

        //if we dont have an iron mine, attempt to create an iron mine at a point nearest our field truck or factory
        if ((numMines < 3 && !underAttack) || numMines < 1) {
            int truckToUse = Main.generateRandom(0, trucks.size());
            if (trucks.size() > 0) {    //if we have a truck but no mine
                for (Coordinate c : Game.myMap.coordinates) {
                    if (trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 2 && Game.mainGrid.getOccupantOfRange(c.x * Tile.SIZE - 100, c.x * Tile.SIZE + 100, c.y * Tile.SIZE + 100, c.y * Tile.SIZE - 100, trucks.get(truckToUse)) == null) {
                        closestPatch = c;
                        distanceFromClosestPatch = trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                    trucks.get(truckToUse).BuildAt(closestPatch.x * Tile.SIZE + Main.generateRandom(0, 25), closestPatch.y * Tile.SIZE + Main.generateRandom(-25, 25), 3);
                    trucks.remove(trucks.get(truckToUse));
                }

            } else if (factorys.size() > 0) {
                distanceFromClosestPatch = 999999;
                for (Coordinate c : Game.myMap.coordinates) {
                    if (factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 2) {
                        closestPatch = c;
                        distanceFromClosestPatch = factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                   // this.buildIronMine(closestPatch.x*Tile.SIZE, closestPatch.y*Tile.SIZE);
                    this.buildTruck();
                }
            }
        }
        System.out.println(numRigs + " rigs" + !underAttack);
        if (numRigs < 2 && hasIronMine) {
            System.out.println(trucks.size() + " trucks");
            int truckToUse = Main.generateRandom(0, trucks.size());
            if (trucks.size() > 0) {    //if we have a truck but no mine
                for (Coordinate c : Game.myMap.coordinates) {
                    if (trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 1 && Game.mainGrid.getOccupantOfRange(c.x * Tile.SIZE - 100, c.x * Tile.SIZE + 100, c.y * Tile.SIZE + 100, c.y * Tile.SIZE - 100, trucks.get(truckToUse)) == null) {
                        closestPatch = c;
                        distanceFromClosestPatch = trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                    trucks.get(truckToUse).BuildAt(closestPatch.x * Tile.SIZE + Main.generateRandom(0, 25), closestPatch.y * Tile.SIZE + Main.generateRandom(-25, 25), 2);
                    trucks.remove(trucks.get(truckToUse));
                }

            } else if (factorys.size() > 0) {
                distanceFromClosestPatch = 999999;
                for (Coordinate c : Game.myMap.coordinates) {
                    if (factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 1) {
                        closestPatch = c;
                        distanceFromClosestPatch = factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                   //this.buildOilrig(closestPatch.x*Tile.SIZE, losestPatch.y*Tile.SIZE);
                    this.buildTruck();
                }
            }
        }
        if(metal > 300 && factorys.size() < 2 && hasIronMine && !underAttack){
            System.out.println(trucks.size() + " trucks");
            int truckToUse = Main.generateRandom(0, trucks.size());
            if (trucks.size() > 0) {    //if we have a truck but no mine
                for (Coordinate c : Game.myMap.coordinates) {
                    if (trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 0 && Game.mainGrid.getOccupantOfRange(c.x * Tile.SIZE - 350, c.x * Tile.SIZE + 350, c.y * Tile.SIZE + 350, c.y * Tile.SIZE - 350, trucks.get(truckToUse)) == null) {
                        closestPatch = c;
                        distanceFromClosestPatch = trucks.get(truckToUse).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                    trucks.get(truckToUse).BuildAt(closestPatch.x * Tile.SIZE + Main.generateRandom(0, 25), closestPatch.y * Tile.SIZE + Main.generateRandom(-25, 25), 2);
                    trucks.remove(trucks.get(truckToUse));
                }

            } else if (factorys.size() > 0) {
                distanceFromClosestPatch = 999999;
                for (Coordinate c : Game.myMap.coordinates) {
                    if (factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE) < distanceFromClosestPatch && c.fertility == 1) {
                        closestPatch = c;
                        distanceFromClosestPatch = factorys.get(0).pf.getDistanceFrom(c.x * Tile.SIZE, c.y * Tile.SIZE);
                    }
                }
                if (closestPatch != null) {
                   //this.buildOilrig(closestPatch.x*Tile.SIZE, closestPatch.y*Tile.SIZE);
                    this.buildTruck();
                }
            }
        }
        
        if(oil > 100 && metal > 50 && hasIronMine){
            for(BasicFactory bf: factorys){
                bf.setDestinaion(bf.x+Main.generateRandom(-400, 400), bf.y+Main.generateRandom(-400,400));
                bf.Produce3();//make helicopters if we have too much oil
            }
        }
        
        if(metal > 150 && hasIronMine && oil > 50 && oil < 200 && !(needAA && hasOilRig)){
           for(BasicFactory bf : factorys) {
                bf.setDestinaion(bf.x + Main.generateRandom(-500, 500), bf.y + Main.generateRandom(-500, 500));
               bf.Produce1(); //make tanks
           }
        }
        
        this.makeCombatMove(); //now its time to coordinate a tactical move
        System.out.println("ENDING PLAY");
    }
    /**
     * part of the make move process. this method decides what to do with the army
     */
    public void makeCombatMove(){
        int friendlyCombat = 0;     //number of friendly combat units
        int friendlyAA = 0;         //number of friendly anti-air combat units
        int enemyCombat = 0;        //number of enemy combat units
        int enemyAA = 0;            //number of enemy antiair units
        int enemyA = 0;             //number of enemy aircraft
        boolean weAreAttacking = false;      //if we are attacking
        ArrayList<Unit> enemyUnits = new ArrayList<>();
        ArrayList<Unit> friendlyUnits = new ArrayList<>();
        ArrayList<Unit> oncomingUnits = new ArrayList<>();  //enemy units that are attacking us
        ArrayList<Unit> combatFriendly = new ArrayList<>(); //friendly combat units
        for(Unit u : Game.handler.getUnits()){
            if (u.team == this.team){
                friendlyUnits.add(u);
                if(u.weapon != Weapon.none) combatFriendly.add(u);
                if(u.isMilitary){
                    friendlyCombat++;
                    if(u.weapon.targetsAir()){
                        friendlyAA++;
                    }
                }
            }else{
                //enemy units
                enemyUnits.add(u);
                if(u.isMilitary){
                    enemyCombat++;
                    if(u.target != null && u.target.team == team) oncomingUnits.add(u);
                    if(u.weapon.targetsAir()){
                        enemyAA++;
                    }
                    if(u.plane == Plane.air){
                        enemyA++;
                    }
                }
            }
        }
        if(enemyUnits.isEmpty() || friendlyCombat <1) return; //dont bother if we are out of combat units or there are no opponents left
        if(enemyA > 0 && friendlyAA < 1){
            this.needAA = true; //if the enemy has aircraft and we have no AA, alert we need antiair
        }
        if(oncomingUnits.isEmpty()) underAttack = false;
        else underAttack = true;
        
        //SCENARIO : ATTACK THEM FOR HAVING WEAKER MILITARY OR WE ARE SUPPLY CAPPED
        if((enemyCombat < friendlyCombat-timidness && !(enemyA > 0 && friendlyAA < 1)) || !this.hasSupply ){ //if we have more combat units than the enemy by at least 3 and we have AA (only if the enemy has Air)
            Unit attackTarget = null;
            Unit alternateTarget = null;
            for(Unit e : enemyUnits){
                if(e.unitTypes.contains(Type.structure)){
                    attackTarget = e;   //use one of their structures as the attack target
                    break;              //we can stop after we find a structure to use
                }
                alternateTarget = e;    //some other nonstructure unit
            }
            if(attackTarget == null) attackTarget = alternateTarget;
            for(Unit f : combatFriendly){
              f.issueAttackMove(Main.generateRandom(attackTarget.x - 100, attackTarget.x + 100), Main.generateRandom(attackTarget.y -100, attackTarget.y+100));
                
            }
            weAreAttacking = true;
        }
        
        
        //SCENARIO : WE ARE ATTACKED
        System.out.println("oncoming: " + oncomingUnits.size());
        if(oncomingUnits.size() > 0 && !weAreAttacking){
            for(Unit u : combatFriendly){
                u.issueAttackMove(oncomingUnits.get(0).x, oncomingUnits.get(0).y);
            }
        }
        
    }
    

    /**
     * makes the AI build an iron mine at a specified coordinate if able AI will
     * attempt to build a fieldtruck if none are available to construct the
     * building
     *
     * @param x
     * @param y
     */
    public void buildIronMine(int x, int y) {
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                FieldTruck ft = (FieldTruck) u;
                if (ft.queued) {
                    continue;
                }
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
     *
     * @param x
     * @param y
     */
    public void buildOilrig(int x, int y) {
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                FieldTruck ft = (FieldTruck) u;
                if (ft.queued) {
                    continue;
                }
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
                if (bf.lastProduced != null && bf.lastProduced.name == "Field Truck" && bf.lastProduced.isAlive) {
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
     *
     * @param x
     * @param y
     */
    public void buildFactory(int x, int y) {
        for (Unit u : Game.handler.getUnits()) {
            if (u.name == "Field Truck" && u.team == team) {
                FieldTruck ft = (FieldTruck) u;
                if (ft.queued) {
                    continue;
                }
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
    
    /**
     * orders one of the available factories to produce a field truck
     */
    public void buildTruck(){
        for(Unit u : Game.handler.getUnits()){
            if(u.name == "Factory" && u.team == team){
                BasicFactory fac = (BasicFactory)u;
                if(fac.isProducing)continue;
                else fac.Produce2();
                return;
            }
        }
    }
}
