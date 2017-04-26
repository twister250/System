package br.com.player.dao.video;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.Video;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

public class VideoDAO
  extends DAO
{
  private static final long serialVersionUID = 1L;
  private static List<Video> videoList = null;
  private static PreparedStatement preparedStatement = null;
  private static ResultSet resultSet = null;
  private static Timestamp timestamp = null;
  private static Video video = null;
  
  public long create(Video video)
    throws NamingException, SQLException, Exception
  {
    long out = -1L;
    try
    {
      timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
      preparedStatement = getConnection().prepareStatement("insert into video (name, description, file, filetype, created, modified) values (?, ?, ?, ?, ?, ?)", 1);
      preparedStatement.setString(1, video.getName());
      preparedStatement.setString(2, video.getDescription());
      preparedStatement.setString(3, video.getFile().getName(video.getFile().getNameCount() - 1).toString());
      preparedStatement.setString(4, video.getFileType());
      preparedStatement.setTimestamp(5, timestamp);
      preparedStatement.setNull(6, 91);
      preparedStatement.executeUpdate();
      resultSet = preparedStatement.getGeneratedKeys();
      if ((resultSet != null) && (resultSet.next())) {
        out = resultSet.getLong(1);
      }
      return out;
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (NamingException e)
    {
      throw e;
    }
    finally
    {
      try
      {
        closeConnection();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public Video get(Long id)
    throws NamingException, SQLException, Exception
  {
    try
    {
      preparedStatement = getConnection().prepareStatement("select id, name, description, file, filetype, created, modified from video where id = ?");
      preparedStatement.setLong(1, id.longValue());
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next())
      {
        video.setId(Long.valueOf(resultSet.getLong("ID")));
        video.setName(resultSet.getString("NAME"));
        video.setDescription(resultSet.getString("DESCRIPTION"));
        video.setFile(Paths.get(resultSet.getString("FILE"), new String[0]));
        video.setFileType(resultSet.getString("FILETYPE"));
        video.setCreated(resultSet.getDate("CREATED"));
        video.setModified(resultSet.getDate("MODIFIED"));
      }
      return video;
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (NamingException e)
    {
      throw e;
    }
  }
}