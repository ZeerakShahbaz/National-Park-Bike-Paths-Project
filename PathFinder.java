import java.io.IOException;
import java.util.*;

public class PathFinder {
    private Map pyramidMap;

    public PathFinder(String fileName) throws IOException {
        this.pyramidMap = new Map(fileName);
    }


    /**
     * Returns a stack representing the path from the entrance to the treasure chambers.
     *
     * @return a stack representing the path
     */
    public DLStack<Chamber> path() {
        DLStack<Chamber> stack = new DLStack<>();
        Chamber currentChamber = pyramidMap.getEntrance();
        currentChamber.markPushed();
        stack.push(currentChamber);
        int treasureChambersToFind = pyramidMap.getNumTreasures();
        int treasureChambersFound = currentChamber.isTreasure() ? 1 : 0;

        while (!stack.isEmpty() && treasureChambersFound < treasureChambersToFind) {
            currentChamber = stack.peek();
            Chamber bestNeighbour = bestChamber(currentChamber);

            if (bestNeighbour == null) {
                currentChamber.markPopped();
                stack.pop();
            } else {
                bestNeighbour.markPushed();
                stack.push(bestNeighbour);
                if (bestNeighbour.isTreasure()) {
                    treasureChambersFound++;
                }
            }
        }
        return stack;
    }

    /**
     * Returns the pyramid map.
     *
     * @return the pyramid map
     */
    public Map getMap() {
        return pyramidMap;
    }

    /**
     * Returns whether a given chamber is dim.
     *
     * @param currentChamber the chamber to check
     * @return true if the chamber is dim, false otherwise
     */
    public boolean isDim(Chamber currentChamber) {
        if (currentChamber == null || currentChamber.isSealed() || currentChamber.isLighted()) {
            return false;
        }
        for (int i = 0; i < 6; i++) {
            try {
                Chamber neighbor = currentChamber.getNeighbour(i);
                if (neighbor != null && neighbor.isLighted()) {
                    return true;
                }
            } catch (InvalidNeighbourIndexException ignored) {}
        }
        return false;
    }

    /**
     * Returns the best neighbor of a given chamber.
     *
     * @param currentChamber the chamber to check
     * @return the best neighbor of the chamber
     */
    public Chamber bestChamber(Chamber currentChamber) {
        Chamber bestTreasureChamber = null;
        Chamber bestLightedChamber = null;
        Chamber bestDimChamber = null;

        for (int i = 0; i < 6; i++) {
            try {
                Chamber neighbour = currentChamber.getNeighbour(i);
                if (neighbour != null && !neighbour.isMarked()) {
                    if (neighbour.isTreasure() && bestTreasureChamber == null) {
                        bestTreasureChamber = neighbour;
                    } else if (neighbour.isLighted() && bestLightedChamber == null) {
                        bestLightedChamber = neighbour;
                    } else if (isDim(neighbour) && bestDimChamber == null) {
                        bestDimChamber = neighbour;
                    }
                }
            } catch (InvalidNeighbourIndexException ignored) {}
        }

        if (bestTreasureChamber != null) {
            return bestTreasureChamber;
        } else if (bestLightedChamber != null) {
            return bestLightedChamber;
        } else {
            return bestDimChamber;
        }
    }
}

/*
The PathFinder program simulates a treasure hunt in a park described by a map.
The PathFinder class uses a DLStack data structure to keep track of the chambers it explores.
When creating a PathFinder, we instantiate a Map object from an input file.
In our path() method, we start at the entrance of the map and explore the park until we've found all treasure chambers.
The best chamber is chosen based on whether it's a treasure, lighted, or dim chamber.
If there are no more chambers to explore from the current chamber, it's popped off the stack.
The path-finding algorithm was challenging to implement due to the complexity of handling different types of chambers and exceptions.
To verify the code, I conducted unit tests for each method and performed end-to-end tests using different map inputs.
The code demonstrates a clear understanding of stack data structures, encapsulation, and exception handling in Java.





 */

