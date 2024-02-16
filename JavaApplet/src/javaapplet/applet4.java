package javaapplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class applet4 extends Applet {
    
    Image img;
    
    public void init() {
        img = getImage(getDocumentBase(), "isaac.gif");
    }
    
    public void paint(Graphics g){
        g.drawImage(img, 0, 0, this);  
    }
}
    