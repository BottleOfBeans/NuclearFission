import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Random;

public class Particle extends GameWindow {


    //FINALS
    final double NUETRON_BUFFER = 0.9;

    // Visual Effects
    double radius = 8;
    double diameter;
    Color color;
    Boolean collided = false;
    Boolean split = false;

    Random rand = new Random();
    
    //Current Vectors
    Vector2 currentPos;
    Vector2 previousPos;
    Vector2 acceleration;
    Vector2 gAccel = new Vector2(0,0);

    public ArrayList<Particle> splitParts = new ArrayList<Particle>(); 
    
    Object [][] particleidentity = {
        {"U-235", 5, Color.RED}, // Uranium Atom - 235
        {"U-238", 4, Color.CYAN}, // Uranium 238
        {"Ba-141", 3, Color.ORANGE}, // Barium 141    // FORMAT {NAME, RADIUS, COLOR}
        {"Kr-92", 3, Color.GREEN}, // Krypton 92
        {"N", 2, Color.WHITE}, // Neutron
        {"E", 1, Color.YELLOW}, // Energy
    };

    Object [] self;

    

    /* MAIN REACTION                                                                    MAIN REACTION
     * Neutron impactes at theta with velocity X                                            [ ]
     * Krypton goes out motionless slightly moved at theta + 90                             [ ]
     * Barium goes out motionless slightly moved at theta - 90                              [ ]
     * Top Neutron (1) goes out with velocity X * Offset at theta + 135                     [ ]
     * Middle Neutron (2) goes otu with velocity X * Offset at theta + 180                  [ ]
     * Bottom Neutron (2) goes out with velocity X * Offset at theta - 135                  [ ]
     */

     /* CONTROL RODS                                                              CONTROL RODS (OPTIONAL)
      * To simluate the process of control rods "absorbing" the neutrons                    [ ]
      * control rods should be dashed lines that go down. (y values divisible by 4??)       [ ]
      * They can be solid but need to simluate some leakage                                 [ ]
      * Little Goofy Steam Animation (Optional) to show power!                              [ ]
      */ 

    /* USER INTERACTION                                                        USER INTERACTION (PREFERABLE)
     * People should be able to click on the control rods to remove them                    [ ]
     * People should be able to click in nuetron into existence (with random Vec2 velocity) [ ]
     * People should be able to determine the concentration of Uranium-235                  [ ]
     */



    public int type = 0; //Who am I (identity crisis)


    
    public Particle(Vector2 currentPos, Vector2 currentVelo, int type) {
        // Taking in the Vector Inputs
        this.acceleration = new Vector2(0,0);
        this.currentPos = currentPos;
        this.previousPos = currentPos;
        this.self = particleidentity[type];
        this.radius = (int) this.self[1];
        this.diameter = this.radius * 2;
        this.color = (Color) this.self[2];
        this.gAccel = currentVelo;
    }

    // Updating location of particle
    public void updateLocation(double deltaTime) {
        Vector2 velocity = currentPos.returnSubtract(this.previousPos);
        this.previousPos = this.currentPos;

        this.currentPos.addVector(velocity);
        this.currentPos.addVector(acceleration.returnMultiply(deltaTime*deltaTime));
        
        acceleration = new Vector2(0,0);
    }

    public void accelerate(Vector2 acceleration){
        this.acceleration.addVector(acceleration);
    }

    public void update(double deltaTime){
        if(!collided){
            solveCollisions();
            this.accelerate(gAccel);
            this.updateLocation(deltaTime); 
        }
    }

    public void uraniumSplit(Particle u){
        Particle N1 = new Particle(this.currentPos, randomVector(3), 5);
        Particle N2 = new Particle(this.currentPos, randomVector(3), 5);
        Particle N3 = new Particle(this.currentPos, randomVector(3), 5);
        
        Particle bariumParticle = new Particle(this.currentPos, randomVector(3), 5);
        Particle kryptonParticle = new Particle(this.currentPos, randomVector(3), 5);
       
        GameWindow.particles.add(N1);
        GameWindow.particles.add(N2);
        GameWindow.particles.add(N3);
        GameWindow.particles.add(bariumParticle);
        GameWindow.particles.add(kryptonParticle);
    }

    public Vector2 randomVector(double gSpeed){
        double randomXVelo = rand.nextInt(-10,10);
        double randomYVelo = rand.nextInt(-10,10);
        Vector2 gVelo = new Vector2(randomXVelo, randomYVelo);
        gVelo.normalize().multiply(gSpeed);
        return gVelo;
        
    }

    public void solveCollisions(){
        for(Particle p : GameWindow.particles){
            if(p != this){
                /*
                 * Checking if the particle collides with the atom
                 */
                boolean XConstraints = this.currentPos.x > p.currentPos.x - p.radius  &&  this.currentPos.x < p.currentPos.x + p.radius;
                boolean YConstraints = this.currentPos.y > p.currentPos.y - p.radius  &&  this.currentPos.y < p.currentPos.y + p.radius;

                /*
                 * Checks the particle that is collided is Uranium
                 */
                boolean isUranium = p.self[0].equals("U-235") && this.self[0].equals("N");

                if (XConstraints && YConstraints){                        
                        if (isUranium){
                            this.collided = true; // Sets collided to true, ensures for only one collision
                            p.collided = true; // Sets the collided particle to true, ensures that it deletes itself :)
                            uraniumSplit(p);
                        }
                    }
                }
            }
    }

    // Various Get methods for location, object itself and effect areas.
    public Object getParticle() {
        if(collided){
            return null;
        }
        return new Ellipse2D.Double(currentPos.x - radius, currentPos.y-radius, diameter, diameter);
    }
}
