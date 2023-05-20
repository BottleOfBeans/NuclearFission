import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Branch{
    
    private static final double GRAV_CONST = 6.674e-11; // Gravitational Constant constant
    private static final double MAXIMUM_BODIES = 1;
    private static final double MAXIMUMLEVEL = 500;
    private static final double DIST_MULTIPLIER = 1e6;
     
    public double level = 0;
    
    double totalMass = 0;
    double centerX = 0;
    double centerY = 0;

    Rectangle2D bounds;
    Color color = Color.RED;
    
    public ArrayList<Particle> particles = new ArrayList<Particle>(); // List containing all of the particles
    
    public Branch[] children =  new Branch[4];
    public Branch parent = null; 
    
    public Branch(Rectangle2D bounds, double LEVEL, Branch parent){
        
        this.bounds = bounds;
        this.level = LEVEL;
        this. parent = parent;
        this.centerX = bounds.getCenterX();
        this.centerY = bounds.getCenterY();
    }

    public void addPoint(Particle p){
        if(bounds.contains(new Point.Double(p.currentPos.x, p.currentPos.y))){
            if(particles.size() < MAXIMUM_BODIES){
                particles.add(p);
                p.deepestBranch = this;
            }else{
                if(level <= MAXIMUMLEVEL){
                    if(children[0] == null){
                        subDivide();
                        for(Branch child : children){
                            for(Particle part : particles){
                                child.addPoint(part);
                            }
                        }
                    }
                    for(Branch child : children){
                        child.addPoint(p);
                    }
                }else{
                    particles.add(p);
                    p.deepestBranch = this;
                }                
            }
        }
    }

    public void subDivide(){
        double w = bounds.getWidth()/2;
        double h = bounds.getHeight()/2;
        double x = bounds.getMinX();
        double y = bounds.getMinY();

        Rectangle2D nw = new Rectangle2D.Double(x, y, w, h);
        Rectangle2D ne = new Rectangle2D.Double(x+w, y, w, h);
        Rectangle2D sw = new Rectangle2D.Double(x, y+h, w, h);
        Rectangle2D se = new Rectangle2D.Double(x+w, y+h, w, h);

        children[0] = new Branch(nw, level + 1, this);
        children[1] = new Branch(ne, level + 1, this);
        children[2] = new Branch(sw, level + 1, this);
        children[3] = new Branch(se, level + 1, this);
    }

    public void displayBranch(Graphics g){
        // Quick definition of varibles to use with the G2D library
        Graphics2D graphics = (Graphics2D) g;
        
        graphics.setColor(Color.GREEN);
        graphics.fillOval((int)centerX - 5/2, (int)centerY - 5/2, 5, 5);

        graphics.setColor(this.color);
        graphics.draw(bounds);

        for(Branch subBranch : children){
            if(subBranch != null){
                subBranch.displayBranch(g);
            }
        }
       
    }

    public void updateBranch(){
        
        for(Particle p : particles){ //Removes Points that no longer exist
            if(!bounds.contains(new Point2D.Double(p.currentPos.x, p.currentPos.y))){
                particles.remove(p);
            }
        }

        if(particles.size() == 0){
            for(Branch child: children){
                child = null;
            }
        }      
    }


    public ArrayList<Particle> giveNeighbors(Particle p){
        if(this.parent != null){
            if(this.parent.parent != null){
                return this.parent.parent.particles;
            }
            return this.parent.particles;
        }
        return this.particles;
    }
}