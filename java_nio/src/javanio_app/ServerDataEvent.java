package javanio_app;

import java.nio.channels.SocketChannel;

/**
 * Created by Lenovo on 11.03.2016.
 */
public class ServerDataEvent {
    public MainClass server;
    public SocketChannel socket;
    public byte[] data;

    public ServerDataEvent(MainClass server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}
