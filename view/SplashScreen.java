package view;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class SplashScreen extends JWindow {

    // It likes when you have ID tagged soldiers with ID tagged weapons
    private static final long serialVersionUID = 6248477399124883341L;
    // It make the splash screen go weeeeeeeeeee until it runs out of breath
    private final int duration;

    // Oh right i need to figure out how much breath the splash has better ask somebody
    public SplashScreen(int duration){
        this.duration = duration;
    }

    public void showSplashWindow() {
        JPanel content = new JPanel(new BorderLayout());

        content.setBackground(Color.BLACK);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        BufferedImage image = null;
        try{
            image = ImageIO.read(new File("images/SplashScreen.png"));
        }catch (IOException e1){

        }

        int width = image.getWidth();
        int height = image.getHeight(); 

        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;

        setBounds(x,y,width,height);
        
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/images/SplashScreen.png")));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));


        content.add(label,BorderLayout.CENTER);

        setContentPane(content);

        setVisible(true);
        try {
    	
            Thread.sleep(duration); 
        } catch (InterruptedException e){

        }
        dispose();
    }




    
}
