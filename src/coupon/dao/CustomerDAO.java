package coupon.dao;

import coupon.beans.*;
import java.util.Set;

import system.exception.CouponSystemException;

public interface CustomerDAO {
void createCustomer (Customer customer) throws CouponSystemException ;

void removeCustomer (Customer customer) throws CouponSystemException ;

void updateCustomer (Customer customer) throws CouponSystemException ;

Customer getCustomer (long id) throws CouponSystemException ;

Set<Customer> getAllCustomers () throws CouponSystemException ;

Set<Coupon> getAllCoupons (Customer customer) throws CouponSystemException ;

boolean login (String Cust_name , String password) throws CouponSystemException ;

void link_Customer_Coupon (long customerID, long couponID)throws CouponSystemException;

void unlink_all_Customer_Coupons (Customer customer)throws CouponSystemException;

Customer getCustomerByName (String cust_name) throws CouponSystemException ;

}
