package com.project.parsers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Some common tools that the parsers can use
 * @author psyomn
 *
 */
public class Common
{
  /**
   * 
   * @param iRelativePath the relative file path to the file
   * @return the contents of the file as string
   */
  public static String getFileContentsAsString(String iRelativePath)
  {
    File file = new File(iRelativePath);
    FileInputStream wFis = null;
    BufferedReader wBuffReader = null;
    DataInputStream wInputStream = null;
    StringBuilder contents = null;
    String wLine; 
    char c; 
    
    try
    {
      wFis = new FileInputStream(file);
      wInputStream = new DataInputStream(wFis);
      wBuffReader = new BufferedReader(new InputStreamReader(wInputStream));
      contents = new StringBuilder();
      
      while((wLine = wBuffReader.readLine()) != null)
      {
        contents.append(wLine);
      }
      
      wBuffReader.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
        
    return contents.toString();
  }
}
