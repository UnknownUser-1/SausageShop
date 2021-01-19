/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverShit;

/**
 *
 * @author pro56
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ConnectionUser {

    private ConnectionThread connThread = new ConnectionThread();

    private boolean purchase = false;
    private Serializable data;

    public ConnectionUser() {
        connThread.setDaemon(true);
    }

    public void startConnection() throws Exception {
        connThread.start();
    }

    public void send(Serializable data) throws Exception {
        connThread.out.writeObject(data);
    }

    public ObjectInputStream acquire() throws Exception {
        return connThread.in;
    }
    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }

    private class ConnectionThread extends Thread {

        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        @Override
        public void run() {
            try (
                    Socket socket = new Socket("localhost", 5000);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                this.in = in;
                //   socket.setTcpNoDelay(true);
                acquire(in);
                while (true) {
                    if (purchase) {
                        out.writeObject(data);
                        acquire(in);
                    }
                }
            } catch (Exception e) {
                System.out.println("Что-то сломалось к хуям");
            }
        }

        public void acquire(final ObjectInputStream in1) throws ClassNotFoundException, IOException {
            data = (Serializable) in1.readObject();
        }
    }
}
