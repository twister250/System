package br.com.player.entity;

import java.sql.Date;

public class Video {

	private Long id;
	private String name;
	private String description;
	private String fileType;
	private String file;
	private Properties videoType;
	private Date created;
	private Date modified;
	private User user;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @return the videoType
	 */
	public Properties getVideoType() {
		return videoType;
	}
	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}
	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	/**
	 * @param videoType the videoType to set
	 */
	public void setVideoType(Properties videoType) {
		this.videoType = videoType;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Reescrita do método toString() com plotagem dos atributos
	 */
	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", description=" + description + ", fileType=" + fileType
				+ ", file=" + file + ", videoType=" + videoType + ", created=" + created + ", modified=" + modified
				+ ", user=" + user + "]";
	}
}