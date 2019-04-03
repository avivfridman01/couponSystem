package thread;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import coupon.beans.Coupon;
import coupon.dbdao.CouponDBDAO;
import system.exception.CouponSystemException;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDBDAO couponDBDAO = new CouponDBDAO();

	public DailyCouponExpirationTask() {
	}

	@Override
	public void run() {
		Set<Coupon> set = new LinkedHashSet<>();
		while (true) {
			try {
				set = couponDBDAO.getAllCoupons();
				Iterator<Coupon> it = set.iterator();
				while (it.hasNext()) {
					Coupon coupon = it.next();
					if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
						couponDBDAO.unlinkCompanyCoupon(coupon.getId());
						couponDBDAO.unlinkCustomerCoupon(coupon.getId());
						couponDBDAO.removeCoupon(coupon);
					}
				}
				// removes coupons automatically from tables: company_coupon and customer_coupon
				// and coupon
				// couponDBDAO.removeExpiredCoupons();
				Thread.sleep(100 * 60 * 60 * 24);
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
