/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import sausageShop.models.Category;
import sausageShop.models.Product;
import serverSide.dao.PersistException;
import serverSide.dao.SqlDao;

/**
 *
 * @author pro56
 */
public class ClientSession extends Thread {

    private static SqlDao sqlDao = new SqlDao(Server.getDBConnection());
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
                        case 0://Добавление новой категории
                            Category category;
                            category = sqlDao.persistCategory(messageCame.getCategory());
                            out.writeObject(category);
                            break;
                        case 1://Удаление старой категории
                            sqlDao.deleteCategory(messageCame.getCategory());
                            break;
                        case 2://Добавление нового продукта
                            Product product;
                            product = sqlDao.persistProduct(messageCame.getProduct());
                            out.writeObject(product);
                            break;
                        case 3://Удаление старого продукта
                            sqlDao.deleteProduct(messageCame.getProduct());
                            break;
                        case 4://при покупке товара
                            sqlDao.update(messageCame.getProduct());
                            break;
                        default:
                            throw new AssertionError();
                    }
                } catch (EOFException e) {
                    socket.close();
                    System.out.println("Мужик ушел без сосисок");
                } catch (ClassNotFoundException | PersistException e) {
                    e.printStackTrace();
                }
            }
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
