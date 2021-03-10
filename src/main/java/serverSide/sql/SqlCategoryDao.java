package serverSide.sql;

import sausageShop.models.Category;
import serverSide.dao.AbstractJDBCDao;
import serverSide.dao.DaoFactory;
import serverSide.dao.PersistException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SqlCategoryDao extends AbstractJDBCDao<Category, Integer> {


    public SqlCategoryDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    private class PersistCategory extends Category{
        public void setId(int id){super.setId(id); }
    }


    /**
     * Возвращает sql запрос для получения всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    @Override
    public String getSelectQuery() {
        return null;
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
    protected List<Category> parseResultSet(ResultSet rs) throws PersistException {
        return null;
    }

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     */
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Category object) throws PersistException {

    }

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     *
     * @param statement
     * @param object
     */
    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Category object) throws PersistException {

    }

    /**
     * Создает новую запись и соответствующий ей объект
     */
    @Override
    public Category create() throws PersistException {
        return null;
    }

}
