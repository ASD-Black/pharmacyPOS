
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

public class hr {
    Connection conn;
    
    public hr(){
        dbConnection con = new dbConnection();
        conn = con.Connect();
    }
    
    public String addCustomer(String name, String address_nile_1, String address_nile_2, String city, String state, String contactNo, String reg_date, double credit_bal, Component comp){
        try{
            String SQL= "insert into customers(cust_id, name, address_nile_1, address_nile_2, city, state, contactNo, reg_date, credit_bal) values(?,?,?,?,?,?,?,?,?)";
            
            String cust_Code = generateCustomerCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, cust_Code);
            //System.out.println("4");
            pst.setString(2, name);
            //System.out.println("5");
            pst.setString(3, address_nile_1);
            //System.out.println("6");
            pst.setString(4, address_nile_2);
            //System.out.println("7");
            pst.setString(5, city);
            //System.out.println("8");
            pst.setString(6, state);
            //System.out.println("9");
            pst.setString(7, contactNo);
            //System.out.println("10");
            pst.setString(8, reg_date);
            //System.out.println("11");
            pst.setDouble(9, credit_bal);
            pst.execute();
            //System.out.println("12");
            increaseNoOfCustByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return cust_Code;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Customer Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void UpdateCustomerData(String name, String address_nile_1, String address_nile_2, String city, String state, String contactNo, String cust_Code, Component comp){
        try{
           
        String sql = "update customers set name=?, address_nile_1=?, address_nile_2=?, city=?, state=?, contactNo=? where cust_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
         //System.out.println("aaaaaaaaaa");
            pst.setString(1, name);
            //System.out.println("5");
            pst.setString(2, address_nile_1);
            //System.out.println("6");
            pst.setString(3, address_nile_2);
            //System.out.println("7");
            pst.setString(4, city);
            //System.out.println("8");
            pst.setString(5, state);
            //System.out.println("9");
            pst.setString(6, contactNo);
            pst.setString(7, cust_Code);
            
            pst.execute();
            //System.out.println("aaaaaaaaaa");
            JOptionPane.showMessageDialog(comp, "Updated Successfully!","Movie Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Item Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateCustomerCode(Component comp){
        String type = "C";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select cust from count");
            while(rs.next()){
                noMovies = rs.getInt("cust");
                
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
    
    public void increaseNoOfCustByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select cust from count");
            while(rs.next()){
                noMovies = rs.getInt("cust");
            }
            try{
                String SQL = "update count set cust="+(noMovies+1)+" where cust="+noMovies;
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
    
     public ResultSet showUpdatedCustDetails(){
        String SQl1 = "select cust_id as 'Customer ID', name as 'Customer Name', address_nile_1 as 'Address line 1', address_nile_2 as 'Address line 2', city as 'City', state as 'State', contactNo as 'Contact No', reg_date as 'Reg Date' from customers ORDER BY cust_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl1);
            return rs;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Store is Empty", "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
     
    public ResultSet getCustomerDetails(String custUD){
       String SQL = "select * from customers where cust_id='"+custUD+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(SQL);
           return rs;
       }
       catch(Exception e){
           return null;
       }
   }
    
    public boolean deleteCustomer(String custID){
        String sql = "delete from customers where cust_id='"+custID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Customer", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public ResultSet searchByCusName(String name){
       String item = null;
       item = name;
       String sql3 = "select cust_id as 'Customer ID', name as 'Customer Name', address_nile_1 as 'Address line 1', address_nile_2 as 'Address line 2', city as 'City', state as 'State', contactNo as 'Contact No', reg_date as 'Reg Date' from customers where name like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet searchByCusState(String state){
       String item = null;
       item = state;
       String sql3 = "select cust_id as 'Customer ID', name as 'Customer Name', address_nile_1 as 'Address line 1', address_nile_2 as 'Address line 2', city as 'City', state as 'State', contactNo as 'Contact No', reg_date as 'Reg Date' from customers where state like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet searchByCusCity(String city){
       String item = null;
       item = city;
       String sql3 = "select cust_id as 'Customer ID', name as 'Customer Name', address_nile_1 as 'Address line 1', address_nile_2 as 'Address line 2', city as 'City', state as 'State', contactNo as 'Contact No', reg_date as 'Reg Date' from customers where city like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet searchByCusID(String id){
       String item = null;
       item = id;
       String sql3 = "select cust_id as 'Customer ID', name as 'Customer Name', address_nile_1 as 'Address line 1', address_nile_2 as 'Address line 2', city as 'City', state as 'State', contactNo as 'Contact No', reg_date as 'Reg Date' from customers where cust_id like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    
    
    
    public String addRep(String f_name, String l_name, String address, String contactNo, String reg_date, Component comp){
        try{
            String SQL= "insert into employee(e_id, f_name, l_name, address, contactNo, reg_date) values(?,?,?,?,?,?)";
            
            String repID = generateRepCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, repID);
            //System.out.println("4");
            pst.setString(2, f_name);
            //System.out.println("5");
            pst.setString(3, l_name);
            //System.out.println("6");
            pst.setString(4, address);
            //System.out.println("7");
            pst.setString(5, contactNo);
            //System.out.println("8");
            pst.setString(6, reg_date);
 
            pst.execute();
            //System.out.println("12");
            increaseNoOfRepByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return repID;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Employee Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void UpdateRepData(String f_name, String l_name, String address, String contactNo, String repID, Component comp){
        try{
           
        String sql = "update employee set f_name=?, l_name=?, address=?, contactNo=? where e_id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
         //System.out.println("aaaaaaaaaa");
            pst.setString(1, f_name);
            //System.out.println("5");
            pst.setString(2, l_name);
            //System.out.println("6");
            pst.setString(3, address);
            //System.out.println("7");
            pst.setString(4, contactNo);
            
            pst.setString(5, repID);
            
            pst.execute();
            //System.out.println("aaaaaaaaaa");
            JOptionPane.showMessageDialog(comp, "Updated Successfully!","Employee Details",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Employee Updating failed!","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String generateRepCode(Component comp){
        String type = "E";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select emp from count");
            while(rs.next()){
                noMovies = rs.getInt("emp");
                
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
    
    public void increaseNoOfRepByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select emp from count");
            while(rs.next()){
                noMovies = rs.getInt("emp");
            }
            try{
                String SQL = "update count set emp="+(noMovies+1)+" where emp="+noMovies;
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
    
    public String addJob(String type, String cust, String Proccesor, String ram, String mb, String kb, String battery, String hdd,String rom, String vga, String charger, String status, int days, String date,String dis, String mobile, Component comp){
        try{
            String SQL= "insert into job(jobCode, type, cust, proccesor, ram, mb, kb, battery, hdd, rom, vga, charger, status, days, date, dis, mobile) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            String jobeCode = generateJobCode(comp);
            //System.out.println(itemCode);
            PreparedStatement pst = conn.prepareStatement(SQL);
            pst.setString(1, jobeCode);
            //System.out.println("4");
            pst.setString(2, type);
            //System.out.println("5");
            pst.setString(3, cust);
            pst.setString(4, Proccesor);
            //System.out.println("6");
            pst.setString(5, ram);
            //System.out.println("7");
            pst.setString(6, mb);
            //System.out.println("8");
            pst.setString(7, kb);
            //System.out.println("9");
            pst.setString(8, battery);
            //System.out.println("10");
            pst.setString(9, hdd);
            pst.setString(10, rom);
            pst.setString(11, vga);
            pst.setString(12, charger);
            pst.setString(13, status);
            pst.setInt(14, days);
            pst.setString(15, date);
            pst.setString(16, dis);
            pst.setString(17, mobile);
            //System.out.println("11");
            pst.execute();
            //System.out.println("12");
            increaseNoOfJobByOne();
            //System.out.println("22222222");
            //JOptionPane.showMessageDialog(comp, "Movie Added Successfully","Movie Details", JOptionPane.INFORMATION_MESSAGE);
            return jobeCode;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(comp, "Job Added Failed","Owner Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public String generateJobCode(Component comp){
        String type = "JOB";
        int noMovies = 0;
        String movieID = null;
        
        try{
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select job from count");
            while(rs.next()){
                noMovies = rs.getInt("job");
                
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
    
    public void increaseNoOfJobByOne(){
        try{
            int noMovies=0;
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("select job from count");
            while(rs.next()){
                noMovies = rs.getInt("job");
            }
            try{
                String SQL = "update count set job="+(noMovies+1)+" where job="+noMovies;
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
    public ResultSet showUpdatedJobDetails(){
        String SQl1 = "select jobCode as 'Job ID', type as 'Type', cust as 'Customer Name', dis as 'Discription', date as 'Job Date', status as 'Status', days as 'Days' from job ORDER BY jobCode DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl1);
            return rs;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "No Data..!", "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean deleteJob(String eID){
        String sql = "delete from job where jobCode='"+eID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Employee", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public ResultSet searchByJobID(String id){
       String item = null;
       item = id;
       String sql3 = "select jobCode as 'Job ID', type as 'Type', cust as 'Customer Name', dis as 'Discription', date as 'Job Date', status as 'Status', days as 'Days' from job where jobCode like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    public ResultSet searchJobByCusName(String name){
       String item = null;
       item = name;
       String sql3 = "select jobCode as 'Job ID', type as 'Type', cust as 'Customer Name', dis as 'Discription', date as 'Job Date', status as 'Status', days as 'Days' from job where cust like '%"+item+"%'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(sql3);
           return rs;
       }
       catch(Exception e){
           return null;
       }
    }
    
    public ResultSet showUpdatedRepDetails(){
        String SQl1 = "select e_id as 'Sales Rep ID', f_name as 'First Name', l_name as 'Last Name', address as 'Address', contactNo as 'Contact No', reg_date as 'Reg Date' from employee ORDER BY e_id DESC";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQl1);
            return rs;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "No Data..!", "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public ResultSet getRepDetails(String repID){
       String SQL = "select * from employee where e_id='"+repID+"'";
       
       try{
           Statement stmnt = conn.createStatement();
           ResultSet rs = stmnt.executeQuery(SQL);
           return rs;
       }
       catch(Exception e){
           return null;
       }
   }
    
    public boolean deleteRep(String eID){
        String sql = "delete from employee where e_id='"+eID+"'";
        
        try{
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot Delete Employee", "Database Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean validateCustName(String name){
        if(name.equals("")){
            return false;
        }
        else if(name.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateCustAddrL1(String addr1){
        if(addr1.equals("")){
            return false;
        }
        else if(addr1.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateCustAddrL2(String addr2){
        if(addr2.equals("")){
            return false;
        }
        else if(addr2.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateCustCity(String city){
        if(city.equals("")){
            return false;
        }
        else if(city.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateCustState(String state){
        if(state.equals("")){
            return false;
        }
        else if(state.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateCustConNo(String conn){
        if(conn.equals("")){
            return false;
        }
        else if(conn.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateRepName1(String name1){
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
    public boolean validateRepName2(String name2){
        if(name2.equals("")){
            return false;
        }
        else if(name2.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateRepAddr(String addr){
        if(addr.equals("")){
            return false;
        }
        else if(addr.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateRepConn(String connc){
        if(connc.equals("")){
            return false;
        }
        else if(connc.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean validateJobCust(String cusName){
        if(cusName.equals("")){
            return false;
        }
        else if(cusName.length()==1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean validateJobCutMobile(String mobile){
        if(mobile.equals("")){
            return false;
        }
        else if(mobile.length()<10){
            return false;
        }
        else{
            return true;
        }
    }
    
    
    
}
