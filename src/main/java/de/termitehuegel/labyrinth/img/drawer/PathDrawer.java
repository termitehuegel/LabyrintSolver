package de.termitehuegel.labyrinth.img.drawer;

import de.termitehuegel.labyrinth.graph.GraphVertex;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import lombok.NonNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PathDrawer {

    public static void drawSolutionPath(@NonNull LabyrinthSolution solution, @NonNull BufferedImage img, @NonNull Color color) {
         for (int i=1; i<solution.getSolutionPath().size(); i++) {
             drawEdge(img, color, solution.getSolutionPath().get(i-1), solution.getSolutionPath().get(i));
         }
    }

    private static void drawEdge(@NonNull BufferedImage img, @NonNull Color color,
                                 @NonNull GraphVertex<LabyrinthLocation> start,
                                 @NonNull GraphVertex<LabyrinthLocation> end) {
        final int xDirection = getXDirection(start, end);
        final int yDirection = getYDirection(start, end);
        if (yDirection != 0 && xDirection != 0) {
            throw new IllegalArgumentException("Vertices need to only differ in one dimension.");
        }
        int pointerX = start.getValue().getX();
        int pointerY = start.getValue().getY();
        while (pointerX != end.getValue().getX() || pointerY != end.getValue().getY()) {
            img.setRGB(pointerX, pointerY, color.getRGB());
            pointerX += xDirection;
            pointerY += yDirection;
        }
    }

    private static int getXDirection(@NonNull GraphVertex<LabyrinthLocation> start,
                                     @NonNull GraphVertex<LabyrinthLocation> end) {
        if (start.getValue().getX() > end.getValue().getX()) {
            return -1;
        } else if (start.getValue().getX() < end.getValue().getX()) {
            return 1;
        }
        return 0;
    }

    private static int getYDirection(@NonNull GraphVertex<LabyrinthLocation> start,
                                     @NonNull GraphVertex<LabyrinthLocation> end) {
        if (start.getValue().getY() > end.getValue().getY()) {
            return -1;
        } else if (start.getValue().getY() < end.getValue().getY()) {
            return 1;
        }
        return 0;
    }
}
