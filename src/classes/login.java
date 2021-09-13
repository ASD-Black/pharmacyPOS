
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

public class login {
    Connection conn;
    
    public login(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public ResultSet getSelectedUserDetails(String usr){
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rsUserDetails = stmnt.executeQuery("select * from user_info where u_name='"+usr+"'");
            return rsUserDetails; 
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getExistingUsers(){
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rsUsers = stmnt.executeQuery("select u_name from user_info");
            return rsUsers; 
        }
        catch(Exception e){
            return null;
        }
    }
    
    public boolean validateLoginDetails(String usrn, String pw, Component comp){
        boolean unOK = false;
        boolean pwOK = false;
        String user="";
        
        if(usrn.equals("") | pw.equals("")){
            JOptionPane.showMessageDialog(comp,"Incorrect Username or Password !","Sign In Failed",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            try{
                Statement stmnt = conn.createStatement();
                ResultSet rsLoginfo = stmnt.executeQuery("select * from user_info");

                while(rsLoginfo.next()){
                    if(usrn.equals(rsLoginfo.getString("username"))){
                        unOK = true;
                        if(pw.equals(rsLoginfo.getString("password"))){
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
}
