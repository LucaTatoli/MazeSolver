import java.util.concurrent.TimeUnit;

public class Maze {

    public static void main(String args[]) {
        MazeSolverFrame maze = new MazeSolverFrame();
        while(maze.isRunning()) {
            while (maze.nextMove()) {

                try {
                    if(maze.isCompleted())
                        TimeUnit.MILLISECONDS.sleep(50);
//                    else
//                        TimeUnit.MILLISECONDS.sleep(5);
                    maze.repaint();
                } catch (Exception e) {
                }
                ;
            }
            if(!maze.isCompleted())
                maze.reset();
        }
    }
}