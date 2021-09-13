
package classes;
import java.awt.Component;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;
import java.util.*;
import java.util.function.Supplier;

public class items_add_to_bill {
    Connection conn;
    
    public items_add_to_bill(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public String addItemsToBill(String itemCode, String name, int packSize, int qty, int free, double w_price,int dis, double r_price, String exp_date, double value,  String bill_id,double costValue, String cashBalanceID, Component comp){
        try{
            String SQL3= "insert into bill_items(bill_item_id, item_code, item_name, pack_size, qty, free, w_price, dis, r_price, exp_date, value, bill_id, costValue, cashBalID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            String billItemCode = generateBillItemCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, billItemCode);
            pst.setString(2, itemCode);
            
            pst.setString(3, name);
            
            pst.setInt(4, packSize);
            
            pst.setInt(5, qty);
            pst.setInt(6, free);
            
            pst.setDouble(7, w_price);
            pst.setInt(8, dis);
            
            pst.setDouble(9, r_price);
            pst.setString(10, exp_date);
            
            pst.setDouble(11, value);
            
            pst.setString(12, bill_id);
            pst.setDouble(13, costValue);
            pst.setString(14, cashBalanceID);
            
            pst.execute();
            
            increaseNoOfBillItemsByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return billItemCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
    
    public String addBillSubItems(String snnn, String billItmCode, Component comp){
        try{
            String SQL3= "insert into bill_sub_items(snn, bill_item_id) values(?,?)";

            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, snnn);
            pst.setString(2, billItmCode);

            pst.execute();
            
            increaseNoOfBillItemsByOne();
            return billItmCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateBillItemCode(Component comp){
        String type = "BI";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select bill_itms from count");
            while(rs.next()){
                noMovies = rs.getInt("bill_itms");
                
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
    
    public String getBillItemCode(Component comp){
        String type = "BI";
        int noMovies = 0;
        String DiskID = null;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet Rs = stmnt.executeQuery("select bill_itms from count");
            while(Rs.next()){
                noMovies = Rs.getInt("bill_itms");
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
    
//    public String getDiskID(Component comp){
//        String diskID=null;
//        String mID = getBillItemCode(comp);
//        System.out.println(mID);
//       
//        try{
//            Statement stmnt1 = conn.createStatement();
//            ResultSet Rs1 = stmnt1.executeQuery("select diskID from movie where movieID ='"+mID+"'");
//            while(Rs1.next()){
//                diskID = Rs1.getString("diskID");
//            }
//            return diskID;
//        }    
//        
//        catch(Exception e){
//            return null;
//        }
//        
//    }
    
    public void increaseNoOfBillItemsByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select bill_itms from count");
            while(rs.next()){
                noMovies = rs.getInt("bill_itms");
            }
            try{
                String SQL = "update count set bill_itms="+(noMovies+1)+" where bill_itms="+noMovies;
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
    
    public double calculateTotal(String bill_id){
        double caldTotal = 0;
        String id = null;
       id = bill_id;
       try{
            Statement stmnt5 = conn.createStatement();
            ResultSet rs = stmnt5.executeQuery("SELECT SUM(value) FROM bill_items where bill_id='"+id+"'");
            while(rs.next()){
                caldTotal = rs.getDouble("SUM(value)");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       System.out.println(caldTotal);
       return caldTotal;
    }
    
    
    
    public ResultSet getAddedItemsBy_bill_ID(String bill_ID){
       String id = null;
       id = bill_ID;
       System.out.println(id);
       String sql2 = "select bill_item_id as 'Bill Item Code', item_code as 'Item Code', item_name as 'Item Name', pack_size as 'Warranty(M)', qty as 'QTY', r_price as 'r_price', exp_date as 'Date', value as 'Value', cashBalID as 'Cash Bal Code' from bill_items where bill_id='"+id+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql2);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet getSubItemDataFromBill(String billIDDD){
       String idd1 = null;
       idd1 = billIDDD;
       String sql = "select idbill_sub_itemsID as 'ID', snn as 'Serial Numbers' from bill_sub_items where bill_item_id ='"+idd1+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String addTempDataToBill(String bill_date, double total, int bill_dis, String cust_id, String e_id, double net_amount, int setle_days, String status, int days, Component comp){
        try{
            String SQL= "insert into bill(bill_id, bill_date, total, bill_dis, cust_id, e_id, net_amount, setle_days, status, days) values(?,?,?,?,?,?,?,?,?,?)";
            
            String bill_id = generateBill_id(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, bill_id);
            //System.out.println("4");
            pst.setString(2, bill_date);
            //System.out.println("5");
            pst.setDouble(3, total);
            //System.out.println("6");
            pst.setInt(4, bill_dis);
            //System.out.println("7");
            pst.setString(5, cust_id);
            //System.out.println("8");
            pst.setString(6, e_id);
            //System.out.println("9");
            pst.setDouble(7, net_amount);
            //System.out.println("10");
            pst.setInt(8, setle_days);
            //System.out.println("11");
            pst.setString(9, status);
            pst.setInt(10, days);
            pst.execute();
            //System.out.println("12");
            //increaseNoOfBillByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return bill_id;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "temporary Row Add ERORR.!! ","bill Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateBill_id(Component comp){
        String type = "MS";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select bill from count");
            while(rs.next()){
                noMovies = rs.getInt("bill");
                
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
    
    public String getBill_id(Component comp){
        String type = "MS";
        int noMovies = 0;
        String DiskID = null;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet Rs = stmnt.executeQuery("select bill from count");
            while(Rs.next()){
                noMovies = Rs.getInt("bill");
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
    
    public void increaseNoOfBillByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select bill from count");
            while(rs.next()){
                noMovies = rs.getInt("bill");
            }
            try{
                String SQL = "update count set bill="+(noMovies+1)+" where bill="+noMovies;
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
    
    public boolean checkBillRecordAvailability(Component comp){
        boolean available = false;
        ResultSet result = null;
        int total=0;
        
        try{
            String nextBill_id = generateBill_id(comp);
            Statement stmnt3 = conn.createStatement();
            result = stmnt3.executeQuery("select total from bill where bill_id='"+nextBill_id+"'");
            while(result.next()){
                total = result.getInt("total");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(total == 1){
            available = true;
        }
        else{
            available = false;
        }
        //System.out.println(available);
        return available;
        
    }
    
    public boolean UpdateBillData(String bill_date, double total, int bill_dis, String cust_id, String e_id, double net_amount, int setle_days, String status, int itmCount, int no_ex_bonous, String advancedCode, double advAmount, String invoiceType, String recevingDate, String creditCodeChequeCode, double grangAmount, String bill_time, String discription, String bill_id, Component comp){
        boolean isKo = false;
        try{
           
        String sql = "update bill set bill_date=?, total=?, bill_dis=?, cust_id=?, e_id=?, net_amount=?, setle_days=?,status=?, itm_count=?, no_ex_bonous=?, advancedCode=?, advAmount=?, invoiceType=?, recevingDate=?, creditCodeChequeCode=?, grangAmount=?, bill_time=?, bill_discrip=? where bill_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, bill_date);
            pst.setDouble(2, total);
            pst.setInt(3, bill_dis);
            pst.setString(4, cust_id);
            pst.setString(5, e_id);
            pst.setDouble(6, net_amount);
            pst.setInt(7, setle_days);
            pst.setString(8, status);
            pst.setInt(9, itmCount);
            pst.setInt(10, no_ex_bonous);
            pst.setString(11, advancedCode);
            pst.setDouble(12, advAmount);
            pst.setString(13, invoiceType);
            pst.setString(14, recevingDate);
            pst.setString(15, creditCodeChequeCode);
            pst.setDouble(16, grangAmount);
            pst.setString(17, bill_time);
            pst.setString(18, discription);
            pst.setString(19, bill_id);
            
            pst.execute();
            increaseNoOfBillByOne();
            
            JOptionPane.showMessageDialog(comp, "Invoice Created Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
            return isKo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Invoice Creation failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            return isKo = false;
        }
    }
    
    public boolean EditBillData(double total, int bill_dis, String cust_id, String e_id, double net_amount, int itmCount, String bill_discrip, String bill_id, Component comp){
        boolean isKo = false;
        try{
           
        String sql = "update bill set total=?, bill_dis=?, cust_id=?, e_id=?, net_amount=?, itm_count=?, bill_discrip=? where bill_id=?";
        PreparedStatement pst = conn.prepareStatement(sql); 
            pst.setDouble(1, total);
            pst.setInt(2, bill_dis);
            pst.setString(3, cust_id);
            pst.setString(4, e_id);
            pst.setDouble(5, net_amount);
            pst.setInt(6, itmCount);
            pst.setString(7, bill_discrip);
            pst.setString(8, bill_id);
            
            
            pst.execute();
            //increaseNoOfBillByOne();
            
            JOptionPane.showMessageDialog(comp, "Invoice Edit Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
            return isKo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Invoice Edit failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            return isKo = false;
        }
    }
    
    public boolean updateBillWithPrices(double total, int bill_dis, double net_amount, int itmCount, String bill_id, Component comp){
        boolean isKoo = false;
        try{
           
        String sql = "update bill set total=?, bill_dis=?, net_amount=?, itm_count=? where bill_id=?";
        PreparedStatement pst7 = conn.prepareStatement(sql); 
            pst7.setDouble(1, total);
            pst7.setInt(2, bill_dis);  
            pst7.setDouble(3, net_amount);
            pst7.setInt(4, itmCount);
            pst7.setString(5, bill_id);
            
            pst7.execute();
            //increaseNoOfBillByOne();
            
            //JOptionPane.showMessageDialog(comp, "Invoice Edit Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
            isKoo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(comp, "Invoice Edit failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            isKoo = false;
        }
        return isKoo;
    }
    
    public boolean deleteAddedBillItems(String billItmCode){
        String sql = "delete from bill_items where bill_item_id='"+billItmCode+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Movie", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public ResultSet showEmployeeDetails(){
        String SQl3 = "select e_id as 'Rep ID', f_name as 'Name' from employee";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl3);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showCustomersDetails(){
        String SQl4 = "select cust_id as 'Customer ID', name as 'Name', address_nile_1 as 'Address' from customers";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl4);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet searchCustomerByName(String cusName){
       String name = null;
       name = cusName;
       String sql = "select cust_id as 'Customer ID', name as 'Name', city as 'City' from customers where name like '%"+name+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet showAllBillDetails(){
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as SalesRep, bill.total as Total, bill.bill_dis as Dis,bill.net_amount as NetAmount,bill.setle_days as PayIN,bill.status as Status,bill.days as Days FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND status='PENDING' OR status='COMPLETE' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String addCreditInvoice(String bill_id, double invoTotal, double payed, double dueAmt, String dueDate, String cuscomerID, Component comp){
        try{
            String SQL3= "insert into creditedinvoices(cdID, bill_id, invoTotal, payed, dueAmt, dueDate, cust_id) values(?,?,?,?,?,?,?)";
            
            String qoutItemCoda = generateCreditInvoiceCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, qoutItemCoda);
            pst.setString(2, bill_id);
            pst.setDouble(3, invoTotal); 
            pst.setDouble(4, payed); 
            pst.setDouble(5, dueAmt);    
            pst.setString(6, dueDate);
            pst.setString(7, cuscomerID);

            pst.execute();
            
            increaseNoOfCreditInvoiceCodeByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return qoutItemCoda;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String addInvoiceDiscription(String invoID, String initial, String  warranty, String returnDis, Component comp){
        try{
            String SQL3= "insert into invoice_discription(invoice_id, initial_dis, warranty_dis, return_dis) values(?,?,?,?)";

            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, invoID);
            pst.setString(2, initial);
            pst.setString(3, warranty);
            pst.setString(4, returnDis);

            pst.execute();
            
            increaseNoOfCreditInvoiceCodeByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return "OK";
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String updateInvoiceDiscriptionByWarrantyDiscription(String invoId, String dis){
        try{
            String SQL3= "update invoice_discription set warranty_dis=? where invoice_id=?";
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, dis); 
            pst.setString(2, invoId);   

            pst.execute();

            return "KO";
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateInvoiceDiscriptionByReturnDiscription(String invoId, String dis){
        try{
            String SQL3= "update invoice_discription set return_dis=? where invoice_id=?";
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, dis); 
            pst.setString(2, invoId);   

            pst.execute();

            return "KO";
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public String updateCreditInvoice(double payed, double dueAmt, String credID, Component comp){
        try{
            String SQL3= "update creditedinvoices set payed=?, dueAmt=? where cdID=?";
            
            String qoutItemCod = generateCreditInvoiceCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setDouble(1, payed); 
            pst.setDouble(2, dueAmt);   
            pst.setString(3, credID);

            pst.execute();
            
            increaseNoOfCreditInvoiceCodeByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return qoutItemCod;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String addCreditSubInvoice(double paymente, String dDate,String ccccid, Component comp){
        try{
            String SQL3= "insert into credit_invoice_sub(payment, dddddate, cdID) values(?,?,?)";
            
            String qoutItemCod = generateCreditInvoiceCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setDouble(1, paymente);
            pst.setString(2, dDate);
            pst.setString(3, ccccid); 

            pst.execute();
            
            increaseNoOfCreditInvoiceCodeByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return qoutItemCod;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateCreditInvoiceCode(Component comp){
        String type = "CI";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select creditInvo from count");
            while(rs.next()){
                noMovies = rs.getInt("creditInvo");
                
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
    
    public void increaseNoOfCreditInvoiceCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select creditInvo from count");
            while(rs.next()){
                noMovies = rs.getInt("creditInvo");
            }
            try{
                String SQL = "update count set creditInvo="+(noMovies+1)+" where creditInvo="+noMovies;
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
    
    public String addChequeInvoice(String bill_id, String dddate, String dueeDate, double netAmtt, String chequeNo, String statess, Component comp){
        try{
            String SQL3= "insert into chequeinvo(chequeInvoID, bill_id, dddate, dueeDate, netAmtt, chequeNo, statess) values(?,?,?,?,?,?,?)";
            
            String chequeCod = generateChequeInvoiceCode(comp);
            //System.out.println(billItemCode);
            PreparedStatement pst = conn.prepareStatement(SQL3);
            pst.setString(1, chequeCod);
            pst.setString(2, bill_id);
            pst.setString(3, dddate); 
            pst.setString(4, dueeDate); 
            pst.setDouble(5, netAmtt);
            pst.setString(6, chequeNo);
            pst.setString(7, statess);

            pst.execute();
            
            increaseNoOfchequeInvoiceCodeByOne();
            
            //JOptionPane.showMessageDialog(comp, "Item Added Successfully","Item Details", JOptionPane.INFORMATION_MESSAGE);
            return chequeCod;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Item Added Failed","Item Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean updateChequeTable(double net_amount, String bill_id, Component comp){
        boolean isKo = false;
        try{
           
        String sql = "update chequeinvo set netAmtt=?  where bill_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);             
            pst.setDouble(1, net_amount);
            pst.setString(2, bill_id);
            
            
            pst.execute();
            //increaseNoOfBillByOne();
            
            //JOptionPane.showMessageDialog(comp, "Invoice Edit Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
             isKo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "Invoice Edit failed!","Database Error",JOptionPane.ERROR_MESSAGE);
             isKo = false;
        }
        return isKo;
    }
    
    public String generateChequeInvoiceCode(Component comp){
        String type = "CQ";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select chequeInvoID from count");
            while(rs.next()){
                noMovies = rs.getInt("chequeInvoID");
                
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
    
    public void increaseNoOfchequeInvoiceCodeByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select chequeInvoID from count");
            while(rs.next()){
                noMovies = rs.getInt("chequeInvoID");
            }
            try{
                String SQL = "update count set chequeInvoID="+(noMovies+1)+" where chequeInvoID="+noMovies;
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
    
    public ResultSet getItemDetails(String SNCode){
       String SQL = "select * from items items INNER JOIN sub_items sub_items ON items.itm_code = sub_items.itm_code where sub_items.sn='"+SNCode+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(SQL);
           return rs;
       }
       catch(Exception e){
           return null;
       }
   }
    
    public String getItemNameFromBillItemTable(String ItemCobe, String billID){
       String itemName = "";
       String sql345 = "select item_name from bill_items where item_code='"+ItemCobe+"' and bill_id='"+billID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsag = stmnt.executeQuery(sql345);
           while(rsag.next()){
                itemName = rsag.getString("item_name");  
            }
           return itemName;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getBillItemIDFromBillItemTable(String ItemCobe, String billID){
       String BillitemID = "";
       String sql3456 = "select bill_item_id from bill_items where item_code='"+ItemCobe+"' and bill_id='"+billID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                BillitemID = rsagg.getString("bill_item_id");  
            }
           return BillitemID;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public double getRetailPrice(String billItemID){
       double r_price = 0;
       String sql3456 = "select r_price from bill_items where bill_item_id='"+billItemID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                r_price = rsagg.getDouble("r_price");  
            }
           return r_price;
       }
       catch(Exception e){
           return 0;
       }
    }
    
    public ArrayList<String> getBilledSN(String billItemID){
       ArrayList<String> listA = new ArrayList();
       
       String sql3456 = "select snn from bill_sub_items where bill_item_id='"+billItemID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                listA.add(rsagg.getString("snn"));
            }
           return listA;
       }
       catch(Exception e){
           return null; 
       }
    }
    
    public void updateItemSerial(String SSNN){
        try {
            String queryyy = "update sub_items set inStock=? where sn = ?";
            PreparedStatement pstlu = conn.prepareStatement(queryyy);

            pstlu.setString(1, "yes");
            pstlu.setString(2, SSNN);
            pstlu.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public double getCostPrice(String billItemID){
       double r_price = 0;
       String sql3456 = "select w_price from bill_items where bill_item_id='"+billItemID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                r_price = rsagg.getDouble("w_price");  
            }
           return r_price;
       }
       catch(Exception e){
           return 0;
       }
    }
    public int getQTY(String billItemID){
       int qqttyy = 0;
       String sql3456 = "select qty from bill_items where bill_item_id='"+billItemID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                qqttyy = rsagg.getInt("qty");  
            }
           return qqttyy;
       }
       catch(Exception e){
           return 0;
       }
    }
    
    
    public String checkSNAvailability(String billItemID){
       String qqttyy = "";
       String sql3456 = "select snn from bill_sub_items where bill_item_id='"+billItemID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                qqttyy = rsagg.getString("snn");  
            }
           return qqttyy;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String checkSNAvailabilityOnItemsTable(String ItemCode){
       String qqttyyy = "";
       String sql3456 = "select sn from sub_items where itm_code='"+ItemCode+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagg = stmnt.executeQuery(sql3456);
           while(rsagg.next()){
                qqttyyy = rsagg.getString("sn");  
            }
           return qqttyyy;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getItemStatus(String snnnn){
       String ssss = "";
       String sql34567 = "select inStock from sub_items where sn='"+snnnn+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsaggg = stmnt.executeQuery(sql34567);
           while(rsaggg.next()){
                ssss = rsaggg.getString("inStock");  
            }
           return ssss;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getInitialDis(String InvoID){
       String ssssd = "";
       String sql34567 = "select initial_dis from invoice_discription where invoice_id='"+InvoID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagggd = stmnt.executeQuery(sql34567);
           while(rsagggd.next()){
                ssssd = rsagggd.getString("initial_dis");  
            }
           return ssssd;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getWarrantylDis(String InvoID){
       String ssssdf = "";
       String sql34567 = "select warranty_dis from invoice_discription where invoice_id='"+InvoID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagggdf = stmnt.executeQuery(sql34567);
           while(rsagggdf.next()){
                ssssdf = rsagggdf.getString("warranty_dis");  
            }
           return ssssdf;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public String getReturnlDis(String InvoID){
       String ssssdff = "";
       String sql34567 = "select return_dis from invoice_discription where invoice_id='"+InvoID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsagggdff = stmnt.executeQuery(sql34567);
           while(rsagggdff.next()){
                ssssdff = rsagggdff.getString("return_dis");  
            }
           return ssssdff;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public boolean deleteBillSN(String BillsnID){
        String sql = "delete from bill_sub_items where idbill_sub_itemsID='"+BillsnID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete This SN", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteBillItem(String BillItemID){
        String sql = "delete from bill_items where bill_item_id='"+BillItemID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete This Item", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    

    public boolean validateCustomer(String ID){
        if(ID.equals("")){
            return false;
        }
        else if(ID.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateEmp(String eID){
        if(eID.equals("")){
            return false;
        }
        else if(eID.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateTotal(String eID){
        if(eID.equals("")){
            return false;
        }
        else if(eID.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
}
