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
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;
import br.com.player.util.Messages;

public class PropertiesTypeDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PropertiesTypeDAO.class);
	
	private static List<PropertiesType> list = null;
	private static PropertiesType type = null;
	private static PropertiesTypeDAO propertiesTypeDAO = null;
	private static Timestamp timestamp = null;
	private static User user = null;
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public static PropertiesTypeDAO getInstance() {
		return propertiesTypeDAO == null ? new PropertiesTypeDAO() : propertiesTypeDAO;
	}

	public long create(Object object, User user) throws NamingException, SQLException, Exception {

		type = (PropertiesType) object;
		
		long id = -1;
		
		try {

			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			
			preparedStatement = getConnection().prepareStatement(SQLPropertiesType.SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, type.getName());
			preparedStatement.setString(2, type.getDescription());
			preparedStatement.setTimestamp(3, timestamp);
			preparedStatement.setNull(4, 91);
			preparedStatement.setLong(5, user.getId().longValue());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet == null)
				throw new Exception("Erro ao inserir classificação no banco de dados.");

			while (resultSet.next()) {
				id = Long.valueOf(resultSet.getLong(1));
			}
			
			return id;

		} catch (NamingException e) {
			log.error(Messages.ERROR_INS + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_INS + Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_INS + "[" + type.toString() + "]", e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();

			try {
			
				closeConnection();
			
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + "[" + type.toString() + "]", e);
				throw e;
			}
		}
	}

	public PropertiesType get(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[" + id + "]");
		
		try {

			timestamp = null;
			
			preparedStatement = getConnection().prepareStatement(SQLPropertiesType.SQL_GET);
			preparedStatement.setLong(1, id.longValue());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				
				type = new PropertiesType();
				type.setId(Long.valueOf(resultSet.getLong(SQLPropertiesType.ID)));
				type.setName(resultSet.getString(SQLPropertiesType.NAME));
				type.setDescription(resultSet.getString(SQLPropertiesType.DESCRIPTION));
				
				timestamp = resultSet.getTimestamp(SQLPropertiesType.CREATED);
				if (timestamp != null)
					type.setCreated(new Date(timestamp.getTime()));
				
				timestamp = resultSet.getTimestamp(SQLPropertiesType.MODIFIED);
				if (timestamp != null)
					type.setModified(new Date(timestamp.getTime()));
				
				user = new User();
				user.setId(Long.valueOf(resultSet.getLong(SQLPropertiesType.USER_ID)));
				user.setName(resultSet.getString(SQLPropertiesType.USER_NAME));
				type.setUser(user);
			}
			
			return type;

		} catch (NamingException e) {
			log.error(Messages.ERROR_GET + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_GET + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_GET + "[id=" + id + "]", e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			if (resultSet != null && !resultSet.isClosed())
				resultSet.close();

			try {
			
				closeConnection();
			
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + "[id=" + id + "]", e);
				throw e;
			}
		}
	}
	
	public long update(Object object, User user) throws NamingException, SQLException, Exception {

		type = (PropertiesType) object;
		
		if (log.isDebugEnabled())
			log.debug("PropertiesTypeDAO:update(" + type.toString() + ")");
		
		try {

			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			preparedStatement = getConnection().prepareStatement(SQLPropertiesType.SQL_UPDATE, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setString(1, type.getName());
			preparedStatement.setString(2, type.getDescription());
			preparedStatement.setTimestamp(3, timestamp);
			preparedStatement.setLong(4, user.getId().longValue());
			preparedStatement.setLong(5, type.getId().longValue());

			return preparedStatement.executeUpdate();

		} catch (NamingException e) {
			log.error(Messages.ERROR_UPD + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_UPD + Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_UPD + "[" + type.toString() + "]", e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();

			try {

				closeConnection();

			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);				
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + "[" + type.toString() + "]", e);
				throw e;
			}
		}
	}

	public long delete(Long id) throws NamingException, SQLException, Exception {
		
		try {
			
			preparedStatement = getConnection().prepareStatement(SQLPropertiesType.SQL_DELETE);
			preparedStatement.setLong(1, id.longValue());
			
			return preparedStatement.executeUpdate();

		} catch (NamingException e) {
			log.error(Messages.ERROR_DEL + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DEL + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_DEL + "[id=" + id + "]", e);
			throw e;
		} finally {
			
			if (preparedStatement != null && !preparedStatement.isClosed())
				preparedStatement.close();			
			
			try {
				
				closeConnection();
				
			} catch (NamingException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + "[id=" + id + "]", e);
				throw e;
			}
		}
	}
	
	public List<PropertiesType> list() throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[" + null + "]");
		
		try {

			timestamp = null;
			
			preparedStatement = getConnection().prepareStatement(SQLPropertiesType.SQL_LIST);
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<PropertiesType>();

			while (resultSet.next()) {

				type = new PropertiesType();
				type.setId(Long.valueOf(resultSet.getLong(SQLPropertiesType.ID)));
				type.setName(resultSet.getString(SQLPropertiesType.NAME));
				type.setDescription(resultSet.getString(SQLPropertiesType.DESCRIPTION));
				
				timestamp = resultSet.getTimestamp(SQLPropertiesType.CREATED);
				if (timestamp != null)
					type.setCreated(new Date(timestamp.getTime()));
				
				timestamp = resultSet.getTimestamp(SQLPropertiesType.MODIFIED);
				if (timestamp != null)
					type.setModified(new Date(timestamp.getTime()));

				user = new User();
				user.setId(Long.valueOf(resultSet.getLong(SQLPropertiesType.USER_ID)));
				user.setName(resultSet.getString(SQLPropertiesType.USER_NAME));
				type.setUser(user);
				
				list.add(type);
			}
			
			return list;

		} catch (NamingException e) {
			log.error(Messages.ERROR_LIST + Messages.ERROR_DATASOURCE, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_LIST + Messages.ERROR_DATABASE, e);
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
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATASOURCE, e);
				throw e;
			} catch (SQLException e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION + Messages.ERROR_DATABASE, e);
				throw e;
			} catch (Exception e) {
				log.error(Messages.ERROR_CLOSE_CONNECTION, e);
				throw e;
			}
		}
	}	
}