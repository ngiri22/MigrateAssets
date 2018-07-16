package com.lumileds.opentext.config;

public class MigrationConstants {

	//DB values
	public static final String DB_URL = Configurations.getInstance().getProperty("db.url");
	public static final String DB_DRIVER = Configurations.getInstance().getProperty("db.driver");
	public static final String DB_USER = Configurations.getInstance().getProperty("db.user");
	public static final String DB_PASSWORD = Configurations.getInstance().getProperty("db.password");
	
	//XML values	
	public static final String XML_FIELDS_ELEMENT = Configurations.getInstance().getProperty("xml.fields.element");
	public static final String XML_FIELD_ELEMENT = Configurations.getInstance().getProperty("xml.field.element");
	public static final String XML_NAME_ATTRIBUTE = Configurations.getInstance().getProperty("xml.name.attribute");
	public static final String XML_ID_ELEMENT = Configurations.getInstance().getProperty("xml.id.element");
	public static final String XML_VERSION_ELEMENT = Configurations.getInstance().getProperty("xml.version.element");
	public static final String XML_ISMASTER_ELEMENT = Configurations.getInstance().getProperty("xml.isMaster.element");
	public static final String XML_ISLATESTVERSION_ELEMENT = Configurations.getInstance().getProperty("xml.isLatestVersion.element");
	public static final String XML_FULLPATH_ELEMENT = Configurations.getInstance().getProperty("xml.fullPath.element");
	public static final String XML_FILENAME_ELEMENT = Configurations.getInstance().getProperty("xml.fileName.element");
	public static final String XML_CLASSIFICATIONS_ELEMENT = Configurations.getInstance().getProperty("xml.classifications.element");
	public static final String XML_CLASSIFICATION_ELEMENT = Configurations.getInstance().getProperty("xml.classification.element");
	public static final String XML_NAMEPATH_ELEMENT = Configurations.getInstance().getProperty("xml.namePath.element");
	public static final String XML_LABELPATH_ELEMENT = Configurations.getInstance().getProperty("xml.labelPath.element");
	
	//File Input values
	public static final String FILE_INPUT_LOCATION = Configurations.getInstance().getProperty("file.input.location");
	public static final String FILE_PROCESSED_LOCATION = Configurations.getInstance().getProperty("file.processed.location");
	public static final String FILE_INPUT_BATCH_COUNT = Configurations.getInstance().getProperty("file.input.batch.count");
	public static final String FILE_ERROR_LOCATION = Configurations.getInstance().getProperty("file.error.location");
	
	// Constants (not user configurable)
	public static final String PATH_CONFFILE = "config.properties";

}
