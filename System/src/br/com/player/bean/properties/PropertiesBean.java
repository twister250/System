package br.com.player.bean.properties;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.properties.PropertiesBO;
import br.com.player.bo.properties.PropertiesTypeBO;
import br.com.player.entity.Properties;
import br.com.player.util.Constants;
import br.com.player.util.Messages;

@ManagedBean(name = "propertiesBean", eager = true)
@ApplicationScoped
public class PropertiesBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PropertiesBean.class);

	private Properties property;

	private List<Properties> properties;

	@ManagedProperty("#{propertiesBO}")
	private PropertiesBO propertiesBO;

	@ManagedProperty("#{propertiesTypeBO}")
	private PropertiesTypeBO propertiesTypeBO;

	@PostConstruct
	public void init() {

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:init()");

		property = new Properties();

		try {

			properties = propertiesBO.list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + ":" + Messages.ERROR_INIT_BEAN + "["
					+ PropertiesTypeBean.class.getSimpleName() + "]", e);
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + ":" + Messages.ERROR_INIT_BEAN + "["
					+ PropertiesTypeBean.class.getSimpleName() + "]", e);
		} catch (Exception e) {
			log.error(Messages.ERROR_INIT_BEAN + "[" + PropertiesTypeBean.class.getSimpleName(), e);
		}
	}

	public String create() {

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:create()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			propertiesBO.create(property);

			setProperties(propertiesBO.list());

			addInfoMessage(null, "Info", "Propriedade \"" + property.getName() + "\" criada com sucesso.");

			return getUrl(Properties.class, Constants.URI_LIST, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE, e);
			addErrorMessage(null, Messages.ERROR_CREATE, e.getMessage());
			return null;
		}
	}

	public String edit() {

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:edit()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			propertiesBO.edit(property);

			setProperties(propertiesBO.list());

			addInfoMessage(null, "Info", "Propriedade \"" + property.getName() + "\" alterada com sucesso.");

			return getUrl(Properties.class, Constants.URI_LIST, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_EDIT, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_EDIT, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_EDIT, e);
			addErrorMessage(null, Messages.ERROR_EDIT, e.getMessage());
			return null;
		}
	}

	public String delete(String id) {

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:delete(" + id + ")");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			propertiesBO.delete(Long.valueOf(Long.parseLong(id)));

			setProperties(propertiesBO.list());

			addInfoMessage(null, "Info", "Propriedade " + id + " removida com sucesso.");

			return getUrl(Properties.class, Constants.URI_LIST, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_REMOVE, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_REMOVE, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_REMOVE, e);
			addErrorMessage(null, Messages.ERROR_REMOVE, e.getMessage());
			return null;
		}
	}

	public String show(String id) {

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:show(" + id + ")");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			if ((id == null) || (id.equals(""))) {
				addWarningMessage(null, "Aviso", "Id da propriedade não informado.");
				return null;
			}

			setProperty(propertiesBO.get(Long.valueOf(Long.parseLong(id))));

			return getUrl(Properties.class, Constants.URI_SHOW, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW, e);
			addErrorMessage(null, Messages.ERROR_SHOW, e.getMessage());
			return null;
		}
	}

	public String list() {

		setProperty(new Properties());

		if (log.isDebugEnabled())
			log.debug("PropertiesBean:list()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			setProperties(propertiesBO.list());

			return getUrl(Properties.class, Constants.URI_LIST, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			addErrorMessage(null, Messages.ERROR_LIST, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			addErrorMessage(null, Messages.ERROR_LIST, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_LIST, e);
			addErrorMessage(null, Messages.ERROR_LIST, e.getMessage());
			return null;
		}
	}

	public PropertiesBO getPropertiesBO() {
		return propertiesBO;
	}

	public void setPropertiesBO(PropertiesBO propertiesBO) {
		this.propertiesBO = propertiesBO;
	}

	public Properties getProperty() {
		return this.property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public PropertiesTypeBO getPropertiesTypeBO() {
		return this.propertiesTypeBO;
	}

	public void setPropertiesTypeBO(PropertiesTypeBO propertiesTypeBO) {
		this.propertiesTypeBO = propertiesTypeBO;
	}

	public List<Properties> getProperties() {
		return this.properties;
	}

	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}
}