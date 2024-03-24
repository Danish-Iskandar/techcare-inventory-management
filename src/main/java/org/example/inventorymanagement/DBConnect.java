package org.example.inventorymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Class;

public class DBConnect {

    private static String HOST = "jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare";
    private static int PORT = 3306;
    private static String DB_NAME = "mytechcare";
    private static String USERNAME = "admin";
    private static String PASSWORD = "shukri1234";
    public static Connection connection;


    public static Connection getConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare", "admin", "shukri1234");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}



