CREATE TABLE ASSET_MASTER (
	ID VARCHAR(100),
	VERSION VARCHAR(5),
	ISMASTER VARCHAR(5),
	ISLATESTVERSION VARCHAR(5),
	FULLPATH VARCHAR(500),
	FILENAME VARCHAR(500)
);

CREATE TABLE ASSET_METADATA (
	ID varchar(100),
	NAME varchar(50),
	FIELD varchar(1000)
);

CREATE TABLE ASSET_TAXONOMY (
	ID VARCHAR(100),
	NAMEPATH VARCHAR(500),
	LABELPATH VARCHAR(500)
);


CREATE TABLE PAL_METADATA_FIELDS (
	ASSET_ID UNIQUEIDENTIFIER NOT NULL  
	   DEFAULT NEWID() PRIMARY KEY,
	MASTERFILENAME VARCHAR(200),
	IMPORT_DATE TIMESTAMP,
	PAL_METADATA_FILE_NAME VARCHAR(200),
	COUNTRIES VARCHAR(20),
	LANGUAGES VARCHAR(20),
	BRAND VARCHAR(30),
	CREATIONSTATUS VARCHAR(30),
	DESCRIPTION VARCHAR(200),
	DM_ASSET_OWNER VARCHAR(100),
	MEDIA_TITLE VARCHAR(200),
	RIGHTS_COPYRIGHT_CHANNEL VARCHAR(50),
	RIGHTS_COPYRIGHT_DATE_APPLICABLE VARCHAR(20),
	RIGHTS_COPYRIGHT_DATE_END DATE,
	RIGHTS_COPYRIGHT_DATE_START DATE,
	RIGHTS_USAGE VARCHAR(20),
	RIGHTS_VISIBILITY VARCHAR(40),
	SUBJECT_KEYWORDS VARCHAR(1000),
	TYPE_PRODUCT VARCHAR(20),
	EXPORTLOG VARCHAR(500),
	AUTOMOTIVE_REGION VARCHAR(30),
	MUSIC_RIGHTS_APPLICABLE VARCHAR(20)
);