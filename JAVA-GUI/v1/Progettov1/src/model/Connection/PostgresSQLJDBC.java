package model.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresSQLJDBC
{
    public final String url= "database-oobd.c9kspmggklai.eu-west-2.rds.ama  zonaws.com";
    public final String user = "postgres";
    public final String password = "Postgresebello1";


    public void connect()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,user,password);


            if(connection != null)
            {
                System.out.println("Connessione avvenuta");
            }
            else
                System.out.println("Connessione fallita");
        }
        catch (SQLException | ClassNotFoundException err)
        {
            err.printStackTrace();
        }
    }
}
