package br.com.player.bean.video;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.video.VideoBO;
import br.com.player.entity.Video;
import br.com.player.util.Constants;
import br.com.player.util.Messages;

@ManagedBean(name = "videoBean", eager = true)
public class VideoBean extends AbstractBean {
	
	private static final long serialVersionUID = 5904894317097148319L;

	private static Logger log = Logger.getLogger(VideoBean.class);
	
	private Video video;
	
	private List<Video> videos;
	
	@ManagedProperty("#{videoBO}")
	private VideoBO videoBO;
	
	@PostConstruct
	public void init() {
		
		video = new Video();
		
		try {
			
			videos = videoBO.list();
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_INIT_BEAN + Messages.ERROR_DATASOURCE);
			addErrorMessage(null, Messages.ERROR_INIT_BEAN, e.getMessage());
		} catch (SQLException e) {
			log.error(Messages.ERROR_INIT_BEAN + Messages.ERROR_DATABASE);
			addErrorMessage(null, Messages.ERROR_INIT_BEAN, e.getMessage());
		} catch (Exception e) {
			log.error(Messages.ERROR_INIT_BEAN + e.getMessage());
			addErrorMessage(null, Messages.ERROR_INIT_BEAN, e.getMessage());
		}
	}
	
	public String create() {
		
		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}
		
		try {
			
			videoBO.create(video);
			
			setVideos(videoBO.list());
			
			addInfoMessage(null, "Info", "Video \"" + video.getName() + "\" criado com sucesso.");
			
			return list();
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_CREATE + Messages.ERROR_DATASOURCE + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_CREATE + Messages.ERROR_DATABASE + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, e.getMessage());
			return null;
		}
	}
	
	public String edit() {
		
		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}
		
		try {
			
			videoBO.edit(video);
			
			setVideos(videoBO.list());
			
			addInfoMessage(null, "Info", "Video \"" + video.getName() + "\" alterado com sucesso.");

			return list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_EDIT + Messages.ERROR_DATASOURCE + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_EDIT, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_EDIT + Messages.ERROR_DATABASE + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_EDIT, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_EDIT + "[" + video.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_EDIT, e.getMessage());
			return null;
		}
	}
	
	public String delete(String id) {
		
		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}
		
		try {
			
			videoBO.delete(Long.parseLong(id));
			
			setVideos(videoBO.list());
			
			addInfoMessage(null, "Info", "Video " + id + "removido com sucesso.");
			
			return list();
			
		} catch (NamingException e) {
			log.error(Messages.ERROR_REMOVE + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_REMOVE, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_REMOVE + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_REMOVE, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_REMOVE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_REMOVE, e.getMessage());
			return null;
		}
	}
	
	public String show(String id) {
		
		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}
		
		try {
			
			if ((id == null) || (id.equals(""))) {
				addWarningMessage(null, "Aviso", "Id do video não informado.");
				return null;
			}
			
			setVideo(videoBO.get(Long.parseLong(id)));
			
			return getUrl(Video.class, Constants.URI_SHOW, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_SHOW + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_SHOW + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_SHOW, e.getMessage());
			return null;
		}
	}
	
	public String list() {
		
		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}
		
		try {
			
			setVideos(videoBO.list());
			
			return getUrl(Video.class, Constants.URI_LIST, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_LIST + Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_LIST, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_LIST + Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_LIST, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_LIST, e);
			addErrorMessage(null, Messages.ERROR_LIST, e.getMessage());
			return null;
		}
	}
	
	public Video getVideo() {
		return video;
	}
	
	public List<Video> getVideos() {
		return videos;
	}
	
	public VideoBO getVideoBO() {
		return videoBO;
	}
	
	public void setVideo(Video video) {
		this.video = video;
	}
	
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public void setVideoBO(VideoBO videoBO) {
		this.videoBO = videoBO;
	}	
}