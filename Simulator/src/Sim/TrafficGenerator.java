package Sim;

public abstract class TrafficGenerator extends SimEnt {
    int numberOfMessage=0;
    int messageSent=0;
    protected NetworkAddr destination;
    protected Node parent;

    public TrafficGenerator(Node parent,NetworkAddr destination){
        this.parent=parent;
        this.destination=destination;
    }

    public void generate(int numberOfMessage) {
        messageSent=0;
        this.numberOfMessage=numberOfMessage;
        send(this,new TimerEvent(),0);
    }

    protected abstract int createDelay();

    @Override
    public void recv(SimEnt source, Event event) {
        if (messageSent<numberOfMessage) {
            System.out.println("Node " + parent._id.networkId() + "." + parent._id.nodeId()
                    + " sent message with seq: " + messageSent + " at time " + SimEngine.getTime());
           // System.out.println(SimEngine.getTime()+"\t\t"+messageSent);
            parent.send(parent._peer, new Message(parent._id, destination, messageSent), 0);
            send(this, new TimerEvent(), createDelay());
            messageSent++;
        }
    }

}
