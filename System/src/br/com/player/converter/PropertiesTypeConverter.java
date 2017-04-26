package br.com.player.converter;

import br.com.player.bo.properties.PropertiesTypeBO;
import br.com.player.entity.PropertiesType;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

@FacesConverter(forClass=PropertiesType.class, value="typeConverter")
public class PropertiesTypeConverter
  implements Converter
{
  private static Logger log = Logger.getLogger(PropertiesTypeConverter.class);
  
  public Object getAsObject(FacesContext context, UIComponent component, String id)
  {
    if ((id != null) && (id.trim().length() > 0)) {
      try
      {
        PropertiesTypeBO service = new PropertiesTypeBO();
        return service.list().get(Integer.parseInt(id) - 1);
      }
      catch (NamingException e)
      {
        log.error("class: " + e.getClass() + "\nmensagem: " + e.getMessage());
        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao acessar datasource.", "Erro"));
      }
      catch (SQLException e)
      {
        log.error("class: " + e.getClass() + "\nmensagem: " + e.getMessage());
        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao acessar banco de dados.", "Erro"));
      }
      catch (NumberFormatException e)
      {
        log.error("class: " + e.getClass() + "\nmensagem: " + e.getMessage());
        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na convers�o", "Erro"));
      }
      catch (Exception e)
      {
        log.error("class: " + e.getClass() + "\nmensagem: " + e.getMessage());
        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na convers�o", "Erro"));
      }
    }
    return null;
  }
  
  public String getAsString(FacesContext context, UIComponent component, Object object)
  {
    if ((object != null) && (!object.equals(""))) {
      return String.valueOf(((PropertiesType)object).getId());
    }
    return null;
  }
}