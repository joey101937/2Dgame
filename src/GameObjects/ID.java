/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.awt.Color;
import java.awt.color.*;
/**
 *  basic type value for the game objects. each has a color.
 * @author Joseph
 */
public enum ID {
    Player() {

        @Override
        public Color getColor() {
            return Color.WHITE;
        }
    },
    Player2() {

        @Override
        public Color getColor() {
            return Color.BLUE;
        }
    },
    Enemy() {

        @Override
        public Color getColor() {
           return Color.RED;
        }
        
    },
    Wall(){
        @Override
        public Color getColor(){
                    return Color.GRAY;
                }
            },
       Pit(){
        @Override
        public Color getColor(){
                    return Color.GRAY;
                }
            },
    Projectile(){
        @Override
        public Color getColor(){
                    return Color.GRAY;
                }
            },
    Unit() {
                @Override
                public Color getColor() {
                    return Color.ORANGE;
                }
            },
    UI() {
                @Override
        public Color getColor(){
            return Color.BLACK;
        }
    }
    ;
    
    /**
     *this method get the color assigned to each type
     * @return the color
     */
    public abstract java.awt.Color getColor();
}
