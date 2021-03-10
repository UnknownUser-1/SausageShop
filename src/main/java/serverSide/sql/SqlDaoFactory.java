package serverSide.sql;

import serverSide.dao.DaoFactory;
import serverSide.dao.GenericDao;
import serverSide.dao.PersistException;

import java.sql.Connection;

public class SqlDaoFactory implements DaoFactory<Connection> {

    /**
     * Возвращает подключение к базе данных
     */
    @Override
    public Connection getContext() throws PersistException {
        return null;
    }

    /**
     * Возвращает объект для управления персистентным состоянием объекта
     *
     * @param connection
     * @param dtoClass
     */
    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        return null;
    }
}
