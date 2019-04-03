package facade;

import java.util.LinkedHashSet;
import java.util.Set;

import coupon.beans.Company;
import coupon.beans.Coupon;
import coupon.beans.Customer;
import coupon.dbdao.CompanyDBDAO;
import coupon.dbdao.CouponDBDAO;
import coupon.dbdao.CustomerDBDAO;
import system.exception.CouponSystemException;

public class AdminFacade implements CouponClientFacade {

	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();

	public AdminFacade() {
	}

	public void createCompany(Company company) throws CouponSystemException {
		try {
			Company a = companyDBDAO.getCompanyByName(company.getCompName());
			if (a == null) {
				companyDBDAO.createCompany(company);
			} else {
				throw new CouponSystemException("Error. company name " + company.getCompName() + " already exist");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public void removeCompany(Company company) throws CouponSystemException {
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			Company a = companyDBDAO.getCompany(company.getId());
			if (a == null) {
				throw new CouponSystemException("Error. company " + company.getCompName() + " not exist - can't remove");
			}
			set = companyDBDAO.getAllCoupons(company);
			if (!set.isEmpty()) {
				for (Coupon coupon : set) {
					couponDBDAO.unlinkCompanyCoupon(coupon.getId());
					couponDBDAO.unlinkCustomerCoupon(coupon.getId());
					couponDBDAO.removeCoupon(coupon);
				}
			}
			companyDBDAO.removeCompany(company);
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public void updateCompany(Company company) throws CouponSystemException {
		try {
			Company a = companyDBDAO.getCompany(company.getId());
			if (a == null) {
				throw new CouponSystemException(
						"Error. can't update because company " + company.getCompName() + " not exist");
			}
			if (a.getCompName().equalsIgnoreCase(company.getCompName())) {
				companyDBDAO.updateCompany(company);
			} else {
				throw new CouponSystemException("Error. can't update company name");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public Company getCompany(long id) throws CouponSystemException {
		try {
			Company a = companyDBDAO.getCompany(id);
			if (a == null) {
				throw new CouponSystemException("Error. company " + id + " not exist");
			} else {
				return a;
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public Set<Company> getAllCompanies() throws CouponSystemException {
		try {
			Set<Company> set = new LinkedHashSet<>();
			set = companyDBDAO.getAllCompanies();
			return set;
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. couldn't show all companys", e);
		}
	}

	public void createCustomer(Customer customer) throws CouponSystemException {
		try {
			Customer a = customerDBDAO.getCustomerByName(customer.getCustName());
			if (a == null) {
				customerDBDAO.createCustomer(customer);
			} else {
				throw new CouponSystemException("Error. customer name " + customer.getCustName() + " already exist");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public void removeCustomer(Customer customer) throws CouponSystemException {
		try {
			Customer a = customerDBDAO.getCustomerByName(customer.getCustName());
			if (a == null) {
				throw new CouponSystemException("Error. customer " + customer.getCustName() + " not exist - can't remove");
			}
			Set<Coupon> set = new LinkedHashSet<>();
			set = customerDBDAO.getAllCoupons(customer);
			if (!set.isEmpty()) {
				for (Coupon coupon : set) {
					couponDBDAO.unlinkCustomerCoupon(coupon.getId());
				}
			}
			customerDBDAO.removeCustomer(customer);
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		try {
			Customer a = customerDBDAO.getCustomer(customer.getId());
			if (a == null) {
				throw new CouponSystemException(
						"Error. can't update because customer " + customer.getCustName() + " not exist");
			}
			if (a.getCustName().equalsIgnoreCase(customer.getCustName())) {
				customerDBDAO.updateCustomer(customer);
			} else {
				throw new CouponSystemException("Error. can't update customer name");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public Customer getCustomer(long id) throws CouponSystemException {
		try {
			Customer a = customerDBDAO.getCustomer(id);
			if (a == null) {
				throw new CouponSystemException("Error. customer " + id + " not exist");
			} else {
				return a;
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}

	public Set<Customer> getAllCustomers() throws CouponSystemException {
		Set<Customer> set = new LinkedHashSet<>();
		try {
			set = customerDBDAO.getAllCustomers();
			if (set.isEmpty()) {
				throw new CouponSystemException("There are no customers exist in DB");
			}else {
				return set;
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException (e.getMessage());
		}
	}
}
