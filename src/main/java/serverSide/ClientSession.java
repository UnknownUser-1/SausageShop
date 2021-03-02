/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide;

import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import sausegeShop.Serialize;
import sausegeShop.models.Category;

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
            while (!socket.isClosed()) {
                Message messageCame;
                try {
                    messageCame = (Message) in.readObject();
                    switch (messageCame.getMessageType()) {
                        case 0://Админ что-то изменил
                            context.setConfirmData(false);
                            
                           context.setData(messageCame.getData());
                            try (FileOutputStream fos = new FileOutputStream("out.bin")) {
                                Serialize.serializeDatabase((ArrayList<Category>) context.getData(), fos);
                            }
                            break;
                        case 1://Пользователь что-то хочет купить
                            out.writeObject((context.isConfirmData())
                                    ? (new Message(0)) : (new Message(1)));
                            out.flush();
                            if (((Message) in.readObject()).getMessageType() == 0) {//Данные совпадают
                                context.setConfirmData(false);
                                context.setData(((Message) in.readObject()).getData());
                                try (FileOutputStream fos = new FileOutputStream("out.bin")) {
                                    Serialize.serializeDatabase((ArrayList<Category>) context.getData(), fos);
                                }
                            } else {//Данные не совпадают
                                out.writeObject(new Message(context.getData(), 0));
                                out.flush();
                                context.setConfirmData(true);
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                } catch (EOFException e) {
                    socket.close();
                    System.out.println("Мужик ушел без сосисок");
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            try {
                socket.close();
                System.out.println("Мужик ушел без сосисок, но через другую дверь");
            } catch (IOException ex1) {
                System.out.println("А иак вообще можно?");
            }
        }
    }
}
