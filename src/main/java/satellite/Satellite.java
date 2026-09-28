package satellite;

import java.io.*;
import java.util.*;

public class Satellite {
    public static void main(String[] args) {
        try { solve("input.txt"); }
        catch (IOException e) { System.err.println("Error: " + e.getMessage()); }
    }

    static void solve(String file) throws IOException {
        try (Scanner in = new Scanner(new File(file))) {
            int rows = in.nextInt(), cols = in.nextInt();
            int[][] oldImage = readImage(in, rows, cols);
            int[][] newImage = readImage(in, rows, cols);
            printResult(oldImage, newImage);
        }
    }

    static int[][] readImage(Scanner in, int rows, int cols) {
        int[][] image = new int[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) image[r][c] = in.nextInt();
        return image;
    }

    static int findBoundary(int[][] oldImage, int[][] newImage,
                            boolean rows, boolean forward) {
        int limit = rows ? oldImage.length : oldImage[0].length;
        int index = forward ? 0 : limit - 1, step = forward ? 1 : -1;
        while (index >= 0 && index < limit && equalLine(oldImage, newImage, index, rows))
            index += step;
        return index;
    }

    static boolean equalLine(int[][] oldImage, int[][] newImage,
                             int index, boolean rows) {
        int limit = rows ? oldImage[0].length : oldImage.length;
        for (int i = 0; i < limit; i++)
            if ((rows ? oldImage[index][i] : oldImage[i][index]) !=
                    (rows ? newImage[index][i] : newImage[i][index])) return false;
        return true;
    }

    static void printResult(int[][] oldImage, int[][] newImage) {
        int x1 = findBoundary(oldImage, newImage, true, true);
        int x2 = findBoundary(oldImage, newImage, true, false);
        int y1 = findBoundary(oldImage, newImage, false, true);
        int y2 = findBoundary(oldImage, newImage, false, false);
        if (x1 > x2 || y1 > y2) System.out.println("The two images are the same");
        else System.out.println((x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
    }
}