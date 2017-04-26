package br.com.player.dao.factory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.player.util.Resource;

public abstract class DAO implements Serializable{
  
	private Connection connection = null;
	private static DataSource dataSource = null;
	private static final long serialVersionUID = 1L;
  
	private DataSource getDataSource() throws NamingException {
		if (dataSource == null){
			Resource resource = new Resource();
			dataSource = resource.lookupDataSource("DS_SYSTEM", "PROVIDER_URL");
		}
		return dataSource;
	}
  
	public Connection getConnection() throws NamingException, SQLException, Exception {
		if ((this.connection == null) || (this.connection.isClosed())) {
			this.connection = getDataSource().getConnection();
		}
		return this.connection;
	}
  
	public void closeConnection() throws NamingException, SQLException, Exception {
		if (this.connection != null) {
			this.connection.close();
		}
	}
}