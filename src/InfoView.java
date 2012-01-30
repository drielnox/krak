import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoView extends JFrame {

    JLabel programname;
    JLabel author_desc;
    JLabel author;
    JLabel licence_desc;
    JLabel licence;
    JLabel contact_desc;
    JLabel contact;

    public InfoView() {
        super(Messages.getString("InfoView.0")); //$NON-NLS-1$

        programname = new JLabel("CracX V." + Messages.getString("Version")); //$NON-NLS-1$ //$NON-NLS-2$

        JPanel auth = new JPanel();
        auth.setLayout(new GridLayout(1, 2));
        author_desc = new JLabel(Messages.getString("InfoView.2")); //$NON-NLS-1$
        author = new JLabel("Till Riemer"); //$NON-NLS-1$
        auth.add(author_desc);
        auth.add(author);

        JPanel lic = new JPanel();
        lic.setLayout(new GridLayout(1, 2));
        licence_desc = new JLabel(Messages.getString("InfoView.4")); //$NON-NLS-1$
        licence = new JLabel(Messages.getString("InfoView.5")); //$NON-NLS-1$
        lic.add(licence_desc);
        lic.add(licence);

        JPanel con = new JPanel();
        con.setLayout(new GridLayout(1, 2));
        contact_desc = new JLabel(Messages.getString("InfoView.6")); //$NON-NLS-1$
        contact = new JLabel("cracx.sourceforge.net"); //$NON-NLS-1$
        con.add(contact_desc);
        con.add(contact);

        this.setLayout(new GridLayout(4, 1));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(programname);
        add(auth);
        add(lic);
        add(con);

        this.setMinimumSize(new Dimension(600, 100));
        this.setResizable(false);
        this.setVisible(true);
    }
}
