package de.termitehuegel.labyrinth.labyrinth.solver;

import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.graph.GraphVertex;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractSolver implements LabyrinthSolver {
    public abstract LabyrinthSolution solve(Labyrinth labyrinth);

    protected List<GraphVertex<LabyrinthLocation>> getPath(final @NonNull Map<GraphVertex<LabyrinthLocation>, GraphVertex<LabyrinthLocation>>
                                                          predicessorMap, final @NonNull GraphVertex<LabyrinthLocation> pathEnd) {

        final List<GraphVertex<LabyrinthLocation>> path = new ArrayList<>();
        GraphVertex<LabyrinthLocation> currentNode = pathEnd;
        while (predicessorMap.containsKey(currentNode)) {
            path.add(currentNode);
            currentNode = predicessorMap.get(currentNode);
        }
        path.add(currentNode);
        return path;
    }
}
