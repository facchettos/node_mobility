package Sim;

import java.util.ArrayList;
import java.util.Random;

public class PoissonSink extends Sink {
    private ArrayList<Event> message = new ArrayList<>();
    private Node parent;
    private double nextToSend = 0;
    private Random random = new Random();
    private int lambda;

    public PoissonSink(int lambda ,Node parent) {
        this.lambda = lambda;
        this.parent=parent;
    }

    public void recv(SimEnt source, Event event) {
        if (event instanceof Message) {
            message.add(event);
            int poisson = (getPoisson(lambda));
            if (SimEngine.getTime() >= nextToSend) {
                send(this, new TimerEvent(), 0);
                nextToSend = SimEngine.getTime() +poisson;
            } else {
                send(this, new TimerEvent(), nextToSend - SimEngine.getTime());
                nextToSend = nextToSend +  poisson;
            }
        } else {
            System.out.println("Node " + parent._id.networkId() + "."
                    + parent._id.nodeId() + " receives message with seq: "
                    + ((Message) message.remove(0)).seq() + " at time "
                    + SimEngine.getTime());
//            System.out.println(SimEngine.getTime());
        }
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
