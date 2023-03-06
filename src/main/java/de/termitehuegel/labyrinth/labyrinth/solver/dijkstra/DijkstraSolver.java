package de.termitehuegel.labyrinth.labyrinth.solver.dijkstra;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.labyrinth.solver.AbstractSolver;
import lombok.NonNull;

import java.util.*;

public class DijkstraSolver extends AbstractSolver {
    @Override
    public LabyrinthSolution solve(@NonNull Labyrinth labyrinth) {
        final Map<GraphVertex<LabyrinthLocation>, GraphVertex<LabyrinthLocation>> predecessorMap = new HashMap<>();
        final Queue<DijkstraEvaluation> queue = new PriorityQueue<>();
        final Map<GraphVertex<LabyrinthLocation>, DijkstraEvaluation> evaluationMap = new HashMap<>();
        final Set<GraphVertex<LabyrinthLocation>> completed = new HashSet<>();

        queue.offer(new DijkstraEvaluation(labyrinth.getStart(), 0));

        while (!queue.isEmpty()) {
            final DijkstraEvaluation dijkstraEvaluation =  queue.poll();
            completed.add(dijkstraEvaluation.getVertex());
            if (dijkstraEvaluation.getVertex().equals(labyrinth.getEnd())) {
                return new LabyrinthSolution(labyrinth, getPath(predecessorMap, labyrinth.getEnd()));
            }

            for (GraphVertex<LabyrinthLocation> vertex : dijkstraEvaluation.getVertex().getNeighbours()) {
                if (completed.contains(vertex)) {
                    continue;
                }
                final int distance = getDistance(vertex, dijkstraEvaluation.getVertex());

                if (evaluationMap.containsKey(vertex) && dijkstraEvaluation.getDistance()+distance >= evaluationMap.get(vertex).getDistance()) {
                    continue;
                }

                if (evaluationMap.containsKey(vertex)) {
                    evaluationMap.get(vertex).setDistance(dijkstraEvaluation.getDistance()+distance);
                } else {
                    evaluationMap.put(vertex, new DijkstraEvaluation(vertex, dijkstraEvaluation.getDistance()+distance));
                    queue.offer(evaluationMap.get(vertex));
                }
                predecessorMap.put(vertex, dijkstraEvaluation.getVertex());
            }
        }
        return new LabyrinthSolution(labyrinth, null);
    }

    private int getDistance(@NonNull GraphVertex<LabyrinthLocation> start, @NonNull GraphVertex<LabyrinthLocation> end) {
        if (start.getValue().getX() == end.getValue().getX()) {
            if (start.getValue().getY() - end.getValue().getY() < 0) {
                return getDistance(end, start);
            }
            return start.getValue().getY() - end.getValue().getY();
        } else if (start.getValue().getY() == end.getValue().getY()) {
            if (start.getValue().getX() - end.getValue().getX() < 0) {
                return getDistance(end, start);
            }
            return start.getValue().getX() - end.getValue().getX();
        }
        throw new IllegalArgumentException("start and end need to only differ in one dimension");
    }

}
