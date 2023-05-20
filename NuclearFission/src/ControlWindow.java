import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;



public class ControlWindow extends JPanel{ 

    // Must be changed in BOTH GameWindow and ControlWindow
    static int gameWidth = 1000;   
    static int gameHeight = 1000;  

    static int slider_length = 200;
    static int slider_initial_x = gameWidth/2;
    static int slider_x = slider_initial_x;
    static int slider_y = gameHeight/4;

    static int slider_height = slider_length/10;
    static int slider_width = slider_length/20;

    int percent_uranium_235;

    public ControlWindow(){
        this.setSize(gameWidth, gameHeight);
    }

    class ScrollBar  
    {  
    ScrollBar(){  
        JFrame f= new JFrame("Scrollbar");          
        final JScrollBar s=new JScrollBar();  
        s.setBounds(0,600, 600,50);  
        f.add(s);
        f.setSize(600,600);  
    f.setLayout(null);  
    f.setVisible(true);  
    s.addAdjustmentListener((AdjustmentListener) new AdjustmentListener() {  
        public void adjustmentValueChanged(AdjustmentEvent e) {  
                percent_uranium_235 =  s.getValue();  
            }
        }
    );  
    }  
}
}

//         if (e.getX() > GameWindow.slider_x && e.getX() < GameWindow.slider_x + GameWindow.slider_length){
        //     if (e.getY() > GameWindow.slider_y && e.getY() < GameWindow.slider_y + GameWindow.slider_height){  
        //         GameWindow.slider_x = e.getX();

        //         for (Particle p : GameWindow.particles){
        //             if (p.self[0].equals("U-235")){
        //                 GameWindow.particles.remove(p);
        //             }
        //         }

        //         GameWindow.percent_uranium_235 = (GameWindow.slider_x - GameWindow.slider_initial_x) / GameWindow.slider_length;
        //         //System.out.println(GameWindow.percent_uranium_235);

        //         int uranium_235s = 0;
        //         while (uranium_235s / GameWindow.particles.size() < GameWindow.percent_uranium_235){
        //             Random rand = new Random();
        //             double randomXPos =  rand.nextDouble(0 + GameWindow.gameWidth*GameWindow.zoomXOffset/2, GameWindow.gameWidth / GameWindow.zoomX - GameWindow.gameWidth*GameWindow.zoomXOffset/2); // gameWidth/zoomX / 2 ; (For Center)
        //             double randomYPos =  rand.nextDouble(0 + GameWindow.gameHeight*GameWindow.zoomYOffset/2, GameWindow.gameHeight / GameWindow.zoomY - GameWindow.gameHeight*GameWindow.zoomYOffset/2); // gameHeight/zoomY / 2 ; (For Center)
                    
        //             GameWindow.particles.add(new Particle(new Vector2(randomXPos, randomYPos), new Vector2(0,0), 0));
        //             uranium_235s ++;
        //         }
        //     }
        // }



// //     @Override
//     public void mousePressed(MouseEvent e) {
//         if (e.getX() > GameWindow.slider_x && e.getX() < GameWindow.slider_x + GameWindow.slider_length){
//             if (e.getY() > GameWindow.slider_y && e.getY() < GameWindow.slider_y + GameWindow.slider_height){  
//                 GameWindow.slider_x = e.getX();

//                 for (Particle p : GameWindow.particles){
//                     if (p.self[0].equals("U-235")){
//                         GameWindow.particles.remove(p);
//                     }
//                 }

//                 GameWindow.percent_uranium_235 = (GameWindow.slider_x - GameWindow.slider_initial_x) / GameWindow.slider_length;
//                 //System.out.println(GameWindow.percent_uranium_235);

//                 int uranium_235s = 0;
//                 while (uranium_235s / GameWindow.particles.size() < GameWindow.percent_uranium_235){
//                     Random rand = new Random();
//                     double randomXPos =  rand.nextDouble(0 + GameWindow.gameWidth*GameWindow.zoomXOffset/2, GameWindow.gameWidth / GameWindow.zoomX - GameWindow.gameWidth*GameWindow.zoomXOffset/2); // gameWidth/zoomX / 2 ; (For Center)
//                     double randomYPos =  rand.nextDouble(0 + GameWindow.gameHeight*GameWindow.zoomYOffset/2, GameWindow.gameHeight / GameWindow.zoomY - GameWindow.gameHeight*GameWindow.zoomYOffset/2); // gameHeight/zoomY / 2 ; (For Center)
                    
//                     GameWindow.particles.add(new Particle(new Vector2(randomXPos, randomYPos), new Vector2(0,0), 0));
//                     uranium_235s ++;
//                 }
//             }
//         }
//     }