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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial")
public class ContentView extends JPanel implements Observer{

    /**
     * Action-Listener für ContentView
     * 
     * @author Till Riemer
     */
    class ContentViewListener implements ActionListener, ListSelectionListener, ChangeListener {
        String action; // ActionEvent action

        // Standard Constructor
        public ContentViewListener() {
            this.action = "";
        }

        // Constructor for ActionEvent
        public ContentViewListener(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) { // buttons pressed
            if (action == "start") { // start search //$NON-NLS-1$
                File file = new File(filepath.getText());
                File archive_program = new File(programpath.getText() + program.getSelectedValue() + ".exe"); //$NON-NLS-1$
                if (file.exists() && file.canRead() && file.isFile() && archive_program.exists()) { // wenn zu entpackendes
                                                                                                // Archiv und Packprogramm existiert
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    
                    br.setCharset(charset.getText());
                    br.setMaxPWLength(Integer.parseInt(maxPWLength.getText()));
                    br.setMinPWLength(Integer.parseInt(minPWLength.getText()));
                    br.setHandler(new ProgramHandler(program.getSelectedValue().toString(), programpath.getText(), filepath.getText()));
                    
                    if(dictionaryEnabled.isSelected()){ 
                    	// try dictionary attack first, afterwards bruteforce...
                        try {
                            dictionary.setHandler(new ProgramHandler(program.getSelectedValue().toString(), programpath.getText(), filepath.getText()));
                            dictionary.setDictionary(new LineNumberReader(new FileReader(dictionarypath.getText())));
                            Thread t = new Thread(dictionary);
                            t.start();
                            
                        } catch (FileNotFoundException e) {
                            logView.setText(logView.getText() + Messages.getString("ContentView.9")); //$NON-NLS-1$
                        }
                                               
                    }
                    else{ // only bruteforce
                        Thread t = new Thread(br);
                        t.start();
                    }
                } 
                else if (!file.exists()) { // Archiv existiert nicht
                    logView.setText(logView.getText() + Messages.getString("ContentView.15")); //$NON-NLS-1$
                } 
                else if(!file.isFile()){ // Archiv ist keine gültige Datei
                    logView.setText(logView.getText() + Messages.getString("ContentView.11"));                     //$NON-NLS-1$
                } 
                else if(!file.canRead()){ // keine Leseberechtigung für Archiv
                    logView.setText(logView.getText() + Messages.getString("ContentView.19")); //$NON-NLS-1$
                }
                else if (!archive_program.exists()) { // Packprogramm existiert nicht
                    logView.setText(logView.getText() + Messages.getString("ContentView.17")); //$NON-NLS-1$
                }
            } 
            else if (action == "stop") { // Suche stoppen //$NON-NLS-1$
                br.stop();
                start.setEnabled(true);
                stop.setEnabled(false);
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

        @Override
        public void valueChanged(ListSelectionEvent arg0) { // program selector
            if (!arg0.getValueIsAdjusting()) {
                if (program.getSelectedValue() == "7z") { //$NON-NLS-1$
                    programpath.setText("C:\\Program Files\\7-Zip\\"); //$NON-NLS-1$
                } else if (program.getSelectedValue() == "WinRAR") { //$NON-NLS-1$
                    programpath.setText("C:\\Program Files\\WinRAR\\"); //$NON-NLS-1$
                }
            }
        }
    }

    JTextField maxPWLength; 
    JTextField minPWLength; 
    JTextField charset; 
    
    JCheckBox dictionaryEnabled; // if activated, first dictionary attack, then bruteforce
    JTextField dictionarypath; // txt file including dictionary
    JButton dictBrowse; // button to browse for dictionary file

    JTextField filepath; // file to crack
    JButton fileBrowse; // button to browse for file
    
    JTextField programpath; // program path
    JButton programBrowse; // button to browse for program path
    @SuppressWarnings("rawtypes")
	JList program; // archive program selector

    JButton start;  // start search
    JButton stop;   // stop search

    JTextArea logView;

    BruteForce br;                  // MODEL - bruteforce process
    DictionaryAttack dictionary;    // MODEL - dictionary process

    /**
     * Constructor for content view
     * 
     * @param anzeige2
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ContentView(JTextArea logView) {
        this.logView = logView;
        //setLayout(new GridLayout(1, 2));
        setLayout(new FlowLayout());
        
        br = new BruteForce(logView);
        dictionary = new DictionaryAttack(br, logView);
       
        br.addObserver(this);
        dictionary.addObserver(this);

        // ---------------------------------------------------------------------------------
        JPanel bruteforcePanel = new JPanel();
        bruteforcePanel.setLayout(new GridLayout(3, 1));
        bruteforcePanel.setBorder(new TitledBorder(Messages.getString("ContentView.20"))); //$NON-NLS-1$

        minPWLength = new JTextField(Messages.getString("ContentView.3")); //$NON-NLS-1$
        minPWLength.setColumns(2);
        maxPWLength = new JTextField(Messages.getString("ContentView.5")); //$NON-NLS-1$
        maxPWLength.setColumns(2);
        
        charset = new JTextField("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"); //$NON-NLS-1$
        charset.setToolTipText(Messages.getString("ContentView.8")); //$NON-NLS-1$
        charset.setColumns(15);

        bruteforcePanel.add(new JLabel(Messages.getString("ContentView.2"))); // min pw length desc
        bruteforcePanel.add(minPWLength);
        bruteforcePanel.add(new JLabel(Messages.getString("ContentView.4"))); // max pw length desc
        bruteforcePanel.add(maxPWLength);
        bruteforcePanel.add(new JLabel(Messages.getString("ContentView.6"))); // charset desc
        bruteforcePanel.add(charset);
        // ---------------------------------------------------------------------------------
        // DICTIONARY PANEL - options disabled by default
        // ---------------------------------------------------------------------------------
        JPanel dictionaryPanel = new JPanel();
        dictionaryPanel.setBorder(new TitledBorder(Messages.getString("ContentView.21"))); //$NON-NLS-1$
        dictionaryPanel.setLayout(new GridLayout(5, 1));
        
        dictionaryEnabled = new JCheckBox();
        dictionaryEnabled.addChangeListener(new ContentViewListener());
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
        
        dictionaryPanel.add(new JLabel(Messages.getString("ContentView.22"))); // dictionary enabled desc
        dictionaryPanel.add(dictionaryEnabled);
        dictionaryPanel.add(new JLabel(Messages.getString("ContentView.23"))); // dictionary path desc
        dictionaryPanel.add(dictionarypath);
        dictionaryPanel.add(dictBrowse);
        
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(5, 2));
        generalPanel.setBorder(new TitledBorder(Messages.getString("ContentView.32"))); //$NON-NLS-1$

        String[] programs = { "7z", "WinRAR" }; // selectable programs //$NON-NLS-1$ //$NON-NLS-2$
        program = new JList(programs);
        program.setSelectedIndex(0);
        program.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        program.addListSelectionListener(new ContentViewListener());
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

        programpath = new JTextField("C:\\Program Files\\7-Zip\\"); //$NON-NLS-1$
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

        generalPanel.add(new JLabel(Messages.getString("ContentView.24"))); // program desc
        generalPanel.add(program);
        generalPanel.add(new JLabel(Messages.getString("ContentView.12"))); // program path desc
        generalPanel.add(new JLabel());
        generalPanel.add(programpath);
        generalPanel.add(programBrowse);
        generalPanel.add(new JLabel(Messages.getString("ContentView.10"))); // file path desc
        generalPanel.add(new JLabel());
        generalPanel.add(filepath);
        generalPanel.add(fileBrowse);
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        start = new JButton(Messages.getString("ContentView.16")); //$NON-NLS-1$
        start.addActionListener(new ContentViewListener("start")); //$NON-NLS-1$
        start.setEnabled(true);

        stop = new JButton(Messages.getString("ContentView.18")); //$NON-NLS-1$
        stop.addActionListener(new ContentViewListener("stop")); //$NON-NLS-1$
        stop.setEnabled(false);

        buttons.add(start);
        buttons.add(stop);
        // ---------------------------------------------------------------------------------
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(generalPanel);
        JPanel tmpPanel = new JPanel(new GridLayout(2, 2));
        tmpPanel.add(dictionaryPanel);
        tmpPanel.add(new JLabel());
        tmpPanel.add(bruteforcePanel);
        tmpPanel.add(buttons);
        add(tmpPanel);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.equals("success")){
            start.setEnabled(true);
            stop.setEnabled(false);
        }
        else if(arg.equals("failure")){
            start.setEnabled(true);
            stop.setEnabled(false);
        }
        // TODO: handle non-defined args
        
    }

}
