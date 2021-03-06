import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * main window view
 */
public class View extends JFrame {
	
	static View mainView;

    public static void main(String[] args) {
        mainView = new View();
    }
    
    ContentView optionsView;  
    JPanel searchPanel;        
    CracXMenuBar menubar; 

    /**
     * creates the window
     */
    public View() {
        super(Messages.getString("Version")); //$NON-NLS-1$
        this.setLayout(new GridLayout(2, 1));
        
        menubar = new CracXMenuBar(this);

        // VIEW PANEL ---------------------------
        searchPanel = new JPanel();

        JTextArea logView = new JTextArea();
        logView.setEditable(false);
        logView.setText(Messages.getString("View.1")); //$NON-NLS-1$
        JScrollPane logViewPanel = new JScrollPane(logView);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        searchPanel.add(logViewPanel);
        // ----------------------------------------
        // OPTIONS PANEL --------------------
        optionsView = new ContentView(logView);
        // ----------------------------------------

        add(optionsView);
        add(searchPanel);
        
        this.setLocationByPlatform(true);
        this.setJMenuBar(menubar);
        
        this.addWindowListener(new WindowListener()
        {
            @Override
			public void windowClosing(WindowEvent arg0) {
            	try {
            		// save current data to a text file
            	    PrintWriter writer = new PrintWriter("SavedData.log", "UTF-8");
            	    writer.flush();
            	    writer.println("Program:" + optionsView.program.getSelectedIndex());
            	    writer.println("ProgramPath:" + optionsView.programpath.getText());
            	    writer.println("FilePath:" + optionsView.filepath.getText());
            	    writer.println("UseDict:" + optionsView.dictionaryEnabled.isSelected());
            	    writer.println("DictionaryPath:" + optionsView.dictionarypath.getText());
            	    writer.println("MinLength:" + optionsView.minPWLength.getText());
            	    writer.println("MaxLength:" + optionsView.maxPWLength.getText());
            	    writer.println("Charset:" + optionsView.charset.getText());
            	    writer.println("CustomCommandLine:" + optionsView.customCommandLine.getText());
            	    writer.println("CustomReturnValue:" + optionsView.customReturnValue.getText());
            	    writer.close();
            	} catch (IOException e) {
            	   // just don't save the data
            	}
            	System.exit(0);
			}
            
            @Override
			public void windowOpened(WindowEvent arg0) {				
			}

			@Override
			public void windowActivated(WindowEvent arg0) {				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {				
			}			
        });
        
        this.setIconImage(new ImageIcon("icons/mainIcon.png").getImage());        
        this.setResizable(false);        
        
        // parse in saved data
        Path dataFile = Paths.get("SavedData.log");
	 	Charset dataFileCharset = Charset.forName("UTF-8");
		try 
		{
			List<String> savedData = Files.readAllLines(dataFile, dataFileCharset);
			
			Integer programIndex = new Integer(savedData.get(0).substring(savedData.get(0).indexOf(':') + 1));
			optionsView.program.setSelectedIndex(programIndex);
			
			String programPath = savedData.get(1).substring(savedData.get(1).indexOf(':') + 1);
			optionsView.programpath.setText(programPath);
			
			String filePath = savedData.get(2).substring(savedData.get(2).indexOf(':') + 1);
			optionsView.filepath.setText(filePath);
			
			Boolean useDict = new Boolean(savedData.get(3).substring(savedData.get(3).indexOf(':') + 1));
			optionsView.dictionaryEnabled.setSelected(useDict);
			
			String dictPath = savedData.get(4).substring(savedData.get(4).indexOf(':') + 1);
			optionsView.dictionarypath.setText(dictPath);
			
			String minLength = savedData.get(5).substring(savedData.get(5).indexOf(':') + 1);
			optionsView.minPWLength.setText(minLength);
			
			String maxLength = savedData.get(6).substring(savedData.get(6).indexOf(':') + 1);
			optionsView.maxPWLength.setText(maxLength);
			
			String charset = savedData.get(7).substring(savedData.get(7).indexOf(':') + 1);
			optionsView.charset.setText(charset);
			
			String customCommandLine = savedData.get(8).substring(savedData.get(8).indexOf(':') + 1);
			optionsView.customCommandLine.setText(customCommandLine);
			
			String customReturnValue = savedData.get(9).substring(savedData.get(9).indexOf(':') + 1);
			optionsView.customReturnValue.setText(customReturnValue);
		} 
		catch (Exception e) 
		{
			// just use default settings
		}	
		
		optionsView.UpdateTotalCombinationsDisplay();
		
		this.setSize(1200,600);
        //this.pack();
        this.setVisible(true);
    }

}
