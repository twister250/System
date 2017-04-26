package br.com.player.bo.properties;

import br.com.player.dao.properties.PropertiesTypeDAO;
import br.com.player.entity.PropertiesType;
import br.com.player.wrapper.SessionContext;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

@ManagedBean(name="propertiesTypeBO", eager=true)
@ApplicationScoped
@Stateful(passivationCapable=true)
public class PropertiesTypeBO
{
  private static Logger log = Logger.getLogger(PropertiesTypeBO.class);
  
  public PropertiesType create(PropertiesType type)
    throws NamingException, SQLException, Exception
  {
    try
    {
      PropertiesType pojo = PropertiesTypeDAO.getInstance().create(type, SessionContext.getInstance().getUserSession());
      if (pojo == null)
      {
        log.error("Erro ao inserir PropertiesType no banco de dados.");
        throw new Exception("Erro ao inserir PropertiesType no banco de dados.");
      }
      return pojo;
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
  
  public List<PropertiesType> list()
    throws NamingException, SQLException, Exception
  {
    try
    {
      return PropertiesTypeDAO.getInstance().list();
    }
    catch (NamingException e)
    {
      log.error("Erro ao listar PropertiesType. Falha ao acessar datasource. " + e.getMessage());
      throw e;
    }
    catch (SQLException e)
    {
      log.error("Erro ao listar PropertiesType. Falha ao acessar banco de dados. " + e.getMessage());
      throw e;
    }
    catch (Exception e)
    {
      log.error("Erro ao listar PropertiesType. " + e.getMessage());
      throw e;
    }
  }
}