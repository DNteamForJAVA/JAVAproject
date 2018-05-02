package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;

public class MainUI extends JFrame implements ActionListener{
    JPanel topUI;
    JPanel bottomUI;
    JTextArea textTop;
    JTextArea textBottom;

    public MainUI() throws HeadlessException {
        super("Bubble Game");
        this.createContent();
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void createContent(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2,1));
        JPanel topRightPanel = new JPanel();
        JPanel bottomRightPanel = new JPanel();
             
        rightPanel.add(topRightPanel);
        rightPanel.add(bottomRightPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        this.getContentPane().add(mainPanel);
        
        leftPanel.setBackground(new Color(180,180,180));
        topRightPanel.setBackground(new Color(182,142,252));
        bottomRightPanel.setBackground(new Color(151,177,240));
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
