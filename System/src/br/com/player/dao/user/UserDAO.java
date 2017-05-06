package br.com.player.dao.user;

import java.io.Serializable;
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
import br.com.player.entity.User;

public class UserDAO extends DAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserDAO.class);
	private static List<User> userList = null;
	private static Timestamp timestamp = null;
	private static User user = null;
	private static UserDAO userDAO = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public static UserDAO getInstance() {

		if (log.isDebugEnabled())
			log.debug("UserDAO:getInstance()");

		return userDAO = userDAO == null ? new UserDAO() : userDAO;
	}

	public long create(User user) throws NamingException, SQLException, Exception {

		try {
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());

			this.preparedStatement = getConnection().prepareStatement(
					"insert into user (name, email, password, created, modified) values (?,?,?,?,?)", 1);
			this.preparedStatement.setString(1, user.getName());
			this.preparedStatement.setString(2, user.getEmail());
			this.preparedStatement.setString(3, user.getPassword());
			this.preparedStatement.setTimestamp(4, timestamp);
			this.preparedStatement.setNull(5, 91);
			this.preparedStatement.executeUpdate();
			this.resultSet = this.preparedStatement.getGeneratedKeys();

			if ((this.resultSet != null) && (this.resultSet.next())) {
				return this.resultSet.getLong(1);
			}

			return -1L;

		} catch (SQLException e) {
			log.error("Erro ao inserir usuário.", e);
			throw e;
		} catch (NamingException e) {
			log.error("Erro ao acessar base de dados.", e);
			throw e;
		} catch (Exception e) {
			log.error("Erro ao criar usuário na base.", e);
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
				log.error("Erro ao fechar conexão.");
				throw e;
			} catch (SQLException e) {
				log.error("Erro ao fechar conexão.");
				throw e;
			} catch (Exception e) {
				log.error("Erro ao fechar conexão.");
				throw e;
			}
		}
	}

	public User get(Long id) throws NamingException, SQLException, Exception {

		try {
			this.preparedStatement = getConnection()
					.prepareStatement("select id, name, email, created, modified from user where id = ?");
			this.preparedStatement.setLong(1, id.longValue());
			this.resultSet = this.preparedStatement.executeQuery();

			while (this.resultSet.next()) {
				user.setId(Long.valueOf(this.resultSet.getLong("ID")));
				user.setName(this.resultSet.getString("NAME"));
				user.setEmail(this.resultSet.getString("EMAIL"));
				user.setCreated(this.resultSet.getDate("CREATED"));
				user.setModified(this.resultSet.getDate("MODIFIED"));
			}

			return user;

		} catch (NamingException e) {
			log.error("Erro ao acessar base de dados.", e);
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

	public List<User> checkAccess(String email, String password) throws NamingException, SQLException, Exception {
		userList = new ArrayList<User>();

		try {
			this.preparedStatement = getConnection()
					.prepareStatement("select id, name, email, created from user where email = ? and password = ?");
			this.preparedStatement.setString(1, email);
			this.preparedStatement.setString(2, password);
			this.resultSet = this.preparedStatement.executeQuery();

			while (this.resultSet.next()) {
				user = new User();
				user.setId(Long.valueOf(this.resultSet.getLong("ID")));
				user.setName(this.resultSet.getString("NAME"));
				user.setEmail(this.resultSet.getString("EMAIL"));
				user.setCreated(this.resultSet.getDate("CREATED"));
				userList.add(user);
			}

			return userList;

		} catch (NamingException e) {
			log.error("Erro ao acessar base de dados.", e);
			throw e;
		} catch (SQLException e) {
			log.error("Erro ao consultar usuário na base de dados.", e);
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