import javax.swing.SwingUtilities;

import view.Frame;
import view.SplashScreen;

/**
 * Main class for the game.
 */
public class Game {
    /**
     * Main method for the game.
     * @param args the arguments of the game.
     */
    public static void main(String[] args) {

        int duration = 6000;
        
        SplashScreen splashWindow = new SplashScreen(duration);

        splashWindow.showSplashWindow();
        /**
         * SwingUtilities.invokeLater is used to run the game in the EDT.
         */
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Runnable method for the SwingUtilities.invokeLater that run the game by making a new instance of the Frame class.
             */
            public void run() {
                new Frame();
            }
        });
    }
}
