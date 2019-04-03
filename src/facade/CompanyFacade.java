package facade;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import coupon.beans.Coupon;
import coupon.beans.CouponType;
import coupon.dbdao.CompanyDBDAO;
import coupon.dbdao.CouponDBDAO;
import system.exception.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	private long logedID = 0;

	public long getLogedID() {
		return logedID;
	}

	public CompanyFacade(long logedID) {
		this.logedID = logedID;
	}

	public void createCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Coupon a = couponDBDAO.getCouponByTitle(coupon.getTitle());
			if (a == null) {
				couponDBDAO.createCoupon(coupon);
				companyDBDAO.link_Company_Coupon(getLogedID(), coupon.getId());
			} else {
				throw new CouponSystemException("Error. coupon title " + coupon.getTitle() + " already exist");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Coupon a = couponDBDAO.getCoupon(coupon.getId());
			if (a == null) {
				throw new CouponSystemException("Error. coupon " + coupon.getTitle() + " not exist - can't remove");
			}
			couponDBDAO.removeCoupon(coupon);
			couponDBDAO.unlinkCompanyCoupon(coupon.getId());
			couponDBDAO.unlinkCustomerCoupon(coupon.getId());
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Coupon a = couponDBDAO.getCoupon(coupon.getId());
			if (a == null) {
				throw new CouponSystemException("Error. coupon " + coupon.getTitle() + " not exist");
			}
			if (coupon.getId() == a.getId() && coupon.getTitle().equalsIgnoreCase(a.getTitle())) {
				couponDBDAO.updateCoupon(coupon);
			} else {
				throw new CouponSystemException("Error. you cant only update: price, end date");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public Coupon getCoupon(long id) throws CouponSystemException {
		try {
			Coupon a = couponDBDAO.getCoupon(id);
			if (a == null) {
				throw new CouponSystemException("Error. coupon " + id + " not exist");
			} else {
				return a;
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException(e.getMessage());
		}
	}

	public Set<Coupon> getAllCoupons() throws CouponSystemException {
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			set = companyDBDAO.getAllCoupons(companyDBDAO.getCompany(logedID));
			return set;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show coupons", e);
		}
	}

	public Set<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException {
		Set<Coupon> set1 = new LinkedHashSet<>();
		Set<Coupon> set2 = new LinkedHashSet<>();
		try {
			set1 = getAllCoupons();
			for (Coupon coupon : set1) {
				if (coupon.getCouponType().equals(couponType)) {
					set2.add(coupon);
				}
			}
			if (set2.isEmpty()) {
				throw new CouponSystemException("There are no coupons from type " + couponType);
			}
			return set2;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show Coupons By Type " + couponType.name(), e);
		}
	}

	public Set<Coupon> getCouponsByPrice(double price) throws CouponSystemException {
		Set<Coupon> set1 = new LinkedHashSet<>();
		Set<Coupon> set2 = new LinkedHashSet<>();
		try {
			set1 = getAllCoupons();
			for (Coupon coupon : set1) {
				if (coupon.getPrice() <= price) {
					set2.add(coupon);
				}
			}
			if (set2.isEmpty()) {
				throw new CouponSystemException("There are no coupons up to price " + price);
			}
			return set2;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show Coupons to price " + price, e);
		}
	}

	public Set<Coupon> getCouponsUntilDate(Date endDate) throws CouponSystemException {
		Set<Coupon> set1 = new LinkedHashSet<>();
		Set<Coupon> set2 = new LinkedHashSet<>();
		try {
			set1 = getAllCoupons();
			for (Coupon coupon : set1) {
				if (coupon.getEndDate().before(endDate)) {
					set2.add(coupon);
				}
			}
			if (set2.isEmpty()) {
				throw new CouponSystemException("There are no coupons until date " + endDate.toString());
			}
			return set2;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show Coupons until date " + endDate.toString(), e);
		}
	}
}
