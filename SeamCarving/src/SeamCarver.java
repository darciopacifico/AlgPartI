import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {


    private Picture picture;

    /**
     * create a seam carver object based on the given picture
     */
    public SeamCarver(Picture picture) {
        this.picture = picture;



    }

    /**
     * current picture
     */
    public Picture picture() {
        return null;
    }

    /**
     * width of current picture
     */
    public int width() {
        return this.picture.width();
    }

    /**
     * height of current picture
     */
    public int height() {
        return picture.height();
    }

    /**
     * energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 255 * 255 + 255 * 255 + 255 * 255;
        }

        return  squareGradient(picture.get(x-1, y), picture.get(x+1, y)) +
                squareGradient(picture.get(x, y-1), picture.get(x, y+1));
    }


    private double squareGradient(Color one, Color two) {
        int r = Math.abs(one.getRed() - two.getRed());
        int g = Math.abs(one.getGreen() - two.getGreen());
        int b = Math.abs(one.getBlue() - two.getBlue());

        return
        Math.pow(r,2) +
        Math.pow(g,2) +
        Math.pow(b,2);
    }


    /**
     * sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return null;
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        return null;
    }

    /**
     * remove horizontal seam from current picture
     */
    public void removeHorizontalSeam(int[] seam) {

    }

    /**
     * remove vertical seam from current picture
     */
    public void removeVerticalSeam(int[] seam) {

    }
}
