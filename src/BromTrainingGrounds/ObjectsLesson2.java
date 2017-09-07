/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BromTrainingGrounds;

import pkg2dgame.Game;
import Units.GameUnits.*;

/**
 *
 * @author Joseph
 */
public class ObjectsLesson2 {
    /*
    Now that we have done for-loops we need to cover somthing important that is often overlooped. variables have scope.
    scope is weather or not somthing can be seen in a certain part of a a program.
    global variables or fields are delcared at the class level. aka outside of a method.
    local variables are declared at the method-level or in a loop.
    for example, when we make for loops like this:
    
    for(int i = 0; i < 3; i++){
    System.out.println("test");
    }
    
    i is global variables can have overlapping scopes. aka if you have a local variable named myVar in a method, you can also
    have a globalVariable in that class called myVar without getting an error. however, if you do this, the local variable will take priority and block the 
    visibility of the global variable. a local variable and cannot be seen outside of that for-loop or method in which it is created. if you try to refer to it you 
    will get an error. local variables and global variables can have overlapping scopes. aka if you have a local variable named myVar in a method, you can also
    have a globalVariable in that class called myVar without getting an error. however, if you do this, the local variable will take priority and block the 
    visibility of the global variable. 
    
    for example, i have a class with an int variable called health. also in that class, i have a method in which i create a new int variable also called health. 
    inside that method, writing health will ALWAYS refer to the local variable version of health. aka the one i declared in that method. in order to access the global
    varaible i must referance it through the class. aka player.health will get the global variable health of the player class rather than the local variable health
    created in the method
    
    note that you can NOT have two global variables or two local variables with the same name, so
    
    public void test(){
    int myInt = 10;
    int myInt = 11;
    }
    
    will throw an error
    */

   static int myVar = 10;
   static int i = 900;
    
    public static void main(String[] args) {
      int myVar = 20;
        
      
      //what do you think it will print?
        System.out.println(myVar);
        
        for(int i = 0; i < 5; i++){ //this i can only be seen inside its for-loop
            System.out.println(i);  //this prints the for-loop version of i becuase the local variable i declared in the for loop takes priority over global one
        }
        
        // this prints the class variable 900 because it cannot see the local variable as it is in the for-loop
        System.out.println(i);
        
    }

}
