package br.com.player.dao.video;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.User;
import br.com.player.entity.Video;

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
				throw new SQLException("Erro ao inserir video no banco de dados.");
			
			while (resultSet.next()) {
				id = Long.valueOf(resultSet.getLong(1));
			}
			
			if (id < 0)
				throw new SQLException("Erro ao inserir video no banco de dados.");
			
			return id;
			
		} catch (NamingException e) {
			log.error("Erro: Video");
			throw e;
		} catch (SQLException e) {
			log.error("Erro: Video");
			throw e;
		} catch (Exception e) {
			log.error("Erro: Video");
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	public Video get(Long id) throws NamingException, SQLException, Exception {
		try {
			preparedStatement = getConnection().prepareStatement(
					"select id, name, description, file, filetype, created, modified from video where id = ?");
			preparedStatement.setLong(1, id.longValue());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				video.setId(Long.valueOf(resultSet.getLong("ID")));
				video.setName(resultSet.getString("NAME"));
				video.setDescription(resultSet.getString("DESCRIPTION"));
				video.setFile(Paths.get(resultSet.getString("FILE"), new String[0]));
				video.setFileType(resultSet.getString("FILETYPE"));
				video.setCreated(resultSet.getDate("CREATED"));
				video.setModified(resultSet.getDate("MODIFIED"));
			}
			return video;
		} catch (SQLException e) {
			throw e;
		} catch (NamingException e) {
			throw e;
		}
	}
	
	public long update(Object object, User user) throws NamingException, SQLException, Exception {
		return preparedStatement.executeUpdate();
	}
	
	public long delete(Long id) throws NamingException, SQLException, Exception {
		return preparedStatement.executeUpdate();
	}
	
	public List<Video> list() throws NamingException, SQLException, Exception {
		return list;
	}
}