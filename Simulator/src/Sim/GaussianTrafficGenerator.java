package Sim;

import java.util.Random;

public class GaussianTrafficGenerator extends TrafficGenerator {

    private int average;
    private int deviation;
    private Random randomGen=new Random();

    public GaussianTrafficGenerator(int average, int deviation,Node parent,NetworkAddr destination){
        super(parent,destination);
        this.deviation=deviation;
        this.average =average;
    }

    @Override
    protected int createDelay() {
        int gaussianDelay =(int) (randomGen.nextGaussian()*deviation+ average);
        return (gaussianDelay<0)?0:gaussianDelay;
    }


}
