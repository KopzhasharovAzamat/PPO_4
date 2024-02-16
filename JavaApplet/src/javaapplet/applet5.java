package javaapplet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class applet5 extends Applet {
    Image pic; 		
    boolean picLoaded = false;
    private String m_FileName = "v1.png";
    private final String PARAM_String_1 = "v1.png";
    int x = 0, y = 0;
    Image offScreenImage;
    public void init(){
        String param;
        param = getParameter(PARAM_String_1);
        if (param != null) m_FileName = param;
        pic = getImage(getDocumentBase(), m_FileName);
        resize(320, 240);
    }
    public void paint (Graphics g){
        // создание виртуального экрана
        int width = getSize().width;
        int height = getSize().height;
        offScreenImage=createImage(width, height);
        // получение его контекста
        Graphics offScreenGraphics= offScreenImage.getGraphics();
        // вывод изображения на виртуальный экран
        offScreenGraphics.drawImage(pic, x, y, this);
        if(picLoaded){
            g.drawImage(offScreenImage, 0, 0, null);
            showStatus("Done");
        }
        else showStatus("Loading image");
    }
    public boolean imageUpdate(Image img, int infoflags,int x, int y,int w, int h) { 
        if(infoflags == ALLBITS)    { 
            picLoaded = true;   // изображение загружено полностью
            repaint();	     	// перерисовать окно апплета
            return false; 	 // больше метод imageUpdate не вызывать
        }
    return true;     	 // изображение загружено не полностью
    }
}
