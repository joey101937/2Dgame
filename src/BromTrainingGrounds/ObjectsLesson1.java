/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BromTrainingGrounds;

import pkg2dgame.Game;
/**
 *LESSON: FOR-LOOPS and Local Variables
 * this is the lesson about the basic for-loop
 * 
 * CONCEPTS
 * -looping with a condition
 * -local variables
 * -fields or global variables 
 */
public class ObjectsLesson1 {
   
    
//here is our main method that runs unpon launch
    public static void main(String[] args) {
        
        int[] myArray = {1, 5, 10, 9};
       
        for(int x : myArray){
            System.out.println(x);
        }
        
        
    }
}
