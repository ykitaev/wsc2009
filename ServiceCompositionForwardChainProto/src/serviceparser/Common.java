package serviceparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
  
  /**
   * Use this if you want to output some (extensive) debug info into a file
   * @param iFilename
   * @param Contents
   */
  public static void writeOut(String iFilename, String iContents)
  {
    File wFile = new File(iFilename);
    BufferedWriter bw = null;
        
    try
    {
      bw = new BufferedWriter(new FileWriter(wFile));
      bw.write(iContents);
      bw.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}