import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Panel extends JPanel {

    private int[][] world = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    //private AStar mazeSolver;
    private SimulatedAnnealing mazeSolver;
    private int blockSize = 10;
    private int width, height;
    private int prevMouseX, prevMouseY;

    public Panel() {
        int l = 100;
        world = new int[l][l];
        Random r = new Random();
        for(int j = 0; j < l; j++)
            for(int i = 0; i < l; i++) {
                if(i != 0 || j != 0)
                    if (r.nextFloat() > 0.7)
                        world[j][i] = 1;
                    else
                        world[j][i] = 0;
            }

        AStar.Point obj = new AStar.Point(0, 0);

        boolean flag = false;
        for(int j = l-1; j >= 0 ; j--) {
            for (int i = l - 1; i >= 0; i--)
                if (world[j][i] == 0) {
                    obj.x = i;
                    obj.y = j;
                    flag = true;
                    break;
                }
            if (flag)
                break;
        }


        width = blockSize * world[0].length;
        height = blockSize * world.length;
        setSize(width, height);
        setPreferredSize(new Dimension(width+1, height+1));
        //mazeSolver = new AStar(world, new AStar.Point(0, 0), obj);
        mazeSolver = new SimulatedAnnealing(world, new AStar.Point(0, 0), obj, 0.001f);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.black);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);
        g2d.fillRect(mazeSolver.getStartState().x * blockSize, mazeSolver.getStartState().y * blockSize, blockSize, blockSize);

        g2d.setColor(Color.green);
        g2d.fillRect(mazeSolver.getObjective().x * blockSize, mazeSolver.getObjective().y * blockSize, blockSize, blockSize);

        g2d.setColor(Color.blue);
        AStar.Point p = mazeSolver.getCurrentState();
        g2d.fillRect(p.x*blockSize, p.y*blockSize, blockSize, blockSize);

        g2d.setColor(Color.white);

        for(int j = 0; j < height / blockSize; j++)
            for(int i = 0; i < width / blockSize; i++)
                if(world[j][i] == 1)
                    g2d.drawRect(i*blockSize, j*blockSize, blockSize, blockSize);
    }

    public void clear() {
        for(int j = 0; j < world.length; j++)
            for(int i = 0; i < world[0].length; i++)
                world[j][i] = 0;
    }

    public void reset() {
        mazeSolver.reset();
    }

    public void setBlock(int x, int y, boolean erase) {
        x = (int)Math.floor(x/blockSize);
        y = (int)Math.floor(y/blockSize);
        if(x >= 0 && y >= 0 && x < world[0].length && y < world.length && (x != prevMouseX || y != prevMouseY)) {
            world[y][x] = erase ? 0 : 1;
            prevMouseX = x;
            prevMouseY = y;
        }
    }

    public boolean nextMove() {
        return mazeSolver.applyNextMove();
    }

    public boolean isCompleted() {
        return mazeSolver.isCompleted();
    }

}
