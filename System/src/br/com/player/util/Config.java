package br.com.player.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Config {
	private static Properties properties = null;
	private static InputStream inputStream = null;

	public static Properties getProperties(String configFile) {
		properties = properties == null ? new Properties() : properties;
		inputStream = Config.class.getClassLoader().getResourceAsStream(configFile);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}