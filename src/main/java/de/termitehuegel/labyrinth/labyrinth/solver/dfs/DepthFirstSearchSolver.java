package de.termitehuegel.labyrinth.labyrinth.solver.dfs;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.labyrinth.solver.AbstractSolver;
import de.termitehuegel.labyrinth.labyrinth.solver.LabyrinthSolver;
import lombok.NonNull;

import java.util.*;

public class DepthFirstSearchSolver extends AbstractSolver {
    @Override
    public LabyrinthSolution solve(@NonNull Labyrinth labyrinth) {
        final Map<GraphVertex<LabyrinthLocation>, GraphVertex<LabyrinthLocation>> predicessorMap = new HashMap<>();
        final Stack<GraphVertex<LabyrinthLocation>> vertexStack = new Stack<>();
        final Set<GraphVertex<LabyrinthLocation>> visited = new HashSet<>();

        vertexStack.add(labyrinth.getStart());

        while (!vertexStack.isEmpty()) {
            final GraphVertex<LabyrinthLocation> currentVertex = vertexStack.pop();
            visited.add(currentVertex);

            if (currentVertex.equals(labyrinth.getEnd())) {
                return new LabyrinthSolution(labyrinth, getPath(predicessorMap, labyrinth.getEnd()));
            }

            currentVertex.getNeighbours().stream()
                    .filter(vertex -> !visited.contains(vertex))
                    .forEach(vertex -> {
                        predicessorMap.put(vertex, currentVertex);
                        vertexStack.add(vertex);
                    });
        }
        return new LabyrinthSolution(labyrinth, null);
    }
}
