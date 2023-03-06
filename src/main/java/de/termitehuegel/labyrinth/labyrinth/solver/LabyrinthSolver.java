package de.termitehuegel.labyrinth.labyrinth.solver;

import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;

public interface LabyrinthSolver {

    LabyrinthSolution solve(Labyrinth labyrinth);
}
