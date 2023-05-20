import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Particle extends GameWindow {

    // Visual Effects
    double radius = 8;
    double diameter;

    //Current Vectors
    Vector2 currentPos;
    Vector2 previousPos;
    Vector2 acceleration;

    int [] particleidentity = {
        0, // Uranium Atom
        1, // Nuetron
        2, // Barium 141
        3, // Krypton 92
        4, // Uranium 238
        5  // Energy
    };

    /* MAIN REACTION                                                                    MAIN REACTION
     * Nuetron impactes at theta with velocity X                                            [ ]
     * Krypton goes out motionless slightly moved at theta + 90                             [ ]
     * Barium goes out motionless slightly moved at theta - 90                              [ ]
     * Top Nuetron (1) goes out with velocity X * Offset at theta + 135                     [ ]
     * Middle Nuetron (2) goes otu with velocity X * Offset at theta + 180                  [ ]
     * Bottom Nuetron (2) goes out with velocity X * Offset at theta - 135                  [ ]
     */

     /* CONTROL RODS                                                              CONTROL RODS (OPTIONAL)
      * To simluate the process of control rods "absorbing" the nuetrons                    [ ]
      * control rods should be dashed lines that go down. (y values divisible by 4??)       [ ]
      * They can be solid but need to simluate some leakage                                 [ ]
      * Little Goofy Steam Animation (Optional) to show power!                              [ ]
      */ 

    /* USER INTERACTION                                                        USER INTERACTION (PREFERABLE)
     * People should be able to click on the control rods to remove them                    [ ]
     * People should be able to click in nuetron into existence (with random Vec2 velocity) [ ]
     * People should be able to determine the concentration of Uranium-235                  [ ]
     */



    int self = 0; //Who am I (identity crisis)


    
    public Particle(Vector2 currentPos) {
        // Taking in the Vector Inputs
        this.acceleration = new Vector2(0,0);
        this.currentPos = currentPos;
        this.previousPos = currentPos;
        diameter = radius*2;
    }

    // Updating location of particle
    public void updateLocation(double deltaTime) {
        ;
    }

    public void accelerate(Vector2 acceleration){
        this.acceleration.addVector(acceleration);
    }

    public void update(double deltaTime){
        for(int i = 0; i < steps; i ++){
            ;
        }
        this.updateLocation(deltaTime);
    }

    // Various Get methods for location, object itself and effect areas.
    public Object getParticle() {
        return new Ellipse2D.Double(currentPos.x - radius, currentPos.y-radius, diameter, diameter);
    }
}
