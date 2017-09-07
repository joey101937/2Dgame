/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Units;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Joseph
 */
public enum Team {team1() {

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public ArrayList<Integer> getAllies() {
        ArrayList<Integer> output = new ArrayList<Integer>();
        output.add(0);      //allied with neutral
        output.add(1);      ///allied with itself
        return output;
    }


},team2() {

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
            public int getNumber() {
                return 2;
            }

            public ArrayList<Integer> getAllies() {
                ArrayList<Integer> output = new ArrayList<Integer>();
                output.add(0);
                output.add(2);
                return output;
            }


},neutral() {

    @Override
    public Color getColor() {
        return Color.lightGray;
    }

    @Override
    public int getNumber() {
        return 0;
    }
        public ArrayList<Integer> getAllies() {
        ArrayList<Integer> output = new ArrayList<Integer>();
        output.add(0);
        output.add(1);
        output.add(2);
        return output;
    }

},hostile() {

    @Override
    public Color getColor() {
        return Color.darkGray;
    }

    @Override
    public int getNumber() {
        return -1;
    }

        public ArrayList<Integer> getAllies() {
        ArrayList<Integer> output = new ArrayList<Integer>();
        output.add(-1);
        return output;
    }

};

public abstract ArrayList<Integer> getAllies();
public abstract java.awt.Color getColor();
public abstract int getNumber();
public static Team getTeam (int i){
    switch (i){
        case -1:
            return Team.hostile;
        case 0: 
            return Team.neutral;
        case 1:
            return Team.team1;
        case 2: 
            return Team.team2;
        default:
            System.out.println("Unknown team: "+i);
            return null;
    }
}


    
}
