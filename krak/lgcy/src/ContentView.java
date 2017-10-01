import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial")
public class ContentView extends JPanel implements Observer{

	/**
     * Action-Listener für ContentView
     */
    class ContentViewListener implements ActionListener, ChangeListener {

        @Override
        public void actionPerformed(ActionEvent arg0) { // buttons pressed
            if (arg0.getSource() == start) 
            {
            	File file = new File(filepath.getText());
                File archive_program = new File(programpath.getText() + File.separator + program.getSelectedItem().toString() + ".exe"); //$NON-NLS-1$
                
                boolean customCommand = program.getSelectedIndex() == 2;
                
                if (!customCommand && !file.exists()) { // archive not found
                    logView.setText(logView.getText() + Messages.getString("ContentView.15")); //$NON-NLS-1$
                } 
                else if(!customCommand && !file.isFile()){ // archive not a valid file
                    logView.setText(logView.getText() + Messages.getString("ContentView.11")); //$NON-NLS-1$
                } 
                else if(!customCommand && !file.canRead()){ // no read access on archive file
                    logView.setText(logView.getText() + Messages.getString("ContentView.19")); //$NON-NLS-1$
                }
                else if (!customCommand && !archive_program.exists()) { // archive software not found
                    logView.setText(logView.getText() + Messages.getString("ContentView.17")); //$NON-NLS-1$
                }                
                else // if archive and software found
                { 
                	logView.setText("");
                	ChangeUIStateSearch();
                	
                	int commandCheck = 1;
                	if(customCommand)
                	{
                		try {
                			commandCheck = new Integer(Integer.parseInt(customReturnValue.getText()));
                    	}
                    	catch (NumberFormatException e) {
                    		logView.setText(logView.getText() + Messages.getString("ContentView.42")); //$NON-NLS-1$
                    		return;
                    	}
                	}
                	
                	String handlerProgramPath = customCommand ? customCommandLine.getText() : programpath.getText();
                    
                	ProgramHandler handler = new ProgramHandler(program.getSelectedIndex(), handlerProgramPath, filepath.getText());
                	
                	br.Initialize(charset.getText(), Integer.parseInt(minPWLength.getText()), Integer.parseInt(maxPWLength.getText()), 
                			commandCheck, handler);
                    
                    if(dictionaryEnabled.isSelected())
                    { 
                    	// try dictionary attack first, afterwards bruteforce...
                        try 
                        {
                        	dictionary.Initialize(handler, new LineNumberReader(new FileReader(dictionarypath.getText())), commandCheck);
                            Thread t = new Thread(dictionary);
                            t.start();
                            
                        } catch (FileNotFoundException e) {
                            logView.setText(logView.getText() + Messages.getString("ContentView.9")); //$NON-NLS-1$
                            ChangeUIStateConfigure();
                        }
                                               
                    }
                    else{ // only bruteforce
                        Thread t = new Thread(br);
                        t.start();
                    }
                } 
                
            } 
            else if (arg0.getSource() == stop) {
                br.stop();
                ChangeUIStateConfigure();
            }
            else if(arg0.getSource() == program)
            {
            	if (program.getSelectedIndex() == 0) { // 7z
                    programpath.setText("C:\\Program Files\\7-Zip"); //$NON-NLS-1$                    
                } 
            	else if (program.getSelectedIndex() == 1) { // WinRAR
                    programpath.setText("C:\\Program Files\\WinRAR"); //$NON-NLS-1$
                }
            	else if(program.getSelectedIndex() == 2) { // custom
            		programpath.setText(""); //$NON-NLS-1$
            	}
            	
            	programpath.setEnabled(program.getSelectedIndex() != 2);
            	programBrowse.setEnabled(program.getSelectedIndex() != 2);
            	fileBrowse.setEnabled(program.getSelectedIndex() != 2);
                filepath.setEnabled(program.getSelectedIndex() != 2);
            	customCommandLine.setEnabled(program.getSelectedIndex() == 2);
            	customReturnValue.setEnabled(program.getSelectedIndex() == 2);
            }
        }

        @Override
        public void stateChanged(ChangeEvent arg0) { // toggle dictionary enable
        	
        	if(dictionaryEnabled.isSelected()){
                dictionarypath.setEnabled(true);
                dictBrowse.setEnabled(true);
            }
            else{
                dictionarypath.setEnabled(false);
                dictBrowse.setEnabled(false);
            }
        }
    }

    JTextField maxPWLength; 
    JTextField minPWLength; 
    
    JTextField charset; 
    JButton charsetResetButton;
    
    JLabel totalCombinationsDisplay;
    
    JCheckBox dictionaryEnabled; // if activated, first dictionary attack, then bruteforce
    JTextField dictionarypath; // txt file including dictionary
    JButton dictBrowse; // button to browse for dictionary file

    JTextField filepath; // file to crack
    JButton fileBrowse; // button to browse for file
    
    JTextField programpath; // program path
    JButton programBrowse; // button to browse for program path
    @SuppressWarnings("rawtypes")
	JComboBox program; // archive program selector
    
    JTextField customCommandLine; // command line if choosing "custom"
    JTextField customReturnValue; // return value to check if choosing "custom"

    JButton start;  // start search
    JButton stop;   // stop search

    JTextArea logView;

    BruteForce br;                  // MODEL - bruteforce process
    DictionaryAttack dictionary;    // MODEL - dictionary process
    
    static final String defaultCharset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Constructor for content view
     * 
     * @param anzeige2
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ContentView(JTextArea logView) {
        this.logView = logView;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        br = new BruteForce(logView);
        br.addObserver(this);
        
        dictionary = new DictionaryAttack(br, logView);        
        dictionary.addObserver(this);

        // ---------------------------------------------------------------------------------
        // CHARSET SELECTION
        // ---------------------------------------------------------------------------------
        JPanel bruteforcePanel = new JPanel(new GridLayout(3, 1));
        bruteforcePanel.setBorder(new TitledBorder(Messages.getString("ContentView.20"))); //$NON-NLS-1$

        minPWLength = new JTextField(Messages.getString("ContentView.3")); //$NON-NLS-1$
        minPWLength.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UpdateTotalCombinationsDisplay();
			}        	
		});
        minPWLength.setColumns(2);
        
        maxPWLength = new JTextField(Messages.getString("ContentView.5")); //$NON-NLS-1$
        maxPWLength.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UpdateTotalCombinationsDisplay();
			}        	
		});
        maxPWLength.setColumns(2);
        
        charset = new JTextField(defaultCharset); //$NON-NLS-1$
        charset.setToolTipText(Messages.getString("ContentView.8")); //$NON-NLS-1$
        charset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UpdateTotalCombinationsDisplay();
			}        	
		});
        charset.setColumns(15);
        
        charsetResetButton = new JButton(Messages.getString("ContentView.34")); //$NON-NLS-1$
        charsetResetButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		charset.setText(defaultCharset);
        	}
        });    

        JPanel pwLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pwLengthPanel.add(new JLabel(Messages.getString("ContentView.2"))); // min pw length desc
        pwLengthPanel.add(minPWLength);        
        pwLengthPanel.add(new JLabel(Messages.getString("ContentView.4"))); // max pw length desc
        pwLengthPanel.add(maxPWLength);
        bruteforcePanel.add(pwLengthPanel);
        
        JPanel charsetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));        
        charsetPanel.add(new JLabel(Messages.getString("ContentView.6"))); // charset desc
        charsetPanel.add(charset);        
        charsetPanel.add(charsetResetButton);
        bruteforcePanel.add(charsetPanel);        
        
        JPanel bfStatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalCombinationsDisplay = new JLabel();
        bfStatsPanel.add(totalCombinationsDisplay);
        bruteforcePanel.add(bfStatsPanel);
        
        bruteforcePanel.setPreferredSize(new Dimension(390,150));
        
        // ---------------------------------------------------------------------------------
        // DICTIONARY PANEL - options disabled by default
        // ---------------------------------------------------------------------------------
        JPanel dictionaryPanel = new JPanel();
        dictionaryPanel.setBorder(new TitledBorder(Messages.getString("ContentView.21"))); //$NON-NLS-1$
        dictionaryPanel.setLayout(new GridLayout(2, 1));
        
        dictionaryEnabled = new JCheckBox();
        dictionaryEnabled.addChangeListener(new ContentViewListener());
        JPanel dictTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dictTopPanel.add(new JLabel(Messages.getString("ContentView.22"))); // dictionary enabled desc
        dictTopPanel.add(dictionaryEnabled);        
        dictionaryPanel.add(dictTopPanel);    
        
        dictionarypath = new JTextField("C:\\"); //$NON-NLS-1$
        dictionarypath.setToolTipText(Messages.getString("ContentView.27")); //$NON-NLS-1$
        dictionarypath.setColumns(10);
        dictionarypath.setEnabled(false);
        dictBrowse = new JButton("Browse");
        dictBrowse.setEnabled(false);
        dictBrowse.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		JFileChooser chooser = new JFileChooser();
        		chooser.addChoosableFileFilter(new FileFilter() {
        		    public boolean accept(File f) {
        		      if (f.isDirectory()) return true;
        		      return f.getName().toLowerCase().endsWith(".txt");
        		    }
        		    public String getDescription () { return "TXTs"; }  
        		  });
        		  chooser.setMultiSelectionEnabled(false);
        		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    dictionarypath.setText(chooser.getSelectedFile().getAbsolutePath());
                }
			}
        }); 
        
        JPanel dictPathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dictPathPanel.add(new JLabel(Messages.getString("ContentView.23"))); // dictionary path desc
        dictPathPanel.add(dictionarypath);
        dictPathPanel.add(dictBrowse);
        dictionaryPanel.add(dictPathPanel);
        
        dictionaryPanel.setPreferredSize(new Dimension(350,150));
        
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(3, 1));
        generalPanel.setBorder(new TitledBorder(Messages.getString("ContentView.32"))); //$NON-NLS-1$

        String[] programs = { "7z", "WinRAR", "Custom" }; // selectable programs //$NON-NLS-1$ //$NON-NLS-2$
        program = new JComboBox(programs);
        program.setSelectedIndex(0);
       // program.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        program.addActionListener(new ContentViewListener());
        program.setToolTipText(Messages.getString("ContentView.28")); //$NON-NLS-1$
        
        filepath = new JTextField("C:\\"); //$NON-NLS-1$
        filepath.setColumns(10);
        filepath.setToolTipText(Messages.getString("ContentView.30")); //$NON-NLS-1$
        fileBrowse = new JButton("Browse");
        fileBrowse.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		JFileChooser chooser = new JFileChooser();
        		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    filepath.setText(chooser.getSelectedFile().getAbsolutePath());
                }
			}
        });

        programpath = new JTextField("C:\\Program Files\\7-Zip"); //$NON-NLS-1$
        programpath.setToolTipText(Messages.getString("ContentView.14")); //$NON-NLS-1$
        programpath.setColumns(10);
        programBrowse = new JButton("Browse");
        programBrowse.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		JFileChooser chooser = new JFileChooser();
        		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		chooser.setMultiSelectionEnabled(false);
        		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    programpath.setText(chooser.getSelectedFile().getAbsolutePath());
                }
			}
        });

        JPanel programPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        programPanel.add(new JLabel(Messages.getString("ContentView.24"))); // program desc
        programPanel.add(program);
        generalPanel.add(programPanel);
        
        JPanel programPathPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        programPathPanel.add(new JLabel(Messages.getString("ContentView.12"))); // program path desc
        //programPathPanel.add(new JLabel());
        programPathPanel.add(programpath);
        programPathPanel.add(programBrowse);
        generalPanel.add(programPathPanel);
        
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.add(new JLabel(Messages.getString("ContentView.10"))); // file path desc
        filePanel.add(filepath);
        filePanel.add(fileBrowse);
        generalPanel.add(filePanel);
        
        generalPanel.setPreferredSize(new Dimension(375,150));
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setBorder(new TitledBorder(Messages.getString("ContentView.36"))); //$NON-NLS-1$

        start = new JButton(Messages.getString("ContentView.16")); //$NON-NLS-1$
        start.addActionListener(new ContentViewListener()); //$NON-NLS-1$
        start.setEnabled(true);

        stop = new JButton(Messages.getString("ContentView.18")); //$NON-NLS-1$
        stop.addActionListener(new ContentViewListener()); //$NON-NLS-1$
        stop.setEnabled(false);

        buttons.add(start);
        buttons.add(stop);
        buttons.setPreferredSize(new Dimension(150,100));
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        
        JPanel customPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customCommandLine = new JTextField();
        customCommandLine.setPreferredSize(new Dimension(150,20));
        customCommandLine.setToolTipText(Messages.getString("ContentView.37"));
        customReturnValue = new JTextField("0");
        customReturnValue.setPreferredSize(new Dimension(150,20));
        customReturnValue.setToolTipText(Messages.getString("ContentView.38"));
        customPanel.add(new JLabel(Messages.getString("ContentView.39")));
        customPanel.add(customCommandLine);
        customPanel.add(new JLabel(Messages.getString("ContentView.40")));
        customPanel.add(customReturnValue);
        customPanel.setBorder(new TitledBorder(Messages.getString("ContentView.41"))); //$NON-NLS-1$
        customPanel.setPreferredSize(new Dimension(325,100));
        
        add(generalPanel);        
        add(dictionaryPanel);
        add(bruteforcePanel); 
        add(customPanel);
        add(buttons);
        
        setVisible(true);
        
        ChangeUIStateConfigure();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.equals("success") || arg.equals("failure"))
        {
        	ChangeUIStateConfigure();
        }        
    }

    void ChangeUIStateSearch()
    {
    	start.setEnabled(false);
        stop.setEnabled(true);
        dictBrowse.setEnabled(false);
        dictionarypath.setEnabled(false);
        dictionaryEnabled.setEnabled(false);
        charsetResetButton.setEnabled(false);
        charset.setEnabled(false);
        program.setEnabled(false);        
        programpath.setEnabled(false);
        programBrowse.setEnabled(false);
        fileBrowse.setEnabled(false);
        filepath.setEnabled(false);
        maxPWLength.setEnabled(false);
        minPWLength.setEnabled(false);   
        customCommandLine.setEnabled(false);
        customReturnValue.setEnabled(false);
    }
    
    void ChangeUIStateConfigure()
    {
    	start.setEnabled(true);
        stop.setEnabled(false);
        dictBrowse.setEnabled(dictionaryEnabled.isSelected());
        dictionarypath.setEnabled(dictionaryEnabled.isSelected());
        dictionaryEnabled.setEnabled(true);
        charsetResetButton.setEnabled(true);
        charset.setEnabled(true);
        program.setEnabled(true);        
        programpath.setEnabled(program.getSelectedIndex() != 2);
        programBrowse.setEnabled(program.getSelectedIndex() != 2);
        fileBrowse.setEnabled(program.getSelectedIndex() != 2);
        filepath.setEnabled(program.getSelectedIndex() != 2);
        maxPWLength.setEnabled(true);
        minPWLength.setEnabled(true);
        customCommandLine.setEnabled(program.getSelectedIndex() == 2);
        customReturnValue.setEnabled(program.getSelectedIndex() == 2);
        
        UpdateTotalCombinationsDisplay();
    }
    
    public static long MinMaxFactorial(int min, int max, int N)
    {
        long multi = 1;
        for (int i = min; i <= max; i++) {
            multi += Math.pow(N, i);
        }
        return multi;
    }
    
    public void UpdateTotalCombinationsDisplay()
    {
    	Long totalCombinations = MinMaxFactorial(new Integer(minPWLength.getText()), new Integer(maxPWLength.getText()), charset.getText().length());
    	totalCombinationsDisplay.setText(Messages.getString("ContentView.35").replace("[x]", totalCombinations.toString())); //$NON-NLS-1$
    }
}
