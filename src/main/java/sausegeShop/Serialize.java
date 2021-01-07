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
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;

/**
 *
 * @author pro56
 */
public class Serialize {

    public static void serializeDatabase(CategoryController base, OutputStream out) {
        try (out; ObjectOutputStream OOS = new ObjectOutputStream(out)) {
            OOS.writeObject(base);
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        }
    }

    public static void serializeProductController(ProductController base, OutputStream out){
        try (out; ObjectOutputStream OOS = new ObjectOutputStream(out)) {
            OOS.writeObject(base);
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        }
    }

    public static CategoryController deserializeDatabase(InputStream in) {
        CategoryController base;
        try (in; ObjectInputStream OIS = new ObjectInputStream(in)) {
            return base = (CategoryController) OIS.readObject();
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong object type");
        }
        return null;
    }

    public static ProductController deserializeProductController(InputStream in1){
        ProductController base;
        try (in1; ObjectInputStream OIS = new ObjectInputStream(in1)) {
            return base = (ProductController) OIS.readObject();
        } catch (IOException ex) {
            System.out.println("Oozhos occurred");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong object type");
        }
        return null;
    }
}
