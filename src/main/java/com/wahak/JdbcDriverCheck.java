package com.wahak;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author krishna.meena
 */
public class JdbcDriverCheck {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mariadb://srv1826.hstgr.io:3306/u399393185_test";
        String user = "u399393185_deepak";
        String password = "AJTourAndTravel@123";

        Connection conn = DriverManager.getConnection(url, user, password);
        DatabaseMetaData metaData = conn.getMetaData();

        System.out.println("JDBC Driver Name: " + metaData.getDriverName());
        System.out.println("JDBC Driver Version: " + metaData.getDriverVersion());
        conn.close();
    }
}
