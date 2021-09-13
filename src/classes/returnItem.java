
package classes;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.*;

public class returnItem {
    
    Connection conn;
    
    public returnItem(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public ResultSet getInDetails(String itemCode){
       String SQL = "select * from items where itm_code='"+itemCode+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(SQL);
           return rs;
       }
       catch(Exception e){
           return null;
       }
   } 
}
