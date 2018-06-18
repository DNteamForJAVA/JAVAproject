package game;

import java.awt.GridLayout;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonScreen extends JPanel implements Runnable{
    
    protected Button [] buttonTest;
    protected Vector<String> vt = new Vector();
    protected int [] buttonValue = new int[9];
    Thread t;
    public ButtonScreen(){
        buttonTest = new Button[9];
        this.setLayout(new GridLayout(3,3)); 
        t = new Thread(this);
        t.start();
    }
    public void draw(){
        for( int i = 0; i < 9; i++ ){
            buttonTest[i] = new Button(buttonValue[i]);
            this.add(buttonTest[i]);
        }
        buttonTest[0].setSelected(true);
        this.updateUI();
    }
    Random rd = new Random();
    public void setRandomButtonValue(){
        vt.removeAllElements();
        int temp;
        for( int i = 0; i < 9;){
            temp = rd.nextInt(9);
            if( vt.contains("" + temp) == false ){
                vt.add("" + temp);
                i++;
            }
        }
        for( int i = 0; i < 9; i++ ){
            int value = Integer.parseInt(vt.get(i));
            buttonValue[i] = value;
        }
    }
    @Override
    public void run() {
        while(true){
            this.removeAll();
            setRandomButtonValue();
            draw();
            try {
                t.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }           
        }
    }
}
