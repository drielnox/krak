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
 * Graphische Darstellung des Hauptfensters
 */
public class View extends JFrame {

    public static void main(String[] args) {
        new View();
        

    }

    ContentView einstellungen;  // Panel für Voreinstellungen
    JPanel anzeigePanel;        // Anzeige des Suchvorgangs
    BruteforcerMenuBar menubar; // Menüleiste

    /**
     * erzeugt das Fenster
     */
    public View() {
        super(Messages.getString("Version")); //$NON-NLS-1$
        setLayout(new GridLayout(1, 2));
        menubar = new BruteforcerMenuBar();

        // ANZEIGEPANEL ---------------------------
        anzeigePanel = new JPanel();

        JTextArea anzeige = new JTextArea();
//        anzeige.setMinimumSize(new Dimension(100, 200));
        anzeige.setEditable(false);
        anzeige.setText(Messages.getString("View.1")); //$NON-NLS-1$
        JScrollPane anzeigePane = new JScrollPane(anzeige);
        anzeigePanel.setLayout(new BorderLayout());
        anzeigePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        anzeigePanel.add(anzeigePane);
        // ----------------------------------------
        // Einstellungen-Panel --------------------
        einstellungen = new ContentView(anzeige);
        // ----------------------------------------

        add(einstellungen);
        add(anzeigePanel);
        this.setLocation(new Point(10, 10));
        this.setJMenuBar(menubar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

}
