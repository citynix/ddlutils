package org.apache.ddlutils.command;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

class PlatformConfigurationMock extends PlatformConfiguration {

	// private boolean enabled;
	private boolean dropSupported = false;
	private boolean createSuppoed = true;

	PlatformConfigurationMock(Properties p) throws IOException {

		BasicDataSource dataSource = new BasicDataSource();

		String jdbcClassName = p.getProperty("datasource.driverClassName");
		String jdbcUrl = p.getProperty("datasource.url");
		String jdbcUserName = p.getProperty("datasource.username");
		String jdbcPassword = p.getProperty("datasource.password");

		dataSource.setDriverClassName(jdbcClassName);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUserName);
		dataSource.setPassword(jdbcPassword);

		super.setDataSource(dataSource);

		String drop = p.getProperty("operation.drop");

		this.dropSupported = Boolean.parseBoolean(drop);

	}

	public boolean isCreateSupported() {
		return this.createSuppoed;

	}

	public boolean isDropSupported() {
		return this.dropSupported;
	}
}
