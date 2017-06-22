package br.com.player.entity;

import java.sql.Date;

public class Video {

	private Long id;
	private String name;
	private String description;
	private String fileType;
	private String file;
	private Date created;
	private Date modified;
	private User user;

	public Date getCreated() {
		return this.created;
	}

	public Date getModified() {
		return this.modified;
	}

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public String getFileType() {
		return this.fileType;
	}

	public String getFile() {
		return this.file;
	}
	
	public User getUser() {
		return user;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFileType(String type) {
		this.fileType = type;
	}

	public void setFile(String  file) {
		this.file = file;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Video [created=" + created + ", modified=" + modified + ", id=" + id + ", description=" + description
				+ ", name=" + name + ", fileType=" + fileType + ", file=" + file + "]";
	}	
}