package br.com.player.entity;

import java.nio.file.Path;
import java.sql.Date;

public class Video {
	private Date created;
	private Date modified;
	private Long id;
	private String description;
	private String name;
	private String fileType;
	private Path file;

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

	public Path getFile() {
		return this.file;
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

	public void setFile(Path file) {
		this.file = file;
	}
}