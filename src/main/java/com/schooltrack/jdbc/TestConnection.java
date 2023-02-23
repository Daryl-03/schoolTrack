package com.schooltrack.jdbc;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DBManager.getConnection()) {
            System.out.println("Connection successful !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
