package Sim;

import java.util.ArrayList;

public class CBRSink extends Sink {
    private int bitrate;
    private ArrayList<Event> message=new ArrayList<>();
    private Node parent;
    private double nextToSend =0;


    public CBRSink(int bitrate,Node parent){
        this.bitrate=bitrate;
        this.parent=parent;
    }

    public void recv(SimEnt source, Event event) {

        if (event instanceof Message){
            message.add(event);
            if (SimEngine.getTime()>=nextToSend) {
                nextToSend = SimEngine.getTime() + bitrate;
                send(this, new TimerEvent(), 0);
            }
            else {
                send(this, new TimerEvent(), nextToSend - SimEngine.getTime());
                nextToSend = nextToSend+bitrate;
            }
        }else{
            System.out.println("Node " + parent._id.networkId() + "."
                   + parent._id.nodeId() + " receives message with seq: "
                    + ((Message) message.remove(0)).seq() + " at time "
                   + SimEngine.getTime());
           // System.out.println(SimEngine.getTime()+"\t"+((Message) message.remove(0)).seq());
        }
    }
}
