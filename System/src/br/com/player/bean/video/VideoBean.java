package br.com.player.bean.video;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.properties.PropertiesBO;
import br.com.player.bo.video.VideoBO;
import br.com.player.entity.Properties;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.Video;
import br.com.player.util.Constants;
import br.com.player.util.Messages;

@ManagedBean(name = "videoBean", eager = true)
@ApplicationScoped
public class VideoBean extends AbstractBean {
	
	private static final long serialVersionUID = 5904894317097148319L;

	private static Logger log = Logger.getLogger(VideoBean.class);
	
	private static String VIDEO_TYPE = "videoType";
	
	private Video video;
	
	private List<Video> videos;
	
	private List<Properties> videoProperties;
	
	private UploadedFile file;
	
	@ManagedProperty("#{videoBO}")
	private VideoBO videoBO;
	
	@ManagedProperty("#{propertiesBO}")
	private PropertiesBO propertiesBO;
	
	@PostConstruct
	public void init() {
		
		if (log.isDebugEnabled())
			log.debug("[" + null + "]");
		
		video = new Video();
		
		try {
			
			videos = videoBO.list();

			videoProperties = propertiesBO.findAllBy(new Properties(null, null, null, new PropertiesType(null, VIDEO_TYPE)));

			/*for (Properties property : propertiesBO.list()) {
				if (property.getType().getName().equals(VIDEO_TYPE))
					videoProperties.add(property);					
			}*/
			
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

			video.setName(file.getFileName().substring(0, file.getFileName().indexOf(".")));

			video.setFile(file.getFileName());

			video.setFileType(file.getFileName().substring(file.getFileName().lastIndexOf(".")+1));

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

	/**
	 * @return the video
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * @return the videos
	 */
	public List<Video> getVideos() {
		return videos;
	}

	/**
	 * @return the videoProperties
	 */
	public List<Properties> getVideoProperties() {
		return videoProperties;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @return the videoBO
	 */
	public VideoBO getVideoBO() {
		return videoBO;
	}

	/**
	 * @return the propertiesBO
	 */
	public PropertiesBO getPropertiesBO() {
		return propertiesBO;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(Video video) {
		this.video = video;
	}

	/**
	 * @param videos the videos to set
	 */
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	/**
	 * @param videoProperties the videoProperties to set
	 */
	public void setVideoProperties(List<Properties> videoProperties) {
		this.videoProperties = videoProperties;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @param videoBO the videoBO to set
	 */
	public void setVideoBO(VideoBO videoBO) {
		this.videoBO = videoBO;
	}

	/**
	 * @param propertiesBO the propertiesBO to set
	 */
	public void setPropertiesBO(PropertiesBO propertiesBO) {
		this.propertiesBO = propertiesBO;
	}
}