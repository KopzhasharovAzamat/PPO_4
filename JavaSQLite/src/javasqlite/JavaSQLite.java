
package javasqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;


public class JavaSQLite {
    Connection conn = null;
    public static Connection checkSql(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:mySQLite.db");
            System.out.println("Success");
            return conn;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Failure");
            return null;
        }
    }
    public static void main(String[] args) {
        Connection conn = checkSql();
    }
}
