
package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Bubble extends JPanel implements ActionListener {

    public int numRow, numColumn;
    private int bubbleHeight;
    private int bubbleWidth;
    private int x_coordinate, y_coordinate;
    public Timer timer;
    public int remainIcon;
    public boolean isExplode; //đã nổ chưa
    public JLabel [] iconLabel;
    private int redColor, greenColor, blueColor;
    public int [] nameIcon;
   
    
    public Bubble(int x, int y, int numR, int numC)
    {                      
        initBubble(x,y,numR,numC);        
        this.setBounds(this.x_coordinate, this.y_coordinate, this.bubbleWidth, this.bubbleHeight);
        if(MainUI.scoreUpdate > 20){
            timer = new Timer(10, this);
            timer.start();
        }else if(MainUI.scoreUpdate > 15){
            timer = new Timer(15, this);
            timer.start();
        }else if(MainUI.scoreUpdate > 12){
            timer = new Timer(20, this);
            timer.start();
        }else if(MainUI.scoreUpdate > 7){
            timer = new Timer(25, this);
            timer.start();
        }else{
            timer = new Timer(30, this);
            timer.start();
        }
        
    }
    private void initBubble(int x, int y, int numR, int numC)
    {
        this.numRow = numR;
        this.numColumn = numC;
        this.nameIcon = new int[(numR+2)*(numC+2)];
        this.bubbleHeight = (this.numRow+2)*30;
        this.bubbleWidth = (this.numColumn+2)*30;
        this.x_coordinate = x;
        this.y_coordinate = y;
        this.isExplode = false;
        Random rand = new Random();
        this.redColor = rand.nextInt(255);
        this.greenColor = rand.nextInt(255);
        this.blueColor = rand.nextInt(255);
        this.remainIcon = this.numRow*this.numColumn;
        this.iconLabel = new JLabel[(this.numColumn+2)*(this.numRow+2)];
        this.setLayout(new GridLayout (this.numRow+2,this.numColumn+2));
        for (int i=0; i<(this.numColumn+2)*(this.numRow+2); i++)
        {
            this.iconLabel[i] = new JLabel();
            this.add(this.iconLabel[i]);
        }
        this.LoadImage();
        
        
    }
    public void LoadImage(){      
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
            ImageIcon [] icon = new ImageIcon[9];
            
            //scale image part
            int widthLabel = this.bubbleWidth/(this.numColumn+2);
            int heightLabel = this.bubbleHeight/(this.numRow+2);
            int dx=0, dy=0;
            for (int i=0; i<9; i++){
                int ix = image[i].getWidth();
                int iy = image[i].getHeight();
                if (widthLabel/heightLabel > ix/iy){
                    dy = heightLabel;
                    dx = dy*ix/iy;
                } else {
                    dx = widthLabel;
                    dy = dx*iy/ix;
                }
                icon[i] = new ImageIcon(image[i].getScaledInstance(dx,dy, Image.SCALE_SMOOTH));
            }
            
                
            // random image
            int temp = 0, nextIndex;
            Random rand = new Random();
            for (int i=this.numColumn+2; i<(this.numRow+1)*(this.numColumn+2); i++)
            {
                if (temp!=0 && temp!=this.numColumn+1)
                {
                    nextIndex = rand.nextInt(9);
                    this.iconLabel[i].setIcon(icon[nextIndex]);
                    this.nameIcon[i] = nextIndex;
                    temp++;
                }
                else {
                    if (temp == this.numColumn+1)
                        temp = 0;
                    else temp++;
                    this.nameIcon[i] = -1;
                }
            }

        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }       
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBounds(this.x_coordinate, this.y_coordinate, this.bubbleWidth, this.bubbleHeight);
        g.setColor(new Color(this.redColor,this.greenColor,this.blueColor));
        g.drawOval(0, 0, this.bubbleWidth, this.bubbleHeight);
        g.fillOval(0, 0, this.bubbleWidth, this.bubbleHeight);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.remainIcon == 0) this.isExplode = true;
        
        if(this.y_coordinate+this.bubbleHeight == 550){
            timer.stop();
            MainUI.endgameFlag=1;
            MainUI.temp+=1;
        }
        
        if (isExplode) timer.stop();
        else {
            this.dropDown();
            this.repaint();
        }
 
        
    }
    
    private void dropDown()
    {
        this.y_coordinate += 1;
    }
    
}
