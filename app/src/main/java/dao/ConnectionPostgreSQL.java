package dao;

import android.util.Log;
import java.sql.*;
/**
 * Created by JUASP-G73 on 06/12/2016.
 */
public class ConnectionPostgreSQL {
    private static String url = "jdbc:postgresql://10.0.2.2:5432/IFT744";
    private static String user = "postgres";
    private static String passwd = "PG123!";
    private static Connection connect;

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
