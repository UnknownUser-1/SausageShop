/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausegeShop;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import sausegeShop.models.Category;

/**
 * @author pro56
 */
public class Serialize {

    public static void serializeDatabase(ArrayList<Category> base, OutputStream out) {
        try (out; ObjectOutputStream OOS = new ObjectOutputStream(out)) {
            OOS.writeObject(base);
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        }
    }

    public static ArrayList<Category> deserializeDatabase(InputStream in) {
        try (in; ObjectInputStream OIS = new ObjectInputStream(in)) {
            return new ArrayList<>((ArrayList<Category>) OIS.readObject());
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong object type");
        }
        return null;
    }
}
