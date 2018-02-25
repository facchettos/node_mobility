package Sim;

public abstract class Sink extends SimEnt {

    @Override
    public abstract void recv(SimEnt source, Event event);
}
