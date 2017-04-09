package nn.optimazers;


public class MockFunc implements Function {

    @Override
    public double cost(double[] x) {
        double sum = 0;
        for (double aX : x) {
            sum += aX * aX;
        }
        return sum;
    }

    @Override
    public double[] grad(double[] x) {
        double[] res = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            res[i] = x[i] * 2.0;
        }
        return res;
    }
}
