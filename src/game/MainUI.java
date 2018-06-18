package game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.*;

public class MainUI extends JFrame implements ActionListener{
    JPanel topUI;
    JPanel bottomUI;
    JTextArea textTop;
    JTextArea textBottom;
    JPanel mainPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel topRightPanel;
    public static Bubble bubble1, bubble2;
    boolean haveBubble1;
    public static boolean haveBubble2;
    Timer timer; 
    ButtonScreen buttonScreen;
    CardLayout card;
    JPanel firstView;
    JButton start;
    JButton openHighScore;
    JButton openExit;
    JPanel container;
    JLabel score;
    JLabel endScore;
    JButton back;
    
    JLabel gameOver;
    JPanel endView;
    JButton playAgain;
    JButton endHighScore;
    JButton endExit;
    static int endgameFlag=1;
    static int temp =0;
    
    static int scoreUpdate=0;
    ArrayList<Integer>scoreArray = new ArrayList<>();
    String[] showScore = new String[10];
    JPanel scoreView;
    JLabel[] order;
    JLabel[] scoreContent = new JLabel[10];
    JLabel title;
    FileWriter out;
    
    public MainUI() throws HeadlessException {       
        super("Bubble Game");
        for(int i = 0;i<10;i++){
            showScore[i]="";
        }
        timer = new Timer(100,this);
        timer.start();
        this.haveBubble1 = false;
        this.haveBubble2 = false;
        this.createView();
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
    
    public void createView(){
        container = new JPanel();
        card = new CardLayout();
        container.setLayout(card);
        
        firstView = new JPanel();
        firstView.setLayout(null);
        start = new JButton("START");
        start.setBounds(340,200,120,30);
        openHighScore = new JButton("HIGH SCORES");
        openHighScore.setBounds(340,250,120,30);
        openExit = new JButton("EXIT");
        openExit.setBounds(340,300,120,30);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));   
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        topRightPanel = new JPanel();
        topRightPanel.setLayout(null);
        score = new JLabel("0",SwingConstants.CENTER);
        score.setBounds(80, 110, 240, 80);
        score.setFont(new Font("Arial", Font.PLAIN, 100));
        
        endView = new JPanel();
        endView.setLayout(null);
        gameOver = new JLabel("GAME OVER!!",SwingConstants.CENTER);
        gameOver.setBounds(230, 50, 340, 80);     
        gameOver.setFont(new Font("Arial", Font.PLAIN, 50));
        endScore = new JLabel("",SwingConstants.CENTER);
        endScore.setBounds(230, 150, 340, 80);
        endScore.setFont(new Font("Arial", Font.PLAIN, 70));
        playAgain = new JButton("PLAY AGAIN");
        playAgain.setBounds(340, 250, 120, 30);
        endHighScore = new JButton("HIGH SCORES");
        endHighScore.setBounds(340, 300, 120, 30);
        endExit = new JButton("EXIT");
        endExit.setBounds(340, 350,120,30);
        
        scoreView = new JPanel();
        back = new JButton("Back");
        scoreView.setLayout(null);
        title = new JLabel("HIGH SCORES",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 40));
        title.setBounds(180,20,440,80);
        scoreView.add(title);
        order = new JLabel[10];
        try {
            BufferedReader bf = new BufferedReader(new FileReader("high scores.txt"));
            String line;
            int counter=0;
            while((line = bf.readLine())!=null){
                scoreArray.add(Integer.valueOf(line));
                showScore[counter]=line;
                counter++;
            }
            System.out.println(counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0 ;i<10;i++){
            order[i]=new JLabel();
            order[i].setText(String.valueOf(i+1)+".");
            order[i].setFont(new Font("Arial", Font.PLAIN, 20));
            order[i].setBounds(300,100+i*30,50,20);
            scoreView.add(order[i]);
            
            scoreContent[i]= new JLabel(showScore[i]);
            scoreContent[i].setFont(new Font("Arial", Font.PLAIN, 20));
            scoreContent[i].setBounds(340,100+i*30,50,20);
            scoreView.add(scoreContent[i]);
        }
        back.setBounds(650,500,80,30);
        scoreView.add(back);
        
        buttonScreen = new ButtonScreen();
        
        container.add(firstView,"1");
        container.add(mainPanel,"2");
        container.add(endView,"3");
        container.add(scoreView,"4");
        firstView.add(start);
        firstView.add(openHighScore);
        firstView.add(openExit);
        topRightPanel.add(score);
        rightPanel.add(topRightPanel);
        rightPanel.add(buttonScreen);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        endView.add(gameOver);
        endView.add(endScore);
        endView.add(playAgain);
        endView.add(endHighScore);
        endView.add(endExit);
        card.show(container, "1");
        this.getContentPane().add(container);
        
        
        leftPanel.setLayout(null);
        topRightPanel.setBackground(new Color(182,142,252));
        start.addActionListener(this);
        openExit.addActionListener(this);
        openHighScore.addActionListener(this);
        endExit.addActionListener(this);
        endHighScore.addActionListener(this);
        back.addActionListener(this);
        playAgain.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(endgameFlag==1 && temp != 0){
            endScore.setText(String.valueOf(scoreUpdate));
            card.show(container, "3");
            if (bubble1.timer.isRunning())
                bubble1.timer.stop();
            if (this.haveBubble2)
                if (bubble2.timer.isRunning())
                    bubble2.timer.stop();
        }
        if(e.getSource()==back){
            if (temp == 0)
                card.show(container, "1");
            else card.show(container, "3");
        }
        if (e.getSource()==start){
            card.show(container, "2");
            endgameFlag = 0;
        }
        if (e.getSource()==openExit ||e.getSource()==endExit){
            this.dispose();
            System.exit(0);
        }
        if (e.getSource()==openHighScore ||e.getSource()==endHighScore){
            card.show(container, "4");
        }
        if (endgameFlag == 0){
            if (this.haveBubble1 == false){
                this.haveBubble1 = true;
                this.bubble1 = new Bubble(10,-100,3,3);
                this.bubble1.isExplode = false;          
                this.leftPanel.add(bubble1);   
                SwingUtilities.updateComponentTreeUI(this);
            }
            if (this.haveBubble2 == false && this.scoreUpdate >= 3){
                this.haveBubble2 = true;
                this.bubble2 = new Bubble(200,-100,3,3);
                this.bubble2.isExplode = false;          
                this.leftPanel.add(bubble2);   
                SwingUtilities.updateComponentTreeUI(this);
            }
            if (this.haveBubble1 && this.bubble1.isExplode){
                this.haveBubble1 = false;
                this.leftPanel.remove(this.bubble1);
                scoreUpdate++;
                score.setText(String.valueOf(scoreUpdate));
                this.repaint();
            }
            if (this.haveBubble2 && this.bubble2.isExplode){
                this.haveBubble2 = false;
                this.leftPanel.remove(this.bubble2);
                scoreUpdate++;
                score.setText(String.valueOf(scoreUpdate));
                this.repaint();
            }
        }
        if(endgameFlag==1 && temp!=0 ){
            endgameFlag=0;
            scoreArray.add(scoreUpdate);
            Collections.sort(scoreArray);
            Collections.reverse(scoreArray);
            try {
                out = new FileWriter("high scores.txt");
                for(int i = 0;i<10;i++){
                    scoreContent[i].setText(String.valueOf(scoreArray.get(i)));
                    System.out.print(scoreArray.get(i));
                    System.out.print(" ");
                    out.write(String.valueOf(scoreArray.get(i)));
                    out.write("\r\n");
                }
                System.out.println("");
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }           
        }
        if (e.getSource() == playAgain){
            endgameFlag = 0;
            temp = 0;
            card.show(container, "2");
            this.leftPanel.remove(bubble1);
            if (this.haveBubble2)
                this.leftPanel.remove(bubble2);
            this.haveBubble1 = false;
            this.haveBubble2 = false;
            scoreUpdate = 0;
            score.setText(String.valueOf(scoreUpdate));    
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
    
    
}
