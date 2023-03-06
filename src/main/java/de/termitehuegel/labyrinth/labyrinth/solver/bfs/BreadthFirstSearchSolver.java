package de.termitehuegel.labyrinth.labyrinth.solver.bfs;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.labyrinth.solver.AbstractSolver;
import de.termitehuegel.labyrinth.labyrinth.solver.LabyrinthSolver;
import lombok.NonNull;

import java.util.*;

public class BreadthFirstSearchSolver extends AbstractSolver {

    @Override
    public LabyrinthSolution solve(@NonNull Labyrinth labyrinth) {
        final Map<GraphVertex<LabyrinthLocation>, GraphVertex<LabyrinthLocation>> predicessorMap = new HashMap<>();
        final Set<GraphVertex<LabyrinthLocation>> visited = new HashSet<>();
        final Queue<GraphVertex<LabyrinthLocation>> searchQueue = new ArrayDeque<>();

        searchQueue.add(labyrinth.getStart());

        while (!searchQueue.isEmpty()) {
            final GraphVertex<LabyrinthLocation> currentVertex = searchQueue.remove();
            visited.add(currentVertex);

            if (currentVertex.equals(labyrinth.getEnd())) {
                return new LabyrinthSolution(labyrinth, getPath(predicessorMap, labyrinth.getEnd()));
            }

            currentVertex.getNeighbours().stream()
                    .filter(vertex -> !visited.contains(vertex))
                    .forEach(vertex -> {
                        predicessorMap.put(vertex, currentVertex);
                        searchQueue.add(vertex);
                    });
        }
        return new LabyrinthSolution(labyrinth, null);
   }

}
