
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

public class storeItemData {
    Connection conn;
    
    public storeItemData(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public String addTempItem(String name, int warranty, int qty, double w_price, double r_price, String date, String type, Component comp){
        try{
            String SQL= "insert into items(itm_code, itm_name, warranty, qty, w_price, r_price, date, type) values(?,?,?,?,?,?,?,?)";
            String itmCode = generateItemCode(comp);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, itmCode);
            //System.out.println("4");
            pst.setString(2, name);
            //System.out.println("5");
            pst.setInt(3, warranty);
            //System.out.println("6");
            pst.setInt(4, qty);
            //System.out.println("7");
            pst.setDouble(5, w_price);
            //System.out.println("8");
            pst.setDouble(6, r_price);
            //System.out.println("9");
            pst.setString(7, date);
            pst.setString(8, type);

            pst.execute();   
            
            return itmCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String updateTempItem(String name, int warranty, int qty, double w_price, double r_price, String date, String type, String supply, String itmCode, Component comp){
        try{
            String SQL= "update items set itm_name=?, warranty=?, qty=?, w_price=?, r_price=?, date=?, type=?, supply=? where itm_code=?"; 
            PreparedStatement pst = conn.prepareStatement(SQL);
 
            //System.out.println("4");
            pst.setString(1, name);
            //System.out.println("5");
            pst.setInt(2, warranty);
            //System.out.println("6");
            pst.setInt(3, qty);
            //System.out.println("7");
            pst.setDouble(4, w_price);
            //System.out.println("8");
            pst.setDouble(5, r_price);
            //System.out.println("9");
            pst.setString(6, date);
            pst.setString(7, type);
            pst.setString(8, supply);
            pst.setString(9, itmCode);

            pst.execute();   
            increaseNoOfItemsByOne();
            return itmCode;
        }
        catch(Exception ed){
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","DataBase Error", JOptionPane.ERROR_MESSAGE);
            ed.printStackTrace();
            return null;
        }
    }
    
    public String generateItemCode(Component comp){
        String type = "PD";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select item from count");
            while(rs.next()){
                noMovies = rs.getInt("item");
                
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
    
    public String getItemCode(Component comp){
        String type = "PD";
        int noMovies = 0;
        String DiskID = null;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet Rs = stmnt.executeQuery("select item from count");
            while(Rs.next()){
                noMovies = Rs.getInt("item");
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
    
    public String addSubItems(String sn, String itemCode, String stock,Component comp){
        try{
            String SQL= "insert into sub_items(sn, itm_code, inStock) values(?,?,?)";
            
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, sn);
            //System.out.println("4");
            pst.setString(2, itemCode);
            pst.setString(3, stock);
            pst.execute();
            
            return itemCode;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getSubItemData(String itemID){
       String idd = null;
       idd = itemID;
       String sql = "select sID as 'Sub Item Code', sn as 'Serial Num' from sub_items where itm_code ='"+idd+"' and inStock ='yes' ORDER BY sID DESC";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getAllSubItemData(String itemID){
       String idd = null;
       idd = itemID;
       String sql = "select sID as 'Sub Item Code', sn as 'Serial Num', inStock as 'Stock' from sub_items where itm_code ='"+idd+"' ORDER BY sID DESC";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getDiskID(Component comp){
        String diskID=null;
        String mID = getItemCode(comp);
        System.out.println(mID);
       
        try{
            Statement stmnt1 = conn.createStatement();
            ResultSet Rs1 = stmnt1.executeQuery("select diskID from movie where movieID ='"+mID+"'");
            while(Rs1.next()){
                diskID = Rs1.getString("diskID");
            }
            return diskID;
        }    
        
        catch(Exception e){
            return null;
        }
        
    }
    
    public void increaseNoOfItemsByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select item from count");
            while(rs.next()){
                noMovies = rs.getInt("item");
            }
            try{
                String SQL = "update count set item="+(noMovies+1)+" where item="+noMovies;
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
    
    public boolean checkItemRecordAvailability(Component comp){
        boolean available = false;
        ResultSet result = null;
        int total=0;
        
        try{
            String nextItem_id = generateItemCode(comp);
            System.out.println(nextItem_id);
            Statement stmnt3 = conn.createStatement();
            result = stmnt3.executeQuery("select qty from items where itm_code='"+nextItem_id+"'");
            while(result.next()){
                total = result.getInt("qty");
                System.out.println(total);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(total == -1){
            available = true;
        }
        else{
            available = false;
        }
        //System.out.println(available);
        return available;
        
    }
    
    public int getSubItemCount(String ItmCode){
        int count = 0;
        String code = null;
        code = ItmCode;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss = stmnt.executeQuery("select COUNT(sn) from sub_items where itm_code='"+code+"'");
            while(rss.next()){
                count = rss.getInt("COUNT(sn)");    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return count;
    }
    
    public boolean deleteSubItem(String subItmCode){
        String sql = "delete from sub_items where sID='"+subItmCode+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Single Item", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
 
    public boolean validateItmName(String name1){
        
        if(name1.equals("")){
            
            return false;
            
        }
        else if(name1.length()==1){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validatePackSize(String size){
        
        if(size.equals("")){
            
            return false;
        }
        else if(size.length()==1){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateRprice(String rPrice){
        
        if(rPrice.equals("")){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateWprice(String wPrice){
        
        if(wPrice.equals("")){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateQTY(String qty){
        
        if(qty.equals("")){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateCompamy(String com){
        if(com.equals("")){
            return false;
        }
        else if(com.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateEXPdate(String date){
        
        if(date.equals("")){
            return false;
        }
        else if(date.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
}
