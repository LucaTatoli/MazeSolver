import javax.swing.*;
import java.awt.event.*;

public class MazeSolverFrame extends JFrame {

    private Panel panel;
    private boolean startFlag = false;
    private boolean running = true;

    public MazeSolverFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState() == WindowEvent.WINDOW_CLOSING)
                    running = false;
            }
        });
        panel = new Panel();
        add(panel);
        pack();
        setResizable(false);
        setVisible(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'x')
                    startFlag = true;
                else if(e.getKeyChar() == 'c')
                    panel.clear();
                else if(e.getKeyChar() == 'r') {
                    startFlag = false;
                    panel.reset();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e))
                    panel.setBlock(e.getX(), e.getY(), false);
                else
                    panel.setBlock(e.getX(), e.getY(), true);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }

    public boolean nextMove() {
        if(!startFlag)
            return true;
        return panel.nextMove();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isCompleted() {
        return panel.isCompleted();
    }

    public void reset() { panel.reset(); }


}
