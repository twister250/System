package br.com.player.dao.factory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.player.entity.User;
import br.com.player.util.Resource;

public abstract class DAO implements Serializable {

	private static Connection connection = null;
	private static DataSource dataSource = null;
	private static final long serialVersionUID = 1L;

	private static DataSource getDataSource() throws NamingException {
		if (dataSource == null) {
			Resource resource = new Resource();
			dataSource = resource.lookupDataSource("DS_SYSTEM", "PROVIDER_URL");
		}
		return dataSource;
	}

	public static Connection getConnection() throws NamingException, SQLException, Exception {
		if ((connection == null) || (connection.isClosed())) {
			connection = getDataSource().getConnection();
		}
		return connection;
	}

	public static void closeConnection() throws NamingException, SQLException, Exception {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	public abstract long create(Object object, User user) throws NamingException, SQLException, Exception;
	
	public abstract Object get(Long id) throws NamingException, SQLException, Exception;
	
	public abstract long update(Object object, User user) throws NamingException, SQLException, Exception;
	
	public abstract long delete(Long id) throws NamingException, SQLException, Exception;
	
	public abstract List<?> list() throws NamingException, SQLException, Exception;
	
}