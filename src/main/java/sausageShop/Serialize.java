/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausageShop;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import sausageShop.controllers.CategoryController;
import sausageShop.models.Category;
import serverSide.dao.PersistException;
import serverSide.dao.SqlDao;
import serverSide.server.Server;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;

/**
 * @author pro56
 */
public class Serialize {

    public static ArrayList<Category> deserializeDatabase() throws PersistException, SQLException {
        SqlDao sqlCategoryDao = new SqlDao(Server.getDBConnection());
        return sqlCategoryDao.getAllCategoriesFromDataBase();
    }

    /*public static void convertObjectToXml(CategoryController categories, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(CategoryController.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(categories, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }*/
}
