
package javaapplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class applet2 extends Applet {

    int curX=50, curY=50;
     MouseInputAdapter p;
     public applet2(){
          // обработчик события
         p = new MouseInputAdapter(){
         public void mousePressed(MouseEvent e) {	               
               curX=e.getX(); curY=e.getY();
               repaint();
         }};
       this.addMouseListener(p);
    }
    public void init()  {
      resize(640,480);
    }
     public void paint(Graphics g)  {
       g.drawString("Hello, WWW",curX,curY);
     }


}
