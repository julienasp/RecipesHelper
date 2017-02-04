package dao;

import android.util.Log;
import java.sql.*;
/**
* ConnectionPostgreSQL knows all the detail about the database connection
*
* @author  Julien Aspirot
* @version 1.0
* @since   2017-02-04 
*/
public class ConnectionPostgreSQL {
    private static String url = "jdbc:postgresql://10.0.2.2:5432/IFT744";
    private static String user = "postgres";
    private static String passwd = "PG123!";
    private static Connection connect;

    
   /**
   * This method is used to get the instance of the database connection  
   * @return Connection it returns a connection object
   */
    public static Connection getInstance(){

        if(connect == null){
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                Log.e("ConnectionPostgreSQL",e.toString());
            } catch (ClassNotFoundException e) {
                Log.e("ConnectionPostgreSQL",e.toString());
            }
        }
        return connect;
    }
}
