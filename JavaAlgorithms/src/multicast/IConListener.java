package multicast;

import java.net.DatagramPacket;

public interface IConListener {
    public void onInputDatagram(DatagramPacket packet);
}
