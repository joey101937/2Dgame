/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import GameObjects.ID;
import Pathfinding.*;
import Terrain.*;
import Units.*;
import Units.GameUnits.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import openingGUI.AISettingsGUI;

/**
 * main core of the game. contains core loop
 *
 * @author Joseph
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -8374032800533728354L;

///fields
    BufferedImage backgroundBI = null;
    public static int width = 1500;  //1500
    public static int height = 1050;    ///sets aspect ratio and demensions
    public static Grid mainGrid = null;
    private Thread thread = null;
    private boolean running = false;
    public static Handler handler;
    public static KeyInput ki = new KeyInput(handler);
    public MouseListener ml = (ki);
    public static rtsHUD mainHUD;
    public static boolean isMouseDown = false;      //weather or not the left mouse button is being held down
    public static int downx, downy;                 //location of the last time the left mouse button was presse
    public static int mouseLocX, mouseLocY;         //current location of mouse
    public MouseMotionListener mml = ki;
    public static Window window = null;
    public static User localUser = new User(Team.team1, false); //local user is the person playing the game
    public static User enemyAI = new User(Team.team2, true);    //default opponent
    public static User hostileAI = new User(Team.hostile, true); //hostile to all sides
    public static User NeutralAI = new User (Team.neutral, false); // nobody's enemy
    public static boolean isCtrlDown; //is the player pressing ctrl
    public static boolean isAdown;    ///is the player pressing the A key
    public static Map myMap = null;
    public static ArrayList<User> users = new ArrayList<>();
    private static int TPS = 0;         //number of ticks per second may be redundant with FPS meter
    public static int Camx, camy;        //location of upperleft corner of camera
    public static boolean lookingForTarget = false; //weather or not we are looking for a unit to use hte attack command on
    
    /**
     * used standalone
     * @param mapSeed which seed to load
     */
    public Game(int mapSeed) {
        this.instantiateMap(mapSeed);
        ki.setGame(this);
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(ml);
        this.addMouseMotionListener(mml);
        window = new Window(width, height, "Joey's RTS", this);
        mainHUD = new rtsHUD(0, 0, ID.UI);
        handler.addObject(mainHUD);
        this.instatiateUsers();

        this.setIgnoreRepaint(true);
        this.window.frame.setIgnoreRepaint(true);

        this.setStartingUnits(mapSeed);
        AISettingsGUI asg = new AISettingsGUI();
        System.out.println("Game Setup done");
    }
    
    /**
     * to be used with the gui
     * @param level which level to load
     * @param width width of the window
     * @param height height of the window
     */
    public Game(int level, int windowWidth, int windowHeight){
        int mapseed = level; //pass a level and we conver that to the corresponding mapseed
        this.instantiateMap(mapseed);
        Game.myMap.camHeight = windowHeight / Tile.SIZE;
        Game.myMap.camWidth = windowWidth / Tile.SIZE;
        ki.setGame(this);
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(ml);
        this.addMouseMotionListener(mml);
        window = new Window(width, height, "Joey's RTS", this, windowWidth, windowHeight); //using the second constructor which takes windown dimensions explicitly
        mainHUD = new rtsHUD(0, 0, ID.UI);
        handler.addObject(mainHUD);
        this.instatiateUsers();
        this.setIgnoreRepaint(true);
        this.window.frame.setIgnoreRepaint(true);
        this.setStartingUnits(mapseed);
        System.out.println("Game Setup done");
    }
    

    //starts the main game
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    ///stops the main game
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //core tick, tells all game Objects to tick
    private void tick() {
        TPS++;
        handler.tick();
        if (mainHUD != null) {
            mainHUD.tick();
        }
        this.ki.tick();
        this.window.ui.tick();
        for(User u : users){
        u.tick();
        }
    }

    //core render method, tells all game Objects to render
    private void render() {

        Game.Camx = Map.camX * Tile.SIZE;
        Game.camy = Map.camY * Tile.SIZE;

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) { ///run once at the start
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g = (Graphics2D) g;
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, Game.width, Game.height);  //green area outside playable field

        //this.renderBackGround(g);
        if (myMap != null) {
            myMap.render(g);       //used to be renderCamera
        }
        handler.render(g);

        g.dispose();

        bs.show();
    }

    //Core game loop 
    @Override
    public void run() {
        this.requestFocus(); ///automatically selects window so you dont have to click on it
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                this.render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                System.out.println("TPS: " + TPS);
                TPS = 0;
                frames = 0;
                ///this triggers once a second
                System.out.println("(" + KeyInput.tx + "," + KeyInput.ty + ")");

            }
        }
        stop();
    }

    public void renderBackGround(Graphics g) {
        ImageIcon backgroundIcon = new ImageIcon(Main.getDir() + Main.assets + "BGSpecialty.png");
        g.drawImage(this.createSprite(backgroundIcon), 0, 0, null);

    }

    public BufferedImage createSprite(ImageIcon i) {
        Icon icon = i; ////the pic that i want as the bg
        BufferedImage bi = new BufferedImage(
                clamp(icon.getIconWidth(), width, width),
                clamp(icon.getIconHeight(), height, height),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
// paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bi;
    }

    public int clamp(int input, int max, int min) {
        if (input > max) {
            return input = max;
        }
        if (input < min) {
            return input = min;
        }
        return input;
    }

    public MouseMotionListener getMML() {
        return this.mml;
    }

    private void setStartingUnits(int seed) {
        switch (seed) {
            case 1:
                //case 1 is blank opens canvas
                FieldTruck ft = new FieldTruck(500,500,1);
                handler.addObject(ft);
                ft.BuildAt(500, 300, 1);
                return;
            case 2:
                handler.addObject(new BasicFactory(400,800,-1));
                handler.addObject(new BasicFactory(2600,800,2));
                return;
            case 4:
                handler.addObject(new BasicTank(900, 100, 2));
                handler.addObject(new BasicTank(1000, 100, 2));
                handler.addObject(new BasicTank(1100, 100, 2));
                handler.addObject(new BasicTank(1200, 100, 2));
                handler.addObject((new BasicTank(400, 100, 2)));
                handler.addObject(new BasicBoat(700, 300, 1));
                handler.addObject(new BasicTank(400, 800, 1));
                handler.addObject(new BasicFactory(600, 800, 1));
                handler.addObject(new FieldTruck(600, 600, 1));
                this.addUnit(new Helicopter(900, 900, 1));
                this.addUnit(new Helicopter(1000, 200, 2));
                return;
            case 5:
                //FieldTruck truck = new FieldTruck(1100,300,1);
                BasicFactory bf = new BasicFactory(500, 300, 1);
                handler.addObject(new FieldTruck(500,700,1));
                handler.addObject(bf);
                
                handler.addObject(new FieldTruck(1200,900,2));
                 handler.addObject(new BasicFactory(1350,900,2));
                //  handler.addObject(new BasicTank(1500,900,2));
               // handler.addObject(truck);
                return;
            case 6:
                BasicFactory bf1 = new BasicFactory(350,700,1);
                FieldTruck ft1 = new FieldTruck (550,700,1);
                handler.addObject(bf1);
                handler.addObject(ft1);
                BasicFactory bf2 = new BasicFactory(2000, 500, 2);
                FieldTruck ft2 = new FieldTruck(1900,500,2);
                handler.addObject(bf2);
                handler.addObject(ft2);
            default:
                break;
        }

    }

    private void instantiateMap(int mapNum) {
        myMap = new Map(mapNum);
        width = myMap.tileSet.length * Tile.SIZE;
        height = myMap.tileSet[0].length * Tile.SIZE;
        Game.mainGrid = new Grid(Game.width, Game.height);
    }

    private void instatiateUsers() {
        localUser.ui = this.window.ui;  //sets the player ui
        localUser.ui.owner = localUser;
        Game.users.add(localUser);
        Game.users.add(enemyAI);
        Game.users.add(hostileAI);
        localUser.isAI =false;
    }

    public void addUnit(Unit u) {
        this.handler.addObject(u);
    }

    public void removeUnit(Unit u) {
        while (Game.handler.getObjects().contains(u)) {
            try {
                Game.handler.getObjects().remove(u);
            } catch (Exception e) {
            }
        }
    }
}
