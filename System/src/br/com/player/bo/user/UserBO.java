package br.com.player.bo.user;

import br.com.player.dao.user.UserDAO;
import br.com.player.entity.User;

import java.sql.SQLException;
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

@ManagedBean(name="userBO")
@SessionScoped
@Stateful(passivationCapable=true)
public class UserBO{
  
	private User user = null;
	
	private static Logger log = Logger.getLogger(UserBO.class);
  
	public User checkAccess(User pojo) throws NamingException, SQLException, Exception{
	
		if(log.isDebugEnabled())
			log.debug("UserBO:checkAccess("+pojo+")");
		
		this.user = null;
	    
		if ((pojo.getEmail() == null) || (pojo.getEmail().equals("")))
	      throw new Exception("O campo Login n�o pode ser vazio.");
	    
		if ((pojo.getPassword() == null) || (pojo.getPassword().equals("")))
	      throw new Exception("O campo Senha n�o pode ser vazio.");
	    
		pojo.setEmail(pojo.getEmail().toLowerCase().trim());
	    
	    List<User> usersList = UserDAO.getInstance().checkAccess(pojo.getEmail(), pojo.getPassword());
	    
	    if ((usersList == null) || (usersList.size() == 0))
	      return null;
	    
	    if (usersList.size() > 1)
	      return null;
	    
	    this.user = ((User)usersList.get(0));
	    
	    return this.user;
	}
  
  	public User get(Long id) throws NamingException, SQLException, Exception{
  		
  		if(log.isDebugEnabled())
  			log.debug("UserBO:get("+id+")");
  		
  		try{
  			
  			this.user = UserDAO.getInstance().get(id);
  			
  			return this.user;
  		}catch (NamingException e){
  			log.error("UserBO:get("+id+")",e);
  			throw e;
  		}catch (SQLException e){
  			log.error("UserBO:get("+id+")",e);
  			throw e;
  		}catch (Exception e){
  			log.error("UserBO:get("+id+")",e);
  			throw e;
  		}
  	}
  
	public long create(User user) throws NamingException, SQLException, Exception{
		
		if(log.isDebugEnabled())
			log.debug("UserBO:create("+user+")");
		
		try{
		
			return UserDAO.getInstance().create(user);
			
	    }catch (SQLException e){
	    	log.error("UserBO:create("+user+")",e);
	    	throw e;
	    }catch (NamingException e){
	    	log.error("UserBO:create("+user+")",e);
	    	throw e;
	    }catch (Exception e){
	    	log.error("UserBO:create("+user+")",e);
	    	throw e;
	    }
	}
}