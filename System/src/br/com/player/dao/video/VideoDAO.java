package br.com.player.dao.video;

import java.nio.file.Paths;
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
import br.com.player.entity.User;
import br.com.player.entity.Video;
import br.com.player.util.Messages;

public class VideoDAO extends DAO {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(VideoDAO.class);
	
	private static List<Video> list = null;
	private static Video video = null;
	private static VideoDAO videoDAO = null;
	private static Timestamp timestamp = null;
	private static User user = null;
	
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	public static VideoDAO getInstance() {
		return videoDAO == null ? new VideoDAO() : videoDAO;
	}
	
	public long create(Object object, User user) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[object=" + object.toString() + ";" + "user=" + user.toString() + "]");
		
		video = (Video) object;
		
		long id = -1;
		
		try {
			
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			
			preparedStatement = getConnection().prepareStatement(SQLVideo.SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, video.getName());
			preparedStatement.setString(2, video.getDescription());
			preparedStatement.setString(3, video.getFile().getName(video.getFile().getNameCount() - 1).toString());
			preparedStatement.setString(4, video.getFileType());
			preparedStatement.setTimestamp(5, timestamp);
			preparedStatement.setNull(6, Types.NULL);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet == null)
				throw new SQLException(Messages.ERROR_INS + "[" + video.toString() + "]");
			
			while (resultSet.next()) {
				id = Long.valueOf(resultSet.getLong(1));
			}
			
			if (id < 0)
				throw new SQLException(Messages.ERROR_INS + "[" + video.toString() + "]");
			
			return id;
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_INS + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_INS + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_INS + "[" + video.toString() + "]");
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
		
	public Video get(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			preparedStatement = getConnection().prepareStatement(SQLVideo.SQL_GET);
			preparedStatement.setLong(1, id.longValue());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				video.setId(Long.valueOf(resultSet.getLong(SQLVideo.ID)));
				video.setName(resultSet.getString(SQLVideo.NAME));
				video.setDescription(resultSet.getString(SQLVideo.DESCRIPTION));
				video.setFile(Paths.get(resultSet.getString(SQLVideo.FILE), new String[0]));
				video.setFileType(resultSet.getString(SQLVideo.FILETYPE));
				video.setCreated(resultSet.getDate(SQLVideo.CREATED));
				video.setModified(resultSet.getDate(SQLVideo.MODIFIED));
				
				user = new User();
				user.setId(resultSet.getLong(SQLVideo.USER_ID));
				video.setUser(user);
			}
			
			return video;
			
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
	
	public long update(Object object, User user) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[object=" + object.toString() + ";" + "user=" + user.toString() + "]");
		
		video = (Video) object;
		
		try {
			
			timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			
			preparedStatement = getConnection().prepareStatement(SQLVideo.SQL_UPDATE, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setString(1, video.getName());
			preparedStatement.setString(2, video.getDescription());
			preparedStatement.setString(3, video.getFileType());
			preparedStatement.setString(4, video.getFile().getName(-1).toString());
			preparedStatement.setTimestamp(5, timestamp);
			preparedStatement.setLong(6, user.getId().longValue());
			preparedStatement.setLong(7, video.getId().longValue());
			
			return preparedStatement.executeUpdate();			

		} catch (NamingException e) {
			log.error(Messages.ERROR_UPD + "[" + Messages.ERROR_DATASOURCE + "]" + "[" + video.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_UPD + "[" + Messages.ERROR_DATABASE + "]" + "[" + video.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_UPD + "[" + video.toString() + "]", e);
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
	
	public long delete(Long id) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			preparedStatement = getConnection().prepareStatement(SQLVideo.SQL_DELETE);
			preparedStatement.setLong(1, id.longValue());
			
			return preparedStatement.executeUpdate();			
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_DEL + "[id=" + id + "]" + "[" + Messages.ERROR_DATASOURCE + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DEL + "[id=" + id + "]" + "[" + Messages.ERROR_DATABASE + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_DEL + "[id=" + id + "]", e);
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
	
	public List<Video> list() throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[" + null + "]");
		
		timestamp = null;
		
		try {
			
			preparedStatement = getConnection().prepareStatement(SQLVideo.SQL_LIST);
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<Video>();
			
			while (resultSet.next()) {
				
				video = new Video();
				video.setId(resultSet.getLong(SQLVideo.ID));
				video.setName(resultSet.getString(SQLVideo.NAME));
				video.setDescription(resultSet.getString(SQLVideo.DESCRIPTION));
				video.setFileType(resultSet.getString(SQLVideo.FILETYPE));
				video.setFile(Paths.get(resultSet.getString(SQLVideo.FILE)));
				video.setCreated(resultSet.getDate(SQLVideo.CREATED));
				video.setModified(resultSet.getDate(SQLVideo.MODIFIED));
				
				user = new User();
				user.setId(resultSet.getLong(SQLVideo.USER_ID));
				user.setName(resultSet.getString(SQLVideo.USER_NAME));
				
				video.setUser(user);
				
				list.add(video);				
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
		}		
	}
}