import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
        add(new JLabel(new ImageIcon("info.png")));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 250));
        this.setResizable(false);
        this.setVisible(true);
    }
}
