package de.termitehuegel.labyrinth.labyrinth;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class LabyrinthSolution {

    private final Labyrinth labyrinth;
    private final List<GraphVertex<LabyrinthLocation>> solutionPath;

    public boolean isSolvable() {
        return solutionPath != null;
    }
}
