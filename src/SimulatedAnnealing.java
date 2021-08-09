import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {

    private int[][] world;
    private AStar.Point currentState, startState, objective;
    private boolean doneFlag = false;
    private float temperature = 10;
    private float annealing;

    public SimulatedAnnealing(int[][] world, AStar.Point state, AStar.Point objective, float annealing) {
        this.world = world;
        this.objective = objective;
        startState = state;
        currentState = state;
        this.annealing = annealing;
    }

    public float hFunction(AStar.Point state) {
        int dX = Math.abs(objective.x - state.x);
        int dY = Math.abs(objective.y - state.y);
        return (float)Math.sqrt(dX*dX + dY*dY);
    }

    public ArrayList<AStar.Point> findNeighbours(AStar.Point state) {
        AStar.Point move;
        ArrayList<AStar.Point> neighbours = new ArrayList<>();
        if(currentState.x - 1 >= 0 && world[currentState.y][currentState.x - 1] == 0) {
            move = new AStar.Point(currentState.x - 1, currentState.y);
            neighbours.add(move);
        }
        if(currentState.x + 1 < world[0].length && world[currentState.y][currentState.x + 1] == 0) {
            move = new AStar.Point(currentState.x + 1, currentState.y);
            neighbours.add(move);
        }
        if(currentState.y - 1 >= 0 && world[currentState.y - 1][currentState.x] == 0) {
            move = new AStar.Point(currentState.x, currentState.y - 1);
            neighbours.add(move);
        }
        if(currentState.y + 1 < world.length && world[currentState.y + 1][currentState.x] == 0) {
            move = new AStar.Point(currentState.x, currentState.y + 1);
            neighbours.add(move);
        }
        return neighbours;
    }

    public boolean applyNextMove() {
        if(temperature > 0) {
            ArrayList<AStar.Point> neighbours = findNeighbours(currentState);

            Random r = new Random();
            int selected = (int)Math.floor(neighbours.size()*r.nextFloat());
            AStar.Point selectedNeighbour = neighbours.get(selected);

            if(hFunction(selectedNeighbour) < hFunction(currentState))
                currentState = selectedNeighbour;
            else {
                float delta = hFunction(selectedNeighbour) - hFunction(currentState);
                if(r.nextFloat() < Math.pow(Math.E, -delta/temperature))
                    currentState = selectedNeighbour;
            }

            System.out.println(temperature);
            temperature -= annealing;

            if(currentState.equals(objective)) {
                temperature = 0;
                System.out.println("Objective reached");
                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isCompleted() {
        return currentState.equals(objective);
    }

    public AStar.Point getCurrentState() {
        return currentState;
    }

    public AStar.Point getStartState() {
        return startState;
    }

    public AStar.Point getObjective() {
        return objective;
    }

    public void reset() {
        this.currentState = startState;
        this.temperature = 10;
    }

}
