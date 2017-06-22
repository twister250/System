package br.com.player.dao.video;

public abstract class SQLVideo {
	protected static final String ID 			= "ID";
	protected static final String NAME 			= "NAME";
	protected static final String DESCRIPTION 	= "DESCRIPTION";
	protected static final String FILE 			= "FILE";
	protected static final String FILETYPE 		= "FILETYPE";
	protected static final String CREATED 		= "CREATED";
	protected static final String MODIFIED 		= "MODIFIED";
	protected static final String USER_ID 		= "USER_ID";
	protected static final String USER_NAME 	= "USER_NAME";	
	protected static final String SQL_INSERT 	= "insert into video (name, description, file, filetype, created, modified) values (?, ?, ?, ?, ?, ?)";
	protected static final String SQL_GET 		= "select id, name, description, file, filetype, created, modified, user_id from video where id = ?";
	protected static final String SQL_UPDATE 	= "update video set name = ?, description = ?, fileType = ?, file = ?, modified = ?, user_id = ? where id = ?";
	protected static final String SQL_DELETE 	= "delete from video where id = ?";
	protected static final String SQL_LIST 		= "select a.id, a.name, a.description, a.fileType, a.file, a.created, a.modified, b.id, b.name from video, user where a.user_id = b.id";
}