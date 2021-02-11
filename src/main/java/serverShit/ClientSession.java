/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverShit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import sausageShop.Serialize;
import sausageShop.models.Category;

/**
 *
 * @author pro56
 */
public class ClientSession {

    private final Socket socket;
    private final Context context;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientSession(final Socket socket, final Context context) throws IOException {
        this.socket = socket;
        this.context = context;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void run() throws IOException, ClassNotFoundException {
        send();
        while (true) {
            if (context.changed) {
                try (FileOutputStream fos = new FileOutputStream("out.bin")) {
                    Serialize.serializeDatabase(new ArrayList<>((ArrayList<Category>) in.readObject()), fos);
                }
                send();
            }
        }
    }

    public void send() throws IOException {
        out.writeObject(context.getData());
    }
}
