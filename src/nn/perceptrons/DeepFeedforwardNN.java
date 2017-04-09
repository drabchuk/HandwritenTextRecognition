package nn.perceptrons;

import static nn.algebra.Alg.*;

import nn.NeuralNetwork;

import java.io.File;
import java.io.IOException;

/**
 * Created by Denis on 30.03.2017.
 */
public class DeepFeedforwardNN extends NeuralNetwork {

    private int depth;
    private int[] layerSizes;
    private double[][][] theta;

    public DeepFeedforwardNN(int depth, int[] layerSizes, double[][][] theta) {
        this.depth = depth;
        this.layerSizes = layerSizes;
        this.theta = theta;
    }

    @Override
    public double[] predict(double[] features) {
        double[] a = features;
        for (int i = 0; i < depth - 1; i++) {
            a = addForwardNum(1.0, features);
            double[] z = mult(theta[i], a);
            a = sigmoid(z);
        }
        return a;
    }

    public void saveWeights(File file) throws IOException {

    }

}
