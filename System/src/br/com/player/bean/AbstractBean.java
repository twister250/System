package br.com.player.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.player.entity.Properties;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;
import br.com.player.entity.Video;
import br.com.player.wrapper.SessionContext;

public abstract class AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public void addErrorMessage(String key, String summary, String message) {
		addMessage(key, summary, message, FacesMessage.SEVERITY_ERROR);
	}

	public void addInfoMessage(String key, String summary, String message) {
		addMessage(key, summary, message, FacesMessage.SEVERITY_INFO);
	}

	public void addWarningMessage(String key, String summary, String message) {
		addMessage(key, summary, message, FacesMessage.SEVERITY_WARN);
	}

	private void addMessage(String key, String summary, String message, FacesMessage.Severity severity) {
		FacesMessage facesMessage = new FacesMessage(severity, summary, message);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(key, facesMessage);
	}

	public boolean validateUserSession() {
		return SessionContext.getInstance().getUserSession() != null;
	}

	public List<String> isFormValid(Object entity) {
		if ((entity instanceof User)) {
			return validateUserForm((User) entity);
		}
		if ((entity instanceof Properties)) {
			return validatePropertiesForm((Properties) entity);
		}
		if ((entity instanceof PropertiesType)) {
			return validatePropertiesTypeForm((PropertiesType) entity);
		}
		if ((entity instanceof Video)) {
			return validateVideoForm((Video) entity);
		}
		return null;
	}

	private List<String> validateUserForm(User user) {
		List<String> fields = new ArrayList<String>();
		if ((user.getEmail() == null) || (user.getEmail().equals(""))) {
			fields.add("email");
		}
		if ((user.getName() == null) || (user.getName().equals(""))) {
			fields.add("name");
		}
		if ((user.getPassword() == null) || (user.getPassword().equals(""))) {
			fields.add("password");
		}
		return fields;
	}

	private List<String> validatePropertiesForm(Properties property) {
		List<String> fields = new ArrayList<String>();
		if ((property.getName() == null) || (property.equals(""))) {
			fields.add("name");
		}
		if ((property.getType() == null) || (property.equals(""))) {
			fields.add("type");
		}
		if ((property.getValue() == null) || (property.equals(""))) {
			fields.add("value");
		}
		return fields;
	}

	private List<String> validatePropertiesTypeForm(PropertiesType type) {
		List<String> fields = new ArrayList<String>();
		if ((type.getDescription() == null) || (type.getDescription().equals(""))) {
			fields.add("description");
		}
		if ((type.getName() == null) || (type.getName().equals(""))) {
			fields.add("name");
		}
		return fields;
	}

	private List<String> validateVideoForm(Video video) {
		List<String> fields = new ArrayList<String>();
		if ((video.getFile() == null) || (video.getFile().equals(""))) {
			fields.add("file");
		}
		if ((video.getName() == null) || (video.getName().equals(""))) {
			fields.add("name");
		}
		return fields;
	}
}