/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.io.Serializable;
import java.util.ArrayList;
import sausageShop.models.Category;

/**
 *
 * @author pro56
 */
public class Message implements Serializable {

    private ArrayList<Category> data;

    public Message(ArrayList<Category> data, int messageType) {
        this.data = data;
        this.messageType = messageType;
    }

    public Message(int messageType) {
        this.messageType = messageType;
        data = null;
    }

    private int messageType;

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
