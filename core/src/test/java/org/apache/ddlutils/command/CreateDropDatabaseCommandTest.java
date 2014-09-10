package org.apache.ddlutils.command;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

public class CreateDropDatabaseCommandTest {

    @Test
    public void create() {

	List<PlatformConfigurationMock> lst = CommandTestUtility.getAvailableConfiguration();

	String expected = "ddlutils";

	for (PlatformConfigurationMock config : lst) {

	    boolean isSupported = config.isCreateSupported();
	    if (!isSupported)
		continue;

	    CreateDatabaseCommand createCommand = new CreateDatabaseCommand();

	    createCommand.setPlatformConfiguration(config);

	    try {

		createCommand.execute();

		Connection conn = config.getDataSource().getConnection();

		String catName = testCatalogExist(conn);

		assertEquals(expected, catName);

	    } catch (CommandException e) {
		e.printStackTrace();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	CommandTestUtility.release(lst);

    }

    @Test
    public void drop() {

	List<PlatformConfigurationMock> lst = CommandTestUtility.getAvailableConfiguration();

	String expected = "ddlutils";

	for (PlatformConfigurationMock config : lst) {

	    try {

		String dbtype = config.getPlatform().getName();

		boolean isSupported = config.isDropSupported();

		if (!isSupported)
		    continue;

		DropDatabaseCommand dropCommand = new DropDatabaseCommand();

		dropCommand.setPlatformConfiguration(config);

		dropCommand.execute();

		Connection conn = config.getDataSource().getConnection();

		String catName = testCatalogExist(conn);

		assertEquals(expected, catName);

	    } catch (CommandException e) {
		e.printStackTrace();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	CommandTestUtility.release(lst);

    }

    private String testCatalogExist(Connection connection) {
	ResultSet resultSet;
	try {
	    resultSet = connection.getMetaData().getCatalogs();
	    while (resultSet.next()) {
		String databaseName = resultSet.getString(1);
		return databaseName;
	    }
	    resultSet.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
