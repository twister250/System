package br.com.player.dao.video;

public abstract class SQLVideo
{
  protected static final String VIDEO_ID = "ID";
  protected static final String VIDEO_NAME = "NAME";
  protected static final String VIDEO_DESCRIPTION = "DESCRIPTION";
  protected static final String VIDEO_FILE = "FILE";
  protected static final String VIDEO_FILETYPE = "FILETYPE";
  protected static final String VIDEO_CREATED = "CREATED";
  protected static final String VIDEO_MODIFIED = "MODIFIED";
  protected static final String SQL_INSERT = "insert into video (name, description, file, filetype, created, modified) values (?, ?, ?, ?, ?, ?)";
  protected static final String SQL_GET = "select id, name, description, file, filetype, created, modified from video where id = ?";
}