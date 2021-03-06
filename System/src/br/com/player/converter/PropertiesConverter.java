package br.com.player.converter;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import br.com.player.bo.properties.PropertiesBO;
import br.com.player.entity.Properties;
import br.com.player.util.Messages;

@FacesConverter(forClass = Properties.class, value = "propertiesConverter")

public class PropertiesConverter implements Converter {
	
	private static Logger log = Logger.getLogger(PropertiesConverter.class);
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		
		if (log.isDebugEnabled())
			log.debug("[context=" + context.toString() + "; component=" + component.toString() + "; id=" + id + "]");
		
		if (id != null && id.trim().length() > 0) {
		
			try {
				
				PropertiesBO service = new PropertiesBO();
				return (Object) service.get(Long.parseLong(id));

			} catch (NamingException e) {
				log.error(Messages.ERROR_CONVERT + Messages.ERROR_DATASOURCE + "getAsObject[id=" + id + "]", e);
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", Messages.ERROR_DATASOURCE));
			} catch (SQLException e) {
				log.error(Messages.ERROR_CONVERT + Messages.ERROR_DATABASE + "getAsObject[id=" + id + "]", e);
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", Messages.ERROR_DATABASE));				
			} catch (NumberFormatException e) {
				log.error(Messages.ERROR_CONVERT + "getAsObject[id=" + id + "]", e);
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", e.getMessage()));				
			} catch (Exception e) {
				log.error(Messages.ERROR_CONVERT + "getAsObject[id=" + id + "]", e);
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", e.getMessage()));				
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		
		if (log.isDebugEnabled())
			log.debug("[context=" + context.toString() + "; component=" + component.toString() + "; object=" + object + "]");
		
		if (object != null && !object.equals(""))
			return String.valueOf(((Properties) object).getId());
		
		return null;
	}
}
