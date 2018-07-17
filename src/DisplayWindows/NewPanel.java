package DisplayWindows;

import javax.swing.*;
import java.awt.*;

class NewPanel extends JPanel
{
    String photoName;
    public NewPanel(String photoName)
    {
        this.photoName = photoName;
    }

    public void paintComponent(Graphics g)
    {
        int x=0,y=0;
        java.net.URL imgURL=getClass().getResource(photoName);

        //test.jpg是测试图片，与Demo.java放在同一目录下
        ImageIcon icon=new ImageIcon(imgURL);
        g.drawImage(icon.getImage(),x,y,getSize().width,getSize().height,this);
        while(true)
        {
            g.drawImage(icon.getImage(),x,y,this);
            if(x>getSize().width && y>getSize().height)break;
            //这段代码是为了保证在窗口大于图片时，图片仍能覆盖整个窗口
            if(x>getSize().width)
            {
                x=0;
                y+=icon.getIconHeight();
            }
            else
                x+=icon.getIconWidth();
        }
    }
}
