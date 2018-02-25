package Sim;

public class UpdateMessage extends Message {
    UpdateMessage(NetworkAddr from, NetworkAddr to, int seq) {
        super(from, to, seq);
    }
}
