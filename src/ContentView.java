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
            }
            else{
                dictionarypath.setEnabled(false);
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
    JLabel maxPWLengthdesc;
    JTextField minPWLength; 
    JLabel minPWLengthdesc;
    JTextField charset; 
    JLabel charsetdesc;
    
    JLabel dictionaryEnabledDesc;
    JCheckBox dictionaryEnabled; // if activated, first dictionary attack, then bruteforce
    JLabel dictionarypathdesc;
    JTextField dictionarypath; // txt file including dictionary

    JTextField filepath; // file to crack
    JLabel filepathdesc;
    JTextField programpath; // program path
    JLabel programpathdesc;
    @SuppressWarnings("rawtypes")
	JList program; // program selector
    JLabel programdesc;

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
        setLayout(new GridLayout(4, 1));
        
        br = new BruteForce(logView);
        dictionary = new DictionaryAttack(br, logView);
       
        br.addObserver(this);
        dictionary.addObserver(this);

        // ---------------------------------------------------------------------------------
        JPanel bruteforcePanel = new JPanel();
        bruteforcePanel.setLayout(new GridLayout(3, 1));
        bruteforcePanel.setBorder(new TitledBorder(Messages.getString("ContentView.20"))); //$NON-NLS-1$

        minPWLengthdesc = new JLabel(Messages.getString("ContentView.2")); //$NON-NLS-1$
        minPWLength = new JTextField();
        minPWLength.setColumns(2);
        minPWLength.setText(Messages.getString("ContentView.3")); //$NON-NLS-1$

        maxPWLengthdesc = new JLabel(Messages.getString("ContentView.4")); //$NON-NLS-1$
        maxPWLength = new JTextField();
        maxPWLength.setColumns(2);
        maxPWLength.setText(Messages.getString("ContentView.5")); //$NON-NLS-1$
        
        charsetdesc = new JLabel(Messages.getString("ContentView.6")); //$NON-NLS-1$
        charsetdesc.setToolTipText(Messages.getString("ContentView.7")); //$NON-NLS-1$
        charset = new JTextField();
        charset.setToolTipText(Messages.getString("ContentView.8")); //$NON-NLS-1$
        charset.setColumns(15);
        charset.setText("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"); //$NON-NLS-1$

        bruteforcePanel.add(minPWLengthdesc);
        bruteforcePanel.add(minPWLength);
        bruteforcePanel.add(maxPWLengthdesc);
        bruteforcePanel.add(maxPWLength);
        bruteforcePanel.add(charsetdesc);
        bruteforcePanel.add(charset);
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel dictionaryPanel = new JPanel();
        dictionaryPanel.setBorder(new TitledBorder(Messages.getString("ContentView.21"))); //$NON-NLS-1$
        dictionaryPanel.setLayout(new GridLayout(2,2));
        
        dictionaryEnabledDesc = new JLabel(Messages.getString("ContentView.22")); //$NON-NLS-1$
        dictionaryEnabled = new JCheckBox();
        dictionaryEnabled.addChangeListener(new ContentViewListener());
        dictionarypathdesc = new JLabel(Messages.getString("ContentView.23")); //$NON-NLS-1$
        dictionarypathdesc.setToolTipText(Messages.getString("ContentView.26")); //$NON-NLS-1$
        dictionarypath = new JTextField();
        dictionarypath.setToolTipText(Messages.getString("ContentView.27")); //$NON-NLS-1$
        dictionarypath.setColumns(15);
        dictionarypath.setText("C:\\"); //$NON-NLS-1$
        dictionarypath.setEnabled(false);
        
        dictionaryPanel.add(dictionaryEnabledDesc);
        dictionaryPanel.add(dictionaryEnabled);
        dictionaryPanel.add(dictionarypathdesc);
        dictionaryPanel.add(dictionarypath);
        
        // ---------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(3, 2));
        generalPanel.setBorder(new TitledBorder(Messages.getString("ContentView.32"))); //$NON-NLS-1$

        programdesc = new JLabel(Messages.getString("ContentView.24")); //$NON-NLS-1$
        programdesc.setToolTipText(Messages.getString("ContentView.25")); //$NON-NLS-1$
        String[] programs = { "7z", "WinRAR" }; // selectable programs //$NON-NLS-1$ //$NON-NLS-2$
        program = new JList(programs);
        program.setSelectedIndex(0);
        program.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        program.addListSelectionListener(new ContentViewListener());
        program.setToolTipText(Messages.getString("ContentView.28")); //$NON-NLS-1$
        
        filepathdesc = new JLabel(Messages.getString("ContentView.10")); //$NON-NLS-1$
        filepathdesc.setToolTipText(Messages.getString("ContentView.29")); //$NON-NLS-1$
        filepath = new JTextField();
        filepath.setColumns(15);
        filepath.setToolTipText(Messages.getString("ContentView.30")); //$NON-NLS-1$
        filepath.setText("C:\\"); //$NON-NLS-1$

        programpathdesc = new JLabel(Messages.getString("ContentView.12")); //$NON-NLS-1$
        programpathdesc.setToolTipText(Messages.getString("ContentView.13")); //$NON-NLS-1$
        programpath = new JTextField();
        programpath.setToolTipText(Messages.getString("ContentView.14")); //$NON-NLS-1$
        programpath.setColumns(15);
        programpath.setText("C:\\Program Files\\7-Zip\\"); //$NON-NLS-1$

        generalPanel.add(programdesc);
        generalPanel.add(program);
        generalPanel.add(programpathdesc);
        generalPanel.add(programpath);
        generalPanel.add(filepathdesc);
        generalPanel.add(filepath);
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
        add(dictionaryPanel);
        add(bruteforcePanel);
        add(buttons);
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
