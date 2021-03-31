/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sausageShop.models.Category;
import sausageShop.models.Product;

/**
 *
 * @author pro56
 */
public class Message implements Serializable {

    private List<Category> data;
    private Category category;
    private Product product;

    public Message(List<Category> data, int messageType) {
        this.data = data;
        this.messageType = messageType;
    }

    public Message(int messageType) {
        this.messageType = messageType;
        data = null;
    }

    public Message(Category category){
        this.category = category;
    }

    public Message(Product product){
        this.product = product;
    }

    public Message(){}

    private int messageType;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
