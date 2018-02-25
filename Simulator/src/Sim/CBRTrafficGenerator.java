package Sim;

public class CBRTrafficGenerator extends TrafficGenerator {
    private int bitrate;


    public CBRTrafficGenerator(int bitrate,Node parent, NetworkAddr destination){
        super(parent,destination);
        this.bitrate=bitrate;
    }


    @Override
    protected int createDelay() {
        return bitrate;
    }

}
