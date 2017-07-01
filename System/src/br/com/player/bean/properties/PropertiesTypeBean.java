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
import br.com.player.bo.properties.PropertiesTypeBO;
import br.com.player.entity.PropertiesType;
import br.com.player.util.Constants;
import br.com.player.util.Messages;

@ManagedBean(name = "propertiesTypeBean", eager = true)
@ApplicationScoped
public class PropertiesTypeBean extends AbstractBean {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PropertiesTypeBean.class);
	private PropertiesType type;
	private List<PropertiesType> types;

	@ManagedProperty("#{propertiesTypeBO}")
	private PropertiesTypeBO propertiesTypeBO;

	@PostConstruct
	public void init() {

		if (log.isDebugEnabled())
			log.debug("[" + null + "]");

		type = new PropertiesType();

		try {

			types = propertiesTypeBO.list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_INIT_BEAN + Messages.ERROR_DATASOURCE + "[" + PropertiesTypeBean.class.getSimpleName() + "]", e);
		} catch (SQLException e) {
			log.error(Messages.ERROR_INIT_BEAN + Messages.ERROR_DATABASE + "[" + PropertiesTypeBean.class.getSimpleName() + "]", e);
		} catch (Exception e) {
			log.error(Messages.ERROR_INIT_BEAN + "[" + PropertiesTypeBean.class.getSimpleName() + "]", e);
		}
	}

	public String create() {

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBean:create()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			propertiesTypeBO.create(type);

			setTypes(propertiesTypeBO.list());

			addInfoMessage(null, "Info", "Classificação \"" + type.getName() + "\" criada com sucesso.");

			return list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_CREATE + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_CREATE + Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE + "[" + type.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_CREATE, e.getMessage());
			return null;
		}
	}

	public String edit() {

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBean:edit()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			propertiesTypeBO.edit(type);

			setTypes(propertiesTypeBO.list());

			addInfoMessage(null, "Info", "Classificação \"" + type.getName() + "\" alterada com sucesso.");

			return list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_EDIT + Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
			addErrorMessage(null, Messages.ERROR_EDIT, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_EDIT + Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
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
			log.debug("PropertiesTypeBean:delete(" + id + ")");

		try {

			propertiesTypeBO.delete(Long.valueOf(Long.parseLong(id)));

			setTypes(propertiesTypeBO.list());

			log.info("Classificação " + id + " removida com sucesso.");

			addInfoMessage(null, "Info", "Classificação " + id + " removida com sucesso.");

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

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBean:show(" + id + ")");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			if ((id == null) || (id.equals(""))) {
				addWarningMessage(null, "Aviso", "Id da classificação não informado.");
				return null;
			}

			setType(propertiesTypeBO.get(Long.valueOf(Long.parseLong(id))));

			return getUrl(PropertiesType.class, Constants.URI_SHOW, null, true);

		} catch (NamingException e) {
			log.error(Messages.ERROR_SHOW + Messages.ERROR_DATASOURCE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATASOURCE);
			return null;
		} catch (SQLException e) {
			log.error(Messages.ERROR_SHOW + Messages.ERROR_DATABASE + "[id=" + id + "]", e);
			addErrorMessage(null, Messages.ERROR_SHOW, Messages.ERROR_DATABASE);
			return null;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW, e);
			addErrorMessage(null, Messages.ERROR_SHOW, e.getMessage());
			return null;
		}
	}

	public String list() {

		setType(new PropertiesType());

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBean:list()");

		if (!isUserSessionAlive()) {
			return getUrl(Constants.URI_LOGIN, true);
		}

		try {

			setTypes(propertiesTypeBO.list());

			return getUrl(PropertiesType.class, Constants.URI_LIST, null, true);

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

	public PropertiesType getType() {
		return type;
	}

	public void setType(PropertiesType type) {
		this.type = type;
	}

	public PropertiesTypeBO getPropertiesTypeBO() {
		return propertiesTypeBO;
	}

	public void setPropertiesTypeBO(PropertiesTypeBO propertiesTypeBO) {
		this.propertiesTypeBO = propertiesTypeBO;
	}

	public List<PropertiesType> getTypes() {
		return types;
	}

	public void setTypes(List<PropertiesType> types) {
		this.types = types;
	}
}