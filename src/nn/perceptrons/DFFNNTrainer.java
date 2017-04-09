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

    int gradientDescentSteps = 10;

    public DFFNNTrainer(NeuralNetwork nn, double[][] tx, double[][] ty) {
        super(nn, tx, ty);
    }

    @Override
    public void train() {

    }

    private double[][][] grad() {
        return null;
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
