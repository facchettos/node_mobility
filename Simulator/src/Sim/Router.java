package Sim;

// This class implements a simple router

public class Router extends SimEnt {

    private RouteTableEntry[] _routingTable;
    private int _interfaces;
    private int _now = 0;

    // When created, number of interfaces are defined

    Router(int interfaces) {
        _routingTable = new RouteTableEntry[interfaces];
        _interfaces = interfaces;
    }

    public void printTable(){
        System.out.println("Previous interfaces table");
        for (int i = 0; i <_interfaces ; i++) {
            if (_routingTable[i]!=null)
                System.out.println(((Node) (_routingTable[i].node())).getAddr().networkId()+"."+((Node) (_routingTable[i].node())).getAddr().nodeId());
            else
                System.out.println("null");
        }
    }

    public boolean updateInterface(int networkAddress, SimEnt link, SimEnt node, int newInterface) {
        //remove the node from the previous interface

        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress) {
                    _routingTable[i] = null;

                }
            }
        if (newInterface < _interfaces) {
            _routingTable[newInterface] = new RouteTableEntry(link, node);

        } else {
            System.out.println("Trying to connect to port not in router");
            return false;
        }
        System.out.println("\n\n");

        //link the node to the new interface

        System.out.println("New interfaces table");
        for (int i = 0; i <_interfaces ; i++) {
            if (_routingTable[i]!=null)
                System.out.println(((Node) (_routingTable[i].node())).getAddr().networkId()+"."+((Node) (_routingTable[i].node())).getAddr().nodeId());
            else
                System.out.println("null");
        }
        System.out.println("\n\n");

        ((Link) link).setConnector(this);
        System.out.println("Successfully changed the interface\n");
        return true;
    }

    // This method connects links to the router and also informs the
    // router of the host connects to the other end of the link

    public void connectInterface(int interfaceNumber, SimEnt link, SimEnt node) {
        if (interfaceNumber < _interfaces) {
            _routingTable[interfaceNumber] = new RouteTableEntry(link, node);
        } else
            System.out.println("Trying to connect to port not in router");

        ((Link) link).setConnector(this);
    }

    // This method searches for an entry in the routing table that matches
    // the network number in the destination field of a messages. The link
    // represents that network number is returned

    private SimEnt getInterface(int networkAddress) {
        SimEnt routerInterface = null;
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress) {
                    routerInterface = _routingTable[i].link();
                }
            }
        return routerInterface;
    }


    // When messages are received at the router this method is called

    public void recv(SimEnt source, Event event) {
        if (event instanceof Message) {
            if (! (event instanceof UpdateMessage))
			    System.out.println("Router handles packet with seq: " + ((Message) event).seq()+" from node: "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
            else
                System.out.println("Router handles update message from node "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
			System.out.println("Router sends to node: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());
            send(sendNext, event, _now);

        }
    }
}
