package Sim;

import java.util.ArrayList;
import java.util.Random;

public class GaussianSink extends Sink {
    private ArrayList<Event> message = new ArrayList<>();
    private Node parent;
    private double nextToSend = 0;
    private Random random = new Random();
    private int average;
    private int deviation;

    public GaussianSink(int average, int deviation, Node parent) {
        this.average = average;
        this.deviation = deviation;
        this.parent=parent;
    }


    public void recv(SimEnt source, Event event) {
        if (event instanceof Message) {
            message.add(event);
            int gaussDelay = (int) (random.nextGaussian() * deviation + average);
            if (SimEngine.getTime() >= nextToSend) {
                send(this, new TimerEvent(), 0);
                nextToSend = SimEngine.getTime() + ((gaussDelay < 0) ? 0 : gaussDelay);
            } else {
                send(this, new TimerEvent(), nextToSend - SimEngine.getTime());
                nextToSend = nextToSend + ((gaussDelay < 0) ? 0 : gaussDelay);
            }
        } else {
//            System.out.println("Node " + parent._id.networkId() + "."
//                    + parent._id.nodeId() + " receives message with seq: "
//                    + ((Message) message.remove(0)).seq() + " at time "
//                    + SimEngine.getTime());
            System.out.println(SimEngine.getTime()+"\t"+((Message)message.remove(0)).seq());
        }
    }

}
