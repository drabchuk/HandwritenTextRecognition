package nn.perceptrons;

import nn.NeuralNetwork;

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
        return new double[0];
    }

}
