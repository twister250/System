package br.com.player.dao.properties;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.Properties;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;
import br.com.player.util.Messages;

public class PropertiesDAO extends DAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PropertiesDAO.class);
	
	private static List<Properties> list = null;
	private static Properties property = null;
	private static PropertiesDAO propertiesDAO = null;
	private static PropertiesType type = null;
	private static Timestamp timestamp = null;
	private static User user = null;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public static PropertiesDAO getInstance() {
		return propertiesDAO == null ? new PropertiesDAO() : propertiesDAO;
	}

	public long create(Object object, User user) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[object=" + object.toString() + ";user=" + user.toString() + "]");
		
		property = (Properties) object;
		
		long id = -1;

		try {
			
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			preparedStatement = getConnection().prepareStatement(SQLProperties.SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, property.getName());
			preparedStatement.setString(2, property.getValue());
			preparedStatement.setLong(3, property.getType().getId().longValue());
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.setNull(5, Types.NULL);
			preparedStatement.setLong(6, user.getId().longValue());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet == null)
				throw new SQLException("Erro ao inserir propriedade no banco de dados.");

			while (resultSet.next()) {
				id = Long.valueOf(resultSet.getLong(1));
			}
			
			if (id < 0)
				throw new SQLException(Messages.ERROR_INS + "[" + property.toString() + "]");

			return id;

		} catch (NamingException e) {
			log.error(Messages.ERROR_INS + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_INS + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_INS + "["+property.toString()+"]", e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();
			
			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();
			
			try {
				
				closeConnection();
				
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}

	public Properties get(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
				
		try {

			timestamp = null;

			preparedStatement = getConnection().prepareStatement(SQLProperties.SQL_GET);
			preparedStatement.setLong(1, id.longValue());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				property = new Properties();
				property.setId(Long.valueOf(resultSet.getLong(SQLProperties.ID)));
				property.setName(resultSet.getString(SQLProperties.NAME));
				property.setValue(resultSet.getString(SQLProperties.VALUE));
				
				timestamp = resultSet.getTimestamp(SQLProperties.CREATED);
				if (timestamp != null)
					property.setCreated(new Date(timestamp.getTime()));
				
				timestamp = resultSet.getTimestamp(SQLProperties.MODIFIED);
				if (timestamp != null)
					property.setModified(new Date(timestamp.getTime()));

				PropertiesType type = new PropertiesType();
				type.setName(resultSet.getString(SQLProperties.TYPE_NAME));
				type.setId(Long.valueOf(resultSet.getLong(SQLProperties.TYPE_ID)));
				
				property.setType(type);
				
				user = new User();
				user.setId(Long.valueOf(resultSet.getLong(SQLProperties.USER_ID)));
				user.setName(resultSet.getString(SQLProperties.USER_NAME));
				property.setUser(user);
			}
			
			return property;
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_GET + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_GET + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_GET, e);
			throw e;
		} finally {
			
			if (preparedStatement != null && preparedStatement.isClosed())
				preparedStatement.close();
			
			if (resultSet != null && resultSet.isClosed())
				resultSet.close();
			
			try {
				
				closeConnection();
				
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}

	public long update(Object object, User user) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[object=" + object.toString() + ";user=" + user.toString() + "]");
		
		property = (Properties) object;		
		
		try {
			
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			preparedStatement = getConnection().prepareStatement(SQLProperties.SQL_UPDATE, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setString(1, property.getName());
			preparedStatement.setString(2, property.getValue());
			preparedStatement.setLong(3, property.getType().getId().longValue());
			preparedStatement.setTimestamp(4, timestamp);
			preparedStatement.setLong(5, user.getId().longValue());
			preparedStatement.setLong(6, property.getId().longValue());

			return preparedStatement.executeUpdate();

		} catch (NamingException e) {
			log.error(Messages.ERROR_UPD + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_UPD + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_UPD, e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			try {

				closeConnection();

			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}

	public long delete(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			preparedStatement = getConnection().prepareStatement(SQLProperties.SQL_DELETE);
			preparedStatement.setLong(1, id.longValue());
			
			return preparedStatement.executeUpdate();
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_DEL + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DEL + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_DEL, e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			try {

				closeConnection();

			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}

	public List<Properties> list() throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[" + null + "]");
		
		try {

			timestamp = null;
			
			preparedStatement = getConnection().prepareStatement(SQLProperties.SQL_LIST);
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<Properties>();
			
			while (resultSet.next()) {			

				property = new Properties();
				property.setId(Long.valueOf(resultSet.getLong(SQLProperties.ID)));
				property.setName(resultSet.getString(SQLProperties.NAME));
				property.setValue(resultSet.getString(SQLProperties.VALUE));
				
				timestamp = resultSet.getTimestamp(SQLProperties.CREATED);
				if (timestamp != null)
					property.setCreated(new Date(timestamp.getTime()));
				
				timestamp = resultSet.getTimestamp(SQLProperties.MODIFIED);
				if (timestamp != null)
					property.setModified(new Date(timestamp.getTime()));
				
				user = new User();
				user.setId(Long.valueOf(resultSet.getLong(SQLProperties.USER_ID)));
				user.setName(resultSet.getString(SQLProperties.USER_NAME));
				
				type = new PropertiesType();
				type.setId(Long.valueOf(resultSet.getLong(SQLProperties.TYPE_ID)));
				type.setName(resultSet.getString(SQLProperties.TYPE_NAME));
				
				property.setUser(user);
				property.setType(type);
				
				list.add(property);
			}
			
			return list;
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_LIST + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_LIST + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_LIST, e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();
			
			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();

			try {
				
				closeConnection();
				
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}
	
	public List<Properties> findAllBy(Properties pojo) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[" + pojo.toString() + "]");
		
		timestamp = null;
		
		int index = 1;
		
		try {
		
			preparedStatement = getConnection().prepareStatement(generateSQLFindAllBy(pojo, SQLProperties.SQL_FIND_ALL_BY));
			
			if (pojo.getName() != null)
				preparedStatement.setString(index++, pojo.getName());

			if (pojo.getType().getId() != null)
				preparedStatement.setLong(index++, pojo.getType().getId().longValue());
			
			if (pojo.getType().getName() != null)
				preparedStatement.setString(index++, pojo.getType().getName());
			
			if (pojo.getCreated() != null)
				preparedStatement.setTimestamp(index++, new Timestamp(pojo.getCreated().getTime()));
			
			if (pojo.getModified() != null)
				preparedStatement.setTimestamp(index++, new Timestamp(pojo.getModified().getTime()));
			
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<Properties>();
			
			while (resultSet.next()) {			

				property = new Properties();
				property.setId(Long.valueOf(resultSet.getLong(SQLProperties.ID)));
				property.setName(resultSet.getString(SQLProperties.NAME));
				property.setValue(resultSet.getString(SQLProperties.VALUE));
				
				timestamp = resultSet.getTimestamp(SQLProperties.CREATED);
				if (timestamp != null)
					property.setCreated(new Date(timestamp.getTime()));
				
				timestamp = resultSet.getTimestamp(SQLProperties.MODIFIED);
				if (timestamp != null)
					property.setModified(new Date(timestamp.getTime()));
				
				user = new User();
				user.setId(Long.valueOf(resultSet.getLong(SQLProperties.USER_ID)));
				user.setName(resultSet.getString(SQLProperties.USER_NAME));
				
				type = new PropertiesType();
				type.setId(Long.valueOf(resultSet.getLong(SQLProperties.TYPE_ID)));
				type.setName(resultSet.getString(SQLProperties.TYPE_NAME));
				
				property.setUser(user);
				property.setType(type);
				
				list.add(property);
			}
			
			return list;
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_LIST + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_LIST + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_LIST, e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();
			
			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();

			try {
				
				closeConnection();
				
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}
	
	private static String generateSQLFindAllBy(Properties property, String sql) {
		
		StringBuilder sb = new StringBuilder(sql);
		
		if (property.getName() != null)
			sb.append(" and a.name = ?");

		if (property.getType().getId() != null)
			sb.append(" and a.properties_type_id = ?");
		
		if (property.getType().getName() != null)
			sb.append(" and b.name = ?");
		
		if (property.getCreated() != null)
			sb.append(" and a.created = ?");
		
		if (property.getModified() != null)
			sb.append(" and a.modified = ?");
		
		if (log.isDebugEnabled())
			log.debug("[sql=" + sb.toString() + ";" + property.toString() + "]");
		
		return sb.toString();
	}
}