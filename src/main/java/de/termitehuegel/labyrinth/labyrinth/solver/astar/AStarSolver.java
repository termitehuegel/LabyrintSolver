package de.termitehuegel.labyrinth.labyrinth.solver.astar;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.labyrinth.solver.AbstractSolver;
import de.termitehuegel.labyrinth.labyrinth.solver.dijkstra.DijkstraEvaluation;
import lombok.NonNull;

import java.util.*;

public class AStarSolver extends AbstractSolver {
    @Override
    public LabyrinthSolution solve(@NonNull Labyrinth labyrinth) {
        final Map<GraphVertex<LabyrinthLocation>, GraphVertex<LabyrinthLocation>> predecessorMap = new HashMap<>();
        final Queue<AStarEvaluation> queue = new PriorityQueue<>();
        final Map<GraphVertex<LabyrinthLocation>, AStarEvaluation> evaluationMap = new HashMap<>();
        final Set<GraphVertex<LabyrinthLocation>> completed = new HashSet<>();

        evaluationMap.put(labyrinth.getStart(), new AStarEvaluation(labyrinth.getStart(), 0, 0));

        queue.offer(evaluationMap.get(labyrinth.getStart()));

        while (!queue.isEmpty()) {
            final AStarEvaluation currentEvaluation = queue.poll();
            completed.add(currentEvaluation.getVertex());

            if (currentEvaluation.getVertex().equals(labyrinth.getEnd())) {
                return new LabyrinthSolution(labyrinth, getPath(predecessorMap, labyrinth.getEnd()));
            }

            currentEvaluation.getVertex().getNeighbours().stream()
                    .filter(vertex -> !completed.contains(vertex))
                    .forEach(vertex -> {
                        final int distance = getDistance(vertex, currentEvaluation.getVertex());
                        if (!evaluationMap.containsKey(vertex)) {
                            evaluationMap.put(vertex, new AStarEvaluation(vertex, getHeuristicDistance(vertex,
                                    labyrinth.getEnd()), distance+currentEvaluation.getDistance()));
                            predecessorMap.put(vertex, currentEvaluation.getVertex());
                            queue.add(evaluationMap.get(vertex));
                        } else {
                            if (evaluationMap.get(vertex).getDistance() > distance + currentEvaluation.getDistance()) {
                                evaluationMap.get(vertex).setDistance(distance + currentEvaluation.getDistance());
                                predecessorMap.put(vertex, currentEvaluation.getVertex());
                            }
                        }
                    });
        }


        return new LabyrinthSolution(labyrinth, null);
    }

    private int getHeuristicDistance(@NonNull GraphVertex<LabyrinthLocation> start, @NonNull GraphVertex<LabyrinthLocation> end) {
        final int xDistance = start.getValue().getX() - end.getValue().getX();
        final int yDistance = start.getValue().getY() - end.getValue().getY();

        return (int) Math.round(Math.sqrt(xDistance*xDistance + yDistance*yDistance));
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
