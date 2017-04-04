package nn.algebra;

import java.util.Random;

/**
 * Created by Kolia on 30.10.2016.
 */
public abstract class Alg {



    public static double[][] mult(double[][] a, double[][] b) throws IllegalArgumentException{
        int h = a.length;
        int w = b[0].length;
        int la = a[0].length;
        int lb = b.length;
        double[][] res = new double[h][w];
        if (la != lb) {
            throw new IllegalArgumentException("Matrices not multipliable!");
        }
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sum = 0.0;
                for (int k = 0; k < la; k++) {
                    sum += a[i][k] * b[k][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    public static double[][] mult(double[][] a, double[][] b, String transpose) throws IllegalArgumentException{
        int h = a.length;
        int w = b.length;
        int la = a[0].length;
        int lb = b[0].length;
        double[][] res = new double[h][w];
        if (la != lb) {
            throw new IllegalArgumentException("Matrices not multipliable!");
        }
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sum = 0.0;
                for (int k = 0; k < la; k++) {
                    sum += a[i][k] * b[j][k];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    public static double[][] mult(double[][] a, String transpose, double[][] b) throws IllegalArgumentException{
        int h = a[0].length;
        int w = b[0].length;
        int la = a.length;
        int lb = b.length;
        double[][] res = new double[h][w];
        if (la != lb) {
            throw new IllegalArgumentException("Matrices not multipliable!");
        }
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sum = 0.0;
                for (int k = 0; k < la; k++) {
                    sum += a[k][i] * b[k][j];
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    public static double[][] dotMult(double a, double[][] b) throws IllegalArgumentException{
        int h = b.length;
        int w = b[0].length;
        double[][] res = new double[h][w];
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                res[i][j] = a * b[i][j];
            }
        }
        return res;
    }

    public static double[][] dotDiv(double a, double[][] b) throws IllegalArgumentException{
        int h = b.length;
        int w = b[0].length;
        double[][] res = new double[h][w];
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                res[i][j] = a / b[i][j];
            }
        }
        return res;
    }

    public static double[][] dotDiv(double[][] a, double b) throws IllegalArgumentException{
        int h = a.length;
        int w = a[0].length;
        double[][] res = new double[h][w];
        double sum;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                res[i][j] = a[i][j] / b;
            }
        }
        return res;
    }

    public static double[] dotDiv(double[] a, double b) throws IllegalArgumentException{
        int h = a.length;
        double[] res = new double[h];
        for (int i = 0; i < h; i++) {
                res[i] = a[i] / b;
        }
        return res;
    }

    public static double[] mult(double[][] a, double[] b) throws IllegalArgumentException{
        int h = a[0].length;
        int la = a.length;
        int lb = b.length;
        double[] res = new double[h];
        if (la != lb) {
            throw new IllegalArgumentException("Matrices not multipliable!");
        }
        double sum;
        for (int i = 0; i < h; i++) {
                sum = 0.0;
                for (int k = 0; k < la; k++) {
                    sum += a[k][i] * b[k];
                }
                res[i] = sum;
        }
        return res;
    }

    public static double[][] dotMult(double[][] a, double[][] b) throws IllegalArgumentException{
        int ha = a.length;
        int wa = a[0].length;
        int hb = b.length;
        int wb = b[0].length;
        double[][] res = new double[ha][wa];
        if (ha != hb || wa != wb) {
            throw new IllegalArgumentException("Matrices not multipliable!");
        }
        double sum;
        for (int i = 0; i < ha; i++) {
            for (int j = 0; j < wa; j++) {
                res[i][j] = a[i][j] * b[i][j];
            }
        }
        return res;
    }


    public static double[][] sum(double[][] a, double[][] b) throws IllegalArgumentException{
        int ha = a.length;
        int wb = b[0].length;
        int wa = a[0].length;
        int hb = b.length;
        double[][] res = new double[ha][wa];
        if (ha != hb || wa != wb) {
            throw new IllegalArgumentException("Matrices sizes not equals!");
        }
        for (int i = 0; i < ha; i++) {
            for (int j = 0; j < wa; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    public static double sum(double[] a) {
        int l = a.length;
        double s = 0.0;
        for (int i = 0; i < l; i++) {
            s += a[i];
        }
        return s;
    }

    public static double[] sum(double[][] a) {
        int h = a.length;
        int w = a[0].length;
        double[] s = new double[h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                s[i] += a[i][j];
            }
        }
        return s;
    }

    public static double[][] randInitForWeights(int h, int w) {
        final double div = Math.sqrt(6.0) / Math.sqrt(h + w);
        double[][] res = new double[h][w];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                res[i][j] = rand.nextDouble() * 2.0 * div - div;
            }
        }
        return res;
    }

    public static double[][] sigmoid(double[][] a) {
        int h = a.length;
        int w = a[0].length;
        double[][] res = new double[h][w];
        for (int i = 0; i < h; i++) {
            res[i] = sigmoid(a[i]);
        }
        return res;
    }

    public static double[] sigmoid(double[] a) {
        int l = a.length;
        double[] res = new double[l];
        for (int i = 0; i < l; i++) {
            res[i] = sigmoid(a[i]);
        }
        return res;
    }

    public static double sigmoid(double a) {
        return 1.0 / (1.0 + Math.exp(-a));
    }

    public static double[][] log(double[][] a) {
        int h = a.length;
        int w = a[0].length;
        double[][] res = new double[h][w];
        for (int i = 0; i < h; i++) {
            res[i] = log(a[i]);
        }
        return res;
    }

    public static double[][] sub(double[][] a, double[][] b) throws IllegalArgumentException{
        int ha = a.length;
        int wb = b[0].length;
        int wa = a[0].length;
        int hb = b.length;
        double[][] res = new double[ha][wa];
        if (ha != hb || wa != wb) {
            throw new IllegalArgumentException("Matrices sizes not equals!");
        }
        for (int i = 0; i < ha; i++) {
            for (int j = 0; j < wa; j++) {
                res[i][j] = a[i][j] - b[i][j];
            }
        }
        return res;
    }

    public static double[][] sub(double a, double[][] b) throws IllegalArgumentException{
        int w = b[0].length;
        int h = b.length;
        double[][] res = new double[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                res[i][j] = a - b[i][j];
            }
        }
        return res;
    }

    public static double[] log(double[] a) {
        int l = a.length;
        double[] res = new double[l];
        for (int i = 0; i < l; i++) {
            res[i] = log(a[i]);
        }
        return res;
    }

    public static double log(double a) {
        return Math.log(a);
    }

}
