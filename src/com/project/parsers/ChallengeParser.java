package com.project.parsers;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.project.domain.Service;
import com.project.domain.Task;

/**
 * The challenge parser. This retrieves Cin and Cout given in problem.xml file
 * @author psyomn
 *
 */
public class ChallengeParser
{
  private List<Task> mTasks = new ArrayList<Task>();
  
  public void parse(String iXMLContents)
  {
    Document doc = null;
    DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null; 
    NodeList nodes = null;
    
    try 
    {
      docBuilder = fact.newDocumentBuilder();
      doc = docBuilder.parse(new ByteArrayInputStream(iXMLContents.getBytes()));
      nodes = doc.getElementsByTagName("task");
      
      /* Iterate through SOLUTIONS */
      for (int i=0; i < nodes.getLength(); ++i)
      {
        Task task = null;
        NodeList children;
        NodeList inputList;
        NodeList outputList;
        
        children = nodes.item(i).getChildNodes(); 
        
        task = new Task(); 
        
        /* 0 is for provided */
        inputList = children.item(0).getChildNodes();
        for (int j=0; j < inputList.getLength(); ++j)
        {
           task.addGivenInstance(inputList.item(j).getAttributes()
               .getNamedItem("name").getNodeValue());
        }
        
        /* 1 is for wanted */ 
        outputList = children.item(1).getChildNodes();
        for (int j=0; j < outputList.getLength(); ++j)
        {
          task.addGoalInstance(outputList.item(j).getAttributes()
            .getNamedItem("name").getNodeValue());
        }
        
        System.out.println(task);
        mTasks.add(task);
      }
      
      /* Parse abstractions */
      nodes = doc.getElementsByTagName("task");
      
      /* Iterate through SOLUTIONS */
      for (int i=0; i < nodes.getLength(); ++i)
      {
        Task task = null;
        NodeList children;
        NodeList inputList;
        NodeList outputList;
        
        children = nodes.item(i).getChildNodes(); 
        
        task = new Task(); 
        
        /* 0 is for provided */
        inputList = children.item(0).getChildNodes();
        for (int j=0; j < inputList.getLength(); ++j)
        {
           task.addGivenInstance(inputList.item(j).getAttributes()
               .getNamedItem("name").getNodeValue());
        }
        
        /* 1 is for wanted */ 
        outputList = children.item(1).getChildNodes();
        for (int j=0; j < outputList.getLength(); ++j)
        {
          task.addGoalInstance(outputList.item(j).getAttributes()
            .getNamedItem("name").getNodeValue());
        }
        
        mTasks.add(task);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }
}
