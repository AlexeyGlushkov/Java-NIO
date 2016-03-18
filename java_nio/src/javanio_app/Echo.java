package javanio_app;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lenovo on 10.03.2016.
 */
public class Echo implements Runnable {
    private List queue = new LinkedList();

    public void processData(MainClass server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized(queue) {
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    public void run() {
        ServerDataEvent dataEvent;

        while(true) {
            // Wait for data to become available
            synchronized(queue) {
                while(queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                    }
                }
                dataEvent = (ServerDataEvent) queue.remove(0);
            }

            // Return to sender
//            dataEvent.server.send(dataEvent.socket, dataEvent.data);
            String input = new String(dataEvent.data);
            if (input.contains("SGR=")){
                int begin = input.indexOf("=") + 1;
                int end = input.indexOf("[");
                String gname = input.substring(begin,end);
                dataEvent.server.setGroups(input, gname);
                continue;
            }else
            if (input.contains("GN=")){
                int begin = input.indexOf("=") + 1;
                int end = input.indexOf("[");
                String gname = input.substring(begin,end);
                try {
                    dataEvent.server.sendToGroup(input.replaceAll("GN=",""), gname);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }else
            if (input.contains("AUTH")){
                new Authorization(input, dataEvent).run();
                continue;
            }else
            if (input.contains("GET ALL IP")){
                try {
                    dataEvent.server.sendAllIp(dataEvent.socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            } else
            if (input.contains("CLOSE")){
                dataEvent.server.sendAll("CLOSE SERVER".getBytes());
                System.exit(0);
            } else
            if (input.contains("GET ALL MAC")){
                for (String s : dataEvent.server.macAdresses){
                    dataEvent.server.send(dataEvent.socket, (s + "\r\n").getBytes());
                }
                continue;
            }
            {
                if (input.contains(":")){
                    dataEvent.server.macAdresses.add(input.replaceAll("\r\n", ""));
                }
                dataEvent.server.sendAll(dataEvent.data);
            }
        }
    }
}
