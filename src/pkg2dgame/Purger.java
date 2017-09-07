/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Units.Unit;
import java.util.Iterator;

/**
 * its goal is to periodically remove dead units from the Game handler
 * @author Joseph
 */
public class Purger implements Runnable{
    public Handler handler;
    public Iterator it;
    public Purger(Handler h){
        handler = h;
        it = h.iterator;
    }

    //remove all dead units every 5 seconds
    @Override
    public void run() {
        try{
        while(true){
           Thread.sleep(5000);
            while (it.hasNext()) {
                Object o = it.next();
                GameObject go = (GameObject)o;
                if(go.id == ID.Unit){
                    Unit u = (Unit)go;
                    if(!u.isAlive){
                        handler.getObjects().remove(u);
                    }
                }
            }
        }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Purger down!");
        }
    }
}
