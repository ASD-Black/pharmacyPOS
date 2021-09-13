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
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class typesHandler {
    Connection conn;
    
    public typesHandler(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public void addJobType(String typeName1, Component comp){
        try{
            String SQL= "insert into job_typs(type_namee) values(?)";
            
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, typeName1);
            
            pst.execute();
            JOptionPane.showMessageDialog(comp, "Type created Successfully!","User Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Type Added Failed","User Error", JOptionPane.ERROR_MESSAGE);      
        }
    }
    
    public void addItemType(String typeName2, Component comp){
        try{
            String SQL= "insert into item_typs(type_name) values(?)";
            
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, typeName2);
            
            pst.execute();
            JOptionPane.showMessageDialog(comp, "Type created Successfully!","User Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Type Added Failed","User Error", JOptionPane.ERROR_MESSAGE);      
        }
    }
    
    public ResultSet showAllJobTypes(){   
        String SQl55 = "SELECT iddd as 'ID', type_namee as 'Type Name' FROM job_typs";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl55);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showAllItemTypes(){   
        String SQl55 = "SELECT iddd as 'ID', type_name as 'Type Name' FROM item_typs";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl55);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public boolean deleteJobType(int tID){
        String sql = "delete from job_typs where iddd='"+tID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Type", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteItemType(int tID){
        String sql = "delete from item_typs where iddd='"+tID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Type", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
