
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

public class userController {
    Connection conn;
    
    public userController(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public void addNewUser(String uname, String pass, String power, Component comp){
        try{
            String SQL= "insert into user_info(u_name, password, power) values(?,?,?)";
            
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, uname);
            //System.out.println("4");
            pst.setString(2, pass);
            //System.out.println("5");
            pst.setString(3, power);
            //System.out.println("6");
            pst.execute();
            JOptionPane.showMessageDialog(comp, "User created Successfully!","User Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "User Added Failed","User Error", JOptionPane.ERROR_MESSAGE);      
        }
    }
    
    public ResultSet showAllPendingBillDetails(){     
        String SQl66 = "SELECT user_id as User ID, u_name as User Name FROM user_info";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rsh = stmt.executeQuery(SQl66);
            return rsh;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public void UpdateUser(String u_name, String pass, String power,int uID, Component comp){
        try{
           
        String sqlk = "update user_info set u_name=?, password=?, power=? where user_id=?";
        PreparedStatement psth = conn.prepareStatement(sqlk);
         //System.out.println("aaaaaaaaaa");
            psth.setString(1, u_name);
            //System.out.println("5");
            psth.setString(2, pass);
            //System.out.println("6");
            psth.setString(3, power);
            psth.setInt(4, uID);
            //System.out.println("7");
            psth.execute();
            //System.out.println("aaaaaaaaaa");
            JOptionPane.showMessageDialog(comp, "Updated Successfully!","User Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "User Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean deleteUser(int uID){
        String sql = "delete from user_info where user_id='"+uID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete User", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean validateAdminLoginDetails(String pw, String power, Component comp){
        boolean unOK = false;
        boolean pwOK = false;
        String user="";
        
        if(pw.equals("") | power.equals("")){
            //JOptionPane.showMessageDialog(comp,"Incorrect Password or No access !","Sign In Failed",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            try{
                Statement stmnt = conn.createStatement();
                ResultSet rsLoginfo = stmnt.executeQuery("select * from user_info");

                while(rsLoginfo.next()){
                    if(pw.equals(rsLoginfo.getString("password"))){
                        unOK = true;
                        if(power.equals(rsLoginfo.getString("power"))){
                            pwOK = true;
                            //Manue.accessedUser = rsLoginfo.getString("userID");
                        }
                    }  
                }

                if(unOK && pwOK){
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(comp,"Database connection failed!","Database Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
    
    public ResultSet getUserDetails(String IDDD){
       String SQL = "select * from user_info where user_id='"+IDDD+"'";
       
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
