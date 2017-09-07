/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dgame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Joseph
 */
public class Sprite {
    //Fields
    public URL url;
    public BufferedImage image;
    
    //Constructor
    public Sprite(URL u){
        this.url = u;
        try{
            image = ImageIO.read(u);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
