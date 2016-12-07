package dao;

import java.sql.Connection;

/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public abstract class AbstractDao<T> {
    protected Connection connect = ConnectionPostgreSQL.getInstance();
    public abstract T find(Integer id);
}
