package de.termitehuegel.labyrinth;

import de.termitehuegel.labyrinth.img.drawer.PathDrawer;
import de.termitehuegel.labyrinth.img.parser.ImageParser;
import de.termitehuegel.labyrinth.labyrinth.Labyrinth;
import de.termitehuegel.labyrinth.labyrinth.LabyrinthSolution;
import de.termitehuegel.labyrinth.labyrinth.solver.LabyrinthSolver;
import de.termitehuegel.labyrinth.labyrinth.solver.astar.AStarSolver;
import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(@NonNull String[] args) {
        if (args.length != 2) {
            System.out.println("Arg1: labyrinthPath.bmp, Arg2: SolutionPath.bmp");
            return;
        }
        final String IMAGE_PATH = args[0];
        final String IMAGE_RESULT_PATH = args[1];
        final Color PATH_COLOR = Color.WHITE;
        final Color SOLUTION_PATH_COLOR = Color.RED;
        final String IMAGE_FORMAT = "bmp";

        try {
            final BufferedImage img = ImageIO.read(new File(IMAGE_PATH));
            final Labyrinth labyrinth = ImageParser.parseLabyrinthImage(img, PATH_COLOR);
            final LabyrinthSolver solver = new AStarSolver();
            final LabyrinthSolution solution = solver.solve(labyrinth);

            if (solution.isSolvable()) {
                PathDrawer.drawSolutionPath(solution, img, SOLUTION_PATH_COLOR);
                ImageIO.write(img, IMAGE_FORMAT, new File(IMAGE_RESULT_PATH));

                int count = 0;
                for (int x=0; x<img.getWidth(); x++) {
                    for (int y = 0; y < img.getHeight(); y++) {
                        if (img.getRGB(x, y) == SOLUTION_PATH_COLOR.getRGB()) {
                            count++;
                        }
                    }
                }
                System.out.println("Found Path with: " + (count+1) + " steps.");
            } else {
                System.out.println("Can't solve labyrinth.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
