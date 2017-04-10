package nn.optimazers;

import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

/**
 * Created by Denis on 09.04.2017.
 */
public class NNFunc implements Function {

    DFFNNTrainer trainer;

    public NNFunc(DFFNNTrainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public double cost(double[] x) {
        trainer.setTheta(x);
        return trainer.cost();
    }

    @Override
    public double[] grad(double[] x) {
        trainer.setTheta(x);
        return trainer.reshapeGrad();
    }
}
