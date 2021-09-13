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

public class quotation {
    Connection conn;
    
    public quotation(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public String addItemsToQuotation(String itemm_code, String itemm_name, int warranty, int q_qty, double r_price, String itm_date, String qid, double q_value, Component comp){
        try{
            String SQL3= "insert into quotation_itms(quotItemCode, itemm_code, itemm_name, warranty, q_qty, itm_date, r_price, qid, q_value) values(?,?,?,?,?,?,?,?,?)";
            
            String qoutItemCode = generateQoutationItemCode(comp);
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
            
            increaseNoOfQuotationItemsByOne();
            
            JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return qoutItemCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateQoutationItemCode(Component comp){
        String type = "QI";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select q_items from count");
            while(rs.next()){
                noMovies = rs.getInt("q_items");
                
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
    
    public void increaseNoOfQuotationItemsByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select q_items from count");
            while(rs.next()){
                noMovies = rs.getInt("q_items");
            }
            try{
                String SQL = "update count set q_items="+(noMovies+1)+" where q_items="+noMovies;
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
    
    public String addTempDataToQuotation(String q_date, double totall, int q_dis, double q_netAmount, int q_itmCount, String q_discription, String cust_id, Component comp){
        try{
            String SQL= "insert into qoutations(qid, q_date, totall, q_dis, q_netAmount, q_itmCount, q_discription, cust_id) values(?,?,?,?,?,?,?,?)";
            
            String qid = generateQuotation_id(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, qid);
            //System.out.println("4");
            pst.setString(2, q_date);
            //System.out.println("5");
            pst.setDouble(3, totall);
            //System.out.println("6");
            pst.setInt(4, q_dis);
            //System.out.println("7");
            pst.setDouble(5, q_netAmount);
            //System.out.println("8");
            pst.setInt(6, q_itmCount);
            //System.out.println("9");
            pst.setString(7, q_discription);
            //System.out.println("10");
            pst.setString(8, cust_id);
            //System.out.println("11");
            
            pst.execute();
            //System.out.println("12");
            //increaseNoOfBillByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return qid;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "temporary Row Add ERORR.!! ","bill Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String updateQuotationData(String q_date, double totall, int q_dis, double q_netAmount, int q_itmCount, String q_discription, String cust_id, String qid, Component comp){
        try{
            String SQL= "update qoutations set q_date=?, totall=?, q_dis=?, q_netAmount=?, q_itmCount=?, q_discription=?, cust_id=? where qid=?";

            PreparedStatement pst = conn.prepareStatement(SQL);
            
            //System.out.println("4");
            pst.setString(1, q_date);
            //System.out.println("5");
            pst.setDouble(2, totall);
            //System.out.println("6");
            pst.setInt(3, q_dis);
            //System.out.println("7");
            pst.setDouble(4, q_netAmount);
            //System.out.println("8");
            pst.setInt(5, q_itmCount);
            //System.out.println("9");
            pst.setString(6, q_discription);
            //System.out.println("10");
            pst.setString(7, cust_id);
            pst.setString(8, qid);
            //System.out.println("11");
            
            pst.execute();
            increaseNoOfQuotationByOne();
            
            JOptionPane.showMessageDialog(comp, "Quotation Created Successfully","Quotation Details",JOptionPane.INFORMATION_MESSAGE);
            return qid;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Quotation Creation failed.!","Quotation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }   

    public String generateQuotation_id(Component comp){
        String type = "Q";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select qid from count");
            while(rs.next()){
                noMovies = rs.getInt("qid");
                
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
    
    public String getQuotation_id(Component comp){
        String type = "Q";
        int noMovies = 0;
        String DiskID = null;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet Rs = stmnt.executeQuery("select qid from count");
            while(Rs.next()){
                noMovies = Rs.getInt("qid");
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
    
    public void increaseNoOfQuotationByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select qid from count");
            while(rs.next()){
                noMovies = rs.getInt("qid");
            }
            try{
                String SQL = "update count set qid="+(noMovies+1)+" where qid="+noMovies;
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
    
    public boolean deleteAddedQuotationItems(String quotItmCode){
        String sql = "delete from quotation_itms where quotItemCode='"+quotItmCode+"'";
        
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
    
    public double calculate_q_Total(String bill_id){
        double caldTotal = 0;
        String id = null;
       id = bill_id;
       try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(q_value) FROM quotation_itms where qid='"+id+"'");
            while(rs.next()){
                caldTotal = rs.getDouble("SUM(q_value)");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       System.out.println(caldTotal);
       return caldTotal;
    }
    
    public boolean checkQuotationRecordAvailability(Component comp){
        boolean availablee = false;
        ResultSet result = null;
        int totall=0;
        
        try{
            String nextQuotation_id = generateQuotation_id(comp);
            Statement stmnt33 = conn.createStatement();
            result = stmnt33.executeQuery("select totall from qoutations where qid='"+nextQuotation_id+"'");
            while(result.next()){
                totall = result.getInt("totall");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(totall == 1){
            availablee = true;
        }
        else{
            availablee = false;
        }
        //System.out.println(available);
        return availablee;
        
    }
    
    public ResultSet getAddedItemsBy_qid(String quot_id){
       String id = null;
       id = quot_id;
       String sql2 = "select quotItemCode as 'Quot Item ID', itemm_code as 'Item Code', itemm_name as 'Item Name', warranty as 'Warranty(M)', q_qty as 'QTY', r_price as 'r_price', q_value as 'Value' from quotation_itms where qid='"+id+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql2);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public boolean validateCustomer(String cust){
        if(cust.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateDiscription(String dis){
        if(dis.equals("")){
            return false;
        }     
        else{
            return true;
        }
    }
    
    public boolean validateQTY(String qtty){
        if((Integer.parseInt(qtty)) == 0){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateQTY1(String diss){
        if(diss.equals("")){
            return false;
        }     
        else{
            return true;
        }
    }
    
}
