
/*
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class displayPanel extends JPanel{
    public displayPanelController parent;
    public ArrayList<Creature> creatureList = new ArrayList<>();
    public final int creatureScaleFactor = 5;
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        this.removeAll();
        if (parent != null) {
            g.setColor(new Color(parent.parent.redComp, parent.parent.greenComp, parent.parent.blueComp));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (parent != null) {
            for (Creature creature : creatureList) {
                if (Math.round(creature.health) > 0) {
                    creatureDisplayRectangle creatureRectangle = new creatureDisplayRectangle(Math.round(creature.health * creatureScaleFactor), Math.round(creature.health * creatureScaleFactor), creature.name, creature.heat + " Heat", creature.stomachFill + " Stomach Fill", parent.parent.control.cPanel.subject == creature);
                    creatureRectangle.bRed = parent.parent.redComp;
                    creatureRectangle.bGreen = parent.parent.greenComp;
                    creatureRectangle.bBlue = parent.parent.blueComp;
                    creatureRectangle.red = creature.red;
                    creatureRectangle.green = creature.green;
                    creatureRectangle.blue = creature.blue;


                    creatureRectangle.setBounds(creature.x, creature.y, 5 * creatureScaleFactor + 100, 5 * creatureScaleFactor + 100);
                    this.add(creatureRectangle);
                    creatureRectangle.repaint();
                }
            }
        }
    }
    public void setCreatureList(ArrayList<Creature> newCreatureList) {
        creatureList = newCreatureList;
    }
}
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

class displayPanel extends JPanel{
    public displayPanelController parent;
    public ArrayList<Creature> creatureList = new ArrayList<>();
    public final int creatureScaleFactor = 5;

    public displayPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (parent != null) {
                    for (Component component : getComponents()) {
                        if (component instanceof creatureDisplayRectangle) {
                            creatureDisplayRectangle rectangle = (creatureDisplayRectangle) component;
                            if (rectangle.getBounds().contains(e.getPoint())) {
                                System.out.println("Clicked on creature: " + rectangle.creature);
                                parent.parent.control.cPanel.displayPanelUpdate(rectangle.creature);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        this.removeAll();
        if (parent != null) {
            g.setColor(new Color(parent.parent.redComp, parent.parent.greenComp, parent.parent.blueComp));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (parent != null) {
            for (Creature creature : creatureList) {
                if (Math.round(creature.health) > 0) {
                    creatureDisplayRectangle creatureRectangle = new creatureDisplayRectangle(Math.round(creature.health * creatureScaleFactor), Math.round(creature.health * creatureScaleFactor), creature.name, new BigDecimal(creature.heat.toString()).setScale(2, RoundingMode.HALF_UP) + " Heat", new BigDecimal(creature.stomachFill.toString()).setScale(2, RoundingMode.HALF_UP) + " Stomach Fill", parent.parent.control.cPanel.subject == creature);
                    creatureRectangle.bRed = parent.parent.redComp;
                    creatureRectangle.bGreen = parent.parent.greenComp;
                    creatureRectangle.bBlue = parent.parent.blueComp;
                    creatureRectangle.red = creature.red;
                    creatureRectangle.green = creature.green;
                    creatureRectangle.blue = creature.blue;
                    creatureRectangle.creature = creature;
                    creatureRectangle.setBounds(creature.x, creature.y, 5 * creatureScaleFactor + 100, 5 * creatureScaleFactor + 100);
                    this.add(creatureRectangle);
                    creatureRectangle.repaint();
                }
            }
        }
    }

    public void setCreatureList(ArrayList<Creature> newCreatureList) {
        creatureList = newCreatureList;
    }
}
