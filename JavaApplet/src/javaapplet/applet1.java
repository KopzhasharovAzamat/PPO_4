package javaapplet;

import java.applet.Applet;
import java.awt.Graphics;

public class applet1 extends Applet {

    public void init() {
        resize(150,150);
    }
    public void paint(Graphics g)  {
        g.drawString("Hello, WWW", 10, 20);
    }


}
