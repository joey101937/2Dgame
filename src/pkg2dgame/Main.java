/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import Terrain.Tile;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import openingGUI.MainGUI;


/**
 *
 * @author Joseph
 */
public abstract class Main {

    
    public static File stopCommandSprite = new File(Main.getDir() + "Assets/StopCommand.png");
    public static String stopCommandSpriteFileName = (Main.getDir() + "Assets/StopCommand.png");
    public static String assets = "Assets"+File.separator;
    public static boolean linux = false;
    
   public static void main(String[] args) {
       //display("Launching Game...");
       //checkIfLinux();
       System.out.println("Looking in " + Main.getDir() + Main.assets);
       //Game game = new Game(6);
       MainGUI mg = new MainGUI();
    }

   
   
    
    
        /**
         * displays a dialog box give the user a message, String s.
         * pauses the thread until the user hits OK
         * @param s 
         */
        public static void display(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    
    
        /**
         * returns the directory of the Jar File with a / on the end
         * @return 
         */
        
        public static String getDir(){
            String output = System.getProperty("user.dir")+File.separator;
            return output;
    }
    
        /**
         * fixes // and / for file names
         * @param input
         * @return 
         */
    private static String fixSlash(String input){
        ArrayList<Character> output = new ArrayList<>();
        for(int i = 0;i < input.length();i++){
            output.add(input.charAt(i));
            if (input.charAt(i)=='\\'){output.add(input.charAt(i));}
        }
       String o = "";
        for (int x = 0; x< output.size();x++){
            o = o.concat(output.get(x).toString());
        }
          o =  o.replace('\\', File.separatorChar); //changes slashes to be right thing
        return o;
    }
    
    /**
     * displays a dialogue box with a text input field. And a question for the user.
     * returns the text area's contents.
     * @param question
     * @return 
     */
    public static String prompt(String question){
        return JOptionPane.showInputDialog(question);
    }
    
    /**
     * asks the user what size window they want
     * @return the dimension they want
     */
    public static Dimension takeWindowSize() {
        Integer input = null;
        try {
            input = new Integer(prompt("Desired Window size in pixels (700-1900)"));
        } catch (Exception e) {
            display("Must enter Number 700-1900");
            return takeWindowSize();
        }
        if (input <= 700) {
            Game.myMap.camHeight = 700 / Tile.SIZE;
            Game.myMap.camWidth = 700 / Tile.SIZE;
            return new Dimension(700,700);
        }
        if (input >= 1900) {
            Game.myMap.camHeight = 1050 / Tile.SIZE;
            Game.myMap.camWidth = 1900 / Tile.SIZE;
            return new Dimension(1900,1050);
        }
        while (input % Tile.SIZE != 0) {
            input--;
        }
        int h = (input*2)/3;
        while(h % Tile.SIZE != 0){
            h--;
        }
        if(h<700) h =700;   //minimum height is 700
        Game.myMap.camHeight = h / Tile.SIZE;
        Game.myMap.camWidth = input / Tile.SIZE;
        
        return new Dimension(input,h);
    }
    /**
     * asks the user if they are running linux then changes assets accordingly 
     * OBSOLETE
     */
        public static void askIfLinux() {
        String input;
        input = new String(prompt("Are you on Linux? (Y/N)"));
        if(input.charAt(0)=='y' || input.charAt(0) == 'Y'){
            //answered yes
            Main.assets = "Assets/";
            return;
        }
        if(input.charAt(0)=='n' || input.charAt(0) == 'N'){
            //ansered no
            return;
        }
        else{
            //didnt answer
            Main.display("Please Answer yes or no");
            askIfLinux();
        }
    }
        
     /**
     * checks if the system is using forward slashes or back slashes then changes assets accordingly
     * adds a backslash or forward slash as necessary to end of assets directory
     */
        private static void checkIfLinux(){
            Main.assets += File.separator;
            
            if(Main.getDir().charAt(0) == '\\' || Main.getDir().charAt(0) == '/'){
                Main.display("Linux detected");
                linux = true;
            }else{
                System.out.println(Main.getDir().charAt(0));
                System.out.println("Not Using Linux");
            }
                    
        }

        
        /**
         * returns a random integer between the given parameters
         * @return the number
         */
        public static int generateRandom(int min, int max){
            if(min == max) return min; //if they are the same return that number
            if(max < min){
                //if the numbers are entered backwards, rerun the method with the correct order
                return generateRandom(max, min);
            }else{
                //here is the body of our method
                int diff = max - min;
                int output = (int)(Math.random()*diff); //generates a random number between 0 and the difference between the numbers
                return (min + output);                //returns that random number plus the min
            }
        }
        /**
         * clamps the input to a given set of constraints and returns the max or min rather than the inputed value 
         * if the input is outside the given range
         * @param input the number to test
         * @param max maximum value
         * @param min minimum value
         * @return if the number is between max and min then return input, else return the min or max respectively
         */
    public static int clamp(int input, int max, int min) {
        if(max < min){
            int temp = min;
            min = max;
            max = temp;
        }
        if (input > max) {
            return input = max;
        }
        if (input < min) {
            return input = min;
        }
        return input;
    }
    
    public static int promptForNumber(String question){
        int output = 0;
        String input = Main.prompt(question);
        try{
            output = Integer.parseInt(input);
        }catch(Exception e){
            Main.display("Must enter integer.");
            return Main.promptForNumber(question);
        }
        return output;
    }
        
}
