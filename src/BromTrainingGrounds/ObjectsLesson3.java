/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BromTrainingGrounds;

import Units.GameUnits.BasicTank;
import pkg2dgame.Game;
import pkg2dgame.Main;

 /*    
to get to a field (global variable for objects) i must go through that class to get it. aka class.field rather than just field
tank.health rather than just health

if it is a non-static field that we are trying to get to, we need to do objectName.field and if it is a static field, we do className.field
OBJECT: tank1.health;       <- health is a non-static field and therefor each tank has its own health. we refer to tank1's health specifically
CLASS:  Main.assets;        <- the assets field in Main is static and therefor we call the name of the class itself not a specific instantiation of it.

usually in object oriented programming we access fields within a class with the same name as a local variable using the keyword "this".

for example, lets say i have a player class whos constructor takes one int parameter called health and that is used to set his starting health value.
it would look like this:

Public Player(int health){
this.health = health;
}

notice we use two variables even though we only use one variable name. that is because the "this.health" refers to the player object's health value while
the "health" refers to the local variable that we gave it in the parameter.
*/
public class ObjectsLesson3 {
   private static  Game game = new Game(1);
   private static BasicTank tank1 = new BasicTank(500,500,1);
   
   
    public static void main(String[] args){
        game.addUnit(tank1);
        wait(1000);
        int health = 0;
        tank1.health = health;      //sets the tanks health to 0  after a second. this will kill the tank
        
        
        Main.display("See that the tanks health was set to 0? \n Now lets continue with the lesson!");
        
        /*
        now lets do some for-loop practice
        variables declared in the for-loop only exist in the for-loop however we can use them there to provide utility.
        we can use that local variable that we create as a utility and also keep track of things. for example we can do somthing different for each loop
        depending on how many times we have looped.  in the following example im going to make some tanks spawn in a line on an interval
        
        
        first line tells us we are going to loop 6 times
        second line sets the tank's spawn X coordinate. we are going to start at 500 then increase by 100 each loop so the tanks spawn in a line
        then we wait so we can spawn them at an interval rather than them all just appearing        
        */
        
        
        
        for(int i = 0; i < 6; i++){ 
           int x = 500 + (i * 100);   
           game.addUnit(new BasicTank(x,500,1));
            wait(1000);
        }
        
    }
    
    
    
    
    
        private static void wait(int duration){
        try{
            Thread.sleep(duration);
        }catch(Exception e){}
    }
}

