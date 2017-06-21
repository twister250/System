package br.com.player.test;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import br.com.player.dao.factory.DAO;
import br.com.player.entity.User;

public class Test extends DAO {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
	}

	@Override
	public long create(Object object, User user) throws NamingException, SQLException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object get(Long id) throws NamingException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long update(Object object, User user) throws NamingException, SQLException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delete(Long id) throws NamingException, SQLException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> list() throws NamingException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
}