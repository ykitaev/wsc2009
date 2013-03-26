package com.project.presentation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * This is the main view to the actual program.
 * @author psyomn
 *
 */
public class MainGUI extends JFrame
{
  protected JButton mButtonParseTaxonomy = new JButton("Parse Taxonomy");
  protected JButton mButtonParseChallenge = new JButton("Parse Challenge");
  protected JButton mButtonDoIt = new JButton("Do it...");
  protected JButton mButtonParseServices = new JButton("Parse Services");
  /* From 1 -> 10 */
  protected JList mListBranching = new JList();
  protected GridBagLayout mLayout = new GridBagLayout();
  protected GridBagConstraints mGBConstraints = new GridBagConstraints();
  
  
  public MainGUI()
  {
    super("WSC 2009 | Unamed Team");
  
    ArrayList<Component> wArr = new ArrayList<Component>();
    
    /* 
     * Must add the components here in the order you want them shown in the
     * actual ui
     */
    wArr.add(mButtonParseChallenge);
    wArr.add(mButtonParseServices);
    wArr.add(mButtonParseTaxonomy);
    wArr.add(mButtonDoIt);
    wArr.add(mListBranching);
    wArr.add(mListBranching);
    
    mGBConstraints.gridx = 0;
    mGBConstraints.gridy = 0;
    mGBConstraints.fill  = GridBagConstraints.BOTH;
    
    this.setLayout(mLayout);
    
    for (int i = 0; i < wArr.size(); ++i)
    {
      this.add(wArr.get(i), mGBConstraints);
      ++mGBConstraints.gridy;
    }
    
    this.setBounds(10, 10, 500, 400);
    this.setResizable(false);
  }
}
