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

import org.apache.log4j.Logger;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.Properties;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;

public class PropertiesDAO extends DAO implements Serializable {
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static List<Properties> list = null;
	private static Properties property = null;
	private static PropertiesDAO propertiesDAO = null;
	private static PropertiesType type = null;
	private static Timestamp timestamp = null;
	private static User user = null;
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PropertiesDAO.class);

	public static PropertiesDAO getInstance() {
		return propertiesDAO == null ? new PropertiesDAO() : propertiesDAO;
	}

	public Properties create(Properties property, User user) throws SQLException, NamingException, Exception {
		try {
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			this.preparedStatement = getConnection().prepareStatement(
					"insert into properties (name, value, properties_type_id, created, modified, user_id) values (?,?,?,?,?,?)",
					1);
			this.preparedStatement.setString(1, property.getName());
			this.preparedStatement.setString(2, property.getValue());
			this.preparedStatement.setLong(3, property.getType().getId().longValue());
			this.preparedStatement.setTimestamp(4, timestamp);
			this.preparedStatement.setNull(5, 91);
			this.preparedStatement.setLong(6, user.getId().longValue());
			this.preparedStatement.executeUpdate();
			this.resultSet = this.preparedStatement.getGeneratedKeys();
			if ((this.resultSet != null) && (this.resultSet.next())) {
				property.setId(Long.valueOf(this.resultSet.getLong(1)));
				property.setCreated(new Date(timestamp.getTime()));
				property.setUser(user);
				return property;
			}
			return null;
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (NamingException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
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

	public Properties get(Long id) throws NamingException, SQLException, Exception {
		try {
			this.preparedStatement = getConnection().prepareStatement(
					"select a.id, a.name, a.value, a.properties_type_id type_id, a.created, a.modified, a.user_id, b.name type_name, c.name user_name\tfrom properties a, properties_type b, user c\twhere a.id = ?\tand a.properties_type_id = b.id\tand a.user_id = c.id");
			this.preparedStatement.setLong(1, id.longValue());
			this.resultSet = this.preparedStatement.executeQuery();
			while (this.resultSet.next()) {
				property = new Properties();

				property.setId(Long.valueOf(this.resultSet.getLong("ID")));
				property.setName(this.resultSet.getString("NAME"));
				property.setValue(this.resultSet.getString("VALUE"));
				property.setCreated(this.resultSet.getDate("CREATED"));
				property.setModified(this.resultSet.getDate("MODIFIED"));

				PropertiesType type = new PropertiesType();
				type.setName(this.resultSet.getString("TYPE_NAME"));
				type.setId(Long.valueOf(this.resultSet.getLong("TYPE_ID")));
				property.setType(type);
				user.setId(Long.valueOf(this.resultSet.getLong("USER_ID")));
				property.setUser(user);
			}
			return property;
		} catch (NamingException e) {
			log.error(e);
			throw e;
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
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

	public long update(Properties property) throws SQLException, NamingException, Exception {
		try {
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			this.preparedStatement = getConnection().prepareStatement(
					"update properties set name = ?, value = ?, properties_type_id = ?, modified = ? where id = ?");
			this.preparedStatement.setString(1, property.getName());
			this.preparedStatement.setString(2, property.getValue());
			this.preparedStatement.setLong(3, property.getType().getId().longValue());
			this.preparedStatement.setTimestamp(4, timestamp);
			this.preparedStatement.setLong(5, property.getId().longValue());
			return this.preparedStatement.executeUpdate();
		} catch (NamingException e) {
			log.error(e);
			throw e;
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
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
				log.error(e);
				throw e;
			} catch (SQLException e) {
				log.error(e);
				throw e;
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
	}

	public int delete(Long id) throws SQLException, NamingException, Exception {
		try {
			this.preparedStatement = getConnection().prepareStatement("delete from properties where id = ?");
			this.preparedStatement.setLong(1, id.longValue());
			return this.preparedStatement.executeUpdate();
		} catch (NamingException e) {
			log.error(e);
			throw e;
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
			}
		}
	}

	public List<Properties> list() throws SQLException, NamingException, Exception {
		try {
			this.preparedStatement = getConnection().prepareStatement(
					"select a.id, a.name, a.value, b.id type_id, b.name type_name, a.created, a.modified,\ta.user_id, c.name user_name\tfrom properties a, properties_type b, user c\twhere a.properties_type_id = b.id and\ta.user_id = c.id");
			this.resultSet = this.preparedStatement.executeQuery();
			list = new ArrayList();
			while (this.resultSet.next()) {
				user = new User();
				user.setId(Long.valueOf(this.resultSet.getLong("USER_ID")));
				user.setName(this.resultSet.getString("USER_NAME"));

				type = new PropertiesType();
				type.setId(Long.valueOf(this.resultSet.getLong("TYPE_ID")));
				type.setName(this.resultSet.getString("TYPE_NAME"));

				property = new Properties();
				property.setId(Long.valueOf(this.resultSet.getLong("ID")));
				property.setName(this.resultSet.getString("NAME"));
				property.setValue(this.resultSet.getString("VALUE"));
				property.setCreated(this.resultSet.getDate("CREATED"));
				property.setModified(this.resultSet.getDate("MODIFIED"));
				property.setUser(user);
				property.setType(type);
				list.add(property);
			}
			return list;
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (NamingException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
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
				log.error(e);
				throw e;
			} catch (SQLException e) {
				log.error(e);
				throw e;
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
	}
}