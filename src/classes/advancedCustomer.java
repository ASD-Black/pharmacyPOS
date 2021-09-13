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

public class advancedCustomer {
    
    Connection conn;
    
    public advancedCustomer(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public String addItemsToAdvancedCust(String itemm_code, String itemm_name, int warranty, int q_qty, double r_price, String itm_date, String qid, double q_value, Component comp){
        try{
            String SQL3= "insert into advancedcustitms(advancedCustItemID, avd_itemm_code, adv_itmName, adv_warranty, adv_qty, adv_itemDate, adv_retail, idadvancedCustID, adv_value) values(?,?,?,?,?,?,?,?,?)";
            
            String qoutItemCode = generateAdvancedCustItemCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, qoutItemCode);
            pst.setString(2, itemm_code);
            pst.setString(3, itemm_name); 
            pst.setInt(4, warranty); 
            pst.setInt(5, q_qty);
            pst.setString(6, itm_date);
            pst.setDouble(7, r_price);
            pst.setString(8, qid);
            pst.setDouble(9, q_value);

            pst.execute();
            
            increaseNoOfAdvancedCustItemsByOne();
            
            JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return qoutItemCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
    
    public String generateAdvancedCustItemCode(Component comp){
        String type = "AI";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select avd_cust_item_id from count");
            while(rs.next()){
                noMovies = rs.getInt("avd_cust_item_id");
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from count table","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        if(noMovies<9){
            movieID = type.concat("00000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<99){
            movieID = type.concat("0000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<999){
            movieID = type.concat("000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<9999){
            movieID = type.concat("00").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<99999){
            movieID = type.concat("0").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<999999){
            movieID = type.concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot GenerateID. Maximum No Of Items Reached", "Database Error", JOptionPane.ERROR_MESSAGE);
            return movieID;
        }
    }
    
    public void increaseNoOfAdvancedCustItemsByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select avd_cust_item_id from count");
            while(rs.next()){
                noMovies = rs.getInt("avd_cust_item_id");
            }
            try{
                String SQL = "update count set avd_cust_item_id="+(noMovies+1)+" where avd_cust_item_id="+noMovies;
                Statement stmnt2 = conn.createStatement();
                stmnt2.executeUpdate(SQL);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String addTempDataToAdvancedCust(String adv_date, double adv_total, double adv_amount, double adv_duePayment, int adv_itmCount, String adv_discrip, String cust_id, String statuss, Component comp){
        try{
            String SQLv= "insert into advancedcust(idadvancedCustID, adv_date, adv_total, adv_amount, adv_duePayment, adv_itmCount, adv_discrip, cust_id, statuss) values(?,?,?,?,?,?,?,?,?)";
            
            String adv_id = generateAdvancedCust_id(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQLv);
            pst.setString(1, adv_id);
            //System.out.println("4");
            pst.setString(2, adv_date);
            //System.out.println("5");
            pst.setDouble(3, adv_total);
            //System.out.println("6");
            pst.setDouble(4, adv_amount);
            //System.out.println("7");
            pst.setDouble(5, adv_duePayment);
            //System.out.println("8");
            pst.setInt(6, adv_itmCount);
            //System.out.println("9");
            pst.setString(7, adv_discrip);
            //System.out.println("10");
            pst.setString(8, cust_id);
            //System.out.println("11");
            pst.setString(9, statuss);
            pst.execute();
            //System.out.println("12");
            //increaseNoOfBillByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return adv_id;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "temporary Row Add ERORR.!! ","bill Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String updateAdvancedCustData(String adv_date, double adv_total, double adv_amount, double adv_duePayment, int adv_itmCount, String adv_discrip, String cust_id, String statuss, String qid, Component comp){
        try{
            String SQL6= "update advancedcust set adv_date=?, adv_total=?, adv_amount=?, adv_duePayment=?, adv_itmCount=?, adv_discrip=?, cust_id=?, statuss=? where idadvancedCustID=?";

            PreparedStatement pst = conn.prepareStatement(SQL6);
            
            //System.out.println("4");
             pst.setString(1, adv_date);
            //System.out.println("5");
            pst.setDouble(2, adv_total);
            //System.out.println("6");
            pst.setDouble(3, adv_amount);
            //System.out.println("7");
            pst.setDouble(4, adv_duePayment);
            //System.out.println("8");
            pst.setInt(5, adv_itmCount);
            //System.out.println("9");
            pst.setString(6, adv_discrip);
            //System.out.println("10");
            pst.setString(7, cust_id);
            //System.out.println("11");
            pst.setString(8, statuss);
         
            pst.setString(9, qid);
            //System.out.println("11");
            
            pst.execute();
            increaseNoOfAdvancedCustIDByOne();
            
            JOptionPane.showMessageDialog(comp, "Advanced invoice Created Successfully","Quotation Details",JOptionPane.INFORMATION_MESSAGE);
            return qid;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Advanced invoice Creation failed.!","Quotation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateAdvancedCust_id(Component comp){
        String type = "A";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select avd_cust_id from count");
            while(rs.next()){
                noMovies = rs.getInt("avd_cust_id");
                
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from count table","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        if(noMovies<9){
            movieID = type.concat("00000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<99){
            movieID = type.concat("0000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<999){
            movieID = type.concat("000").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<9999){
            movieID = type.concat("00").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<99999){
            movieID = type.concat("0").concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else if(noMovies<999999){
            movieID = type.concat(Integer.toString(noMovies+1));
            return movieID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot Generate ID. Maximum No Of Items Reached", "Database Error", JOptionPane.ERROR_MESSAGE);
            return movieID;
        }
    }
    
    public String getAdvancedCust_id(Component comp){
        String type = "A";
        int noMovies = 0;
        String DiskID = null;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet Rs = stmnt.executeQuery("select avd_cust_id from count");
            while(Rs.next()){
                noMovies = Rs.getInt("avd_cust_id");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Cannot retrive data from count table","Database Error",JOptionPane.ERROR_MESSAGE);
        }
        
        if(noMovies<9){
            movieID = type.concat("00000").concat(Integer.toString(noMovies));
            return movieID;
        }
        else if(noMovies<99){
            movieID = type.concat("0000").concat(Integer.toString(noMovies));
            return movieID;
        }
        else if(noMovies<999){
            movieID = type.concat("000").concat(Integer.toString(noMovies));
            return movieID;
        }
        else if(noMovies<9999){
            movieID = type.concat("00").concat(Integer.toString(noMovies));
            return movieID;
        }
        else if(noMovies<99999){
            movieID = type.concat("0").concat(Integer.toString(noMovies));
            return movieID;
        }
        else if(noMovies<999999){
            movieID = type.concat(Integer.toString(noMovies));
            return movieID;
        }
        else{
            JOptionPane.showMessageDialog(comp, "Cannot GenerateID. Maximum No Of Items Reached", "Database Error", JOptionPane.ERROR_MESSAGE);
            return movieID;
        }
        
    }
    
    public void increaseNoOfAdvancedCustIDByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select avd_cust_id from count");
            while(rs.next()){
                noMovies = rs.getInt("avd_cust_id");
            }
            try{
                String SQL = "update count set avd_cust_id="+(noMovies+1)+" where avd_cust_id="+noMovies;
                Statement stmnt2 = conn.createStatement();
                stmnt2.executeUpdate(SQL);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getAddedItemsBy_advancedCust_ID(String iid){
       String id = null;
       id = iid;
       String sql2 = "select advancedCustItemID as 'Advanced Item ID', avd_itemm_code as 'Item Code', adv_itmName as 'Item Name', adv_warranty as 'Warranty(M)', adv_qty as 'QTY', adv_retail as 'r_price', adv_value as 'Value' from advancedcustitms where idadvancedCustID='"+id+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql2);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getAdvancedCustBy_id(String iid){
       String id = null;
       id = iid;
       String sql2 = "select ad.idadvancedCustID as 'Advanced ID', ad.adv_date as 'Date', ad.adv_amount as 'Advanced Amount', ad.adv_total as 'Total Amount', ad.adv_discrip as 'Discription', cu.name as 'Customer' FROM customers cu INNER JOIN advancedcust ad ON cu.cust_id = ad.cust_id WHERE ad.cust_id = ad.cust_id AND ad.statuss='Not Completed' AND ad.idadvancedCustID like '%"+id+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql2);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    public ResultSet getAdvancedCustBy_CustName(String name){
       String id = null;
       id = name;
       String sql2 = "select ad.idadvancedCustID as 'Advanced ID', ad.adv_date as 'Date', ad.adv_amount as 'Advanced Amount', ad.adv_total as 'Total Amount', ad.adv_discrip as 'Discription', cu.name as 'Customer' FROM customers cu INNER JOIN advancedcust ad ON cu.cust_id = ad.cust_id WHERE ad.cust_id = ad.cust_id AND ad.statuss='Not Completed' AND cu.name like '%"+name+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql2);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public double calculate_adv_Total(String avd_id){
        double caldTotal = 0;
        String id = null;
       id = avd_id;
       try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(adv_value) FROM advancedcustitms where idadvancedCustID='"+id+"'");
            while(rs.next()){
                caldTotal = rs.getDouble("SUM(adv_value)");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       System.out.println(caldTotal);
       return caldTotal;
    }
    
    public boolean checkAdvancedCustRecordAvailability(Component comp){
        boolean availablee1 = false;
        ResultSet result = null;
        int totall=0;
        
        try{
            String AdvancedCust_id = generateAdvancedCust_id(comp);
            Statement stmnt33 = conn.createStatement();
            result = stmnt33.executeQuery("select adv_total from advancedcust where idadvancedCustID='"+AdvancedCust_id+"'");
            while(result.next()){
                totall = result.getInt("adv_total");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(totall == 1){
            availablee1 = true;
        }
        else{
            availablee1 = false;
        }
        //System.out.println(available);
        return availablee1;
        
    }
    
    public boolean deleteAddedAdvancedCustItems(String advCustID){
        String sql = "delete from advancedcustitms where advancedCustItemID='"+advCustID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Item", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
