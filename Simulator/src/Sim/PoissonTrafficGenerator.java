package Sim;

public class PoissonTrafficGenerator extends TrafficGenerator {
    int u;

    public PoissonTrafficGenerator(int u,Node parent,NetworkAddr destination){
        super(parent, destination);
        this.u=u;
    }


    @Override
    protected int createDelay() {
        return getPoisson(u);
    }

    public static int getPoisson(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }
}
