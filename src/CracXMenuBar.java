import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CracXMenuBar extends JMenuBar {
    JMenu program;
    
    JMenuItem info;
    JMenuItem close;

    JMenu language;
    JMenuItem german;
    JMenuItem english;

    public CracXMenuBar(final JFrame view) {
        program = new JMenu(Messages.getString("BruteforcerMenuBar.0")); //$NON-NLS-1$

        // --------------- Info -------------------------
        info = new JMenuItem(Messages.getString("BruteforcerMenuBar.1")); //$NON-NLS-1$
        info.setIcon(new ImageIcon("icons/info.png"));
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoView();
            }
        });
        program.add(info);
        // ----------------------------------------------
        program.addSeparator();
        // ------------- Quit ------------------------
        close = new JMenuItem(Messages.getString("BruteforcerMenuBar.2")); //$NON-NLS-1$
        close.setIcon(new ImageIcon("icons/exit.png"));
        // close.setIcon(new ImageIcon("icons/exit.jpg"));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //System.exit(0);
                view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
            }
        });
        program.add(close);
        // ----------------------------------------------
        // ---------- Languages --------------------------
        language = new JMenu(Messages.getString("BruteforcerMenuBar.3")); //$NON-NLS-1$
        english = new JMenuItem("English"); //$NON-NLS-1$
        english.setIcon(new ImageIcon("icons/english.png")); //$NON-NLS-1$
        english.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent arg0) 
        	{
        		if(Messages.getLanguage() != "messages")
        		{
        			Messages.setLanguage("messages");
        		}                
            }
        });
        german = new JMenuItem("Deutsch"); //$NON-NLS-1$
        german.setIcon(new ImageIcon("icons/german.png")); //$NON-NLS-1$
        german.addActionListener(new ActionListener(){
        	@Override
            public void actionPerformed(ActionEvent arg0) 
        	{
        		if(Messages.getLanguage() != "messages_de")
        		{
        			Messages.setLanguage("messages_de");
        		}
            }
        });

        language.add(english);
        language.add(german);
        
        add(program);
        add(language);
    }
}