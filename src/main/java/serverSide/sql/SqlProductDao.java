package serverSide.sql;


import sausageShop.models.Product;
import serverSide.dao.AbstractJDBCDao;
import serverSide.dao.DaoFactory;
import serverSide.dao.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SqlProductDao extends AbstractJDBCDao<Product,Integer> {


    public SqlProductDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
        addRelation(Product.class,"category");
    }

    /**
     * Возвращает sql запрос для получения всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    @Override
    public String getSelectQuery() {
        return "SELECT * FROM sausage.product;";
    }

    /**
     * Возвращает sql запрос для вставки новой записи в базу данных.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    @Override
    public String getCreateQuery() {
        return null;
    }

    /**
     * Возвращает sql запрос для обновления записи.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    @Override
    public String getUpdateQuery() {
        return null;
    }

    /**
     * Возвращает sql запрос для удаления записи из базы данных.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    @Override
    public String getDeleteQuery() {
        return null;
    }

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     *
     * @param rs
     */
    @Override
    protected List<Product> parseResultSet(ResultSet rs) throws PersistException {
        return null;
    }

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     */
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Product object) throws PersistException {

    }

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     */
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Product object) throws PersistException {

    }

    /**
     * Создает новую запись и соответствующий ей объект
     */
    @Override
    public Product create() throws PersistException {
        return null;
    }
}
