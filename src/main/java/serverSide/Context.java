/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide;

import java.util.ArrayList;
import sausegeShop.models.Category;

public class Context {

    private ArrayList<Category> data;
    private SessionsManager sessinonsManager;
    private boolean confirmData;
    public boolean stopFlag;
    

    public Context(ArrayList<Category> data) {
        this.data = data;
        this.stopFlag = false;
        this.sessinonsManager = new SessionsManager();
        confirmData = true;
    }

    public SessionsManager getSessionsManger() {
        return this.sessinonsManager;
    }

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> data) {
        this.data = data;
    }

    public boolean isConfirmData() {
        return confirmData;
    }

    public void setConfirmData(boolean confirmData) {
        this.confirmData = confirmData;
    }
    
}
