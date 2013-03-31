package presentation;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import conceptparser.ConceptInstanceMap;

import planner.Driver;
import planner.ForwardChainReasoningPlanner;
import planner.OutputManager;

/**
 * This is the main view to the actual program.
 * @author psyomn
 *
 */
public class MainGUI extends JFrame
{
  
  
	private static final long serialVersionUID = 1L;
	
	public MainGUI()
	  {
	    super("WSC 2009 | Blade Runners");
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
	    
	    OutputManager.setTextArea(mTextOutput);
	  }
	
	  private void initComponents()
	  {
	    initListBranchingComponent();
	    initSolutionComboComponent();
	    initActionListeners();
	    initBindButtonsToActionListeners();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	  private String getAbsoluteFilePathFromFileChooser()
	  {
	    JFileChooser wFileChooser = new JFileChooser();
	    String wFilename = null;
	    
	    if (wFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
	    {
	      wFilename = wFileChooser.getSelectedFile().getAbsolutePath();
	    }
	    
	    return wFilename;
	  }
	  
	  private void callButtonParseChallenge()
	  {
		challengeFile = getAbsoluteFilePathFromFileChooser();
	    OutputManager.writeToFile("Challenge file selected: \n" + challengeFile);
	  }
	  
	  private void callButtonParseTaxonomy()
	  {
		  owlFile = getAbsoluteFilePathFromFileChooser();
		    OutputManager.writeToFile("Taxonomy file selected: \n" + owlFile);
	  }
	  
	  private void callButtonParseServices()
	  {
		  serviceFile = getAbsoluteFilePathFromFileChooser();
		  OutputManager.writeToFile("Services file selected: \n" + serviceFile);
	  }
	  
	  private void callButtonDoIt()
	  {
		  Runner r = new Runner();
		  r.start();
    
	  }
	  
	  private void callButtonStop()
	  {
		  ForwardChainReasoningPlanner.active = false;
	  }
	  
	  private void initBindButtonsToActionListeners()
	  {
	    mButtonDoIt
	      .addActionListener(mActionListeners.get("BUTTON_DO_IT"));
	    mButtonParseChallenge     
	      .addActionListener(mActionListeners.get("BUTTON_PARSE_CHALLENGE"));
	    mButtonParseServices
	      .addActionListener(mActionListeners.get("BUTTON_PARSE_SERVICES"));
	    mButtonParseTaxonomy
	      .addActionListener(mActionListeners.get("BUTTON_PARSE_TAXONOMY"));
	    mButtonStop
	      .addActionListener(mActionListeners.get("BUTTON_STOP"));
	  }
	  
	  private void initActionListeners()
	  {
	    mActionListeners.put("BUTTON_PARSE_CHALLENGE", 
	      new ActionListener(){
	      public void actionPerformed(ActionEvent iActionEvent)
	      {
	        callButtonParseChallenge();
	      }});
	    
	    mActionListeners.put("BUTTON_PARSE_SERVICES",
	      new ActionListener(){
	      public void actionPerformed(ActionEvent iActionEvent)
	      {
	        callButtonParseServices();
	      }});
	    
	    mActionListeners.put("BUTTON_PARSE_TAXONOMY", 
	      new ActionListener(){
	      public void actionPerformed(ActionEvent iActionEvent)
	      {
	        callButtonParseTaxonomy();
	      }});
	    
	    mActionListeners.put("BUTTON_DO_IT", 
	      new ActionListener(){
	      public void actionPerformed(ActionEvent iActionEvent)
	      {
	        callButtonDoIt();
	      }});
	    
	    mActionListeners.put("BUTTON_STOP",
	      new ActionListener(){
	      public void actionPerformed(ActionEvent iActionEvent)
	      {
	        callButtonStop();
	      }});
	  }
	  
	  class Runner extends Thread
	  {
		  public void run()
		  {
			  int solutionsWanted = 1;
			    if (solutionsWanted > 0
			    		&& null != owlFile
			    		&& null != challengeFile
			    		&& null != serviceFile
			    		&& owlFile.length() > 0
			    		&& challengeFile.length() > 0
			    		&& serviceFile.length() > 0)
			    {
			    	try
			    	{
			    		ForwardChainReasoningPlanner.active = true;
			    		Driver.run(challengeFile, serviceFile, owlFile, solutionsWanted, 1);
			    	}
			    	catch (Exception e)
			    	{
			    		OutputManager.writeToFile("Error: " + e.getMessage());
			    	}
			    }
			    else
			    {
			    	OutputManager.writeToFile("Please Initialize all values first!");
			    }
			    ConceptInstanceMap.reset();
		  }
		  
	  }
  
private String challengeFile = "";
private String serviceFile = "";
private String owlFile = "";
  protected HashMap<String,ActionListener> mActionListeners = 
      new HashMap<String,ActionListener>();
  protected JButton mButtonParseTaxonomy = new JButton("Parse Taxonomy");
  protected JButton mButtonParseChallenge = new JButton("Parse Challenge");
  protected JButton mButtonDoIt = new JButton("[ RUN SEARCH ]");
  protected JButton mButtonParseServices = new JButton("Parse Services");
  protected JButton mButtonStop = new JButton("Stop");
  protected JTextArea mTextOutput = new JTextArea(16,58);
  protected JScrollPane mTextScroll = new JScrollPane(mTextOutput);
  /* From 1 -> 10 */
  protected JLabel mListLabel = new JLabel("Parallelism: ");
  protected JComboBox<String> mListBranching = null;
  
  protected JLabel mSolutionLabel = new JLabel("Num of solutions:");
  protected JComboBox<String> mSolutionCombo = null;
  
  protected GridBagLayout mLayout = new GridBagLayout();
  protected GridBagConstraints mGBConstraints = new GridBagConstraints();

}