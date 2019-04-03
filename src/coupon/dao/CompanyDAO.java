package coupon.dao;

import coupon.beans.*;
import java.util.Set;

import system.exception.CouponSystemException;

public interface CompanyDAO {
void createCompany (Company company) throws CouponSystemException ;

void removeCompany (Company company) throws CouponSystemException ;

void updateCompany (Company company) throws CouponSystemException ;

Company getCompany (long id) throws CouponSystemException ;

Set<Company> getAllCompanies () throws CouponSystemException ;

Set<Coupon> getAllCoupons (Company company) throws CouponSystemException ;

boolean login (String Comp_name , String password) throws CouponSystemException ;

void link_Company_Coupon (long companyID, long couponID) throws CouponSystemException;

void unlink_all_Company_Coupons (Company company)throws CouponSystemException;

Company getCompanyByName (String comp_name) throws CouponSystemException ;

}
