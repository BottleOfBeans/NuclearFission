
import javax.swing.*;

public class Main {
    public static void main(String args[]) {
        /*
         * Setting up the JFrame Window
         * Resizeable --> False
         * Close Operation --> Exit On Close
         * Window Name --> "Romir's Silly Goofy Little Game Thing :)"
         * Window Visibility --> True
         */
        JFrame window = new JFrame();
        JFrame controls = new JFrame();
        
        // Closing the window on the X button click
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controls.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Window can be adjusted
        window.setResizable(false);
        controls.setResizable(false);

        // Window Title
        window.setTitle("Nuclear Fission");
        controls.setTitle("Fission Controls");
        // Addding the window to the window?

        ControlWindow controlWindow = new ControlWindow();
        controls.add(controlWindow);
        controls.setResizable(true);
        controls.setSize(500, 900);

        // Pack, creating and starting the FPS loop

        GameWindow gameWindow = new GameWindow();
        window.add(gameWindow);
        // No Header (Borderless)
        //window.setUndecorated(true);

        // Pack, creating and starting the FPS loop
        window.pack();
        controls.pack();
        window.setVisible(true);
        controls.setVisible(true);
        gameWindow.startWindowThread();
    }

}
