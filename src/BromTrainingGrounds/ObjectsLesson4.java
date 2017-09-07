
package BromTrainingGrounds;

import Units.GameUnits.BasicTank;
import pkg2dgame.Game;

/**
 *  This time we are going to review static  vs  nonstatic variable posession one more time
 * 
 * lets say i have the following setup:
 * 
 * public Class testClass {
 *  public int myInt = 0;
 * public static void main(String[] args){
 * myInt = 10;
 *    }
 * }
 * 
 * what do you think would happen?
 * the answer is i would get an error. this is becuase i cannot referance a nonstatic variable from a static context.
 * 
 * this is because the main method is static, it belongs to the class itself while the int variable is not static. that means it is not owned by
 * the class but rather every instanciation of that class.
 * 
 * i would have to first create an instance of that class then use that to get access to myInt.
 * 
 * TestClass example = new TestClass();
 * example.myInt = 10;      
 * 
 * this is how i would have to do it ^
 * 
 *  if i want to get access to a STATIC variable i would use className.variable
 * TestClass.myIntStatic = 10.
 * 
 * NOTE static methods are owned and executed by the class file itself and therefore does not need an object to run it,
 * HOWEVER a static method CAN be run through an created object if you want.
 */
public class ObjectsLesson4 {
    private int myInt = 0;
    private static int myIntStatic = 0;

    public static void main(String[] args) {
        /*
        here i instanciate an object of this class then use it to access myInt. myInt DOES NOT EXIST BEFORE THAT INSTANCE IS CREATED.
        This is becuase that variable is part of each instance and if there is no instance to hold it then it does not exist.
        myIntStatic, however is differnet. if it is static that means there is only one in existance and can never be more. 
        this is because there is only one classfile and that variable belongs to that one classfile. every time you access a static variabe
        you are accessing the same one each time. this is differnt from nonstatic becuase which variable you are manipulating changes depending on which instance
        you are using. tank1.health and tank2.health are two different variables. 
        */
        
    ObjectsLesson4 instance = new ObjectsLesson4(); 
    instance.myInt = 10;  
    ObjectsLesson4.myIntStatic = 20;
    }
    
    /** 
    this is the method i have given you. it pauses the code for the duration
    make sure you wait till after hte tanks spawn in before using this method or it will delay their spawns as well
    although you may want that if you want the enemies to trickle in over time
         */
    private static void wait(int duration){
        try{
            Thread.sleep(duration);
        }catch(Exception e){}
    }
}
