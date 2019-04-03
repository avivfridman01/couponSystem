package coupon.dbdao;

import coupon.beans.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;
import connection.pool.ConnectionPool;
import coupon.dao.CompanyDAO;
import system.exception.CouponSystemException;

public class CompanyDBDAO implements CompanyDAO {
	
	@Override
	public void createCompany(Company company) throws CouponSystemException {
		String sql = "insert into Company values (?,?,?,?)";
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, company.getId());
				stm.setString(2, company.getCompName());
				stm.setString(3, company.getPassword());
				stm.setString(4, company.getEmail());
				stm.executeUpdate();
			}
			System.out.println("Company " + company.getCompName() + " was created in DB");
		} catch (SQLException e) {
			String x = "Error while trying to create Company " + company.getCompName() + " in DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		// unlink_all_Company_Coupons(company);
		String sql = "delete from company where id = ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, company.getId());
				stm.executeUpdate();
			}
			System.out.println("Company " + company.getCompName() + " was deletet from DB");
		} catch (SQLException e) {
			String x = "Error while trying to delete Company " + company.getCompName() + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		String sql = "update company set COMP_NAME = ?, PASSWORD =?, EMAIL =? where ID =? ";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, company.getCompName());
				stm.setString(2, company.getPassword());
				stm.setString(3, company.getEmail());
				stm.setLong(4, company.getId());
				stm.executeUpdate();
			}
			System.out.println("Company " + company.getCompName() + " was update in DB");
		} catch (SQLException e) {
			String x = "Error while trying to update Company " + company.getCompName() + " in DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Company getCompany(long id) throws CouponSystemException {
		String sql = "select * from company where ID = ?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, id);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					Company a = new Company();
					a.setId(rs.getLong("ID"));
					a.setCompName(rs.getString("COMP_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					a.setEmail(rs.getString("EMAIL"));
					return a;
				}
				return null;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read Company from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Set<Company> getAllCompanies() throws CouponSystemException {
		String sql = "select * from company";
		Connection con = ConnectionPool.getInstance().getConnection();
		Set<Company> set = new LinkedHashSet<>();
		try {
			try (Statement stm = con.createStatement();) {
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					Company a = new Company();
					a.setId(rs.getLong("ID"));
					a.setCompName(rs.getString("COMP_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					a.setEmail(rs.getString("EMAIL"));
					set.add(a);
				}
				return set;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read all Companys from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Set<Coupon> getAllCoupons(Company company) throws CouponSystemException {
		String sql = "select * from Company_Coupon where comp_id = ?";
		Connection con = ConnectionPool.getInstance().getConnection();
		Set<Coupon> set = new LinkedHashSet<>();
		try {
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, company.getId());
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
							"Error while trying to return coupons of company " + company.getCompName() + " from DB");
				}
			}
		} catch (SQLException e) {
			String x = "Error while trying to return coupons of company " + company.getCompName() + " from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public boolean login(String Comp_name, String password) throws CouponSystemException {
		String sql = "select * from company where comp_name = ? and password = ? ";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, Comp_name);
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
		} catch (SQLException e) {
			String x = "login faild";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void link_Company_Coupon(long companyID, long couponID) throws CouponSystemException {
		String sql = "insert into Company_Coupon values (?,?)";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, companyID);
				stm.setLong(2, couponID);
				stm.executeUpdate();
			}
			System.out.println("company " + companyID + " linked to coupon " + couponID);
		} catch (SQLException e) {
			String x = "Error while trying to link Company with the new coupon";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public void unlink_all_Company_Coupons(Company company) throws CouponSystemException {
		String sql = "delete from Company_Coupon where comp_id= ?";
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setLong(1, company.getId());
				stm.executeUpdate();
			}
			System.out.println("company " + company.getId() + " unlinked all coupons");
		} catch (SQLException e) {
			String x = "Error while trying to unlink Company from all coupons";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}

	@Override
	public Company getCompanyByName(String comp_name) throws CouponSystemException {
		String sql = "select * from company where comp_name = ?";
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			try (PreparedStatement stm = con.prepareStatement(sql);) {
				stm.setString(1, comp_name);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					Company a = new Company();
					a.setId(rs.getLong("ID"));
					a.setCompName(rs.getString("COMP_NAME"));
					a.setPassword(rs.getString("PASSWORD"));
					a.setEmail(rs.getString("EMAIL"));
					return a;
				}
				return null;
			}
		} catch (SQLException e) {
			String x = "Error while trying to read Company from DB";
			throw new CouponSystemException(x, e);
		} finally {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		}
	}
}
