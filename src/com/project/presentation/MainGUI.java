package com.project.presentation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
  protected JButton mButtonStop = new JButton("Stop");
  protected JTextArea mTextOutput = new JTextArea(16,58);
  protected JScrollPane mTextScroll = new JScrollPane(mTextOutput);
  /* From 1 -> 10 */
  protected JLabel mListLabel = new JLabel("Parallelism");
  protected JComboBox<String> mListBranching = null;
  
  protected JLabel mSolutionLabel = new JLabel("Num of solutions:");
  protected JComboBox<String> mSolutionCombo = null;
  
  protected GridBagLayout mLayout = new GridBagLayout();
  protected GridBagConstraints mGBConstraints = new GridBagConstraints();
  
  
  public MainGUI()
  {
    super("WSC 2009 | Unamed Team");
    initComponents();
    ArrayList<Component> wArr = new ArrayList<Component>();
        
    /* 
     * Must add the components here in the order you want them shown in the
     * actual ui
     */
    wArr.add(mTextScroll);
    wArr.add(mButtonParseChallenge);
    wArr.add(mButtonParseServices);
    wArr.add(mButtonParseTaxonomy);
    wArr.add(mListLabel);
    wArr.add(mListBranching);
    wArr.add(mSolutionLabel);
    wArr.add(mSolutionCombo);
    wArr.add(mButtonDoIt);
    wArr.add(mButtonStop);
    
    mGBConstraints.gridx = 0;
    mGBConstraints.gridy = 0;
    mGBConstraints.fill  = GridBagConstraints.BOTH;
    
    this.setLayout(mLayout);
    
    for (int i = 0; i < wArr.size(); ++i)
    {
      if (wArr.get(i) == mTextScroll)
        mGBConstraints.weighty = 2.0f;
      
      this.add(wArr.get(i), mGBConstraints);
      ++mGBConstraints.gridy;
      
      if (wArr.get(i) == mTextScroll)
        mGBConstraints.weighty = 0.0f;
    }
    
    this.setBounds(10, 10, 700, 600);
    this.setResizable(true);
  }

  private void initComponents()
  {
    initListBranchingComponent();
    initSolutionComboComponent();
  }
  
  /**
   * Add the items for the list for branching
   */
  private void initListBranchingComponent()
  {
    String[] items = new String[10];
    for(int i = 0; i < 10; items[i] = "" + (++i));
    mListBranching = new JComboBox<String>(items);
  }
  
  private void initSolutionComboComponent()
  {
    String[] items = new String[10];
    for(int i = 0; i < 10; items[i] = "" + (++i));
    mSolutionCombo = new JComboBox<String>(items);
  }
  
}
