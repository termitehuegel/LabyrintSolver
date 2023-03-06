package de.termitehuegel.labyrinth.labyrinth.solver.astar;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.solver.dijkstra.DijkstraEvaluation;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class AStarEvaluation implements Comparable<AStarEvaluation> {

    @Setter
    private int distance;
    @Getter
    private final int heuristicDistance;
    private final @NonNull GraphVertex<LabyrinthLocation> vertex;

    public AStarEvaluation(@NonNull GraphVertex<LabyrinthLocation> vertex, int heuristicDistance, int distance) {
        this.distance = distance;
        this.heuristicDistance = heuristicDistance;
        this.vertex = vertex;
    }


    @Override
    public int compareTo(AStarEvaluation o) {
        return heuristicDistance+distance - o.getDistance() - o.getHeuristicDistance();
    }
}
