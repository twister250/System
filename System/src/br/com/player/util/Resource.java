package br.com.player.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Resource
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static final Properties playerProperties = Config.getProperties("player.properties");
  
  public DataSource lookupDataSource(String dataSource, String provider)
    throws NamingException
  {
    Context context = null;
    Hashtable<String, String> hashTable = new Hashtable();
    hashTable.put("java.naming.provider.url", playerProperties.getProperty(provider));
    hashTable.put("java.naming.factory.initial", playerProperties.getProperty("INITIAL_CONTEXT_FACTORY"));
    context = new InitialContext(hashTable);
    return (DataSource)context.lookup(playerProperties.getProperty(dataSource));
  }
}