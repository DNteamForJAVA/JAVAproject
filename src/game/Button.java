
package game;

import static game.MainUI.bubble1;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Button extends JButton implements ActionListener{
    private ImageIcon []icon = new ImageIcon[9];
    private int []nameIcon = {0,1,2,3,4,5,6,7,8,9};
    private int index;
    public Button(int I){
        this.index = I;
        BufferedImage [] image = new BufferedImage[9];
        try {
            image[0] = ImageIO.read(new File("0.png"));
            image[1] = ImageIO.read(new File("1.png"));
            image[2] = ImageIO.read(new File("2.png"));
            image[3] = ImageIO.read(new File("3.png"));
            image[4] = ImageIO.read(new File("4.png"));
            image[5] = ImageIO.read(new File("5.png"));
            image[6] = ImageIO.read(new File("6.png"));
            image[7] = ImageIO.read(new File("7.png"));
            image[8] = ImageIO.read(new File("8.png"));
            int widthButton = 80;
            int heightButton = 50;
            int dx=0, dy=0;
            for (int i=0; i<9; i++){
                int ix = image[i].getWidth();
                int iy = image[i].getHeight();
                if (widthButton/heightButton > ix/iy)
                {
                    dy = heightButton;
                    dx = dy*ix/iy;
                } else {
                    dx = widthButton;
                    dy = dx*iy/ix;
                }
                icon[i] = new ImageIcon(image[i].getScaledInstance(dx,dy, Image.SCALE_SMOOTH));
                
            }
            this.setIcon(icon[index]);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(MainUI.bubble1.nameIcon.length);
        for (int i=MainUI.bubble1.numColumn+2; i<(MainUI.bubble1.numRow+1)*(MainUI.bubble1.numColumn+2); i++){
            if (index == MainUI.bubble1.nameIcon[i])
            {
                MainUI.bubble1.nameIcon[i] = -1;
                MainUI.bubble1.remainIcon -= 1;
                //System.out.println(MainUI.bubble1.remainIcon);
                MainUI.bubble1.iconLabel[i].setVisible(false);
                SwingUtilities.updateComponentTreeUI(MainUI.bubble1);
                //break;
            }
            if(MainUI.haveBubble2){
                if (index == MainUI.bubble2.nameIcon[i])
                {
                    MainUI.bubble2.nameIcon[i] = -1;
                    MainUI.bubble2.remainIcon -= 1;
                    //System.out.println(MainUI.bubble1.remainIcon);
                    MainUI.bubble2.iconLabel[i].setVisible(false);
                    SwingUtilities.updateComponentTreeUI(MainUI.bubble2);
                    //break;
                }
            }
            
        }

    }
}
