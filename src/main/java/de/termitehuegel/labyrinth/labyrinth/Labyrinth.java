package de.termitehuegel.labyrinth.labyrinth;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Labyrinth {

    private final @NonNull GraphVertex<LabyrinthLocation> start;
    private final @NonNull GraphVertex<LabyrinthLocation> end;
}
