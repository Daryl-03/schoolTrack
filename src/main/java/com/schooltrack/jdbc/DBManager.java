package com.schooltrack.jdbc;

import com.schooltrack.exceptions.DBHandlingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection connection;
    
    private DBManager() {
    
    }
    
    public static Connection getConnection() throws DBHandlingException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/dbproducts", "rich", "rich003!");
            } catch (ClassNotFoundException e) {
                throw new DBHandlingException("Driver Class not found : '" + e.getMessage() + "' ");
            } catch (SQLException e) {
                throw new DBHandlingException("Error : Unable to open connection with database !");
            }
        }
        return connection;
    }
}
