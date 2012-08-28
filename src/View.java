import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * main window view
 */
public class View extends JFrame {

    public static void main(String[] args) {
        new View();
    }

    ContentView optionsView;  
    JPanel searchPanel;        
    BruteforcerMenuBar menubar; 

    /**
     * creates the window
     */
    public View() {
        super(Messages.getString("Version")); //$NON-NLS-1$
        setLayout(new GridLayout(1, 2));
        menubar = new BruteforcerMenuBar();

        // VIEW PANEL ---------------------------
        searchPanel = new JPanel();

        JTextArea logView = new JTextArea();
//        logView.setMinimumSize(new Dimension(100, 200));
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
        this.setLocation(new Point(10, 10));
        this.setJMenuBar(menubar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

}
