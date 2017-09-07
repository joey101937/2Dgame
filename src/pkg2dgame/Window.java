
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import Terrain.Tile;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Joseph
 */
public class Window extends Canvas{
    private static final long serialVersionUID = -240840600533728354L; //idk what this does but i saw it in a video 
 //   public static  JScrollPane sp = new JScrollPane();
    public static JPanel panel = new JPanel();
    public static ScrollPane scroll;
    public JFrame frame;
    public static UI ui;
    
    
    
    public Window(int width, int height, String title, Game game){
   frame = new JFrame(title);
   // sp.setBackground(Color.yellow);
   // sp.setBounds(50, 50, 200, 200);
   // sp.setBackground(Color.yellow);
   // sp.setViewport(null);
    //sp.setViewportView(game);
   //panel.add(sp);
   
      // sp.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
  //  sp.setViewportView(game);
    //sp.validate();
  //  sp.repaint();

    //sp.setVisible(true);
    //sp.setBounds(0, 0, 900, 700);
    
    //sp.setSize(width, height);
   this.ui = new UI(game);
 
    scroll = new ScrollPane();
    scroll.add(game);   
    Dimension d = Main.takeWindowSize();
    Main.display("Chosen dimension: " + d.width + "x"+ d.height);
    game.setBounds(0, 0, width, height);
        frame.add(ui);
      frame.add(scroll);
                
    frame.setPreferredSize(new Dimension(Game.myMap.camWidth*Tile.SIZE , Game.myMap.camHeight*Tile.SIZE));           ///ideal dimension
    frame.setMaximumSize(new Dimension(1920 ,1080));             ///max size
    frame.setMinimumSize(new Dimension(d.width, d.height));             ///min size
    scroll.setWheelScrollingEnabled(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           ///exits on close
    frame.setResizable(true);                                      ///disallows resizing
  //  frame.setLocation(null);                                       //default location is center of screen
 //   frame.add(game);                                                //adds an instance of our game
 

    scroll.setSize(new Dimension(width,height));
    ui.setLocation(0,100);
    ui.setSize(new Dimension(220,700));
    frame.setVisible(true);                                 //lets it be visible
    game.start();
    
    
    }
/**
 * made for use with gui
 * @param width width fo the game world
 * @param height height of the game world
 * @param title title of the window
 * @param game the game
 * @param windowWidth width of the window 
 * @param windowHeight height of the window
 */
    public Window(int width, int height, String title, Game game, int windowWidth, int windowHeight) {
        frame = new JFrame(title);
        this.ui = new UI(game);
        scroll = new ScrollPane();
        scroll.add(game);
        Dimension d = new Dimension(windowWidth,windowHeight);
        game.setBounds(0, 0, width, height);
        frame.add(ui);
        frame.add(scroll);
        frame.setPreferredSize(new Dimension(Game.myMap.camWidth * Tile.SIZE, Game.myMap.camHeight * Tile.SIZE));           ///ideal dimension
        frame.setMaximumSize(new Dimension(1920, 1080));             ///max size
        frame.setMinimumSize(new Dimension(d.width, d.height));             ///min size
        scroll.setWheelScrollingEnabled(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           ///exits on close
        frame.setResizable(true);                                      ///disallows resizing
        scroll.setSize(new Dimension(width, height));
        ui.setLocation(0, 100);
        ui.setSize(new Dimension(220, 700));
        frame.setVisible(true);                                 //lets it be visible
        game.start();
    }
    

    
    
    
    
}
