package main;

import java.util.Arrays;
import connection.pool.ConnectionPool;
import coupon.dao.CompanyDAO;
import coupon.dao.CustomerDAO;
import coupon.dbdao.CompanyDBDAO;
import coupon.dbdao.CustomerDBDAO;
import enums.ClientType;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;
import system.exception.CouponSystemException;
import thread.DailyCouponExpirationTask;

public class CouponSystemSingleton {
	
	private CompanyDAO companyDAO = new CompanyDBDAO();
	private CustomerDAO customerDAO = new CustomerDBDAO();
	private DailyCouponExpirationTask task = new DailyCouponExpirationTask();
	public Thread dailyTask = new Thread(task);
	private static CouponSystemSingleton instance;
	
	private CouponSystemSingleton() throws CouponSystemException {
		ConnectionPool.getInstance();
		dailyTask.start();
	}
	
	public synchronized static CouponSystemSingleton getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new CouponSystemSingleton();
		}
		return instance;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws CouponSystemException {
		try {
			switch (clientType) {
			case ADMIN:
				if (name.equals("admin") && password.equals("1234")) {
					return new AdminFacade();
				}
				throw new CouponSystemException("Error. user name or password incurect. please try again");
			case COMPANY:
				if (companyDAO.login(name, password)) {
					return new CompanyFacade(companyDAO.getCompanyByName(name).getId());
				}
				throw new CouponSystemException("Error. user name or password incurect. please try again");
			case CUSTOMER:
				if (customerDAO.login(name, password)) {
					return new CustomerFacade(customerDAO.getCustomerByName(name).getId());
				}
				throw new CouponSystemException("Error. user name or password incurect. please try again");
			default:
				throw new CouponSystemException(
						"Error. ClientType incurrect. please choose from: " + Arrays.toString(ClientType.values()));
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Error. problem while trying to login", e);
		}
	}

	public void shutdown() throws CouponSystemException {
		dailyTask.interrupt();
		ConnectionPool.getInstance().closeAllConnections();
		System.out.println("Coupon System closed - connection pool closed and daily task stopped");

	}

}
