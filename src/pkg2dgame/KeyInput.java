/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Pathfinding.*;
import Terrain.Tile;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import Units.*;
import Units.GameUnits.FieldTruck;

/**
 * handles all player keyboard and mouse input
 *
 * @author Joseph
 */
public class KeyInput extends KeyAdapter implements MouseListener, MouseMotionListener {

    private Handler handler;
    public static Game myGame;
    public static int gameX = 0;    //postion of window
    public static int gameY = 0;    //position of window

    /////for camera
    public static boolean camUP = false;
    public static boolean camDown = false;
    public static boolean camRight = false;
    public static boolean camLeft = false;

    //for terrain. tx and ty represent the X/Y coordinates of the top right corner of the screen displayed.
    public static int tx, ty;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void setGame(Game g) {
        myGame = g;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_CONTROL) {
                Game.isCtrlDown = true;
            }
            if (key == KeyEvent.VK_Q) {
                Game.isAdown = true;                  //A-move is mapped to Q so its really Q-move
            }
            if (key == KeyEvent.VK_Z) {
                Game.lookingForTarget = true;
            }
//System.out.println(key);
            //lets objects respond based on key input
            for (GameObject o : handler.getObjects()) {

                /* ************************WASD MOVEMENT FOR PLAYER**********************************/
                if (o.getId() == ID.Player) {       //this one is for player objects
                    if (key == KeyEvent.VK_W) {
                        o.setVelY(-o.speed);   //up
                    }
                    if (key == KeyEvent.VK_S) {
                        o.setVelY(+o.speed);     //down
                    }
                    if (key == KeyEvent.VK_A) {
                        o.setVelX(-o.speed);     //left
                    }
                    if (key == KeyEvent.VK_D) {
                        o.setVelX(+o.speed);     //right
                    }
                    if (key == KeyEvent.VK_Q) {
                        for (Node[] nA : Game.mainGrid.nodes) {
                            for (Node n : nA) {
                                if (!n.isValid()) {
                                    System.out.println(n.toString());
                                }
                            }
                        }
                    }
                }
                /* ********************************************************************************/

            }
            ///pressing the P key will damage the selected unit
            if (key == KeyEvent.VK_P) {
                GameObject o = Game.ki.selected.get(0);
                Structure u = (Structure) o;
                if (u.isOutputClear()) {
                    u.Produce1();//{Game.handler.addObject(new BasicTank);}
                }
            }

            ///pressing the x key will issue stop command
            if (key == KeyEvent.VK_X) {
                if (Game.ki.selected.size() > 0) {
                    for (GameObject go : Game.ki.selected) {
                        if (go != null && go.id == ID.Unit) {
                            Unit t = (Unit) go;
                            t.issueStopCommand();
                        }
                    }
                }
            }

            ///pressing the q key will Print selected uqit's current target
            if (key == KeyEvent.VK_Q) {
                if (Game.ki.selected.size() > 0) {
                    for (GameObject go : Game.ki.selected) {
                        if (go != null) {
                            Unit u = (Unit) go;
                            if (u.target != null) {
                                System.out.println("TARGET IS " + u.target);
                            } else {
                                System.out.println("target is null");
                            }
                        };
                    }
                }
            }
            //pressing K will order the local user to make an iron mine
            if (key == KeyEvent.VK_K) {
                Game.localUser.buildFactory(1100, 300);
            }
            if (key == KeyEvent.VK_M) {
                Game.localUser.buildFactory(1100, 600);
            }

            if (key == KeyEvent.VK_W) {   //UP
                //  Game.myMap.camUp();
                this.camUP = true;
            }
            if (key == KeyEvent.VK_S) { //DOWN
                //    Game.myMap.camDown();
                this.camDown = true;
            }
            if (key == KeyEvent.VK_D) {        //RIGHT
                // Game.myMap.camRight();
                this.camRight = true;

            }
            if (key == KeyEvent.VK_A) {        //LFET
                this.camLeft = true;
                // Game.myMap.camLeft();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_CONTROL) {
            Game.isCtrlDown = false; /// ctrl is no longer pressed
        }
        for (GameObject o : handler.getObjects()) {

            if (o.getId() == ID.Player) { //stop player movement in that direction when they release the key
                if (key == KeyEvent.VK_W) {
                    o.setVelY(0);
                }
                if (key == KeyEvent.VK_S) {
                    o.setVelY(0);
                }
                if (key == KeyEvent.VK_A) {
                    o.setVelX(0);
                }
                if (key == KeyEvent.VK_D) {
                    o.setVelX(0);
                }
            }

        }

        if (key == KeyEvent.VK_W) {
            this.camUP = false;
            // myGame.window.scroll.setScrollPosition(    myGame.window.scroll.getScrollPosition().x, myGame.window.scroll.getScrollPosition().y-16);

        }
        if (key == KeyEvent.VK_S) {
            this.camDown = false;
            // myGame.window.scroll.setScrollPosition(    myGame.window.scroll.getScrollPosition().x, myGame.window.scroll.getScrollPosition().y+16);
        }
        if (key == KeyEvent.VK_D) {
            this.camRight = false;
            //  myGame.window.scroll.setScrollPosition(    myGame.window.scroll.getScrollPosition().x+16, myGame.window.scroll.getScrollPosition().y);
        }
        if (key == KeyEvent.VK_A) {
            this.camLeft = false;
            //   myGame.window.scroll.setScrollPosition(    myGame.window.scroll.getScrollPosition().x-16, myGame.window.scroll.getScrollPosition().y);
        }

    }

    //does this every tick
    public void tick() {
        if (this.camUP) {
            myGame.window.scroll.setScrollPosition(myGame.window.scroll.getScrollPosition().x, myGame.window.scroll.getScrollPosition().y - 16);
        }
        if (this.camDown) {
            myGame.window.scroll.setScrollPosition(myGame.window.scroll.getScrollPosition().x, myGame.window.scroll.getScrollPosition().y + 16);
        }
        if (this.camRight) {
            myGame.window.scroll.setScrollPosition(myGame.window.scroll.getScrollPosition().x + 16, myGame.window.scroll.getScrollPosition().y);
        }
        if (this.camLeft) {
            myGame.window.scroll.setScrollPosition(myGame.window.scroll.getScrollPosition().x - 16, myGame.window.scroll.getScrollPosition().y);
        }

        //keeps track of top right corner
        if (this.camUP) {
            ty -= 16;
        }
        if (this.camDown) {
            ty += 16;
        }
        if (this.camRight) {
            tx += 16;
        }
        if (this.camLeft) {
            tx -= 16;
        }
        if (ty < 0) {
            ty = 0;
        }
        if (tx < 0) {
            tx = 0;
        }
        if (tx > Game.width - Game.myMap.camWidth * Tile.SIZE) {
            tx = Game.width - Game.myMap.camWidth * Tile.SIZE;
        }
        if (ty > Game.height - Game.myMap.camHeight * Tile.SIZE) {
            ty = Game.height - Game.myMap.camHeight * Tile.SIZE;
        }
    }

    /**
     * **************** MOUSE LISTENER ********************
     */
    public ArrayList<GameObject> selected = new ArrayList<>(); //arrayList of currently selected GameObjects. not currently implemented
    public GameObject selectedGO = null;                       //currently selected GameObject

    @Override
    public void mouseClicked(MouseEvent me) {
        ///if player presses then unpresses quickly   
        Game.downx = me.getX();      //remember the location of where we pressed down
        Game.downy = me.getY();
        if (me.getButton() == 1) {
            this.leftClick(me);   ///was a left click tap
        }
    }

    @Override
    public synchronized void mousePressed(MouseEvent me) {
        try {
            Game.downx = me.getX();      //remember the location of where we pressed down
            Game.downy = me.getY();
            int avgX = 0, avgY = 0, numUnits = selected.size();
            System.out.println(Game.isAdown + " " + selected.size() + " " + me.getButton());
            if (Game.isAdown && selected.size() > 0 && me.getButton() == 1) {                  //if attempting an a-move
                for (GameObject go : selected) {
                    if (go != null && go.id == ID.Unit) {
                        Unit u = (Unit) go;
                        if (!u.unitTypes.contains(Type.structure)) {          //do not include structures
                            System.out.println("test-KI");
                            avgY += u.y;
                            avgX += u.x;
                            u.issueAttackMove(me.getX(), me.getY());
                        } else {
                            numUnits--;
                        }
                    }
                }
                avgX /= numUnits;
                avgY /= numUnits;
                for (GameObject go : selected) {
                    if (go.id == ID.Unit) {
                        Unit u = (Unit) go;
                        u.issueAttackMove(me.getX() - (avgX - go.x), me.getY() - (avgY - go.y));           //attack move in formation
                    }
                }
            } else if (Game.lookingForTarget && selected.size() > 0 && me.getButton() == 1) {
                //if this is true, the player is trying to use the attack command
                for (GameObject go : selected) {
                    if (go != null && go.id == ID.Unit) {
                        Unit u = (Unit) go;
                        if (u.weapon != Weapon.none) {
                            try {
                                //attempt to target the unit we click on.
                                Unit newTarget = (Unit) Game.mainGrid.getObjectAt(me.getX(), me.getY());
                                u.issueAttackCommand(newTarget);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            Game.downx = me.getX();      //remember the location of where we pressed down
            Game.downy = me.getY();
            if (me.getButton() == 1) {
                Game.isMouseDown = true;       //lets us know that the left mouse button is held down
            }
            if (me.getButton() == 3) {
                this.issueOrder(me);  //was a right click press down
            }

            //now to reset the flags
            Game.isAdown = false;
            Game.lookingForTarget = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        try {
            if (me.getButton() == 1) {
                this.deselectAll();
                Game.isMouseDown = false;
            }
            if (Game.downx != me.getX() && Game.downy != me.getY()) {                                  ///if the mouse did not release where it whent down
                if (me.getButton() == 1) {
                    this.selectRange(Game.downx, me.getX(), Game.downy, me.getY());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Game.mouseLocX = me.getX();
        Game.mouseLocY = me.getY();
    }

    /**
     * every 10 ticks update the current mouse position
     *
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        if (mouseMovedVar > 10) {
            Game.mouseLocX = me.getX();
            Game.mouseLocY = me.getY();
            mouseMovedVar = 0;
        } else {
            mouseMovedVar++;
        }
    }
    int mouseMovedVar = 0;//this variable is used to keep track of number of ticks for the mouseMoved method

    /**
     * handles what happens when the click is a left click. deals with selection
     *
     * @param me the mouse event
     */
    public void leftClick(MouseEvent me) {
        GameObject clicked = Game.mainGrid.getObjectAt(me.getX(), me.getY());  //gets the object
        if (selected.size() > 0) {
            deselectAll();
        }
        selected.add(clicked);  //adds the clicked object to list of slected objects
        if (clicked != null) {
            this.setSelectionFlags();           //sets the boolean field on the objets to reflect that they are selected
        }
        System.out.println(selected);
    }

    /**
     * issues move command to selected units
     *
     * @param me
     */
    public void issueOrder(MouseEvent me) {
        int avgX = 0, avgY = 0, numUnits = 0;
        if (selected.size() > 0) {
            System.out.println(selected.get(0) + " is selected");
            for (GameObject o : selected) {
                if (o != null && o.team == myGame.localUser.team) {         //issue a move command if the selected thing is not null and is on our team
                    if (selected.size() == 1 || Game.isCtrlDown) {
                        o.setDestinaion(me.getX(), me.getY());    //if only one or the user is pressing ctrl, tell them all to go to the same dest
                        if (o.id == ID.Unit) {
                            Unit u = (Unit) o;
                            if (u.name == "Field Truck") {
                                FieldTruck ft = (FieldTruck) u;
                                ft.queued = false;          //if the thing is a field truck, cancel its existing queued build
                            }
                        }
                    }
                    Unit u = (Unit) o;
                    u.isAmoving = false;
                }
            }
        }

        //for multiple units
        if (selected.size() > 1 && !Game.isCtrlDown) {
            for (GameObject go : selected) {
                if (go.id == ID.Unit) {
                    numUnits++;
                }
                Unit u = (Unit) go;
                if (!u.unitTypes.contains(Type.structure)) {  //exclude structures
                    avgX += go.x;
                    avgY += go.y;
                } else {
                    numUnits--;     //if one is a struture, remove it from unit count
                }
            }
            if (numUnits == 0) {
                return;
            }
            avgX /= numUnits;
            avgY /= numUnits;
            for (GameObject go : selected) {
                if (go.id == ID.Unit) {
                    Unit u = (Unit) go;
                    u.isAmoving = false;
                    if (u.unitTypes.contains(Type.structure)) {
                        continue;  //dont give it an order if its a structure
                    }
                    go.setDestinaion(Main.clamp(me.getX() - (avgX - go.x), Game.width, 0), Main.clamp(me.getY() - (avgY - go.y), Game.height, 0)); //make sure to clamp it to the game bounds
                }
            }
        }
    }

    /**
     * sets all currently selected units to deselected
     */
    private void deselectAll() {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i) != null) {
                selected.get(i).isSelected = false;
            }
        }
        selected.clear();
    }

    /**
     * sets the boolean field on the units if they are selected
     */
    private void setSelectionFlags() {
        for (GameObject o : selected) {
            if (o != null) {
                o.isSelected = true;
            }
        }

    }

    /**
     * selects all units in a given range
     *
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     */
    private void selectRange(int startX, int endX, int startY, int endY) {
        boolean invertX = false;    //if start is past the end
        boolean invertY = false;    //if start is past the end
        ArrayList<Node> selectedPoints = new ArrayList<>();             ///all points selected
        GameObject temp;                            //holder

        if (startX > endX) {
            invertX = true;
        }
        if (startY > endY) {
            invertY = true;
        }

        if (!invertX && !invertY) {
            selectedPoints = Game.mainGrid.getRange(startX, endX, endY, startY); //y looks inverted but thats just cuz the code is backwards in the get range method
        }
        if (invertX && !invertY) {
            selectedPoints = Game.mainGrid.getRange(endX, startX, endY, startY);
        }
        if (!invertX && invertY) {
            selectedPoints = Game.mainGrid.getRange(startX, endX, startY, endY);
        }
        if (invertX && invertY) {
            selectedPoints = Game.mainGrid.getRange(endX, startX, startY, endY);
        }

        for (Node n : selectedPoints) {
            temp = n.getOccupant(null);
            if (temp != null && temp.team == myGame.localUser.team) {       //only select if not null and on our team
                if (selected.contains(temp)) {
                    //the object is already selected, so we skip it
                } else {
                    selected.add(temp);
                }
            }
        }
        this.setSelectionFlags();
    }

    public void selectUnit(Unit u) {
        if (!selected.contains(u)) {
            selected.add(u);
            this.setSelectionFlags();
        }
    }
}
