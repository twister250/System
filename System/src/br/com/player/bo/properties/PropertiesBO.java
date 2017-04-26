package br.com.player.bo.properties;

import br.com.player.dao.properties.PropertiesDAO;
import br.com.player.entity.Properties;
import br.com.player.wrapper.SessionContext;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

@ManagedBean(name="propertiesBO", eager=true)
@ApplicationScoped
@Stateful(passivationCapable=true)
public class PropertiesBO
{
  private static Logger log = Logger.getLogger(PropertiesBO.class);
  
  public Properties create(Properties property) throws NamingException, SQLException, Exception{
	  
	  if(log.isDebugEnabled())
		  log.debug("PropertiesBO:create("+property+")");
	  
    try
    {
      Properties pojo = PropertiesDAO.getInstance().create(property, SessionContext.getInstance().getUserSession());
      if (pojo == null)
      {
        log.error("Erro ao inserir Properties no banco de dados.");
        throw new Exception("Erro ao inserir Properties no banco de dados.");
      }
      return pojo;
    }
    catch (NamingException e)
    {
      log.error("Erro ao criar propriedade. [classe: " + getClass().getName() + "; m�todo: create; erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (SQLException e)
    {
      log.error("Erro ao criar propriedade. [classe: " + getClass().getName() + "; m�todo: create; erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (Exception e)
    {
      log.error("Erro ao criar propriedade. [classe: " + getClass().getName() + "; m�todo: create; erro: " + e.getMessage() + "]");
      throw e;
    }
  }
  
  public int delete(Long id)
    throws NamingException, SQLException, Exception
  {
    try
    {
      return PropertiesDAO.getInstance().delete(id);
    }
    catch (NamingException e)
    {
      log.error("Erro ao deletar propriedade. [classe: " + getClass().getName() + "; m�todo: delete(" + id + "); erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (SQLException e)
    {
      log.error("Erro ao deletar propriedade. [classe: " + getClass().getName() + "; m�todo: delete(" + id + "); erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (Exception e)
    {
      log.error("Erro ao deletar propriedade. [classe: " + getClass().getName() + "; m�todo: delete(" + id + "); erro: " + e.getMessage() + "]");
      throw e;
    }
  }
  
  public Properties edit(Properties property)
    throws NamingException, SQLException, Exception
  {
    try
    {
      if (PropertiesDAO.getInstance().update(property) > 0L) {
        return property;
      }
      log.error("Erro ao alterar propriedade.");
      return null;
    }
    catch (NamingException e)
    {
      log.error(e);
      throw e;
    }
    catch (SQLException e)
    {
      log.error(e);
      throw e;
    }
    catch (Exception e)
    {
      log.error(e);
      throw e;
    }
  }
  
  public Properties get(Long id)
    throws NamingException, SQLException, Exception
  {
    try
    {
      return PropertiesDAO.getInstance().get(id);
    }
    catch (NamingException e)
    {
      log.error("Erro ao buscar propriedade. Falha ao acessar datasource. " + e);
      throw e;
    }
    catch (SQLException e)
    {
      log.error("Erro ao buscar propriedade. Falha ao acessar base de dados. " + e);
      throw e;
    }
    catch (Exception e)
    {
      log.error("Erro ao buscar propriedade. " + e);
      throw e;
    }
  }
  
  public List<Properties> list()
    throws NamingException, SQLException, Exception
  {
    try
    {
      return PropertiesDAO.getInstance().list();
    }
    catch (NamingException e)
    {
      log.error("Erro ao listar propriedade. [classe: " + getClass().getName() + "; m�todo: list; erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (SQLException e)
    {
      log.error("Erro ao listar propriedade. [classe: " + getClass().getName() + "; m�todo: list; erro: " + e.getMessage() + "]");
      throw e;
    }
    catch (Exception e)
    {
      log.error("Erro ao listar propriedade. [classe: " + getClass().getName() + "; m�todo: list; erro: " + e.getMessage() + "]");
      throw e;
    }
  }
}