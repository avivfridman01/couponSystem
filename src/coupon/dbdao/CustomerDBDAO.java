package coupon.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;
import connection.pool.ConnectionPool;
import coupon.beans.Coupon;
import coupon.beans.Customer;
import coupon.dao.CustomerDAO;
import system.exception.CouponSystemException;

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		String sql = "insert into customer values (?,?,?)";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, customer.getId());
				stm.setString(2, customer.getCustName());
				stm.setString(3, customer.getPassword());
				stm.executeUpdate();
			}
			System.out.println("Customer " + customer.getCustName() + " created in DB");
		} catch (SQLException e) {
			String x = "Error while trying to create Customer " + customer.getCustName() + " in DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		String sql = "delete from customer where ID = ?";
		Connection con = null;

		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, customer.getId());
				stm.executeUpdate();
			}
			System.out.println("Customer " + customer.getCustName() + " deleted from DB");
		} catch (SQLException e) {
			String x = "Error while trying to delete Customer " + customer.getCustName() + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		String sql = "update customer set CUST_NAME = ? ,PASSWORD = ? where ID = ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, customer.getCustName());
				stm.setString(2, customer.getPassword());
				stm.setLong(3, customer.getId());
				stm.executeUpdate();
			}
			System.out.println("Customer " + customer.getCustName() + " was update in DB");
		} catch (SQLException e) {
			String x = "Error while trying to update Customer " + customer.getCustName() + " in DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		String sql = "select * from customer where ID= ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, id);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					Customer a = new Customer();
					a.setId(rs.getLong("ID"));
					a.setCustName(rs.getString("CUST_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					return a;
				}
				return null;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read Customer " + id + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Set<Customer> getAllCustomers() throws CouponSystemException {
		String sql = "select * from customer";
		Connection con = null;
		Set<Customer> set = new LinkedHashSet<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (Statement stm = con.createStatement();) {
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					Customer a = new Customer();
					a.setId(rs.getLong("ID"));
					a.setCustName(rs.getString("CUST_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					set.add(a);
				}
				return set;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read all Customers from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Set<Coupon> getAllCoupons(Customer customer) throws CouponSystemException {
		String sql = "select * from customer_coupon where cust_id = ?";
		Connection con = null;
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, customer.getId());
				ResultSet rs = stm.executeQuery();
				if (rs != null) {
					while (rs.next()) {
						Coupon a = new Coupon();
						CouponDBDAO dao = new CouponDBDAO();
						a = dao.getCoupon(rs.getLong("coupon_id"));
						set.add(a);
					}
					return set;
				} else {
					throw new CouponSystemException(
							"Error while trying to return coupons of customer " + customer.getCustName() + " from DB");
				}
			}
		} catch (SQLException e) {
			String x = "Error while trying to return coupons of customer " + customer.getCustName() + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public boolean login(String Cust_name, String password) throws CouponSystemException {
		String sql = "select * from customer where cust_name = ? and password = ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, Cust_name);
				stm.setString(2, password);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					System.out.println("login success");
					return true;
				} else {
					System.out.println("login faild - name or password incorrect. try again");
					return false;
				}
			}
		} catch (SQLException | CouponSystemException e) {
			String x = "login faild";
			throw new CouponSystemException(x, e);
		}finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void link_Customer_Coupon(long customerID, long couponID) throws CouponSystemException {
		String sql = "insert into Customer_Coupon values (?, ?)";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, customerID);
				stm.setLong(2, couponID);
				stm.executeUpdate();
			}
			System.out.println("Customer " + customerID + " linked to coupon " + couponID);
		} catch (SQLException e) {
			String x = "Error while trying to link Customer with the new coupon";
			throw new CouponSystemException(x, e);
		}finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void unlink_all_Customer_Coupons(Customer customer) throws CouponSystemException {
		String sql = "delete from Customer_Coupon where cust_id= ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, customer.getId());
				stm.executeUpdate();
			}
			System.out.println("Customer " + customer.getId() + " unlinked from all coupons");
		} catch (SQLException e) {
			String x = "Error while trying to unlink Customer from all coupons";
			throw new CouponSystemException(x, e);
		}finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Customer getCustomerByName(String cust_name) throws CouponSystemException {
		String sql = "select * from customer where cust_name= ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, cust_name);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					Customer a = new Customer();
					a.setId(rs.getLong("ID"));
					a.setCustName(rs.getString("CUST_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					return a;
				}
				return null;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read Customer " + cust_name + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}
}
