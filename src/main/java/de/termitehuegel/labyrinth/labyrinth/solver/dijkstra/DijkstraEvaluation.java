package de.termitehuegel.labyrinth.labyrinth.solver.dijkstra;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class DijkstraEvaluation implements Comparable<DijkstraEvaluation> {

    @Setter
    private int distance;
    private final @NonNull GraphVertex<LabyrinthLocation> vertex;

    public DijkstraEvaluation(@NonNull GraphVertex<LabyrinthLocation> vertex, int distance) {
        this.distance = distance;
        this.vertex = vertex;
    }

    @Override
    public int compareTo(DijkstraEvaluation o) {
        return distance - o.getDistance();
    }
}
