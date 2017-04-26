package br.com.player.converter;

import br.com.player.entity.Entity;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("entityConverter")
public class EntityConverter
  implements Converter
{
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    if (value != null) {
      return getAttributesFrom(component).get(value);
    }
    return null;
  }
  
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    if ((value != null) && (!"".equals(value)))
    {
      Entity entity = (Entity)value;
      addAttribute(component, entity);
      
      Long id = entity.getId();
      if (id != null) {
        return String.valueOf(id);
      }
    }
    return (String)value;
  }
  
  protected void addAttribute(UIComponent component, Entity entity)
  {
    if (entity != null)
    {
      String key = entity.getId().toString();
      getAttributesFrom(component).put(key, entity);
    }
  }
  
  protected Map<String, Object> getAttributesFrom(UIComponent component)
  {
    return component.getAttributes();
  }
}