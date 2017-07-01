package br.com.player.bo.properties;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.dao.properties.PropertiesTypeDAO;
import br.com.player.entity.PropertiesType;
import br.com.player.util.Messages;
import br.com.player.wrapper.SessionContext;

@ManagedBean(name = "propertiesTypeBO", eager = true)
@ApplicationScoped
@Stateful(passivationCapable = true)
public class PropertiesTypeBO {

	private static Logger log = Logger.getLogger(PropertiesTypeBO.class);

	public long create(PropertiesType type) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBO:create(" + type + ")");

		try {

			long id = PropertiesTypeDAO.getInstance().create(type, SessionContext.getInstance().getUserSession());

			if (id <= 0) {
				log.error("Erro ao inserir classificação. [" + type.toString() + "]");
				throw new Exception("Erro ao inserir classificação. [" + type.toString() + "]");
			} else {
				return id;
			}

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + "[" + type.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + "[" + type.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE + "[" + type.toString() + "]", e);
			throw e;
		}
	}

	public long delete(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBO:delete(" + id + ")");

		try {

			return PropertiesTypeDAO.getInstance().delete(id);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_REMOVE);
			throw e;
		}
	}

	public PropertiesType edit(PropertiesType type) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("PropertiesTypeBO:edit(" + type + ")");

		try {

			if (PropertiesTypeDAO.getInstance().update(type, SessionContext.getInstance().getUserSession()) > 0l) {
				return type;
			} else {
				log.error("Erro ao atualizar classificação.");
				throw new Exception("Erro ao atualizar classificação.");
			}

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_EDIT, e);
			throw e;
		}
	}

	public PropertiesType get(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("PropertiesType:get(" + id + ")");

		try {

			return PropertiesTypeDAO.getInstance().get(id);

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW, e);
			throw e;
		}
	}

	public List<PropertiesType> list() throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[" + null + "]");
		try {

			return PropertiesTypeDAO.getInstance().list();

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE, e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE, e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_SHOW, e);
			throw e;
		}
	}
}