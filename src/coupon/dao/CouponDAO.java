package coupon.dao;

import coupon.beans.*;
import java.util.Set;
import system.exception.CouponSystemException;

public interface CouponDAO {
void createCoupon (Coupon coupon) throws CouponSystemException ;
void removeCoupon (Coupon coupon) throws CouponSystemException ;
void updateCoupon (Coupon coupon) throws CouponSystemException ;
Coupon getCoupon (long id) throws CouponSystemException ;
Set<Coupon> getAllCoupons () throws CouponSystemException ;
Set<Coupon> getCouponByType (CouponType couponType) throws CouponSystemException ;
void unlinkCustomerCoupon (long id) throws  CouponSystemException;
void unlinkCompanyCoupon (long id) throws  CouponSystemException;
Coupon getCouponByTitle (String title) throws CouponSystemException ;
//void removeExpiredCoupons() throws CouponSystemException;
}
