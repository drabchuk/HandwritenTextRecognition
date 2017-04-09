package nn.perceptrons;

import nn.NNTrainer;
import nn.NeuralNetwork;

import static nn.algebra.Alg.*;
import static nn.algebra.Alg.log;
import static nn.algebra.Alg.sub;

/**
 * Created by Denis on 04.04.2017.
 */
public class DFFNNTrainer extends NNTrainer {

    private int gradientDescentSteps = 10;
    private DeepFeedforwardNN nn;

    public DFFNNTrainer(DeepFeedforwardNN nn, double[][] tx, double[][] ty) {
        super(tx, ty);
        this.nn = nn;
    }

    @Override
    public void train() {

    }

    public double[][][] grad() {
        int depth = nn.getDepth();
        int[] layerSizes = nn.getLayerSizes();
        double[][][] grad = new double[depth][][];
        for (int i = 0; i < depth - 1; i++) {
            grad[i] = new double[layerSizes[i + 1]][layerSizes[i] + 1];
        }

        int m = tx.length;
        double[][][] za;
        double[][] z;
        double[][] a;
        double[][] delta = new double[depth][];
        for (int i = 0; i < m; i++) {
            za = nn.forwardPropagation(tx[i]);
            z = za[0];
            a = za[1];
            delta[depth - 1] = sub(a[depth - 1], ty[m]);
            for (int j = depth - 2; j > 0 ; j--) {
                delta[j] = mult(nn.getTheta()[j], delta[j + 1]);
            }
        }

        return null;
    }

    public double[] reshapeGrad() {
        return null;
    }

    public double[] reshapeTheta() {
        return null;
    }

    public void setTheta(double[] newTheta) {

    }

    private double cost() {
        int tdLen = ty.length;
        double[][] h = nn.predict(tx);
        return -(sum(sum(
                sum(
                        dotMult(ty, log(h))
                        , dotMult(sub(1.0, ty), log (sub(1.0, h))))
                )
        )
        ) / (double) tdLen;
    }

    @Override
    public void setAccuracy(double accuracy) {
        gradientDescentSteps = (int) accuracy;
    }

}
