/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beSkilled.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mostafizur
 */
public class MySqlConnection {

    private static final String HOST = "jdbc:mysql://localhost:3306";
    private static final String DBNAME = "evd3";
    private static final String URL = HOST + "/" + DBNAME;
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(URL, "root", "1234");
            System.out.println("connection success");
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}
