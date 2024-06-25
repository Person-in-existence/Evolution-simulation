import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

public class displayPanelController extends Thread{
    public displayPanel panel;
    public controller parent;
    public JFrame frame = new JFrame();
    public void run() {
        panel = new displayPanel();
        panel.setLayout(null);
        panel.parent = this;
        panel.setSize(500, 500);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setResizable(false);
        URL iconURL = getClass().getResource("/IconCPanel.png");
        // Check if the URL is not null
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        } else {
            System.out.println("IconURLWasNull");
        }
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.control.ePanel.displayPanelButton();
                parent.displayPanel = null;
                parent.displayPanelThread = null;
            }
        });
        frame.setVisible(true);

    }
    public void update(ArrayList<Creature> creatureList) {
        panel.setCreatureList(new ArrayList<>(creatureList));
        panel.repaint();
    }
}
