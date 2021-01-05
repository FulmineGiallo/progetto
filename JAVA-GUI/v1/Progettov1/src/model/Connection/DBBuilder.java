package model.Connection;

import java.sql.Connection;

public class DBBuilder
{
    private Connection connection;

    public DBBuilder(Connection connection) {
        this.connection = connection;
    }

    public DBBuilder() {
        connection = null;
    }

    private boolean connectionExists() {
        return !(connection == null);
    }
}