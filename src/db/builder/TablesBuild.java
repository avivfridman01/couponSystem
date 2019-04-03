package db.builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TablesBuild {

	public static void main(String[] args) {
//load driver
		String driveName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driveName);
			System.out.println("driver loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//make connection and statement
		String conName = "jdbc:derby://localhost:1527/db;create=true";
		try (Connection con = DriverManager.getConnection(conName);
				Statement stm = con.createStatement();) {
			System.out.println("connected to : " + conName);
			String sql = "create table Company (ID bigint primary key ,";
			sql+= "COMP_NAME varchar (25) ,";
			sql+= "PASSWORD varchar (25) ,";
			sql+= "EMAIL varchar (35) )";
				/////////////////
			String sql2 = "create table Customer (ID bigint primary key ,";
			sql2+="CUST_NAME varchar (25) ,";
			sql2+= "PASSWORD varchar (25) )";
				/////////////////
			String sql3 = "create table Coupon (ID bigint primary key ,";
			sql3+= "TITLE varchar (25) ,";
			sql3+= "START_DATE date ,";
			sql3+= "END_DATE date ,";
			sql3+= "AMOUNT integer ,";
			sql3+= "TYPE varchar (25) ,";
			sql3+= "MESSAGE varchar (25) ,";
			sql3+= "PRICE float ,";
			sql3+= "IMAGE varchar (500) )";
				/////////////////
			String sql4 = "create table Customer_Coupon (CUST_ID bigint ,COUPON_ID bigint, ";
			sql4+= "PRIMARY KEY (CUST_ID, COUPON_ID), FOREIGN KEY(COUPON_ID) REFERENCES Coupon(ID) ON DELETE CASCADE)";
			    ////////////////
			String sql5 = "create table Company_Coupon (COMP_ID bigint ,COUPON_ID bigint, ";
			sql5+= "PRIMARY KEY (COMP_ID, COUPON_ID))";
			stm.executeUpdate(sql);
			stm.executeUpdate(sql2);
			stm.executeUpdate(sql3);
			stm.executeUpdate(sql4);
			stm.executeUpdate(sql5);
		    System.out.println("created tables in DB : Company, Customer, Coupon, Customer_Coupon, Company_Coupon");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	
	}
		
	}


