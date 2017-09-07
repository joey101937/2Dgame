/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BromTrainingGrounds;
import Units.GameUnits.BasicTank;
import Units.GameUnits.Helicopter;
import pkg2dgame.Game;
/*
PART 1
GUESS WHAT IS GOING TO HAPPEN. YOU CAN MANIPULATE THIS IF YOU WANT TO TEST. 
THINK CAREFULLY BECUASE AFTER YOU RUN IT YOULL KNOW FOR SURE AND IT WONT BE HARD

NOW AFTER YOU HAVE DONE THAT:
Change this main method to create a blank game like the previous lesson.
then make a method that takes one parameter: an int. the method should spawn that many tanks in a line that is 1000 pixels long.
the tanks should be evenly spaced, reguardless of how many there are. they should also spawn on a 1 second delay so you can see each one spawning in.
look at my example where i made them spawn in a line 100 pixels apart in the previous lesson.
*/
public class AfterLessonChallenge {
    private static int myInt;
    
    public static void main(String[] args) {
     int myInt = 12;
     
     for(myInt = 0; myInt < 10; myInt++){
         System.out.println("outer loop: " + myInt);
         for(int i = 0; i < 2; i++){
             System.out.println("inner loop: " + myInt);
         }             
     }
        /*
     a point about the above ^
     thats called a nested for-loop. basically when a for loop is inside of another for-loop.
     each time the outer loop runs, the entire inner loop will run first. this means to find the number of times the inner loop will
     run, you must multiply the number of times it would usually run (2 in this case) by the number of times the outer loop runs.1 
     */
        Game game = new Game(1);

        addTanks(500,300,1000,12,game,1);
        addTanks(500,700,1000,12,game,-1);
        
        
    }
    
    //ive given you the wait method to use again
    private static void wait(int duration){
        try{
            Thread.sleep(duration);
        }catch(Exception e){}
    }
    /**
     * creates a number of tanks in a line of given length at the given coordinates for the given team,in the given game
     * @param Xstart    beginning X coordinate
     * @param Ystart    beginning Y coordinate
     * @param length    how long the line will be
     * @param num       number of tanks in the line
     * @param game      what game they go in
     * @param team      what team the tanks are on
     */
    private static void addTanks(int Xstart, int Ystart,int length, int num, Game game, int team){
        int separator = length/num;
        for(int i = 0; i < num; i++){
            game.addUnit(new BasicTank(Xstart+(separator*i),Ystart,team));
        }
    }
}
