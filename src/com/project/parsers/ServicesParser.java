package com.project.parsers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.project.domain.Service;

/**
 * Used to parse the services.wsdl
 * @author psyomn
 */
public class ServicesParser
{
  /**
   * Keys are service names, values are services
   */
  private HashMap<String, Service> mServices = new HashMap<String, Service>();
  
  /**
   * Give a relative file path, to try and parse services
   */
  public void parse(String iXMLContents)
  {
    Document doc = null;
    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null; 
    NodeList nodes = null; 
    
    try 
    {
      docBuilder =  fact.newDocumentBuilder();
      doc = docBuilder.parse(new ByteArrayInputStream(iXMLContents.getBytes()));
      nodes = doc.getElementsByTagName("service");
      
      for (int i=0; i < nodes.getLength(); ++i)
      {
        NodeList children;
        NodeList inputList;
        NodeList outputList;
        
        children = nodes.item(i).getChildNodes(); 
        
        System.out.println(nodes.item(i).getAttributes().getNamedItem("name")
            .getNodeValue());
        
        System.out.println(" Inputs");
        /* 0 is for inputs */
        inputList = children.item(0).getChildNodes();
        for (int j=0; j < inputList.getLength(); ++j)
        {
           System.out.println("\t" + inputList.item(j).getAttributes()
             .getNamedItem("name").getNodeValue());
        }
        
        System.out.println(" Outputs");
        /* 1 is for outputs */ 
        outputList = children.item(1).getChildNodes();
        for (int j=0; j < outputList.getLength(); ++j)
        {
          System.out.println("\t" + outputList.item(j).getAttributes()
            .getNamedItem("name").getNodeValue());
        }
        
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
  }
}
