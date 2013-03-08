/*
 * @author: Shivani Nayar <shivani.nayar@asu.edu>
 * @date: Dec'12
 * 
 */

package com.project.XMLManager;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;

import com.project.DatabaseManager.DatabaseManager;
import com.project.DatabaseManager.FieldInfo;

public class XMLParser extends Activity {
	
	public static ArrayList<FieldInfo> fieldsArray = new ArrayList<FieldInfo>();
	private static Boolean xmlParseState = false;
	private static String APPTITLE = "";
	
	public static String parseXML(InputSource xmlFile) throws IOException, SAXException{
		try 
		{
			if(!xmlParseState)
			{
				DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
				DocumentBuilder docB = docBF.newDocumentBuilder();
				Document doc = docB.parse(xmlFile);
	
				doc.getDocumentElement().normalize();
				
				Node titleField = doc.getElementsByTagName("title").item(0);
				APPTITLE = titleField.getChildNodes().item(0).getNodeValue().trim();
				
				Node tableField = doc.getElementsByTagName("tableName").item(0);
				DatabaseManager.setTABLENAME(tableField.getChildNodes().item(0).getNodeValue().trim());
				
				NodeList fields = doc.getElementsByTagName("fields");
				
				for(int fCount = 0; fCount<fields.getLength(); fCount++)
				{
					Node fieldNode = fields.item(fCount);
					if(fieldNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element firstPersonElement = (Element)fieldNode;
						NodeList firstNameList;
						Element firstNameElement;
						NodeList textFNList;
						String dbName = "", type = "", value = "", hint = "",inputType = "",fieldType = "";
						boolean required = false;
						ArrayList<String> values = new ArrayList<String>();
    					
						//----DB Name
						firstNameList = firstPersonElement.getElementsByTagName("dbName");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    dbName = ((Node)textFNList.item(0)).getNodeValue().trim();
	
	                    //----Type
						firstNameList = firstPersonElement.getElementsByTagName("type");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    type = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    
	                    //----Required
						firstNameList = firstPersonElement.getElementsByTagName("required");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    String req = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    if(req.equals("true"))
	                    	required = true;
	                    else
	                    	required = false;
	                    
	                    //----Value
						firstNameList = firstPersonElement.getElementsByTagName("value");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    value = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    
	                    //----Hint
						firstNameList = firstPersonElement.getElementsByTagName("hint");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    hint = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    
	                    //----InputType
						firstNameList = firstPersonElement.getElementsByTagName("inputType");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    inputType = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    
	                    //----fieldType
	                    firstNameList = firstPersonElement.getElementsByTagName("fieldType");
	                    firstNameElement = (Element)firstNameList.item(0);
	                    textFNList = firstNameElement.getChildNodes();
	                    fieldType = ((Node)textFNList.item(0)).getNodeValue().trim();
	                    
	                    NodeList nfields = firstPersonElement.getElementsByTagName("values");
	                    
	    				for(int counter = 0; counter< nfields.getLength(); counter++)
	    				{
	    					firstNameElement = (Element)nfields.item(counter);
	    					values.add(firstNameElement.getChildNodes().item(0).getNodeValue().trim());
	    				}
	                    
	                    FieldInfo fieldInfo = new FieldInfo(dbName, type, value, hint,inputType,fieldType, required,values);
	          		    fieldsArray.add(fieldInfo);
					}
					System.out.println("Field is:" + fieldNode);
				}
//				for(int i =0; i<fieldsArray.size(); i++){
//					FieldInfo fieldInfo = new FieldInfo();
//	      		    fieldInfo = fieldsArray.get(i);
//					System.out.println("1: "+ fieldInfo.getFieldName());
//				}
				xmlParseState = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return APPTITLE;
	}
}
