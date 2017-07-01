package br.com.player.bo.properties;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.dao.properties.PropertiesDAO;
import br.com.player.entity.Properties;
import br.com.player.util.Messages;
import br.com.player.wrapper.SessionContext;

@ManagedBean(name = "propertiesBO", eager = true)
@ApplicationScoped
@Stateful(passivationCapable = true)
public class PropertiesBO {

	private static Logger log = Logger.getLogger(PropertiesBO.class);

	public long create(Properties property) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[property=" + property.toString() + "]");

		long id = -1;
		
		try {

			id = PropertiesDAO.getInstance().create(property, SessionContext.getInstance().getUserSession());

			if (id <= 0) {
				log.error("Erro ao inserir propriedade. [" + property.toString() + "]");
				throw new Exception("Erro ao inserir propriedade. [" + property.toString() + "]");
			}
			
			return id;			

		} catch (NamingException e) {
			log.error(Messages.ERROR_DATASOURCE + "[" + property.toString() + "]", e);
			throw e;
		} catch (SQLException e) {
			log.error(Messages.ERROR_DATABASE + "[" + property.toString() + "]", e);
			throw e;
		} catch (Exception e) {
			log.error(Messages.ERROR_CREATE + "[" + property.toString() + "]", e);
			throw e;
		}
	}

	public long delete(Long id) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");

		try {
			
			return PropertiesDAO.getInstance().delete(id);
			
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

	public Properties edit(Properties property) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[property=" + property + "]");

		try {
			
			if (PropertiesDAO.getInstance().update(property, SessionContext.getInstance().getUserSession()) <= 0L) {
				log.error("Erro ao alterar propriedade.");
				throw new Exception("Erro ao alterar propriedade.");
			}
			
			return property;
			
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

	public Properties get(Long id) throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("[id=" + id + "]");
		
		try {
			
			return PropertiesDAO.getInstance().get(id);
			
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

	public List<Properties> list() throws NamingException, SQLException, Exception {
		
		if (log.isDebugEnabled())
			log.debug("");
		
		try {
			
			return PropertiesDAO.getInstance().list();
			
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
	
	public List<Properties> findAllBy(Properties property) throws NamingException, SQLException, Exception {

		if (log.isDebugEnabled())
			log.debug("[" + property.toString() + "]");

		try {
			
			return PropertiesDAO.getInstance().findAllBy(property);
			
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