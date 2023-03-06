package de.termitehuegel.labyrinth.graph;

import de.termitehuegel.labyrinth.labyrinth.solver.dijkstra.DijkstraEvaluation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
public class GraphVertex<T> {

    @Getter
    private final @NonNull T value;
    @Getter
    private final Collection<GraphVertex<T>> neighbours = new HashSet<>();

    public void addNeighbour(@NonNull GraphVertex<T> neighbour) {
        if (neighbour == this || neighbours.contains(neighbour)) {
            return;
        }
        neighbours.add(neighbour);
        neighbour.addNeighbour(this);
    }
}
