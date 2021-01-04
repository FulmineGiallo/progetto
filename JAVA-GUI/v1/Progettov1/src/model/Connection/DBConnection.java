package model.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private static DBConnection instance;
    private final String IP= "database-oobd.c9kspmggklai.eu-west-2.rds.amazonaws.com";
    private final String port = "5432";
    private final String user = "postgres";
    private final String password = "Postgresebello1";
    private final String DBname = "Azienda";

    private String url = "jdbc:postgresql://"+IP+":"+port+"/"+ DBname;

    private Connection connection = null;

    public DBConnection() throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,user,password);
        }
        catch (SQLException | ClassNotFoundException err)
        {
            err.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null)
        {
            instance = new DBConnection();
        }
        else
        {
            if (instance.getConnection().isClosed()) {
                instance = new DBConnection();
            }
        }
        return instance;
    }

}
