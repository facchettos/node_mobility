package Sim;

// This class implements a node (host) it has an address, a peer that it communicates with
// and it count messages send and received.

public class Node extends SimEnt {
    NetworkAddr _id;
    SimEnt _peer;
    int _seq = 0;
    Sink sink;
    TrafficGenerator trafficGenerator;
    int newInterface;


    public Node(int network, int node,Sink sink,TrafficGenerator trafficGenerator) {
        super();
        this.sink=sink;
        _id = new NetworkAddr(network, node);
        this.trafficGenerator = trafficGenerator;
    }

    public Node(int network,int node){
        super();
        this._id= new NetworkAddr(network, node);
        this.sink=new CBRSink(0,this);
    }

    public boolean changeInterface(int newInterface,int delayFrom0){
        this.newInterface =newInterface;
        this.send(this,new ChangeEvent(),delayFrom0);
//        if (_peer instanceof Link){
//            this._id=new NetworkAddr(this._id.networkId(),newInterface);
//            if (((Link) _peer)._connectorA == (SimEnt) this ){
//                if (((Link) _peer)._connectorB instanceof Router ){
//                    ((Router) ((Link) _peer)._connectorB).updateInterface(this._id.networkId(),this._peer,this,5);
//                }
//
//            }else{
//                if (((Link) _peer)._connectorB instanceof Router ){
//                    ((Router) ((Link) _peer)._connectorA).updateInterface(this._id.networkId(),this._peer,this,5);
//                }
//            }
//        }
//        updatePeer();
        return false;
    }

    public void updatePeer(){
        send(this._peer,new UpdateMessage(this._id,this.trafficGenerator.destination,-1),0);
    }

    public void setTrafficGenerator(TrafficGenerator trafficGenerator) {
        this.trafficGenerator = trafficGenerator;
    }

    public void generate(int numberOfMessage){
        trafficGenerator.generate(numberOfMessage);
    }


    // Sets the peer to communicate with. This node is single homed

    public void setPeer(SimEnt peer) {
        _peer = peer;

        if (_peer instanceof Link) {
            ((Link) _peer).setConnector(this);
        }
    }


    public NetworkAddr getAddr() {
        return _id;
    }

//**********************************************************************************	
    // Just implemented to generate some traffic for demo.
    // In one of the labs you will create some traffic generators

    int _stopSendingAfter = 0; //messages
    int _timeBetweenSending = 10; //time between messages
    int _toNetwork = 0;
    int _toHost = 0;

    public void StartSending(int network, int node, int number, int timeInterval, int startSeq) {
        _stopSendingAfter = number;
        _timeBetweenSending = timeInterval;
        _toNetwork = network;
        _toHost = node;
        _seq = startSeq;
        send(this, new TimerEvent(), 0);
    }

//**********************************************************************************	

    // This method is called upon that an event destined for this node triggers.

    public void recv(SimEnt src, Event ev) {

        if (ev instanceof Message && !(ev instanceof UpdateMessage)) {
            if (((Message) ev).seq()!=-1)
                sink.recv(src, ev);

        }else if (ev instanceof ChangeEvent){
            if (_peer instanceof Link){
                System.out.println("\n\nThe time is "+SimEngine.getTime()+"\n\n");
                ((Router) ((Link) _peer)._connectorB).printTable();
                this._id=new NetworkAddr(newInterface,this._id.networkId());
                if (((Link) _peer)._connectorA == (SimEnt) this ){
                    if (((Link) _peer)._connectorB instanceof Router ){
                        ((Router) ((Link) _peer)._connectorB).updateInterface(this._id.networkId(),this._peer,this,newInterface);
                    }

                }else{
                    if (((Link) _peer)._connectorB instanceof Router ){
                        ((Router) ((Link) _peer)._connectorA).updateInterface(this._id.networkId(),this._peer,this,newInterface);
                    }
                }
            }

            updatePeer();
        }else if (ev instanceof UpdateMessage){
            System.out.println("node "+this._id.nodeId()+"."+this._id.networkId()+
                    " previous network address of mobile node: "+
                    this.trafficGenerator.destination.networkId()+"."+
                    this.trafficGenerator.destination.nodeId() );
            this.trafficGenerator.destination=((Message) ev).source();
            System.out.println("node "+this._id.nodeId()+"."+
                    this._id.networkId()+" new network address of mobile node: "+
                    this.trafficGenerator.destination.networkId()+"."+
                    this.trafficGenerator.destination.nodeId() );
        }

    }

}
