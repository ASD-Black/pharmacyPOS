
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

public class billSearch {
    
    Connection conn;
    
    public billSearch(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public ResultSet showCompleteBillDetailsByBillId(String billID){
        String bill = null;
        bill = billID;
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Ps)',bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status'  FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND bill.bill_id like '%"+bill+"%' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    public ResultSet showCompleteBillDetailsByType(String type){
        String bill = null;
        bill = type;
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Ps)',bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status'  FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND bill.invoiceType='"+bill+"' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }

    public ResultSet showCompleteBillDetailsByParmacyName(String pharmacyName){
        String name = null;
        name = pharmacyName;
        String SQl7 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Ps)', bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status' FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND customers.name like '%"+name+"%' AND status!='PENDIN' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl7);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    public ResultSet showCompleteBillDetailsByDate(String fromDate, String toDate){
        String from = null;
        String to = null;
        from = fromDate;
        to = toDate;
        String SQl7 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Ps)', bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status' FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND bill.bill_date between '"+from+"' and '"+to+"' AND bill.status!='PENDIN' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl7);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showCompleteBillDetailsByDateANDcustomer(String fromDate, String toDate, String cName){
        String from = null;
        String to = null;
        String name = null;
        from = fromDate;
        to = toDate;
        name = cName;
        String SQl7 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Ps)', bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status' FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND bill.bill_date between '"+from+"' and '"+to+"' AND customers.name like'%"+name+"%' AND bill.status!='PENDIN' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl7);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showCompleteBillDetailsByCity(String City){
        String cty = null;
        cty = City;
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as SalesRep, bill.total as Total, bill.bill_dis as Dis,bill.net_amount as NetAmount,bill.setle_days as PayIN,bill.status as Status,bill.days as Days FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND status='COMPLETE' AND customers.city like '%"+cty+"%' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showUnCompleteBillDetailsByBillId(String billID){
        String bill = null;
        bill = billID;
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as SalesRep, bill.total as Total, bill.bill_dis as Dis,bill.net_amount as NetAmount,bill.setle_days as PayIN,bill.status as Status,bill.days as Days FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND status='PENDING' AND bill.bill_id like '%"+bill+"%' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showUnCompleteBillDetailsByParmacyName(String pharmacyName){
        String name = null;
        name = pharmacyName;
        String SQl7 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as SalesRep, bill.total as Total, bill.bill_dis as Dis,bill.net_amount as NetAmount,bill.setle_days as PayIN,bill.status as Status,bill.days as Days FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND status='PENDING' AND customers.name like '%"+name+"%' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl7);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showUnCompleteBillDetailsByCity(String City){
        String cty = null;
        cty = City;
        String SQl5 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as SalesRep, bill.total as Total, bill.bill_dis as Dis,bill.net_amount as NetAmount,bill.setle_days as PayIN,bill.status as Status,bill.days as Days FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND status='PENDING' AND customers.city like '%"+cty+"%' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet showAllPendingBillDetails(){
        String SQl6 = "SELECT bill.bill_id as InvoiceNo, bill.bill_date Date, customers.name as Customer,employee.f_name as Employee, bill.total as Total, bill.bill_dis as 'Discount (Rs)',bill.net_amount as NetAmount, bill.invoiceType as 'Invoice Type',bill.bill_discrip as 'Discription', bill.creditCodeChequeCode as 'Credit or Cheque ID', bill.advancedCode as 'Advanced Code', bill.status as 'Status' FROM customers customers INNER JOIN bill bill ON customers.cust_id = bill.cust_id INNER JOIN employee employee ON bill.e_id = employee.e_id WHERE bill.cust_id = bill.cust_id  AND bill.e_id = bill.e_id AND bill.status!='PENDIN' ORDER BY bill.bill_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl6);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getChequeDetails(String chequeNo){
       String SQL = "select * from chequeinvo where chequeInvoID='"+chequeNo+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsf = stmnt.executeQuery(SQL);
           return rsf;
       }
       catch(Exception e){
           return null;
       }
   }
    
    public boolean EditChequeData(String status, String chequeNo, String chequeID, Component comp){
        boolean isKo = false;
        try{
           
        String sql = "update chequeinvo set statess=?, chequeNo=? where chequeInvoID=?";
        PreparedStatement pst = conn.prepareStatement(sql); 
            pst.setString(1, status);
            pst.setString(2, chequeNo);
            pst.setString(3, chequeID);
            
            
            pst.execute();
            //increaseNoOfBillByOne();
            
            //JOptionPane.showMessageDialog(comp, "Payment Caimed Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
            return isKo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            return isKo = false;
        }
    }
    public boolean EditBillData(String statuss,String invoID, Component comp){
        boolean isKo = false;
        try{
           
        String sql = "update bill set status=? where bill_id=?";
        PreparedStatement pst = conn.prepareStatement(sql); 
            pst.setString(1, statuss);
            pst.setString(2, invoID);
            
            
            pst.execute();
            //increaseNoOfBillByOne();
            
            //JOptionPane.showMessageDialog(comp, "Payment Caimed Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("ok");
            return isKo = true;
        }
        catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(comp, "failed!","Database Error",JOptionPane.ERROR_MESSAGE);
            return isKo = false;
        }
    }
    
    public ResultSet showCreditInvoData(String invoIDDD){
        String cty = null;
        cty = invoIDDD;
        String SQl5 = "select * from creditedinvoices where bill_id='"+invoIDDD+"'";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    public ResultSet showCreditSubInvoData(String ciIDD){
        String cty = null;
        cty = ciIDD;
        String SQl5 = "select idd as 'ID', payment as 'Payment', dddddate as 'Payment Date' from credit_invoice_sub where cdID='"+ciIDD+"'";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl5);
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }
    
    public ResultSet getAdvancedDetails(String advaID){
       String SQL = "select * from advancedcust where idadvancedCustID='"+advaID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rsf = stmnt.executeQuery(SQL);
           return rsf;
       }
       catch(Exception e){
           return null;
       }
   }
    
    public double getPayedCeditAmt(String invoID){
        double amt = 0;
        String id = null;
        id = invoID;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss = stmnt.executeQuery("select payed from creditedinvoices where bill_id='"+id+"'");
            while(rss.next()){
                amt = rss.getDouble("payed");    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return amt;
    }
    
    public String getBillItemIDBySN(String sn){  
        String billItemID = null;
        String id = null;
        id = sn;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss1 = stmnt.executeQuery("select bill_item_id from bill_sub_items where snn='"+id+"'");
            while(rss1.next()){
                billItemID = rss1.getString("bill_item_id");    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return billItemID;
    }
    
    public String getBillIDByBillItemID(String billItemIDD){  
        String billID = null;
        String id = null;
        id = billItemIDD;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss1 = stmnt.executeQuery("select bill_id from bill_items where bill_item_id='"+id+"'");
            while(rss1.next()){
                billID = rss1.getString("bill_id");    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return billID;
    }
    
    public String getBillTypeByBillID(String ID){  
        String invoType = null;
        String id = null;
        id = ID;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss1 = stmnt.executeQuery("select invoiceType from bill where bill_id='"+id+"'");
            while(rss1.next()){
                invoType = rss1.getString("invoiceType");    
            }
            return invoType;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public double getadvancedAmt(String invoID){
        double amt = 0;
        String id = null;
        id = invoID;
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rss = stmnt.executeQuery("select advAmount from bill where bill_id='"+id+"'");
            while(rss.next()){
                amt = rss.getDouble("advAmount");    
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return amt;
    }
    
}
