package br.com.player.bean.properties;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.properties.PropertiesBO;
import br.com.player.bo.properties.PropertiesTypeBO;
import br.com.player.entity.Properties;
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

@ManagedBean(name="propertiesBean", eager=true)
@ApplicationScoped
public class PropertiesBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PropertiesBean.class);
	private Properties property;
	private List<Properties> properties;
	
	@ManagedProperty("#{propertiesBO}")
	private PropertiesBO propertiesBO;
	
	@ManagedProperty("#{propertiesTypeBO}")
	private PropertiesTypeBO propertiesTypeBO;
  
	@PostConstruct
	public void init(){
		
		if(log.isDebugEnabled())
			log.debug("PropertiesBean:init()");
		
		this.property = (this.property == null ? new Properties() : this.property);
		try{
			this.properties = (this.properties == null ? (this.properties = this.propertiesBO.list()) : this.properties);
		}catch (NamingException e){
			log.error("Erro na inicialização do bean. Erro ao acessar datasource." + e);
			addErrorMessage(null, "Erro", "Problema na inicialização do bean. " + e.getMessage());
		}catch (SQLException e){
			log.error("Erro na inicialização do bean. Erro ao acessar banco de dados." + e);
			addErrorMessage(null, "Erro", "Problema na inicialização do bean. " + e.getMessage());
		}catch (Exception e){
			log.error("Erro na inicialização do bean." + e);
			addErrorMessage(null, "Erro", "Problema na inicialização do bean. " + e.getMessage());
		}
	}

	public String create(){
		
		if(log.isDebugEnabled())
			log.debug("PropertiesBean:create()");
		
		User user = SessionContext.getInstance().getUserSession();
		if (user == null){
			addWarningMessage(null, "Usuário não encontrado.", "É necessário estar logado.");
			return "/player/access/login.xhtml";
		}
		try{
			setProperty(this.propertiesBO.create(this.property));
			setProperties(this.propertiesBO.list());
			return "/player/properties/show.xhtml";
		}catch (NamingException e){
			log.error("Erro ao criar propriedade. Falha ao acessar datasource. " + e);
			addErrorMessage(null, "Erro ao criar propriedade.", e.getMessage());
			return null;
		}catch (SQLException e){
			log.error("Erro ao criar propriedade. Falha ao acessar banco de dados. " + e);
			addErrorMessage(null, "Erro ao criar propriedade.", e.getMessage());
			return null;
		}catch (Exception e){
			log.error("Erro ao criar propriedade. " + e);
			addErrorMessage(null, "Erro ao criar propriedade.", e.getMessage());
		}
		return null;
	}
  
	public String edit(){
		
		if(log.isDebugEnabled())
			log.debug("PropertiesBean:edit()");
		
		try{
			if (this.propertiesBO.edit(this.property) != null){
				setProperties(this.propertiesBO.list());
				setProperty(this.property);
				return "/player/properties/show.xhtml";
			}
			addErrorMessage(null, "ERRO", "Erro ao alterar propriedade.");
			return null;
		}catch (NamingException e){
			log.error("Erro ao alterar propriedade. Falha ao acessar datasource. " + e);
			addErrorMessage(null, "Erro ao editar propriedade.", e.getMessage());
			return null;
		}catch (SQLException e){
			log.error("Erro ao alterar propriedade. Falha ao acessar banco de dados. " + e);
			addErrorMessage(null, "Erro ao editar propriedade.", e.getMessage());
			return null;
		}catch (Exception e){
			log.error("Erro ao alterar propriedade." + e);
			addErrorMessage(null, "Erro ao editar propriedade.", e.getMessage());
		}
		return null;
	}
  
	public String delete(String id){
		  
		if(log.isDebugEnabled())
			log.debug("PropertiesBean:delete("+id+")");
		  
		User user = SessionContext.getInstance().getUserSession();
		if (user == null)
			return "/access/login.xhtml";
	  
		try{
			this.propertiesBO.delete(Long.valueOf(Long.parseLong(id)));
			setProperties(this.propertiesBO.list());
			addInfoMessage(null, "Propriedade: " + id, "Configuração removida com sucesso.");
			return "/player/home.xhtml?faces-redirect=true";
		}catch (NamingException e){
			log.error("Erro ao deletar propriedade. Falha ao acessar datasource. [id: " + id + "] " + e);
			addErrorMessage(null, "Erro ao deletar propriedade.", e.getMessage());
			return null;
		}catch (SQLException e){
			log.error("Erro ao deletar propriedade. Falha ao acessar banco de dados. [id: " + id + "] " + e);
			addErrorMessage(null, "Erro ao deletar propriedade.", e.getMessage());
			return null;
		}catch (Exception e){
			log.error("Erro ao deletar propriedade. [id: " + id + "] " + e);
			addErrorMessage(null, "Erro ao deletar propriedade.", e.getMessage());
		}
		return null;
	}
  
	public String show(String id){
		  
		if(log.isDebugEnabled())
			log.debug("PropertiesBean:show("+id+")");
		  
		try{
			if ((id == null) || (id.equals(""))){
				addErrorMessage(null, "ERRO", "Id não pode ser nulo");
				return null;
			}
			this.property = this.propertiesBO.get(Long.valueOf(Long.parseLong(id)));
			setProperty(this.property);
			return "/player/properties/show.xhtml?faces-redirect=true";
		}catch (NamingException e){
			log.error(e);
			return null;
		}catch (SQLException e){
			log.error(e);
			return null;
		}catch (Exception e){
			log.error(e);
		}
		return null;
	}
  
	public PropertiesBO getPropertiesBO(){
		return this.propertiesBO;
	}
  
	public void setPropertiesBO(PropertiesBO propertiesBO){
		this.propertiesBO = propertiesBO;
	}
  
	public Properties getProperty(){
		return this.property;
	}
  
	public void setProperty(Properties property){
		this.property = property;
	}
  
	public PropertiesTypeBO getPropertiesTypeBO(){
		return this.propertiesTypeBO;
	}
  
	public void setPropertiesTypeBO(PropertiesTypeBO propertiesTypeBO){
		this.propertiesTypeBO = propertiesTypeBO;
	}
  
	public List<Properties> getProperties(){
		return this.properties;
	}
  
	public void setProperties(List<Properties> properties){
		this.properties = properties;
	}
}