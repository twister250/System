package br.com.player.wrapper;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.player.entity.User;

public class SessionContext {
	
	private static SessionContext instance;
	private static final String USER_ATTRIBUTE = "userlogged";

	public static SessionContext getInstance() {
		return instance = instance == null ? new SessionContext() : instance;
	}

	private ExternalContext externalContext() {
		if (FacesContext.getCurrentInstance() == null) {
			throw new RuntimeException("Faces context não pode ser chamado fora de requisição HTTP.");
		}
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public Map<String, Object> getApplicationMap() {
		return externalContext().getApplicationMap();
	}

	public void endSession() {
		externalContext().invalidateSession();
	}

	public Object getAttribute(String attribute) {
		return externalContext().getSessionMap().get(attribute);
	}

	public void setAttribute(String name, Object value) {
		externalContext().getSessionMap().put(name, value);
	}

	public User getUserSession() {
		return (User) externalContext().getSessionMap().get(USER_ATTRIBUTE);
	}
}