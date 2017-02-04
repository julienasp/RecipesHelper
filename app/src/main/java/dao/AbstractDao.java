package dao;

import java.sql.Connection;

/**
* AbstractDao is an abstract class that shares the PostgreSQL connection
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public abstract class AbstractDao<T> {
    protected Connection connect = ConnectionPostgreSQL.getInstance();
    public abstract T find(Integer id);
}
