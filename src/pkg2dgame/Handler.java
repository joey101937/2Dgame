/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Units.Plane;
import Units.Unit;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * handles all game object functionality in relation to the game. stores all
 * present game objects.
 *
 * @author Joseph
 */
public class Handler {

    private LinkedList<GameObject> object = new LinkedList<GameObject>();
    public Iterator<GameObject> iterator = this.object.iterator();
    public Handler() {
       // Purger purger = new Purger(this);
       // Thread purgingThread = new Thread(purger);
      //  purgingThread.start();
    }

    public LinkedList<GameObject> getObjects(){
    return object;
    }
    
    public void tick() {
        //ticks all game objects
        try {
            for (GameObject o : this.object) {
                if (o.id == ID.Unit) {
                    Unit u = (Unit)o;
                    if(u.isAlive) u.tick();
                }else
                {
                    o.tick();   //each object does something each tick
                    this.containToMap(o);
                }
            }
        }catch(ConcurrentModificationException cme){
            System.out.println(cme+ " Handler");
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * renders all game objects. air units will be rendered last so they are on
     * top
     *
     * @param g
     */
    public void render(Graphics g) {
        /*
         for(int i = 0; i < object.size();i++){
         GameObject tempObject = object.get(i);
         tempObject.render(g);
         }
         */
        try {
            for(GameObject go : object){
                if (go.id == ID.Unit) {
                    Unit u = (Unit) go;
                    if (u.plane != Plane.air && u.unitTypes.contains(Units.Type.structure)){
                        if(u.isAlive)u.render(g);
                    }
                }
            }
            for (GameObject go : object) {
                if (go.id == ID.Unit) {
                    Unit u = (Unit) go;
                    if (u.plane != Plane.air && !u.unitTypes.contains(Units.Type.structure)) {
                       if(u.isAlive) u.render(g);
                    }
                } else {
                    go.render(g);
                }
            }
            for (GameObject go : object) {
                if (go.id == ID.Unit) {
                    Unit u = (Unit) go;
                    if (u.plane == Plane.air) {
                      if(u.isAlive)  u.render(g);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void containToMap(GameObject o) {
        o.x = o.clamp(o.x, Game.width - o.width / 2, 5 + o.width / 2);
        o.y = o.clamp(o.y, Game.height - o.height, 5 + o.width / 2);
    }
    
    /**
     * returns all units in the handler
     * @return all game objects with the unit ID
     */
    public LinkedList<Unit> getUnits(){
        LinkedList<Unit> output = new LinkedList<>();
        for(GameObject go : this.object){
            if(go.id == ID.Unit){
                Unit u = (Unit)go;
                if(u.isAlive)output.add(u);
            }
        }
        return output;
    }
    
}
