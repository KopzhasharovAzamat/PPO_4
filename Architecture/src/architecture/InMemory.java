/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package architecture;
import java.awt.*;
import java.awt.image.*;
import static javax.swing.JFrame.DISPOSE_ON_CLOSE;
/**
 *
 * @author Adrian
 */

public class InMemory extends javax.swing.JFrame {
    private int w = 300, h = 300;
    private int [] pix = new int[h*w];
    private Image img;
    InMemory(String s){
        super(s);
        int i = 0;
        for(int y = 0; y < h; y++){
            int red = 255*y/(h-1);
            for(int x = 0; x < w; x++){
                int green = 255*x/(w-1);
                pix[i++] = (255<<24)|(red<<24)|(green<<8)|128;
            }
        }
        setSize(400, 400);
        setVisible(true);
    }
    
    public void paint(Graphics gr){
        if(img == null)
            img = createImage(new MemoryImageSource(w, h, pix, 0, w));
        gr.drawImage(img, 50, 50, this);
    }
    
    public static void main(String[] args){
        new InMemory("Image");
    }
}
