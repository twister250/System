package br.com.player.bo.video;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.dao.video.VideoDAO;
import br.com.player.entity.Video;
import br.com.player.util.Messages;
import br.com.player.wrapper.SessionContext;

@ManagedBean(name = "videoBO", eager = true)
@ApplicationScoped
@Stateful(passivationCapable = true)
public class VideoBO {
	
	private static Logger log = Logger.getLogger(VideoBO.class);
	
	public long create(Video video) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[video=" + video.toString() + "]");
		
		long id = -1;
		
		try {
			
			id = VideoDAO.getInstance().create(video, SessionContext.getInstance().getUserSession());
			
			if (id <= 0) {
				log.error("Erro ao inserir video. [" + video.toString() + "]");
				throw new Exception("Erro ao inserir video.[" + video.toString() + "]");
			}
			
			return id;
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + "[" + video.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + "[" + video.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE + "[" + video.toString() + "]", e);
			throw e;
		}
	}

	public long delete (Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			return VideoDAO.getInstance().delete(id);
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + Messages.ERROR_REMOVE + "[id=" + id + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + Messages.ERROR_REMOVE + "[id=" + id + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_REMOVE + "[id=" + id + "]", e);
			throw e;
		}
	}

	public Video edit(Video video) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[video=" + video.toString() + "]");
		
		try {
			
			if (VideoDAO.getInstance().update(video, SessionContext.getInstance().getUserSession()) <= 0L) {
				log.error("Erro ao alterar video.");
				throw new Exception("Erro ao alterar video.");
			}
			
			return video;

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + Messages.ERROR_EDIT + "[video=" + video.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + Messages.ERROR_EDIT + "[video=" + video.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_EDIT + "[video=" + video.toString() + "]", e);
			throw e;
		}
	}
	
	public Video get(Long id) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			return VideoDAO.getInstance().get(id);
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + Messages.ERROR_SHOW + "[id=" + id + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + Messages.ERROR_SHOW + "[id=" + id + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW + "[id=" + id + "]", e);
			throw e;
		}		
	}
	
	public List<Video> list() throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("");
		
		try {
			
			return VideoDAO.getInstance().list();
		
		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + Messages.ERROR_LIST, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + Messages.ERROR_LIST, e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_LIST, e);
			throw e;
		}
	}
}