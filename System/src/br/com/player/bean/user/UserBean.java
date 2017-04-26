package br.com.player.bean.user;

import br.com.player.bean.AbstractBean;
import br.com.player.bo.user.UserBO;
import br.com.player.entity.User;
import br.com.player.wrapper.SessionContext;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean extends AbstractBean{
  
	private static final long serialVersionUID = 1L;
	private static final String USER_ATTRIBUTE = "userlogged";
	private static Logger log = Logger.getLogger(UserBean.class);
	private User user;
	
	@ManagedProperty("#{userBO}")
	private UserBO userBO;
  
	@PostConstruct
	public void init(){
		
		if(log.isDebugEnabled())
			log.debug("UserBean:init()");
		
		this.user = new User();
	}
  
	public String doLogin() throws Exception{
		
		if(log.isDebugEnabled())
			log.debug("UserBean:doLogin()");
		
		if(this.user == null){
			addErrorMessage(null, "Acesso Negado!", "Preencha os campos corretamente.");
			log.error("Acesso Negado. Preencha os campos corretamente.");
			return null;
		}
		
		if ((this.user.getEmail() == null) || (this.user.getEmail().equals(""))){
			addErrorMessage(null, "Acesso Negado!", "O campo Login não pode ser vazio.");
			log.error("Acesso negado. O campo Login não pode ser vazio.");
			return null;
		}
    
		if ((this.user.getPassword() == null) || (this.user.getPassword().equals(""))){
			addErrorMessage(null, "Acesso Negado!", "O campo Senha não pode ser vazio.");
			return null;
		}
		
	    try{
	    	this.user = this.userBO.checkAccess(this.user);
	    	
	    	if (this.user == null){
	    		addErrorMessage(null, "Acesso Negado!", "Dados incorretos.");
	    		log.error("Acesso Negado. Usuário não encontrado.");
	    		return null;
	    	}
	    	
	    	SessionContext.getInstance().setAttribute("userlogged", this.user);
	    	log.info("Login realizado. [login: " + this.user.getEmail() + "]");
	    	return "/player/home.xhtml?faces-redirect=true";
	    }catch (NamingException e){
	    	addErrorMessage(null, e.getCause().toString(), e.getMessage());
	    	log.error(e.getMessage());
	    	return null;
	    }catch (SQLException e)	    {
	      addErrorMessage(null, e.getCause().toString(), e.getMessage());
	      log.error(e.getMessage());
	      return null;
	    }catch (Exception e){
	    	addErrorMessage(null, e.getCause().toString(), e.getMessage());
	    	log.error(e.getMessage());
	    }
	    return null;
	}
  
	public String doCreate(ActionEvent event){
	    
		if(log.isDebugEnabled())
			log.debug("UserBean:doCreate("+event+")");
		
		if ((this.user.getName() == null) || (this.user.getName().equals(""))) {
	      addErrorMessage(null, "Erro:", "O campo Nome não pode ser vazio.");
	    }
		
	    if ((this.user.getEmail() == null) || (this.user.getEmail().equals(""))) {
	      addErrorMessage(null, "Erro:", "O campo Login não pode ser vazio.");
	    }
	    
	    if ((this.user.getPassword() == null) || (this.user.getPassword().equals(""))) {
	      addErrorMessage(null, "Erro:", "O campo Senha não pode ser vazio.");
	    }
	    
	    try{
	    	
	    	long id = this.userBO.create(this.user);
	    	
	    	if (id < 1L){
	    		addErrorMessage(null, "Erro:", "Não foi possível criar o usuário.");
	    		return null;
	    	}
	    }catch (NamingException e){
	    	log.error("Erro ao criar usuário.", e);
	    	addErrorMessage(null, "Erro:", "Não foi possível criar o usuário.");
	    	return null;
	    }catch (SQLException e){
	    	log.error("Erro ao criar usuário.", e);
	    	addErrorMessage(null, "Erro:", "Não foi possível criar o usuário.");
	    	return null;
	    }catch (Exception e){
	    	log.error("Erro ao criar usuário.", e);
	    	addErrorMessage(null, "Erro:", "Não foi possível criar o usuário.");
	    	return null;
	    }
	    return "/player/home.xhtml?faces-redirect=true";
	}
  
	public String doLogout(){
		SessionContext.getInstance().endSession();
		return "/player/access/login.xhtml?faces-redirect=true";
	}
  
	public static User getUserSession(){
		return SessionContext.getInstance().getUserSession();
	}
  
	public User getUser(){
		return this.user;
	}
  
	public UserBO getUserBO(){
		return this.userBO;
	}
  
	public void setUser(User user){
		this.user = user;
	}
  
	public void setUserBO(UserBO userBO){
		this.userBO = userBO;
	}
}