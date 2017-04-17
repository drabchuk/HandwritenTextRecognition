package nn.perceptrons;

import nn.NNTrainer;
import nn.NeuralNetwork;
import nn.optimazers.*;

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
        Function f = new NNFunc(this);
        OptimizationMethod1Order g = new FletcherReeves(this.reshapeTheta(), 10, 1e-4, 5);
        double[] min = g.optimize(f);
        this.setTheta(min);
    }

    public void train(double criteria, double stepLength, int maxSteps) {
        Function f = new NNFunc(this);
        OptimizationMethod1Order g = new FletcherReeves(this.reshapeTheta(), criteria, stepLength, maxSteps);
        double[] min = g.optimize(f);
        this.setTheta(min);
    }

    public void train(int maxSteps, double alfa) {
        Function f = new NNFunc(this);
        OptimizationMethod1Order g = new GradientDescent(this.reshapeTheta(), maxSteps, alfa);
        double[] min = g.optimize(f);
        this.setTheta(min);
    }

    public void trainTimeBound(int maxSteps, double alfa, int maxTime) {
        Function f = new NNFunc(this);
        OptimizationMethod1Order g = new GradientDescent(this.reshapeTheta(), maxSteps, alfa);
        double[] min = g.optimize(f, maxTime);
        this.setTheta(min);
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
        double[]d;
        for (int i = 0; i < m; i++) {
            za = nn.forwardPropagation(tx[i]);
            z = za[0];
            a = za[1];
            delta[depth - 1] = sub(a[depth - 1], ty[i]);
            for (int j = depth - 1; j > 0; j--) {
                d = mult(nn.getTheta()[j - 1], "t", delta[j]);
                d = removeForwardNum(d);
                delta[j - 1] = dotMult(d, sigmoidGrad(z[j - 1]));
                double[][] thetaGrad = mult(delta[j], a[j - 1], "t");
                grad[j - 1] = sum(grad[j - 1], thetaGrad);
            }

        }

        for (int j = 0; j < depth - 1; j++) {
            grad[j] = dotDiv(grad[j], m);
        }



        return grad;
    }

    public double[] reshapeGrad() {
        double[][][] grad = this.grad();
        int depth = nn.getDepth();
        int[] layerSizes = nn.getLayerSizes();
        int length = 0;
        for (int i = 0; i < depth - 1; i++) {
            length += (layerSizes[i] + 1) * layerSizes[i + 1];
        }
        double[] res = new double[length];
        int position = 0;
        for (int i = 0; i < depth - 1; i++) {
            for (int j = 0; j < layerSizes[i + 1]; j++) {
                System.arraycopy(grad[i][j], 0, res, position, layerSizes[i] + 1);
                position += layerSizes[i] + 1;
            }
        }
        return res;
    }

    public double[] reshapeTheta() {
        double[][][] theta = nn.getTheta();
        int depth = nn.getDepth();
        int[] layerSizes = nn.getLayerSizes();
        int length = 0;
        for (int i = 0; i < depth - 1; i++) {
            length += (layerSizes[i] + 1) * layerSizes[i + 1];
        }
        double[] res = new double[length];
        int position = 0;
        for (int i = 0; i < depth - 1; i++) {
            for (int j = 0; j < layerSizes[i + 1]; j++) {
                System.arraycopy(theta[i][j], 0, res, position, layerSizes[i] + 1);
                position += layerSizes[i] + 1;
            }
        }
        return res;
    }

    public void setTheta(double[] newTheta) {
        int depth = nn.getDepth();
        int[] layerSizes = nn.getLayerSizes();
        double[][][] theta = new double[depth - 1][][];
        for (int i = 0; i < depth - 1; i++) {
            theta[i] = new double[layerSizes[i + 1]][layerSizes[i] + 1];
        }
        int length = 0;
        for (int i = 0; i < depth - 1; i++) {
            length += (layerSizes[i] + 1) * layerSizes[i + 1];
        }
        double[] res = new double[length];
        int position = 0;
        for (int i = 0; i < depth - 1; i++) {
            for (int j = 0; j < layerSizes[i + 1]; j++) {
                System.arraycopy(newTheta, position, theta[i][j], 0, layerSizes[i] + 1);
                position += layerSizes[i] + 1;
            }
        }
        nn.setTheta(theta);
    }

    public double cost() {
        int tdLen = ty.length;
        int outputSize = ty[0].length;
        double[][] hp = nn.predict(tx);
        /*return -(sum(sum(
                sum(
                        dotMult(ty, log(h))
                        , dotMult(sub(1.0, ty), log (sub(1.0, h))))
                )
        )
        ) / (double) tdLen;*/
        double sum = .0;
        double y, h;
        for (int i = 0; i < tdLen; i++) {
            for (int j = 0; j < outputSize; j++) {
                y = ty[i][j];
                h = hp[i][j];
                if (y > 0.5) {//one
                    sum -= log(h);
                } else {
                    sum -= log(1. - h);
                }
                //sum += -y * log(h) - (1. - y) * log(1 - h);
            }
        }
        sum /= tdLen;
        return sum;
    }

    @Override
    public void setAccuracy(double accuracy) {
        gradientDescentSteps = (int) accuracy;
    }

}
