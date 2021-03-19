package serverSide.dao;

import sausageShop.models.Category;
import sausageShop.models.Product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SqlDao implements  Serializable {

    /**
     * Возвращает sql запрос для обновления записи.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    private String getUpdateQueryInCategory(){
        return "UPDATE sausageShop.product SET nameProduct = ?, price = ?, composition = ?, description = ?,\n" +
                " rating = ?, categoryId = ?  WHERE id = ?;";
    }

    /**
     * Возвращает sql запрос для получения всех записей в таблице category и product.
     */
    private String getSelectQueryInCategory(){return "SELECT * FROM sausageShop.category";}

    private String getSelectQueryInProduct(){return "SELECT * FROM sausageShop.product";}

    /**
     * Возвращает sql запрос для вставки новой записи в таблицу category и product.
     */
    private String getCreateQueryInCategory(){return "INSERT INTO sausageShop.category (title) VALUES (?) ";}

    private String getCreateQueryInProduct(){return "INSERT INTO sausageShop.product (nameProduct, price, composition, description, rating, categoryId )\n" +
            " VALUES (? , ?, ?,?,?,?) ";}


    /**
     * Возвращает sql запрос для удаления записи из базы данных в таблице category.
     */
    private   String getDeleteQueryInCategory(){return "DELETE FROM sausageShop.category WHERE id = ?";}

    private   String getDeleteQueryInProduct(){return "DELETE FROM sausageShop.product WHERE id = ?";}

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSetCategory.
     */
    private   ArrayList<Category> parseResultSetCategory(ResultSet rs) throws PersistException{
        ArrayList<Category> categories = new ArrayList<>();
        try {
            while (rs.next()){
                Category category = new Category();
                category.setTitle(rs.getString("title"));
                category.setId(rs.getInt("id"));
                categories.add(category);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return categories;
    }

    private  ArrayList<Product> parseResultSetProduct(ResultSet rs) throws PersistException{
        ArrayList<Product> products = new ArrayList<>();
        try {
            while (rs.next()){
                Product product = new Product();
                product.setName(rs.getString("nameProduct"));
                product.setPrice(rs.getDouble("price"));
                product.setComposition(rs.getString("composition"));
                product.setDescription(rs.getString("description"));
                product.setRating(rs.getDouble("rating"));
                product.setId(rs.getInt("id"));
                product.setCategoryId(rs.getInt("categoryId"));
                products.add(product);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return products;
    }

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    private void prepareStatementForInsertCategory(PreparedStatement statement, Category category) throws PersistException{
        try {
            statement.setString(1, category.getTitle());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    private void prepareStatementForInsertProduct(PreparedStatement statement, Product product) throws PersistException{
        try {
            statement.setString(1,product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setString(3,product.getComposition());
            statement.setString(4,product.getDescription());
            statement.setDouble(5,product.getRating());
            statement.setInt(6,product.getCategoryId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    private void prepareStatementForUpdateProduct(PreparedStatement statement, Product product) throws PersistException{
        try {
            statement.setString(1,product.getName());
            statement.setDouble(2,product.getPrice());
            statement.setString(3,product.getComposition());
            statement.setString(4,product.getDescription());
            statement.setDouble(5,product.getRating());
            statement.setInt(6,product.getCategoryId());
            statement.setInt(7,product.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    private transient Connection connection;

    public Product persistProduct(Product object) throws PersistException {
        Product product;
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        // Добавляем запись
        String sql = getCreateQueryInProduct();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsertProduct(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        // Получаем только что вставленную запись
        sql = getSelectQueryInProduct() + " WHERE id = CURRVAL(pg_get_serial_sequence('sausageShop.product','id'))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            ArrayList<Product> list = parseResultSetProduct(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
           product = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return product;
    }


    public ArrayList<Category> getAllCategoriesFromDataBase() throws PersistException, SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>();
        String sqlCategory = getSelectQueryInCategory();
        String sqlProduct = getSelectQueryInProduct();
        try {
            PreparedStatement statementCategory = connection.prepareStatement(sqlCategory);
            PreparedStatement statementProduct = connection.prepareStatement(sqlProduct);
            ResultSet rsCategory = statementCategory.executeQuery();
            ResultSet rsProduct = statementProduct.executeQuery();
            categories = parseResultSetCategory(rsCategory);
            products = parseResultSetProduct(rsProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Category category : categories) {
            for (Product product : products) {
                if (category.getId().equals(product.getCategoryId())) {
                    category.addProduct(product);
                }
            }
        }
        return categories;
    }


    public Category persistCategory(Category object) throws PersistException {
        Category category;
        if (object.getId() != null) {
            throw new PersistException("Object is already persist.");
        }
        // Добавляем запись
        String sql = getCreateQueryInCategory();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsertCategory(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        // Получаем только что вставленную запись
        sql = getSelectQueryInCategory() + " WHERE id = CURRVAL(pg_get_serial_sequence('sausageShop.category','id'))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            ArrayList<Category> list = parseResultSetCategory(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
           category = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return category;
    }

    public void deleteCategory(Category object) throws PersistException {
        String sql = getDeleteQueryInCategory();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public void deleteProduct(Product object) throws PersistException {
        String sql = getDeleteQueryInProduct();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public void update(Product product) throws PersistException {

        String sql = getUpdateQueryInCategory();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdateProduct(statement, product);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public SqlDao(Connection connection) {
        this.connection = connection;
    }

}
