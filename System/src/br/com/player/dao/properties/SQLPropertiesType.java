package br.com.player.dao.properties;

public abstract class SQLPropertiesType {
	protected static final String ID = "ID";
	protected static final String NAME = "NAME";
	protected static final String DESCRIPTION = "DESCRIPTION";
	protected static final String CREATED = "CREATED";
	protected static final String MODIFIED = "MODIFIED";
	protected static final String USER_ID = "USER_ID";
	protected static final String SQL_INSERT = "insert into properties_type (name, description, created, modified, user_id) values (?,?,?,?,?)";
	protected static final String SQL_GET = "select id, name, description, created, modified, user_id from properties_type where id = ?";
	protected static final String SQL_LIST = "select id, name, description, created, modified, user_id from properties_type";
	protected static final String SQL_UPDATE = "update properties_type set name = ?, description = ?, modified = ?, user_id = ? where id = ?";
	protected static final String SQL_DELETE = "delete from properties_type where id = ?";
}