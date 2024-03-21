package org.example.inventorymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    private static String HOST = "jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare";
    private static int PORT = 3306;
    private static String DB_NAME = "mytechcare";
    private static String USERNAME = "admin";
    private static String PASSWORD = "shukri1234";
    private static Connection connection ;


    public static Connection getConnect (){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://techcaredb.cbg6264eke46.ap-southeast-2.rds.amazonaws.com :3306/mytechcare", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return  connection;
    }





}
