package org.apache.ddlutils.command;

import org.apache.ddlutils.model.Database;

abstract class DatabaseCommandWithModel extends DatabaseCommand {

	private Database databaseModel;

	private PlatformConfiguration configuration;

	public Database getDatabaseModel() {
		return databaseModel;
	}

	public void setDatabaseModel(Database databaseModel) {
		this.databaseModel = databaseModel;
	}

	protected boolean isModelSet() {
		return this.databaseModel != null;
	}

	public PlatformConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(PlatformConfiguration configuration) {
		this.configuration = configuration;
	}
	
	/**
     * {@inheritDoc}
     */
    public boolean isRequiringModel()
    {
        return true;
    }
    

}
