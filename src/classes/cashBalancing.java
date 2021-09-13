
package classes;

import java.sql.ResultSet;
import java.sql.Statement;
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

public class cashBalancing {    
    Connection conn;
    
    public cashBalancing(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    
    public String addDebitePayments(String note, double amt, String datee, String timee, Component comp){
        try{
            String SQLL= "insert into debited(debitCode, note, amount, debited_date, debited_time) values(?,?,?,?,?)";
            
            String repID = generateDebitedPaymentCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pstdr = conn.prepareStatement(SQLL);
            pstdr.setString(1, repID);
            //System.out.println("4");
            pstdr.setString(2, note);
            //System.out.println("5");
            pstdr.setDouble(3, amt);
            //System.out.println("6");
            pstdr.setString(4, datee);
            
            pstdr.setString(5, timee);

            pstdr.execute();
            //System.out.println("12");
            increaseNoOfDebitedPaymentCodeByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return repID;
        }
        catch(Exception e){
            System.out.println("dibited payment error");
            //JOptionPane.showMessageDialog(comp, "Employee Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateDebitedPaymentCode(Component comp){
        String type = "DP";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select debitedP from count");
            while(rs.next()){
                noMovies = rs.getInt("debitedP");
                
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
    
    public void increaseNoOfDebitedPaymentCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select debitedP from count");
            while(rs.next()){
                noMovies = rs.getInt("debitedP");
            }
            try{
                String SQL = "update count set debitedP="+(noMovies+1)+" where debitedP="+noMovies;
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
    
    public String addCreditedPayments(String notee, double amtt, String dateee,String time, Component comp){
        try{
            String SQLLL= "insert into credited(creditCode, note, amount, credited_date, credited_time) values(?,?,?,?,?)";
            
            String CreditedPaymentID = generateCreditedPaymentCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pstdr = conn.prepareStatement(SQLLL);
            pstdr.setString(1, CreditedPaymentID);
            //System.out.println("4");
            pstdr.setString(2, notee);
            //System.out.println("5");
            pstdr.setDouble(3, amtt);
            //System.out.println("6");
            pstdr.setString(4, dateee);
            pstdr.setString(5, time);

            pstdr.execute();
            //System.out.println("12");
            increaseNoOfCreditedPaymentCodeByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return CreditedPaymentID;
        }
        catch(Exception e){
            System.out.println("credites payment error");
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Employee Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateCreditedPaymentCode(Component comp){
        String type = "CP";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select creditedP from count");
            while(rs.next()){
                noMovies = rs.getInt("creditedP");
                
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
    
    public void increaseNoOfCreditedPaymentCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select creditedP from count");
            while(rs.next()){
                noMovies = rs.getInt("creditedP");
            }
            try{
                String SQL = "update count set creditedP="+(noMovies+1)+" where creditedP="+noMovies;
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
    
    public String addReturnedItems(double returnAmt, String itemCodee, String itemName, int r_qty, String bill_id, String date, String SNN, String note, Component comp){
        try{
            String SQLLL1= "insert into returneditmlist(returnCode, returnAmt, itemCodee, r_qty, bill_id, datee, SNn,notee, itemName) values(?,?,?,?,?,?,?,?,?)";
            
            String returnItemCode = generateReturnItemsCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pstdr = conn.prepareStatement(SQLLL1);
            pstdr.setString(1, returnItemCode);
            //System.out.println("4");
            pstdr.setDouble(2, returnAmt);
            //System.out.println("5");
            pstdr.setString(3, itemCodee);
            //System.out.println("6");
            pstdr.setInt(4, r_qty);
            pstdr.setString(5, bill_id);
            pstdr.setString(6, date);
            pstdr.setString(7, SNN);
            pstdr.setString(8, note);
            pstdr.setString(9, itemName);

            pstdr.execute();
            increaseNoOfReturnItemsCodeByOne();
           
            return returnItemCode;
        }
        catch(Exception e){
            System.out.println("add Returned items error");
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Employee Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateReturnItemsCode(Component comp){
        String type = "R";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select return_itms from count");
            while(rs.next()){
                noMovies = rs.getInt("return_itms");
                
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
    
    public void increaseNoOfReturnItemsCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select return_itms from count");
            while(rs.next()){
                noMovies = rs.getInt("return_itms");
            }
            try{
                String SQL = "update count set return_itms="+(noMovies+1)+" where return_itms="+noMovies;
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
    
    public String addWarrantyItems(String notee, String itmDate, int warranty, String wDate, String invoID, String claim,String serial, Component comp){
        try{
            String SQLLL1= "insert into warranty(wCode, notee, itmDate, warranty, wDate, invoID, claim, serialNum) values(?,?,?,?,?,?,?,?)";
            
            String WarrantyCode = generateWarrantyCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pstdr = conn.prepareStatement(SQLLL1);
            pstdr.setString(1, WarrantyCode);
            //System.out.println("4");
            pstdr.setString(2, notee);
            //System.out.println("5");
            pstdr.setString(3, itmDate);
            //System.out.println("6");
            pstdr.setInt(4, warranty);
            pstdr.setString(5, wDate);
            pstdr.setString(6, invoID);
            pstdr.setString(7, claim);
            pstdr.setString(8, serial);
         
            pstdr.execute();
            increaseNoOfWarrantyCodeByOne();
           
            return WarrantyCode;
        }
        catch(Exception e){ 
            JOptionPane.showMessageDialog(comp, "Warranty Added Failed","Warranty Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateWarrantyCode(Component comp){
        String type = "W";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select warranty from count");
            while(rs.next()){
                noMovies = rs.getInt("warranty");
                
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
    
    public void increaseNoOfWarrantyCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select warranty from count");
            while(rs.next()){
                noMovies = rs.getInt("warranty");
            }
            try{
                String SQL = "update count set warranty="+(noMovies+1)+" where warranty="+noMovies;
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
    
    
    public double calculateCreditedMoney(String date){
        double calCred = 0;
        String id = null;
        id = date;
        try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(amount) FROM credited where credited_date='"+id+"'");
            while(rs.next()){
                calCred = rs.getDouble("SUM(amount)");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       
        return calCred;
    }
    
    public double calculateDebitedMoney(String date1){
        double calDeb = 0;
        String id1 = null;
        id1 = date1;
        try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(amount) FROM debited where debited_date='"+id1+"'");
            while(rs.next()){
                calDeb = rs.getDouble("SUM(amount)");

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       
        return calDeb;
    }
    
    public double calculateInvoiceTotalForDay(String date1){
        double calDeb = 0;
        String id1 = null;
        id1 = date1;
        try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(net_amount) FROM bill where bill_date='"+id1+"'");
            while(rs.next()){
                calDeb = rs.getDouble("SUM(net_amount)");

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       
        return calDeb;
    }
    
    public boolean deleteDebitedPayment(String dID){
        String sql = "delete from debited where debitCode='"+dID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete debited payment", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteCreditPayment(String ccID){
        String sql = "delete from credited where creditCode='"+ccID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete credited payment", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteWarrantyItem(String WID){
        String sql = "delete from warranty where wCode='"+WID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public boolean deleteClaimedWarranty(String WrID){
        String sql = "delete from warranty_claims where warrantyItemId='"+WrID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){  
            return false;
        }
    }
    public boolean UpdateSerialDiscription(String invoID, String dis){
        String sql = "update invoice_discription set warranty_dis='"+dis+"' where invoice_id='"+invoID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            //JOptionPane.showMessageDialog(null, "Cannot Delete Warranty Item", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public String getInvoiceWarrantyDiscription(String billID){
       String des = "";
       String sql3456 = "select warranty_dis from invoice_discription where invoice_id='"+billID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                des = rsagg.getString("warranty_dis");  
            }
           return des;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getWarrantyClaimDiscription(String wCode){
       String des = "";
       String sql3456 = "select Note from warranty_claims where warrantyItemId='"+wCode+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                des = rsagg.getString("Note");  
            }
           return des;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getItemCodeBySn(String sn){
       String item = "";
       String sql3456 = "select itm_code from sub_items where sn='"+sn+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                item = rsagg.getString("itm_code");  
            }
           return item;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getInvoiceDis(String invoID){
       String item = "";
       String sql3456 = "select bill_discrip from bill where bill_id='"+invoID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                item = rsagg.getString("bill_discrip");  
            }
           return item;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getAllNonClaimedWarranties(){
       String id = null;

       String sql22 = "select wCode as 'Warranty ID', notee as 'Note', itmDate as 'Item Invo Date', warranty as 'Warranty(M)', wDate as 'Warranty Date', invoID as 'Invoice No', serialNum as 'Item Serial', claim as 'Claim', claimedSerial as 'Claimed SN' from warranty where claim='no' ORDER BY wDate ASC";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsl = stmnt.executeQuery(sql22);
           return rsl;
       }
       catch(Exception e){
           return null;
       }
    }
    public ResultSet getAllReturnItemsByInvoice(String invoID){
       String id = null;

       String sql22 = "select returnCode as 'Return ID', itemCodee as 'Product Code',itemName as 'Product', r_qty as 'QTY', bill_id as 'Invoice No', datee as 'Returned Date', SNn as 'Serial' from returneditmlist where bill_id='"+invoID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsl = stmnt.executeQuery(sql22);
           return rsl;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getAllNonClaimedWarrantiesByInvoice(String invoID){
       String id = null;

       String sql22 = "select wCode as 'Warranty ID', notee as 'Note', itmDate as 'Item Invo Date', warranty as 'Warranty(M)', wDate as 'Warranty Date', invoID as 'Invoice No', serialNum as 'Item Serial', claim as 'Claim', claimedSerial as 'Claimed SN' from warranty where claim='no' and invoID like '%"+invoID+"%' ORDER BY wDate ASC";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsl = stmnt.executeQuery(sql22);
           return rsl;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String checkSNAvailabilityInWarranty(String snn){
       String qqttyy = "";
       String sql3456 = "select wCode from warranty where serialNum='"+snn+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                qqttyy = rsagg.getString("wCode");  
            }
           return qqttyy;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getAllClaimedWarranties(){
       String id = null;

       String sql23 = "select wCode as 'Warranty ID', notee as 'Note', itmDate as 'Item Invo Date', warranty as 'Warranty(M)', wDate as 'Warranty Date', invoID as 'Invoice No', serialNum as 'Item Serial', claim as 'Claim', claimedSerial as 'Claimed SN' from warranty where claim='yes' ORDER BY wDate ASC";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsp = stmnt.executeQuery(sql23);
           return rsp;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet showDebitedDetailsByDate(String date){
        String cty = null;
        cty = date;
        String SQl5 = "select debitCode as 'Code', note as 'Note', amount as 'Debited Amount', debited_date as 'Date', debited_time as 'Time' from debited where debited_date='"+date+"'";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showCreditedDetailsByDate(String datee){
        String cty = null;
        cty = datee;
        String SQl5 = "select creditCode as 'Code', note as 'Note', amount as 'Credited Amount', credited_date as 'Date', credited_time as 'Time' from credited where credited_date='"+datee+"'";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public boolean validatecashIn(String in){
        
        if(in.equals("")){
            
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validatecashOut(String out){
        
        if(out.equals("")){
            
            return false;
        }
        else{
            return true;
        }
    }
}
