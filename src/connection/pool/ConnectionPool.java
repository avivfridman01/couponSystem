package connection.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import system.exception.CouponSystemException;

public class ConnectionPool {
	// ATTRIBUTES
	private Set<Connection> connections = new LinkedHashSet<>();
	public static final int MAX_CONNECTIONS = 10;
	private boolean x;
	private String url = "jdbc:derby://localhost:1527/db";
	private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";

	// SINGLTON
	private ConnectionPool() throws CouponSystemException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			String y = "Couldn't connect to pool - driver not found";
			throw new CouponSystemException(y, e);
		}
		for (int i = 0; i < MAX_CONNECTIONS; i++) {
			try {
				connections.add(DriverManager.getConnection(url));
			} catch (SQLException e) {
				String y = "Couldn't create pool";
				throw new CouponSystemException(y, e);
			}
		}
		x = true;
	}

	private static ConnectionPool instance;

	public synchronized static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	// METHODS
	public synchronized Connection getConnection() throws CouponSystemException {
		if (!x) {
			throw new CouponSystemException("get connection faild - connection pool close");
		}
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				String x = "get Connection faild";
				throw new CouponSystemException(x, e);
			}
		}
		Iterator<Connection> it = connections.iterator();
		Connection con = it.next();
		it.remove();
		notify();
		return con;
	}

	public synchronized void returnConnection(Connection con) throws CouponSystemException {
		if (connections.size() == MAX_CONNECTIONS) {
			try {
				wait();
			} catch (InterruptedException e) {
				String x = "return Connection faild";
				throw new CouponSystemException(x, e);
			}
		}
		connections.add(con);
		notify();
	}

	public synchronized void closeAllConnections() throws CouponSystemException {
		x = false;
		while (connections.size() < MAX_CONNECTIONS) {
			try {
				wait();
			} catch (InterruptedException e) {
				String x = "close all connections faild";
				throw new CouponSystemException(x, e);
			}
		}
		Iterator<Connection> it = connections.iterator();
		while (it.hasNext()) {
			try {
				it.next().close();
				it.remove();
			} catch (SQLException e) {
				String x = "close all connections faild";
				throw new CouponSystemException(x, e);
			}
		}
		instance = null;
	}
}
