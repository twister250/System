package br.com.player.dao.properties;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;

public class PropertiesTypeDAO extends DAO implements Serializable {
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static List<PropertiesType> list = null;
	private static PropertiesType type = null;
	private static PropertiesTypeDAO propertiesTypeDAO = null;
	private static Timestamp timestamp = null;
	private static final long serialVersionUID = 1L;

	public static PropertiesTypeDAO getInstance() {
		return propertiesTypeDAO == null ? new PropertiesTypeDAO() : propertiesTypeDAO;
	}

	public PropertiesType create(PropertiesType type, User user) throws NamingException, SQLException, Exception {
		try {
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			this.preparedStatement = getConnection().prepareStatement(
					"insert into properties_type (name, description, created, modified, user_id) values (?,?,?,?,?)",
					1);
			this.preparedStatement.setString(1, type.getName());
			this.preparedStatement.setString(2, type.getDescription());
			this.preparedStatement.setTimestamp(3, timestamp);
			this.preparedStatement.setNull(4, 91);
			this.preparedStatement.setLong(5, user.getId().longValue());
			this.preparedStatement.executeUpdate();
			this.resultSet = this.preparedStatement.getGeneratedKeys();
			if ((this.resultSet != null) && (this.resultSet.next())) {
				type.setId(Long.valueOf(this.resultSet.getLong(1)));
				type.setCreated(new Date(timestamp.getTime()));
				return type;
			}
			return null;
		} catch (SQLException e) {
			throw e;
		} catch (NamingException e) {
			throw e;
		} finally {
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
			}
			if (this.resultSet != null) {
				this.resultSet.close();
			}
			try {
				closeConnection();
			} catch (NamingException e) {
				throw e;
			} catch (SQLException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public PropertiesType get(Long id) throws NamingException, SQLException, Exception {
		try {
			this.preparedStatement = getConnection().prepareStatement(
					"select id, name, description, created, modified, user_id from properties_type where id = ?");
			this.preparedStatement.setLong(1, id.longValue());
			this.resultSet = this.preparedStatement.executeQuery();
			while (this.resultSet.next()) {
				type = new PropertiesType();
				type.setId(Long.valueOf(this.resultSet.getLong("ID")));
				type.setName(this.resultSet.getString("NAME"));
				type.setDescription(this.resultSet.getString("DESCRIPTION"));
				type.setCreated(this.resultSet.getDate("CREATED"));
				type.setModified(this.resultSet.getDate("MODIFIED"));

				User user = new User();
				user.setId(Long.valueOf(this.resultSet.getLong("USER_ID")));
				type.setUser(user);
			}
			return type;
		} catch (NamingException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
			}
			if (this.resultSet != null) {
				this.resultSet.close();
			}
			try {
				closeConnection();
			} catch (NamingException e) {
				throw e;
			} catch (SQLException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public List<PropertiesType> list() throws NamingException, SQLException, Exception {
		try {
			this.preparedStatement = getConnection()
					.prepareStatement("select id, name, description, created, modified, user_id from properties_type");
			this.resultSet = this.preparedStatement.executeQuery();
			list = new ArrayList();
			while (this.resultSet.next()) {
				User user = new User();
				user.setId(Long.valueOf(this.resultSet.getLong("USER_ID")));

				type = new PropertiesType();
				type.setId(Long.valueOf(this.resultSet.getLong("ID")));
				type.setName(this.resultSet.getString("NAME"));
				type.setDescription(this.resultSet.getString("DESCRIPTION"));
				type.setCreated(this.resultSet.getDate("CREATED"));
				type.setModified(this.resultSet.getDate("MODIFIED"));
				type.setUser(user);
				list.add(type);
			}
			return list;
		} catch (NamingException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
			}
			if (this.resultSet != null) {
				this.resultSet.close();
			}
			try {
				closeConnection();
			} catch (NamingException e) {
				throw e;
			} catch (SQLException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
	}
}