/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Applet.java to edit this template
 */
package javaapplet;

import java.applet.Applet;
import java.awt.Graphics;

/**
 *
 * @author Adrian
 */
public class applet3 extends Applet {

    private String m_String_1 = "DALSNDSJDNALSDNA string";
    private String m_String_2 = "Second string";

    // Имена параметров, нужны для функции getParameter
    private final String PARAM_String_1 = "String_1";
    private final String PARAM_String_2= "String_2";
    
    public applet3() {
    }
    
    public String getAppletInfo()  {
        return "Name: AppletWithParam\r\n" +"";
    }
    // Метод возвращает ссылку на массив с описаниями 
    // параметров в виде { "Name", "Type", "Description" },
    
    public String[][] getParameterInfo()  {
        String[][] info = {
            { PARAM_String_1, "String", "Parameter description" },
            { PARAM_String_2, "String", "Parameter description" },
        };
        return info;
    }
    public void init()  {
        // Чтение всех параметров и запись значений в поля класса
        String param;
        param = getParameter(PARAM_String_1);
        if (param != null)    m_String_1 = param;
        param = getParameter(PARAM_String_2);
        if (param != null)    m_String_2 = param;
        resize(320, 240);
    }
    public void destroy()  {  // код завершения работы апплета  }
        
    }
    public void paint(Graphics g) {
        g.drawString(m_String_1, 10, 20);
        g.drawString(m_String_2, 10, 50);
    }

    public void start()  {
    // код, который должен выполняться при запуске апплета
    }
    public void stop()  {
    // код, который должен работать при остановке апплета
    }

}
