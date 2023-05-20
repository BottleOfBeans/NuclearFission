import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Creates mouse input listener for panel.
 */
public class MouseInput implements MouseListener
{
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        double xPos = e.getX() / GameWindow.zoomX;
        double yPos = e.getY() / GameWindow.zoomY;
        
        Particle part = new Particle(new Vector2(xPos, yPos),new Vector2(3,0), 4);
        GameWindow.particles.add(part);    
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }
}