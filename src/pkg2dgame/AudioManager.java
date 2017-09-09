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
public class AudioManager implements Runnable{
    /* FIELDS */
    private String fileToPlay = "";
    
    
    /**
     * plays the effect corresponding to the given name.
     * should be name.extension, ie: "mySound.mp3"
     * @param Name filename
     */
    public static void Play(String name) {
        Thread t = new Thread(new AudioManager(name));
        t.start();
    }
    
    
    
    
    /**
     * Creates an instance of audiomanager for running a specific sound.
     * @param filename name of the file to play, including extension
     */
    public AudioManager(String filename){
        fileToPlay = filename;
    }


    /**
     * actually plays the sound
     */
    @Override
    public void run() {
        System.out.println("playing: " + fileToPlay);
        try {
            FileInputStream ios = new FileInputStream("Assets/Sounds/" + fileToPlay);
            Player player = new Player(ios);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
