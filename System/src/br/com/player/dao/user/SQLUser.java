package br.com.player.dao.user;

public abstract class SQLUser {
	protected static final String FIELD_ID = "ID";
	protected static final String FIELD_NAME = "NAME";
	protected static final String FIELD_EMAIL = "EMAIL";
	protected static final String FIELD_PASSWORD = "PASSWORD";
	protected static final String FIELD_CREATED = "CREATED";
	protected static final String FIELD_MODIFIED = "MODIFIED";
	protected static final String SQL_INSERT = "insert into user (name, email, password, created, modified) values (?,?,?,?,?)";
	protected static final String SQL_GET = "select id, name, email, created, modified from user where id = ?";
	protected static final String SQL_CHECK_ACCESS = "select id, name, email, created from user where email = ? and password = ?";
}