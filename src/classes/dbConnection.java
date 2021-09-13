
package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.*;

public class dbConnection {
    Connection conn = null;
    
    public Connection Connect(){
        String conURL = "jdbc:mysql://localhost:3306/pharmacy_db?autoReconnect=true&useSSL=false";
        String conUN = "root";
        String conPW = "root";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection(conURL,conUN,conPW);
            return dbcon;
        }
        catch(Exception e){
            System.out.println("jhgasfjgsighsdhfui");
            JOptionPane.showMessageDialog(null, "Database Connection Error", "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
            
        }
    }
}
