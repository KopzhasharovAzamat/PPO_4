/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamssql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Adrian
 */
public class ConnectMSSQL {
    Connection conn = null;
    public static Connection ConnectDB(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connstring = "jdbc:sqlserver://DESKTOP-2BAMTAJ\\SQL_2022;encrypt=true;trustServerCertificate=true;databaseName=javaDb;user=sa;password=123;";
            Connection conn = DriverManager.getConnection(connstring);
            System.out.println("Success");
            return conn;
        } catch (Exception e){
            System.out.println("Failure\n" + e.getMessage());
            return null;
        }
    }
    public static void main(String[] args){
        ConnectDB();
    }
}
