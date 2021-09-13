/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class editItemData {
     Connection conn;
    
    public editItemData(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public void UpdateItemData(String name, int packSize, int qty, double w_price, double r_price, String date,String type, String itm_code, Component comp){
        try{
           
        String sql = "update items set itm_name=?, warranty=?, qty=?, w_price=?, r_price=?, date=?, type=? where itm_code=?";
        PreparedStatement pst = conn.prepareStatement(sql);
         //System.out.println("aaaaaaaaaa");
            
            pst.setString(1, name);
            pst.setInt(2, packSize);
            pst.setInt(3, qty);
            pst.setDouble(4, w_price);
            pst.setDouble(5, r_price);
            pst.setString(6, date);
            pst.setString(7, type);
            pst.setString(8, itm_code);
            
            pst.execute();
            //System.out.println("aaaaaaaaaa");
            JOptionPane.showMessageDialog(comp, "Item Updated Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
