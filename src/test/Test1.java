package test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import coupon.beans.Company;
import coupon.beans.Coupon;
import coupon.beans.CouponType;
import coupon.beans.Customer;
import coupon.dao.CompanyDAO;
import coupon.dbdao.CompanyDBDAO;
import enums.ClientType;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import main.CouponSystemSingleton;
import system.exception.CouponSystemException;

public class Test1 {
	/**
	 * to use this Test1 class you need to go to db.builder package, go to
	 * TablesBuild class and create new DataBase by running the class. then you can
	 * run this Test1 class
	 */
	public static void main(String[] args) throws CouponSystemException {

		CompanyDAO companyDAO = new CompanyDBDAO();
		CouponSystemSingleton couponSystemSingleton = CouponSystemSingleton.getInstance();
		try {
			CouponClientFacade couponClientFacade = couponSystemSingleton.login("admin", "1234", ClientType.ADMIN);
			if (couponClientFacade instanceof AdminFacade) {
				AdminFacade adminFacade = (AdminFacade) couponClientFacade;
				// create 5 company's
				adminFacade.createCompany(new Company(1234, "Teva", "1234", "teva@gmail.com"));
				adminFacade.createCompany(new Company(2345, "Oracel", "2345", "Oracel@gmail.com"));
				adminFacade.createCompany(new Company(3456, "TC", "3456", "TC@gmail.com"));
				adminFacade.createCompany(new Company(4567, "Goldberg", "4567", "Goldberg@gmail.com"));
				adminFacade.createCompany(new Company(5678, "Aria", "5678", "Aria@gmail.com"));
				// create 10 customer's
				adminFacade.createCustomer(new Customer(316352467, "Aviv", "1234"));
				adminFacade.createCustomer(new Customer(234345435, "Orel", "a23a"));
				adminFacade.createCustomer(new Customer(956444327, "Vital", "9999"));
				adminFacade.createCustomer(new Customer(211135677, "Dan", "yy7y"));
				adminFacade.createCustomer(new Customer(864333553, "Pini", "trtr"));
				adminFacade.createCustomer(new Customer(333444324, "EliZur", "5555"));
				adminFacade.createCustomer(new Customer(875655565, "Eli", "0990"));
				adminFacade.createCustomer(new Customer(875555565, "Ron", "aaade"));
				adminFacade.createCustomer(new Customer(113223434, "Mikel", "6tt6t"));
				adminFacade.createCustomer(new Customer(444225532, "Amity", "amiy77"));
			}

			// create coupon for each company
			CompanyFacade companyFacade = new CompanyFacade(companyDAO.getCompanyByName("Teva").getId());
			companyFacade.createCoupon(new Coupon(11111, "Teva", new Date(),
					new GregorianCalendar(2021, Calendar.APRIL, 8).getTime(), 30, CouponType.HEALTH, "limit edition",
					99.9,
					"https://cdne.ojo.pe/thumbs/uploads/articles/images/medicos-exageran-sobre-riesgo--jpg_700x0.jpg"));
			CompanyFacade companyFacade2 = new CompanyFacade(companyDAO.getCompanyByName("Oracel").getId());
			companyFacade2.createCoupon(new Coupon(22222, "Oracel", new Date(),
					new GregorianCalendar(2022, Calendar.FEBRUARY, 12).getTime(), 50, CouponType.ATTRACTIONS, "special",
					199.9,
					"https://thumbs-prod.si-cdn.com/wbMagfoGwofa3YYMyb98OnG6DX4=/800x600/filters:no_upscale()/https://public-media.si-cdn.com/filer/8a/64/8a64a13d-7e67-47b5-91d2-a57324fddbfb/dw3905.jpg"));
			CompanyFacade companyFacade3 = new CompanyFacade(companyDAO.getCompanyByName("TC").getId());
			companyFacade3.createCoupon(new Coupon(33333, "TC", new Date(),
					new GregorianCalendar(2021, Calendar.APRIL, 18).getTime(), 10, CouponType.CAMPING, "limit edition",
					7999.9,
					"https://content.active.com/Assets/Active.com+Content+Site+Digital+Assets/Kids/Galleries/8+Reasons/1.jpg"));
			CompanyFacade companyFacade4 = new CompanyFacade(companyDAO.getCompanyByName("Goldberg").getId());
			companyFacade4.createCoupon(
					new Coupon(44444, "Goldberg", new Date(), new GregorianCalendar(2023, Calendar.MARCH, 22).getTime(),
							55, CouponType.ELECTRICITY, "limit edition", 400,
							"https://www.bigw.com.au/medias/sys_master/images/images/h1d/h10/11583366070302.jpg"));
			CompanyFacade companyFacade5 = new CompanyFacade(companyDAO.getCompanyByName("Aria").getId());
			companyFacade5.createCoupon(new Coupon(55555, "Aria", new Date(),
					new GregorianCalendar(2023, Calendar.MAY, 28).getTime(), 15, CouponType.TRAVELLING, "special", 1199,
					"https://i.ytimg.com/vi/GHNM9X96dlk/maxresdefault.jpg"));
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		// after 10 seconds the coupon system will shut down
		finally {
			try {
				Thread.currentThread();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (couponSystemSingleton != null) {
				couponSystemSingleton.shutdown();
			}
		}
		/*
		 * // Testing area AdminFacade adminFacadeTest = new AdminFacade();
		 * CompanyFacade companyFacadeTest = new CompanyFacade(1234); CustomerFacade
		 * customerFacadeTest = new CustomerFacade(316352467);
		 * 
		 * // try to create company with same name try {
		 * adminFacadeTest.createCompany(new Company(1111, "Teva", "1234",
		 * "teva@gmail.com")); } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * // try to create customer with same name try {
		 * adminFacadeTest.createCustomer(new Customer(1111, "Aviv", "1234")); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 * // try to create coupon with same title (title- Teva) try {
		 * companyFacadeTest.createCoupon( new Coupon(00000, "Teva", new Date(), new
		 * GregorianCalendar(2021, Calendar.APRIL, 8).getTime(), 30, CouponType.HEALTH,
		 * "limit edition", 99.9, "aaa")); } catch (Exception e) { e.printStackTrace();
		 * }
		 * 
		 * // try to remove not existing company try { adminFacadeTest.removeCompany(new
		 * Company(0000, "0000", "0000", "0000")); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * // try to remove not existing customer try {
		 * adminFacadeTest.removeCustomer(new Customer(0000, "0000", "0000")); } catch
		 * (Exception e) { e.printStackTrace(); }
		 * 
		 * // try to remove not existing coupon try { companyFacadeTest.removeCoupon(
		 * new Coupon(0000, "0000", new Date(), new Date(), 0, CouponType.ATTRACTIONS,
		 * "0000", 0, "0000")); } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * 
		 */}
}
