/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import sausageShop.Serialize;
import sausageShop.models.Category;

/**
 *
 * @author pro56
 */
public class ClientSession extends Thread {

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

    @Override
    public void run() {
        try {
            out.writeObject(new Message(context.getData(), 0));
            out.flush();
        } catch (IOException ex) {
            try {
                socket.close();
                Server.getDBConnection().close();
                System.out.println("Мужик ушел без сосисок, но через другую дверь");
            } catch (IOException | SQLException exr) {
                System.out.println("А так вообще можно?");
            }
        }
    }
}
