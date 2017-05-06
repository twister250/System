package br.com.player.dao.properties;

public abstract class SQLPropertiesType {
	protected static final String FIELD_ID = "ID";
	protected static final String FIELD_NAME = "NAME";
	protected static final String FIELD_DESCRIPTION = "DESCRIPTION";
	protected static final String FIELD_CREATED = "CREATED";
	protected static final String FIELD_MODIFIED = "MODIFIED";
	protected static final String FIELD_USER_ID = "USER_ID";
	protected static final String SQL_INSERT = "insert into properties_type (name, description, created, modified, user_id) values (?,?,?,?,?)";
	protected static final String SQL_GET = "select id, name, description, created, modified, user_id from properties_type where id = ?";
	protected static final String SQL_LIST = "select id, name, description, created, modified, user_id from properties_type";
	protected static final String SQL_LIST_EDIT = "select id, name, description, created, modified, user_id from properties_type where id != ?";
}