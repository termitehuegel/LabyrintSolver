package de.termitehuegel.labyrinth.img.parser;

import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthLocation;
import de.termitehuegel.labyrinth.graph.GraphVertex;
import lombok.NonNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageParser {

    public static Labyrinth parseLabyrinthImage(@NonNull BufferedImage img, @NonNull Color pathColor) {

        final Labyrinth labyrinth = getValidLabyrinth(img, pathColor);
        final GraphVertex<LabyrinthLocation>[] lastConnectedUp = new GraphVertex[img.getWidth()];


        lastConnectedUp[labyrinth.getStart().getValue().getX()] = labyrinth.getStart();

        for (int y=1; y<img.getHeight()-1; y++) {
            GraphVertex<LabyrinthLocation> lastConnectedLeft = null;
            for (int x=0; x<img.getWidth(); x++) {

                lastConnectedLeft = parseCoordinate(img, pathColor, x, y, lastConnectedLeft, lastConnectedUp);
            }
        }

        final int endX = labyrinth.getEnd().getValue().getX();
        final int endY = labyrinth.getEnd().getValue().getY();
        if (lastConnectedUp[endX] != null) {
            labyrinth.getEnd().addNeighbour(lastConnectedUp[endX]);
        }

        return labyrinth;
    }

    /**
     * @param img Image to be parsed
     * @param pathColor color of a path
     * @param x x-Coordinate of the tile
     * @param y y-Coordinate of the tile
     * @param lastConnectedLeft last connected node to the left
     * @param lastConnectedUp last connected node up
     * @return returns a new lastConnectedLeft for the next tile
     */
    private static GraphVertex<LabyrinthLocation> parseCoordinate(@NonNull BufferedImage img, @NonNull Color pathColor,
                                                                  int x, int y,
                                                                  GraphVertex<LabyrinthLocation> lastConnectedLeft,
                                                                  GraphVertex<LabyrinthLocation>[] lastConnectedUp) {
        if (img.getRGB(x, y) != pathColor.getRGB()) {
            lastConnectedUp[x] = null;
            return null;
        }

        if (((x > 0 && img.getRGB(x-1, y) == pathColor.getRGB()) || (x < img.getWidth()-1 && img.getRGB(x+1, y) == pathColor.getRGB())) &&
                (img.getRGB(x, y-1) == pathColor.getRGB() || img.getRGB(x, y+1) == pathColor.getRGB())) {

            final GraphVertex<LabyrinthLocation> result = new GraphVertex<>(new LabyrinthLocation(x, y));

            if (lastConnectedLeft != null) {
                result.addNeighbour(lastConnectedLeft);
            }
            if (lastConnectedUp[x] != null) {
                result.addNeighbour(lastConnectedUp[x]);
            }

            lastConnectedUp[x] = result;
            return result;
        }

        return lastConnectedLeft;
    }

    private static Optional<GraphVertex<LabyrinthLocation>> parseStartAndEnd(@NonNull BufferedImage img,
                                                                             @NonNull Color pathColor,
                                                                             int x, int y) {
        if (pathColor.getRGB() !=  img.getRGB(x, y)) {
            return Optional.empty();
        }
        return Optional.of(new GraphVertex<>(new LabyrinthLocation(x, y)));
    }

    private static Labyrinth getValidLabyrinth(@NonNull BufferedImage img, @NonNull Color pathColor) {
        GraphVertex<LabyrinthLocation> start = null;
        GraphVertex<LabyrinthLocation> end = null;
        for (int x=0; x<img.getWidth(); x++) {
            Optional<GraphVertex<LabyrinthLocation>> startNode =  parseStartAndEnd(img, pathColor, x, 0);

            if (startNode.isPresent() && start != null) {
                throw new IllegalArgumentException("The Labyrinth has to many starts.");
            } else if (startNode.isPresent()) {
                start = startNode.get();
            }

            Optional<GraphVertex<LabyrinthLocation>> endNode = parseStartAndEnd(img, pathColor, x, img.getHeight()-1);

            if (endNode.isPresent() && end != null) {
                throw new IllegalArgumentException("The Labyrinth has to many ends.");
            } else if (endNode.isPresent()) {
                end = endNode.get();
            }
        }

        if (start == null || end == null) {
            throw new IllegalArgumentException("The Labyrinth needs to have a start and an end.");
        }
        return new Labyrinth(start, end);
    }
}
