/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import java.io.FileInputStream;
import javazoom.jl.player.Player;

/**
 *
 * @author Joseph
 */
public class audioTest {

    public static void main(String[] args) {
        try {
            FileInputStream ios = new FileInputStream("Assets/testAMV.mp3");
            Player player = new Player(ios);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
