package nn.optimazers;

import static nn.algebra.Alg.*;

/**
 * Created by Denis on 09.04.2017.
 */
public class GradientDescent implements OptimizationMethod1Order {

    private double[] x;
    private int steps;
    private double alfa = 10;

    static {
        System.out.println(FletcherReeves.class + " initialize");
    }

    public GradientDescent(double[] x, int steps, double alfa){
        this.x = x;
        this.steps = steps;
        this.alfa = alfa;
    }

    @Override
    public double[] optimize(Function func) {
        System.out.println("Optimization using gradient descent started");
        System.out.println("max steps: " + steps);
        for (int i = 0; i < steps; i++) {
            long t1 = System.currentTimeMillis();
            double[] grad = func.grad(x);
            x = sub(x, dotMult(alfa, grad));
            long t2 = System.currentTimeMillis();
            long dt = t2 - t1;
            long hours = dt / 3600;
            long minutes = dt % 3600 / 60;
            System.out.println("step: " + i + " cost: " + func.cost(x));
            System.out.println(hours + ":" + minutes + " hh:mm remain");
        }
        return x;
    }

    @Override
    public double[] optimize(Function func, int maxTime) {
        System.out.println("Optimization using gradient descent started");
        System.out.println("max steps: " + steps);
        double totalTime = 0.0;
        for (int i = 0; i < steps && totalTime < maxTime * 60_000; i++) {
            long t1 = System.currentTimeMillis();
            double[] grad = func.grad(x);
            x = sub(x, dotMult(alfa, grad));
            long t2 = System.currentTimeMillis();
            long dt = t2 - t1;
            totalTime += dt;
            double inHours = (double) dt * (double) steps / 3600_000.0;
            int hours = (int) inHours;
            int minutes = (int) (60 * (inHours - hours));
            System.out.println("step: " + i + " cost: " + func.cost(x));
            System.out.println(hours + ":" + minutes + " hh:mm remain");
        }
        return x;
    }


}
