package br.com.player.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import br.com.player.entity.Entity;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

	private static Logger log = Logger.getLogger(EntityConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (log.isDebugEnabled())
			log.debug("EntityConverter:getAsObject(" + context + "," + component + "," + value + ")");

		if (value != null) {
			return getAttributesFrom(component).get(value);
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (log.isDebugEnabled())
			log.debug("EntityConverter:getAsString(" + context + "," + component + "," + value + ")");

		if ((value != null) && (!"".equals(value))) {
			Entity entity = (Entity) value;
			addAttribute(component, entity);

			Long id = entity.getId();
			if (id != null) {
				return String.valueOf(id);
			}
		}
		return (String) value;
	}

	protected void addAttribute(UIComponent component, Entity entity) {

		if (log.isDebugEnabled())
			log.debug("EntityConverter:addAttribute(" + component + "," + entity + ")");

		if (entity != null) {
			String key = entity.getId().toString();
			getAttributesFrom(component).put(key, entity);
		}
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {

		if (log.isDebugEnabled())
			log.debug("EntityConverter:getAttributesFrom(" + component + ")");

		return component.getAttributes();
	}
}