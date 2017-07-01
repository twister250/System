package br.com.player.entity;

import java.sql.Date;

public class PropertiesType implements Entity {
	private Long id;
	private String name;
	private String description;
	private Date created;
	private Date modified;
	private User user;

	public PropertiesType () {}
	
	public PropertiesType (Long id, String name) {
		if (id != null)
			this.id = id;
		if (name != null)			
			this.name = name;
	}
	
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Date getCreated() {
		return this.created;
	}

	public Date getModified() {
		return this.modified;
	}

	public User getUser() {
		return this.user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (this.created == null ? 0 : this.created.hashCode());
		result = prime * result + (this.description == null ? 0 : this.description.hashCode());
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		result = prime * result + (this.modified == null ? 0 : this.modified.hashCode());
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + (this.user == null ? 0 : this.user.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PropertiesType other = (PropertiesType) obj;
		if (this.created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!this.created.equals(other.created)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.modified == null) {
			if (other.modified != null) {
				return false;
			}
		} else if (!this.modified.equals(other.modified)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PropertiesType [id=" + id + ", name=" + name + ", description=" + description + ", created=" + created
				+ ", modified=" + modified + ", user=" + user + "]";
	}
}