package Sim;

public class TrafficGenCBR {

    private Node source;
    private Node destination;
    private Link link;
    private int bitrate;
    private int numberOfMessages;

    public TrafficGenCBR(Node source,Node destination,int bitrate,int numberOfMessages){
        this.source=source;
        this.destination=destination;
        this.bitrate=bitrate;
        this.numberOfMessages=numberOfMessages;
        source._seq=0;
        source._timeBetweenSending=bitrate;
        source._stopSendingAfter=numberOfMessages;
        source._toNetwork=destination.getAddr().networkId();
        source._toHost=destination.getAddr().nodeId();

    }

    public void generate(){
//            System.out.println("Node " + source.getAddr().networkId() + "." + source.getAddr().nodeId()
//                    + " sent message with seq: " + (i + 1) + " at time " + i * bitrate);
            source.recv(destination,new TimerEvent());
    }

}
