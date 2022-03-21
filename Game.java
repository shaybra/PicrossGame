/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: March 20th, 2022
* Class: Game.java
* Proffesor: Daniel Cormier
*/
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
        
        SplashScreen splashWindow = new SplashScreen(6000); //the party is formed and told how long the have to display the entry way
        splashWindow.showSplashWindow(); //they show the entry way to grids house
        /**
         * SwingUtilities.invokeLater is used to run the game in the EDT.
         */
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Runnable method for the SwingUtilities.invokeLater that run the game by making a new instance of the Frame class.
             */
            public void run() {
                new Frame(); // opens up grids house for the party
            }
        });
    }
}
