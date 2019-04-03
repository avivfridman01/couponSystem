package facade;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import coupon.beans.Coupon;
import coupon.beans.CouponType;
import coupon.dbdao.CouponDBDAO;
import coupon.dbdao.CustomerDBDAO;
import system.exception.CouponSystemException;

public class CustomerFacade implements CouponClientFacade {
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	private long logedID = 0;

	public long getLogedID() {
		return logedID;
	}

	public CustomerFacade(long logedID) {
		this.logedID = logedID;
	}

	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if (coupon == null) {
				throw new CouponSystemException("cant purchase coupon because coupons id incorrect");
			}
			Coupon a = couponDBDAO.getCoupon(coupon.getId());
			if (a == null) {
				throw new CouponSystemException("Error. coupon " + coupon.getTitle() + " not exist");
			}
			if (coupon.getAmount() == 0) {
				throw new CouponSystemException("Error. coupon not in stock");
			}
			if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
				throw new CouponSystemException("Error. coupon not valid");
			}
			customerDBDAO.link_Customer_Coupon(getLogedID(), coupon.getId());
			coupon.setAmount(coupon.getAmount() - 1);
			couponDBDAO.updateCoupon(coupon);
			System.out.println("coupon " + coupon.getTitle() + " purchased successfully");
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public Set<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			set = customerDBDAO.getAllCoupons(customerDBDAO.getCustomer(logedID));
			if (set.isEmpty()) {
				throw new CouponSystemException("Error. there are no purchased coupuns for client " + logedID);
			} else {
				return set;
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public Set<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException {
		Set<Coupon> set1 = new LinkedHashSet<>();
		Set<Coupon> set2 = new LinkedHashSet<>();
		try {
			set1 = getAllPurchasedCoupons();
			for (Coupon coupon : set1) {
				if (coupon.getCouponType().equals(couponType)) {
					set2.add(coupon);
				}
			}
			if (set2.isEmpty()) {
				throw new CouponSystemException("Error. there are no purchased coupuns from type " + couponType);
			}
			return set2;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show all purchased coupons from type " + couponType.name(),
					e);
		}
	}

	public Set<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponSystemException {
		Set<Coupon> set1 = new LinkedHashSet<>();
		Set<Coupon> set2 = new LinkedHashSet<>();
		try {
			set1 = getAllPurchasedCoupons();
			for (Coupon coupon : set1) {
				if (coupon.getPrice() <= price) {
					set2.add(coupon);
				}
			}
			if (set2.isEmpty()) {
				throw new CouponSystemException("Error. there are no purchased coupuns up to price: " + price);
			}
			return set2;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show all purchased coupons up to price " + price, e);
		}
	}

	public Set<Coupon> getAllCouponsInStore() throws CouponSystemException {
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			set = couponDBDAO.getAllCoupons();
			return set;
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e);
		}
	}
}