package com.lumileds.opentext.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumileds.opentext.config.MigrationConstants;
import com.lumileds.opentext.data.AssetMetadata;

public class XMLElements {
	
	private static Logger logger = LoggerFactory.getLogger(XMLElements.class);
	

	public AssetMetadata getElements(File xmlFile) throws DocumentException {

		FilesUtility filesUtility = new FilesUtility();
		
		Document document = null;
		
		try {
			SAXReader reader = new SAXReader();
			
			document = reader.read(xmlFile);
			
		}
//		catch (SAXParseException saxEx) {
//			
//			logger.info("Exception while parsing the xml: {} ", saxEx);
//		}
//		
		catch (DocumentException docEx) {
			
			filesUtility.move(xmlFile, MigrationConstants.FILE_ERROR_LOCATION);
			
			logger.error("Exception while parsing the xml: {} ", docEx);
		}
		

		return fetchElements(document);
	}

	private AssetMetadata fetchElements (Document document) throws DocumentException {

		Element root = document.getRootElement();
		
		AssetMetadata assetMetadata = new AssetMetadata();
		
		assetMetadata.setId(root.element(MigrationConstants.XML_ID_ELEMENT).getStringValue());
		assetMetadata.setVersion(root.element(MigrationConstants.XML_VERSION_ELEMENT).getStringValue());
		assetMetadata.setIsMaster(root.element(MigrationConstants.XML_ISMASTER_ELEMENT).getStringValue());
		assetMetadata.setIsLatestVersion(root.element(MigrationConstants.XML_ISLATESTVERSION_ELEMENT).getStringValue());
		assetMetadata.setFullPath(root.element(MigrationConstants.XML_FULLPATH_ELEMENT).getStringValue());
		assetMetadata.setFileName(root.element(MigrationConstants.XML_FILENAME_ELEMENT).getStringValue());
		
		logger.debug(" ID :  {} ", root.element(MigrationConstants.XML_ID_ELEMENT).getStringValue());
		logger.debug(" Version :  {} ", root.element(MigrationConstants.XML_VERSION_ELEMENT).getStringValue());
		logger.debug(" isMaster :  {} ", root.element(MigrationConstants.XML_ISMASTER_ELEMENT).getStringValue());
		logger.debug(" isLatestVersion :  {} ", root.element(MigrationConstants.XML_ISLATESTVERSION_ELEMENT).getStringValue());
		logger.debug(" fullPath :  {} ", root.element(MigrationConstants.XML_FULLPATH_ELEMENT).getStringValue());
		logger.debug(" fileName :  {} ", root.element(MigrationConstants.XML_FILENAME_ELEMENT).getStringValue());
		
		Element classifications = root.element(MigrationConstants.XML_CLASSIFICATIONS_ELEMENT);
		
		HashMap <String,String> nameLabelPathMap = new HashMap<String, String>();
		
		for (Iterator<Element> classificationElementIterator = 
				classifications.elementIterator(MigrationConstants.XML_CLASSIFICATION_ELEMENT);
				classificationElementIterator.hasNext();) {
			
			Element classificationElement = classificationElementIterator.next();
			
			nameLabelPathMap.put(
					classificationElement.element(MigrationConstants.XML_NAMEPATH_ELEMENT).getStringValue()
					,classificationElement.element(MigrationConstants.XML_LABELPATH_ELEMENT).getStringValue()
					);
			
			logger.debug(" NamePath : {}", classificationElement.element(
					MigrationConstants.XML_NAMEPATH_ELEMENT).getStringValue() );
			
			logger.debug(" LabelPath : {}", classificationElement.element(
					MigrationConstants.XML_LABELPATH_ELEMENT).getStringValue() );
			
		}
		
		assetMetadata.setNameLabelPathMap(nameLabelPathMap);
		
		Element nameFieldElement = root.element(MigrationConstants.XML_FIELDS_ELEMENT);
		
		List <Element> nameFieldList = new ArrayList<Element>();

		for (Iterator<Element> fieldElementIterator = 
				nameFieldElement.elementIterator(MigrationConstants.XML_FIELD_ELEMENT);
				fieldElementIterator.hasNext();) 
		{
			Element fieldElement = fieldElementIterator.next();
			
			nameFieldList.add(fieldElement);

		}
		
		assetMetadata.setNameFieldList(nameFieldList);
		
		return assetMetadata;

	}

}
