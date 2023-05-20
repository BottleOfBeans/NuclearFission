

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

    @Override

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
        double initialXPos = e.getX() * GameWindow.zoomXOffset;
        double initialYPos = e.getY() * GameWindow.zoomYOffset;
        
        Particle initial_particle = new Particle(new Vector2(initialXPos, initialYPos),new Vector2(1,0), 0);
        GameWindow.particles.add(initial_particle);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}