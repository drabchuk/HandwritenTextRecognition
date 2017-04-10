package nn.optimazers;

public class FletcherReeves implements Gradient {

    private double[] way;
    private double[] x;
    private double error;
    private double lambda;
    private int steps;

    static {
        System.out.println(FletcherReeves.class + " initialize");
    }

    public FletcherReeves(double[] x, double e, double l, int maxSteps){
        this.x = x;
        error = e;
        lambda = l;
        steps = maxSteps;
    }

    @Override
    public double[] optimize(Function func) {
        System.out.println("Optimization started");
        System.out.println("lambda: " + lambda);
        System.out.println("max steps: " + steps);
        System.out.println("Criteria: " + error);
        way = minus(func.grad(x));
        double s;
        int counter = 0;
        do {
            x = down();
            way = getWay(minus(func.grad(x)),way);
            s = sqrt(way);
            System.out.println("step: " + counter++ + "cost: " + func.cost(x) + "criteria: " + s);
        } while (s > error && counter < steps);
        return x;
    }

    private double[] minus(double[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -arr[i];
        }
        return arr;
    }

    private double[] down(){
        double[] _x = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            _x[i] = x[i] + way[i] * lambda;
        }
        return _x;
    }

    private double[] getWay(double[] fx_new, double[] fx_old){
        double[] _way = new double[way.length];
        double n = norma(fx_new,fx_old);
        for (int i = 0; i < way.length; i++) {
            _way[i] = fx_new[i] + way[i] * n;
        }
        return _way;
    }

    private double norma(double[] grad_new, double[] grad_old){
        double a = sqrt(grad_new);
        double b = sqrt(grad_old);
        return (a*a)/(b*b);
    }

    private double sqrt(double[] arr){
        double sum = 0;
        for (double anArr : arr) {
            sum += anArr * anArr;
        }
        return Math.sqrt(sum);
    }
}
