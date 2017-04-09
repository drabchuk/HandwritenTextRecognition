package nn.optimazers;

import static nn.algebra.Alg.*;

/**
 * Created by Denis on 09.04.2017.
 */
public class GradientDescent implements Gradient{

    private double[] way;
    private double[] x;
    private int steps;
    private double alfa = 1e-6;

    static {
        System.out.println(FletcherReeves.class + " initialize");
    }

    public GradientDescent(double[] x, int steps){
        this.x = x;
        this.steps = steps;
    }

    @Override
    public double[] optimize(Function func) {
        for (int i = 0; i < steps; i++) {
            double[] grad = func.grad(x);
            x = sub(x, dotMult(alfa, grad));
        }
        return x;
    }

    private double[] minus(double[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -arr[i];
        }
        return arr;
    }


}
