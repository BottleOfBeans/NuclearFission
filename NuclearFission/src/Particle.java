import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class Particle extends GameWindow {


    //FINALS
    final double NUETRON_BUFFER = 0.9;
    final double MAXIMUM_VELOCITY = 5;
    // Visual Effects
    double radius = 8;
    double diameter;
    Color color;

    Boolean collided = false;
    Boolean split = false;

    Branch deepestBranch;
    
    //Current Vectors
    Vector2 currentPos;
    Vector2 acceleration;
    Vector2 velocity;

    public ArrayList<Particle> splitParts = new ArrayList<Particle>(); 
    public ArrayList<Particle> splitRequests = new ArrayList<Particle>(); 
    
    Object [][] particleidentity = {
        {"U-235", 10, Color.RED}, // Uranium Atom - 235
        {"U-238", 15, Color.DARK_GRAY}, // Uranium 238
        {"Ba-141", 3, Color.ORANGE}, // Barium 141    // FORMAT {NAME, RADIUS, COLOR}
        {"Kr-92", 3, Color.GREEN}, // Krypton 92
        {"N", 2, Color.WHITE}, // Neutron
        {"E", 1, Color.YELLOW}, // Energy
    };

    Object [] self;


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

    public Particle(Vector2 currentPos, Vector2 currentVelo, int type) {
        // Taking in the Vector Inputs
        this.currentPos = currentPos;
        this.self = particleidentity[type];
        this.radius = (int) this.self[1];
        this.diameter = this.radius * 2;
        this.color = (Color) this.self[2];
        this.velocity = currentVelo;
    }

    // Updating location of particle
    public void updateLocation(double deltaTime) {
        this.currentPos.addVector(this.velocity.returnMultiply(deltaTime * deltaTime));
    }
    
    
    public void update(double deltaTime){
        for(int i = 0; i<steps; i++){

            
            if(this.self[0].equals("Ba-141") || this.self[0].equals("Kr-92")){
                this.velocity = new Vector2(0,0);
                return;
            }
            if(!collided){
                if(!this.self[0].equals("Ba-141") && !this.self[0].equals("Kr-92") && this.deepestBranch != null){
                    solveCollisions();
                    updateLocation(deltaTime);

                }
                solveEdge(); 
            }
        }
        if(splitRequests != null){
            for (Particle p : splitRequests){
                uraniumSplit(p, this);
                
            }
        }
        
        splitRequests.clear();
    }

    public void applySpeedLimit(){
        if(velocity.magnitude() >= MaximumVelocity){
            velocity = velocity.normalize();
            velocity.multiply(10*0);
        }
    }


    public void uraniumSplit(Particle u, Particle n){
        //System.out.println(u.self[0] + "   is splitting with "  + n.self[0]);
        split = true;

        GameWindow.oldParticles.add(u);
        GameWindow.oldParticles.add(n);

        Particle part = new Particle(this.currentPos, randomVector(5), 4);
        Particle part2 = new Particle(this.currentPos, randomVector(5), 4);
        Particle part3 = new Particle(this.currentPos, randomVector(5), 4);

        Particle bariumParticle = new Particle(this.currentPos, new Vector2(0,0), 2);
        Particle kryptonParticle = new Particle(this.currentPos.returnAdd(new Vector2(5,0)), new Vector2(0,0), 3);

        GameWindow.newParticles.add(bariumParticle);
        GameWindow.newParticles.add(kryptonParticle);
        GameWindow.newParticles.add(part);
        GameWindow.newParticles.add(part2);
        GameWindow.newParticles.add(part3);


    }

    public Vector2 randomVector(double gSpeed){
        Random rand = new Random();
        double randomXVelo = rand.nextInt(-5,5);
        double randomYVelo = rand.nextInt(-5,5);
        Vector2 gVelo = new Vector2(randomXVelo, randomYVelo);
        gVelo = gVelo.normalize().returnMultiply(gSpeed);
        return gVelo;
        
    }
    public static Vector2 generateRandomDirectionVector() {
        Random rand = new Random(System.currentTimeMillis()*1000);
        
        double x = rand.nextDouble();
        double y = rand.nextDouble();
    

        return new Vector2(x, y);
    }


    public void solveCollisions(){
        for(Particle p : this.deepestBranch.giveNeighbors(this)){
            if(p.self[0].equals("U-235") || p.self[0].equals("U-238")){
                Vector2 distance = this.currentPos.returnSubtract(p.currentPos);
                double dist = distance.magnitude();
                if(dist < this.radius + p.radius){
                    if(!split){
                        if(p.self[0].equals("U-235") && this.self[0].equals("N")){
                            splitRequests.add(p);
                        }else{
                            if(this != p){
                                this.velocity = (this.velocity.returnAdd(distance.returnMultiply(1))).returnMultiply(0.1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void solveEdge(){
        if(currentPos.x >= gameWidth/zoomX || currentPos.x <= 0){
            this.velocity.multiply(-1);
        }
        if(currentPos.y >= gameHeight/zoomY || currentPos.y <= 0){
            this.velocity.multiply(-1);
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