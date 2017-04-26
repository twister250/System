package br.com.player.dao.properties;

public abstract class SQLProperties
{
  protected static final String FIELD_ID = "ID";
  protected static final String FIELD_NAME = "NAME";
  protected static final String FIELD_VALUE = "VALUE";
  protected static final String FIELD_TYPE_NAME = "TYPE_NAME";
  protected static final String FIELD_TYPE_ID = "TYPE_ID";
  protected static final String FIELD_CREATED = "CREATED";
  protected static final String FIELD_MODIFIED = "MODIFIED";
  protected static final String FIELD_USER_ID = "USER_ID";
  protected static final String FIELD_USER_NAME = "USER_NAME";
  protected static final String SQL_INSERT = "insert into properties (name, value, properties_type_id, created, modified, user_id) values (?,?,?,?,?,?)";
  protected static final String SQL_UPDATE = "update properties set name = ?, value = ?, properties_type_id = ?, modified = ? where id = ?";
  protected static final String SQL_GET = "select a.id, a.name, a.value, a.properties_type_id type_id, a.created, a.modified, a.user_id, b.name type_name, c.name user_name\tfrom properties a, properties_type b, user c\twhere a.id = ?\tand a.properties_type_id = b.id\tand a.user_id = c.id";
  protected static final String SQL_LIST = "select a.id, a.name, a.value, b.id type_id, b.name type_name, a.created, a.modified,\ta.user_id, c.name user_name\tfrom properties a, properties_type b, user c\twhere a.properties_type_id = b.id and\ta.user_id = c.id";
  public static final String SQL_DELETE = "delete from properties where id = ?";
}