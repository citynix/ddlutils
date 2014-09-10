package org.apache.ddlutils.command;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class CommandTestUtility {

    private static FilenameFilter jdbcProfilesFilter = new FilenameFilter() {
	@Override
	public boolean accept(File dir, String name) {
	    return name.toLowerCase().startsWith("jdbc.properties");
	}
    };

    /***
     * A factory method for all available profiles. An active profile has the
     * property "enabled=true".
     * 
     * @return
     */
    public static List<PlatformConfigurationMock> getAvailableConfiguration() {

	URL url = CommandTestUtility.class.getClassLoader().getResource("profiles");

	File dir = new File(url.getPath());

	File[] files = dir.listFiles();

	PlatformConfigurationMock config;

	List<PlatformConfigurationMock> configurations = new LinkedList<PlatformConfigurationMock>();

	for (File file : files) {
	    try {

		Properties properties = new Properties();

		properties.load(file.toURI().toURL().openStream());

		String enableStr = properties.getProperty("enabled");

		boolean enabled = Boolean.parseBoolean(enableStr);

		if (enabled) {
		    config = new PlatformConfigurationMock(properties);
		    configurations.add(config);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	return configurations;
    }

    public static void release(List<PlatformConfigurationMock> configs) {
	for (PlatformConfiguration c : configs) {
	    try {
		c.getDataSource().close();
		c = null;
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
}
