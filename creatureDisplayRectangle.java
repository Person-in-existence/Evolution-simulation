import javax.swing.*;
import java.awt.*;

public class creatureDisplayRectangle extends JComponent {
    public Creature creature;
    public int width;
    public int height;
    public String topString;
    public String bottomString1;
    public String bottomString2;
    public int scaleFactor = 1;
    public int red;
    public int green;
    public int blue;
    public int bRed;
    public int bGreen;
    public int bBlue;
    public boolean isSubject;
    public creatureDisplayRectangle(int width, int height, String topString, String bottomString1, String bottomString2, boolean isSubject) {
        this.width = width;
        this.height = height;
        this.topString = topString;
        this.bottomString1 = bottomString1;
        this.bottomString2 = bottomString2;
        this.isSubject = isSubject;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw the rectangle with a border
        Color contrastColour = new Color(255 - bRed, 255 - bGreen, 255-bBlue);
        g2.setColor(contrastColour);
        if (isSubject) {
            g2.setColor(Color.cyan);
            g2.drawRect(8, 28, width+3, height+3);
            g2.drawRect(9, 29, width+2, height+2);
        } else {
            g2.drawRect(10, 30, width, height);
        }
        g2.setColor(new Color(red, green, blue));
        g2.fillRect(11, 31, width - 2, height - 2);

        // Draw the strings
        if (isSubject) {
            g2.setColor(Color.cyan);
        } else {
            g2.setColor(contrastColour);
        }
        g2.drawString(topString, 10, 20);
        g2.drawString(bottomString1, 10, 40 + height);
        g2.drawString(bottomString2, 10, 55 + height);


    }
}
