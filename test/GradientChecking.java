import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

/**
 * Created by Denis on 10.04.2017.
 */
public class GradientChecking {

    public static void main(String[] args) {
        int depth = 3;
        int[] sizes = new int[]{2, 2, 1};
        int m = 3;
        double[][] tx = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[][] ty = new double[][]{{0}, {1}, {1}, {0}};
        DeepFeedforwardNN nn = new DeepFeedforwardNN(depth, sizes);
        DFFNNTrainer trainer = new DFFNNTrainer(nn, tx, ty);
        System.out.println("init cost: " + trainer.cost());
        double[] backpropGrad = trainer.reshapeGrad();
        System.out.println("backprop grad:");
        for (double g: backpropGrad) {
            System.out.println(g);
        }
        double[] x = trainer.reshapeTheta();
        double delta = 1e-2;
        double[] numericalGrad = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            x[i] += delta;
            trainer.setTheta(x);
            double rCost = trainer.cost();
            x[i] -= 2.0 * delta;
            trainer.setTheta(x);
            double lCost = trainer.cost();
            numericalGrad[i] = (rCost - lCost) / (2.0 * delta);
            x[i] += delta;
        }
        System.out.println("numerical grad: ");
        for (double g: numericalGrad) {
            System.out.println(g);
        }

    }

}
