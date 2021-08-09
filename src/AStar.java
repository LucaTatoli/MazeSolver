import java.util.*;

public class AStar {

    private int[][] world;
    private Point startState;
    private Point currentState;
    private Point objective;
    private Map<Point, Float> frontiera;
    private HashMap<Point, Point> paths;
    private ArrayList<Point> visitati;
    private ArrayList<Point> route;
    private float currentCost = 0;
    private int counter = 0;
    private boolean doneFlag = false;

    public AStar(int[][] world, Point state, Point objective) {
        this.world = world;
        this.objective = objective;
        startState = state;
        currentState = state;
        frontiera = new HashMap<>();
        paths = new HashMap<>();
        visitati = new ArrayList<>();
        visitati.add(currentState);
        paths.put(currentState, currentState);
        currentCost = 0;
        addMovesToFrontiera();
        route = new ArrayList<>();
    }

    public float hFunction(Point state) {
        int dX = Math.abs(objective.x - state.x);
        int dY = Math.abs(objective.y - state.y);
        return (float)Math.sqrt(dX*dX + dY*dY);
    }

    public void addMovesToFrontiera() {
        Point move;
        if(currentState.x - 1 >= 0 && world[currentState.y][currentState.x - 1] == 0) {
            move = new Point(currentState.x - 1, currentState.y);
            if(!visitati.contains(move))
                addToFrontiera(move);
        }
        if(currentState.x + 1 < world[0].length && world[currentState.y][currentState.x + 1] == 0) {
            move = new Point(currentState.x + 1, currentState.y);
            if(!visitati.contains(move))
                addToFrontiera(move);
        }
        if(currentState.y - 1 >= 0 && world[currentState.y - 1][currentState.x] == 0) {
            move = new Point(currentState.x, currentState.y - 1);
            if(!visitati.contains(move))
                addToFrontiera(move);
        }
        if(currentState.y + 1 < world.length && world[currentState.y + 1][currentState.x] == 0) {
            move = new Point(currentState.x, currentState.y + 1);
            if(!visitati.contains(move))
                addToFrontiera(move);
        }
    }

    public void addToFrontiera(Point move) {
        if(frontiera.containsKey(move))
            if(frontiera.get(move) < currentCost + hFunction(move))
                return;
        frontiera.put(move, currentCost + hFunction(move));
        paths.put(move, currentState);
    }

    public boolean applyNextMove() {
        if(!doneFlag) {
            Point move = currentState;
            float currentHValue = Float.MAX_VALUE;

            for (Point p : frontiera.keySet()) {
                if (currentHValue > frontiera.get(p)) {
                    move = p;
                    currentHValue = frontiera.get(p);
                }
            }

            frontiera.remove(move);
            System.out.println("Stato Corrente: " + currentState.x + ", " + currentState.y);
            currentState = move;
            visitati.add(currentState);
            currentCost++;
            addMovesToFrontiera();
            if(frontiera.size() == 0) {
                doneFlag = true;
                counter = Integer.MAX_VALUE;
                System.out.println("Non esiste alcun percorso!");
            }
            System.out.println("Move: " + move.x + ", " + move.y);
            System.out.println("Frontiera: " + frontiera.keySet().size());
            if(currentState.equals(objective))
                doneFlag = true;
            return true;
        }

        if(route.size() == 0) {
            Point father = paths.get(currentState);
            route.add(currentState);
            while (!father.equals(paths.get(father))) {
                route.add(father);
                father = paths.get(father);
            }
            route.add(father);

            currentState = route.get(route.size() - 1);
            return true;
        } else if(counter < route.size() - 1) {
            counter++;
            currentState = route.get(route.size() - 1 - counter);
            return true;
        }
        return false;
    }

    public Point getCurrentState() {
        return currentState;
    }

    public Point getStartState() {
        return startState;
    }

    public Point getObjective() {
        return objective;
    }

    public void reset() {
        doneFlag = false;
        frontiera.clear();
        visitati.clear();
        paths.clear();
        route.clear();
        currentState = startState;
        counter = 0;
        currentCost = 0;

        visitati.add(currentState);
        paths.put(currentState, currentState);
        addMovesToFrontiera();
    }

    public boolean isCompleted() {
        return doneFlag;
    }


    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
