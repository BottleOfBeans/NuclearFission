import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable {
    /*
     * Game Tile Size shows the tile that each of the images on screen should be in
     * pixels, this is not the amount that are displayed in each tile but rather the
     * orignal one
     * Scalable value shows the value that is used to upscale the original images on
     * the screen
     * Game Column and Row amounts show the amount of rows and columns that can be
     * displayed on screen at one time
     */
    static int GameTileSize = 16;
    static int ScalableValue = 3;
    static int gameColumnAmount = 24;
    static int gameRowAmount = 20;

    /*
     * ActualTileSize uses the variables from above to calculate how big each tile
     * actually should be
     * Game Width and Height takes into account the columns and rows and calculates
     * the window heights and widths using all the factors provided above
     */
    static int ActualTileSize = GameTileSize * ScalableValue;
    static int gameWidth = 1000; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1000; // gameRowAmount*ActualTileSize;

    Thread gameThread;

    // Game Values
    int FPS = 60;
    int steps = 2;

    static double zoomX = 0.7;
    static double zoomY = 0.7;

    static double zoomXOffset = 0;
    static double zoomYOffset = 0;
    double deltaTime = 0;

    static double percent_uranium_235 = 40; // (%)

    
    // Creating the game windows and setting up the settings
    public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(new MouseInput());
    }

    // Starting thread, managing frame updates
    public void startWindowThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void drawSlider(){

    }

    /*
     * Particles!
     */
    public double MaximumVelocity = 10;
    public double MaxParticle = 1000; // Maximum particle amount
    public static ArrayList<Particle> particles = new ArrayList<Particle>(); // List containing all of the particles
    public static ArrayList<Particle> newParticles = new ArrayList<Particle>(); // List containing all of the particles
    public static ArrayList<Particle> oldParticles = new ArrayList<Particle>(); // List containing all of the particles

    /*
     * Quadtree
     */
    public static Branch QuadTree = new Branch(new Rectangle2D.Double(0,0,gameHeight,gameWidth), 0, null);

    // Loop that runs the thread, allows for it to sleep and start and ensures
    // proper frame speed
    @Override
    public void run() {
        /*
         * Add code here that runs before he game starts
         */
        Random rand = new Random();

        double initialXPos = gameWidth/zoomX/2;
        double initialYPos = gameHeight/zoomY/2;
        
        Particle initial_particle = new Particle(new Vector2(initialXPos, initialYPos),new Vector2(0,0), 0);
        particles.add(initial_particle);

        //Creating a bunch of random particles with adjustment to zoom
        for (int i = 0; i < MaxParticle; i++) {
            
            double randomXPos =  rand.nextDouble(0 + gameWidth*zoomXOffset/2, gameWidth / zoomX - gameWidth*zoomXOffset/2); // gameWidth/zoomX / 2 ; (For Center)
            double randomYPos =  rand.nextDouble(0 + gameHeight*zoomYOffset/2, gameHeight / zoomY - gameHeight*zoomYOffset/2); // gameHeight/zoomY / 2 ; (For Center)
            
            if (rand.nextInt(0,100) <= percent_uranium_235){
                particles.add(new Particle(new Vector2(randomXPos, randomYPos),randomVector(0.1), 0));
            }
            else {
                particles.add(new Particle(new Vector2(randomXPos, randomYPos),randomVector(0.1), 1));
            }
        }    
        

        // Managing updates and FPS
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update(); // Sending the deltaTime through the update function
                deltaTime = delta;
                repaint();
                delta--;
            }

        }
    }

    public Vector2 randomVector(double gSpeed){
        Random rand = new Random();
        double randomXVelo = rand.nextInt(-10,10);
        double randomYVelo = rand.nextInt(-10,10);
        Vector2 gVelo = new Vector2(randomXVelo, randomYVelo);
        gVelo = gVelo.normalize().returnMultiply(gSpeed);
        return gVelo; 
    }

    public void update() {
        QuadTree = new Branch(new Rectangle2D.Double(0,0,gameHeight/zoomX,gameWidth/zoomY), 0, null);
    }

    // Function that paints the updated version of the frame {FPS} times a second.
    public void paintComponent(Graphics g) {

        
        // Quick definition of varibles to use with the G2D library
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        AffineTransform oldTransform = graphics.getTransform();
        graphics.scale(zoomX, zoomY); // Zoomgin on two axis'!

        graphics.setColor(Color.white);

        for (Particle p : particles) {
            p.update(deltaTime);
            if(!p.collided){
                graphics.setColor((Color) p.color);
                QuadTree.addPoint(p); 
                graphics.fill((Shape) p.getParticle());
            }
            if(p.currentPos.x < 0 - 1 || p.currentPos.x > gameWidth/zoomX + 1){
                oldParticles.add(p);
            }
            if(p.currentPos.y < 0 - 1|| p.currentPos.y > gameHeight/zoomY + 1){
                oldParticles.add(p);
            }
            if(p.self[0].equals("Ba-141")){
                //p.velocity.display();
            }

            //Slider
            //graphics.drawRect(slider_initial_x, slider_y, slider_length, 3);
            //graphics.drawRect(slider_x, slider_y, slider_width, slider_height);

            //p.currentPos.display();
        }
        for (Particle p : oldParticles){
            if(p != null){
                particles.remove(p);
            }
        }
        for (Particle p : newParticles){
            if(p != null){
                particles.add(p);
            }
        }
        //hehehehehahahahahehehehehahahahehahehahahwhehehahahehahehahehahewhehahehahehahehahehahehahehaheheheheheheheheheheheheheheheheheahahahahahehehehehahahahahehehehehehehehahahahahehehehehahahehehahehehahehehahehehahshshaehashehasehashehasehashehasehasheahsehasheahsehasehasehahsehasehasheasehasehasehasehaseahsehasehasehasehasehasehasehasehaseh
        newParticles.clear();
        oldParticles.clear();

        //QuadTree.displayBranch(graphics);
        
        graphics.setTransform(oldTransform); // Putting the locations of the objects to what they should be post zoom.

        graphics.dispose();
    }

}
//hi