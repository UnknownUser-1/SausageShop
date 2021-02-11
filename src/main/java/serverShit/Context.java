/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverShit;

import java.util.ArrayList;

import sausageShop.models.Category;

public class Context {

    private ArrayList<Category> data;
    private SessionsManager sessionsManager;
    public boolean changed;

    public Context(ArrayList<Category> data) {
        this.data = data;
        this.changed = false;
        this.sessionsManager = new SessionsManager();
    }

    public SessionsManager getSessionsManger() {
        return this.sessionsManager;
    }

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }
}
