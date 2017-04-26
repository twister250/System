package br.com.player.bean.properties;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.properties.PropertiesTypeBO;
import br.com.player.entity.PropertiesType;
import br.com.player.entity.User;
import br.com.player.wrapper.SessionContext;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

@ManagedBean(name="propertiesTypeBean", eager=true)
@ApplicationScoped
public class PropertiesTypeBean
  extends AbstractBean
{
  private static final long serialVersionUID = 1L;
  private static Logger log = Logger.getLogger(PropertiesBean.class);
  private PropertiesType type;
  private List<PropertiesType> types;
  @ManagedProperty("#{propertiesTypeBO}")
  private PropertiesTypeBO typeBO;
  
  @PostConstruct
  public void init()
  {
    try
    {
      this.types = (this.types == null ? this.typeBO.list() : this.types);
    }
    catch (NamingException e)
    {
      log.error(e);
    }
    catch (SQLException e)
    {
      log.error(e);
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }
  
  public String create()
  {
    User user = SessionContext.getInstance().getUserSession();
    if (user == null)
    {
      addWarningMessage(null, "Usu�rio n�o encontrado", "Por favor fa�a login para poder utilizar o sistema.");
      return "/player/access/login.xhtml";
    }
    try
    {
      setType(this.typeBO.create(this.type));
      return "/player/properties/show.xhtml";
    }
    catch (NamingException e)
    {
      log.error("Erro ao criar Type. Falha ao acessar datasource. " + e.getMessage());
      return null;
    }
    catch (SQLException e)
    {
      log.error("Erro ao criar Type. Falha ao acessar banco de dados. " + e.getMessage());
      return null;
    }
    catch (Exception e)
    {
      log.error("Erro ao criar Type. " + e.getMessage());
    }
    return null;
  }
  
  public String edit()
  {
    return "";
  }
  
  public String delete()
  {
    return "";
  }
  
  public String list()
  {
    return "";
  }
  
  public PropertiesType getType()
  {
    return this.type;
  }
  
  public void setType(PropertiesType type)
  {
    this.type = type;
  }
  
  public PropertiesTypeBO getTypeBO()
  {
    return this.typeBO;
  }
  
  public void setTypeBO(PropertiesTypeBO typeBO)
  {
    this.typeBO = typeBO;
  }
  
  public List<PropertiesType> getTypes()
  {
    return this.types;
  }
  
  public void setTypes(List<PropertiesType> types)
  {
    this.types = types;
  }
}