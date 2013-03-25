package com.project;

import com.project.parsers.Common;
import com.project.parsers.ServicesParser;
import com.project.parsers.TaxonomyParser;

public class Main
{

  public static void main(String[] args)
  {
    System.out.println("WSC 2009 Class project");
    ServicesParser sp = new ServicesParser();
    TaxonomyParser tp = new TaxonomyParser();
    
    sp.parse(Common.getFileContentsAsString(mTestSetPath + mServicesFilename));
    tp.parse(Common.getFileContentsAsString(mTestSetPath + mTaxonomyFilename));
  }
  
  /**
   * The path where you have extracted the required testsets
   */
  private static final String mTestSetPath = "./data/Testset01/";
  
  /**
   * TODO: can be moved out of here later.
   */
  private static final String mTaxonomyFilename = "taxonomy.xml";
  
  private static final String mServicesFilename = "services.xml";
  
}
