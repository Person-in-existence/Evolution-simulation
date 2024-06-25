import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.security.SignatureException;
import java.util.ArrayList;

public class creaturePanel extends Thread  {
    public static int VIEW = 0;
    public static int EDIT = 1;
    public controlPanel parent;
    public Creature subject;
    public JFrame window;
    public JPanel panel;
    public JLabel subjectNameLabel;
    public JComboBox<String> selectionBox;
    public JLabel modeDisplay;
    public JPanel creaturePane;
    public int mode = VIEW;
    public JButton newCreatureButton;
    public NumberInputField heatLossField;
    public NumberInputField heatGainField;
    public NumberInputField heatField;
    public NumberInputField redField;
    public NumberInputField greenField;
    public NumberInputField blueField;
    public NumberInputField greedField;
    public NumberInputField stomachSizeField;
    public NumberInputField stomachEfficiencyField;
    public NumberInputField stomachFillField;
    public NumberInputField baseReproductionChanceField;
    public NumberInputField ferocityField;

    public JTextField nameField;
    public JLabel heatLossLabel;
    public JLabel heatGainLabel;
    public JLabel heatLabel;
    public JLabel redLabel;
    public JLabel greenLabel;
    public JLabel blueLabel;
    public JLabel greedLabel;
    public JLabel stomachSizeLabel;
    public JLabel stomachEfficiencyLabel;
    public JLabel stomachFillLabel;
    public JLabel baseReproductionChanceLabel;
    public JLabel ferocityLabel;
    public JLabel healthLabel;
    public JButton saveExit;
    public JButton noSaveExit;
    public JButton editCreature;
    public boolean creating = false;
    public JLabel numberOfCreatures;
    public JButton copySettings;
    public JButton pasteSettings;

    public ArrayList<Creature> localCreatures = new ArrayList<>();
    public void run() {
        localCreatures.addAll(parent.parent.getCreatures());
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }



        window = new JFrame();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(600, screenSize.height-100);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.ePanel.creaturePanelButton();
                parent.cPanel = null;
            }
        });
        URL iconURL = getClass().getResource("/IconCPanel.png");
        // Check if the URL is not null
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            window.setIconImage(icon.getImage());
        } else {
            System.out.println("IconURLWasNull");
        }
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Subject name
        constraints.gridy = 0;
        constraints.gridx = 0;
        subjectNameLabel = new JLabel();
        subjectNameLabel.setText("No Creature Selected...");
        panel.add(subjectNameLabel, constraints);

        // Creature selection menu
        constraints.gridx = 1;
        ArrayList<String> creatureNameList = getCreatureNameList();
        // Create a JComboBox and add items to it
        selectionBox = new JComboBox<>(creatureNameList.toArray(new String[0]));
        selectionBox.addActionListener(event -> update(true));
        panel.add(selectionBox, constraints);
        // Create the mode label
        constraints.gridx = 2;
        modeDisplay = new JLabel();
        // modeDisplay has 3 tabs before it, for padding.
        modeDisplay.setText("           View Mode");
        panel.add(modeDisplay, constraints);

        // Creatures Stats:
        constraints.gridy = 1;
        constraints.gridx = 0;
        numberOfCreatures = new JLabel(localCreatures.size() + " Creatures");
        panel.add(numberOfCreatures, constraints);


        //creaturePane for mode functions
        creaturePane = new JPanel(new GridBagLayout());
        constraints.gridy = 2;
        constraints.gridx = 1;
        panel.add(creaturePane, constraints);
        constraints.gridy = 3;
        constraints.weighty = 1;
        panel.add(new JPanel(), constraints);
        // Create the "New creature" button

        addNewCreatureButton();
        panel.revalidate();


        window.getContentPane().add(panel);
        window.setTitle("Creature Panel");
        window.setVisible(true);
    }
    public void update(boolean SelectionChanged) {
        System.out.println("creaturePanel.update() Called");
        System.out.println(parent.parent.getCreatures());
        System.out.println(localCreatures);
        if (selectionBox.getActionListeners().length > 0) {
            selectionBox.removeActionListener(selectionBox.getActionListeners()[0]);
        }
        if (subject==null||SelectionChanged||subject.health < 0) {
            if (selectionBox.getItemCount() > 0) {
                int subjectIndex = selectionBox.getSelectedIndex();
                if (subjectIndex < localCreatures.size()) {
                    subject = localCreatures.get(subjectIndex);
                }
                if (mode == VIEW) {
                    ViewMode();
                }
            }
        } else {
            if ((!parent.parent.getCreatures().contains(subject) & !creating)) {
                subject = null;
                ViewMode();
            }
            selectionBox.removeAllItems();
            ArrayList<String> list = getCreatureNameList();
            System.out.println(parent.parent.getCreatures());
            System.out.println(list);
            for (String s : list) {
                selectionBox.addItem(s);
            }
            localCreatures.clear();
            localCreatures.addAll(parent.parent.getCreatures());
            if (parent.parent.getCreatures().contains(subject)) {
                System.out.println(localCreatures.indexOf(subject));
                selectionBox.setSelectedIndex(parent.parent.getCreatures().indexOf(subject));
            }


        }
        if (subject == null) {
            subjectNameLabel.setText("No Creature Selected...");
        } else {
            subjectNameLabel.setText(subject.name);
            if (mode == VIEW) {
                updateText();
            }
        }
        if (selectionBox.getActionListeners().length == 0) {
            selectionBox.addActionListener(event -> update(true));
        }
        creaturePane.revalidate();
        creaturePane.repaint();
        panel.revalidate();
        panel.repaint();
    }
    public void ViewMode() {
        mode = VIEW;
        if (subject != null) {
            subjectNameLabel.setText(subject.name);
        } else {
            subjectNameLabel.setText("No Creature Selected...");
        }
        creaturePane.removeAll();
        selectionBox.setEnabled(true);
        modeDisplay.setText("           View Mode");
        addNewCreatureButton();
        if (subject != null) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weighty = 0;
            constraints.gridy = 0;
            constraints.gridx = 0;
            // Health
            JLabel healthLabelLabel = new JLabel("Health (/5): ");
            creaturePane.add(healthLabelLabel, constraints);
            constraints.gridx = 1;
            healthLabel = new JLabel(String.valueOf(new BigDecimal(subject.health.toString()).setScale(2, RoundingMode.HALF_UP)));
            creaturePane.add(healthLabel, constraints);

            // Stomach Fill
            constraints.gridx = 0;
            constraints.gridy = 1;
            JLabel stomachFillLabelLabel = new JLabel("Stomach Fill: ");
            creaturePane.add(stomachFillLabelLabel, constraints);
            constraints.gridx = 1;
            stomachFillLabel = new JLabel(String.valueOf(new BigDecimal(subject.stomachFill.toString()).setScale(2, RoundingMode.HALF_UP)));
            creaturePane.add(stomachFillLabel, constraints);

            // Heat
            constraints.gridx = 0;
            constraints.gridy = 2;
            JLabel heatLabelLabel = new JLabel("Heat: ");
            creaturePane.add(heatLabelLabel, constraints);
            constraints.gridx = 1;
            heatLabel = new JLabel(String.valueOf(new BigDecimal(subject.heat.toString()).setScale(2, RoundingMode.HALF_UP)));
            creaturePane.add(heatLabel, constraints);

            // Heat Gain
            constraints.gridy = 3;
            constraints.gridx = 0;
            JLabel heatGainLabelLabel = new JLabel("Heat Gain: ");
            creaturePane.add(heatGainLabelLabel, constraints);
            constraints.gridx = 1;
            heatGainLabel = new JLabel(String.valueOf(subject.heatGain));
            creaturePane.add(heatGainLabel, constraints);

            // Heat Loss
            constraints.gridx = 0;
            constraints.gridy = 4;
            JLabel heatLossLabelLabel = new JLabel("Heat Loss: ");
            creaturePane.add(heatLossLabelLabel, constraints);
            constraints.gridx = 1;
            heatLossLabel = new JLabel(String.valueOf(subject.heatLoss));
            creaturePane.add(heatLossLabel, constraints);

            // Red
            constraints.gridx = 0;
            constraints.gridy = 5;
            JLabel redLabelLabel = new JLabel("Red: ");
            creaturePane.add(redLabelLabel, constraints);
            constraints.gridx = 1;
            redLabel = new JLabel(String.valueOf(subject.red));
            creaturePane.add(redLabel, constraints);

            // Green
            constraints.gridx = 0;
            constraints.gridy = 6;
            JLabel greenLabelLabel = new JLabel("Green: ");
            creaturePane.add(greenLabelLabel, constraints);
            constraints.gridx = 1;
            greenLabel = new JLabel(String.valueOf(subject.green));
            creaturePane.add(greenLabel, constraints);

            // Blue
            constraints.gridx = 0;
            constraints.gridy = 7;
            JLabel blueLabelLabel = new JLabel("Blue: ");
            creaturePane.add(blueLabelLabel, constraints);
            constraints.gridx = 1;
            blueLabel = new JLabel(String.valueOf(subject.blue));
            creaturePane.add(blueLabel, constraints);

            // Greed
            constraints.gridy = 8;
            constraints.gridx = 0;
            JLabel greedLabelLabel = new JLabel("Greed: ");
            creaturePane.add(greedLabelLabel, constraints);
            constraints.gridx = 1;
            greedLabel = new JLabel(String.valueOf(subject.greed));
            creaturePane.add(greedLabel, constraints);

            // Stomach Size
            constraints.gridx = 0;
            constraints.gridy = 9;
            JLabel stomachSizeLabelLabel = new JLabel("Stomach Size: ");
            creaturePane.add(stomachSizeLabelLabel, constraints);
            constraints.gridx = 1;
            stomachSizeLabel = new JLabel(String.valueOf(subject.stomachSize));
            creaturePane.add(stomachSizeLabel, constraints);

            // Stomach Efficiency
            constraints.gridx = 0;
            constraints.gridy = 10;
            JLabel stomachEfficiencyLabelLabel = new JLabel("Stomach Efficiency: ");
            creaturePane.add(stomachEfficiencyLabelLabel, constraints);
            constraints.gridx = 1;
            stomachEfficiencyLabel = new JLabel(String.valueOf(subject.stomachEfficiency));
            creaturePane.add(stomachEfficiencyLabel, constraints);

            // Base Reproduction Chance
            constraints.gridx = 0;
            constraints.gridy = 11;
            JLabel baseReproductionChanceLabelLabel = new JLabel("Base Reproduction Chance: ");
            creaturePane.add(baseReproductionChanceLabelLabel, constraints);
            constraints.gridx = 1;
            baseReproductionChanceLabel = new JLabel(String.valueOf(subject.baseReproductionChance));
            creaturePane.add(baseReproductionChanceLabel, constraints);

            // Ferocity
            constraints.gridx = 0;
            constraints.gridy = 12;
            JLabel ferocityLabelLabel = new JLabel("Ferocity: ");
            creaturePane.add(ferocityLabelLabel, constraints);
            constraints.gridx = 1;
            ferocityLabel = new JLabel(String.valueOf(subject.ferocity));
            creaturePane.add(ferocityLabel, constraints);

            constraints.gridy = 13;
            constraints.gridx = 0;
            constraints.weighty = 1;
            creaturePane.add(new JPanel(), constraints);

            // Button to edit the current creature
            constraints.gridy = 14;
            constraints.gridx = 0;
            constraints.weighty = 0;
            editCreature = new JButton("Edit This Creature");
            editCreature.addActionListener(e->{creating = false; EditMode();});
            creaturePane.add(editCreature, constraints);

            creaturePane.revalidate();
            creaturePane.repaint();
        }
    }
    public void EditMode() {
        mode = EDIT;
        modeDisplay.setText("           Edit Mode");
        creaturePane.removeAll();
        removeNewCreatureButton();
        selectionBox.setEnabled(false);
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension minimumFieldSize = new Dimension(100, 25);



        // Name
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel nameFieldLabel = new JLabel("Creature Name: ");
        creaturePane.add(nameFieldLabel, constraints);
        constraints.gridx = 1;
        nameField = new JTextField(environmentPanel.numberInputFieldColumns);
        nameField.setMinimumSize(minimumFieldSize);
        if (subject.name != null) {
            nameField.setText(subject.name);
        }
        creaturePane.add(nameField, constraints);

        // Heat loss
        constraints.gridx = 0;
        constraints.gridy = 1;

        JLabel heatLossFieldLabel = new JLabel("Heat Loss: ");
        creaturePane.add(heatLossFieldLabel, constraints);
        constraints.gridx = 1;
        heatLossField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        heatLossField.setText(String.valueOf(subject.heatLoss));
        heatLossField.setMinimumSize(minimumFieldSize);
        creaturePane.add(heatLossField, constraints);

        // Heat Gain
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel heatGainFieldLabel = new JLabel("Heat Gain");
        creaturePane.add(heatGainFieldLabel, constraints);
        constraints.gridx = 1;
        heatGainField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        heatGainField.setText(String.valueOf(subject.heatGain));
        heatGainField.setMinimumSize(minimumFieldSize);
        creaturePane.add(heatGainField, constraints);

        // Heat
        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel heatFieldLabel = new JLabel("Heat (0-50): ");
        creaturePane.add(heatFieldLabel, constraints);
        constraints.gridx = 1;
        heatField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        heatField.setText(String.valueOf(subject.heat));
        heatField.setMinimumSize(minimumFieldSize);
        creaturePane.add(heatField, constraints);

        // Red
        constraints.gridx = 0;
        constraints.gridy = 4;
        JLabel redFieldLabel = new JLabel("Red (0-255): ");
        creaturePane.add(redFieldLabel, constraints);
        constraints.gridx = 1;
        redField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        redField.setText(String.valueOf(subject.red));
        redField.setMinimumSize(minimumFieldSize);
        creaturePane.add(redField, constraints);

        // Green
        constraints.gridx = 0;
        constraints.gridy = 5;
        JLabel greenFieldLabel = new JLabel("Green (0-255): ");
        creaturePane.add(greenFieldLabel, constraints);
        constraints.gridx = 1;
        greenField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        greenField.setText(String.valueOf(subject.green));
        greenField.setMinimumSize(minimumFieldSize);
        creaturePane.add(greenField, constraints);


        // Blue
        constraints.gridx = 0;
        constraints.gridy = 6;
        JLabel blueFieldLabel = new JLabel("Blue (0-255): ");
        creaturePane.add(blueFieldLabel, constraints);
        constraints.gridx = 1;
        blueField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        blueField.setText(String.valueOf(subject.blue));
        blueField.setMinimumSize(minimumFieldSize);
        creaturePane.add(blueField, constraints);

        // Greed
        constraints.gridx = 0;
        constraints.gridy = 7;
        JLabel greedFieldLabel = new JLabel("Greed (0+): ");
        creaturePane.add(greedFieldLabel, constraints);
        constraints.gridx = 1;
        greedField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        greedField.setText(String.valueOf(subject.greed));
        greedField.setMinimumSize(minimumFieldSize);
        creaturePane.add(greedField, constraints);

        // Stomach Size
        constraints.gridx = 0;
        constraints.gridy = 8;
        JLabel stomachSizeFieldLabel = new JLabel("Stomach Size (0+): ");
        creaturePane.add(stomachSizeFieldLabel, constraints);
        constraints.gridx = 1;
        stomachSizeField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        stomachSizeField.setText(String.valueOf(subject.stomachSize));
        stomachSizeField.setMinimumSize(minimumFieldSize);
        creaturePane.add(stomachSizeField, constraints);

        // Stomach Efficiency
        constraints.gridx = 0;
        constraints.gridy = 9;
        JLabel stomachEfficiencyFieldLabel = new JLabel("Stomach Efficiency (0-1): ");
        creaturePane.add(stomachEfficiencyFieldLabel, constraints);
        constraints.gridx = 1;
        stomachEfficiencyField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        stomachEfficiencyField.setText(String.valueOf(subject.stomachEfficiency));
        stomachEfficiencyField.setMinimumSize(minimumFieldSize);
        creaturePane.add(stomachEfficiencyField, constraints);

        // Stomach Fill
        constraints.gridx = 0;
        constraints.gridy = 10;
        JLabel stomachFillFieldLabel = new JLabel("Stomach Fill (0-Stomach Size): ");
        creaturePane.add(stomachFillFieldLabel, constraints);
        constraints.gridx = 1;
        stomachFillField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        stomachFillField.setText(String.valueOf(subject.stomachFill));
        stomachFillField.setMinimumSize(minimumFieldSize);
        creaturePane.add(stomachFillField, constraints);

        // Base Reproduction Chance
        constraints.gridx = 0;
        constraints.gridy = 11;
        JLabel baseReproductionChanceFieldLabel = new JLabel("Base Reproduction Chance (0-1): ");
        creaturePane.add(baseReproductionChanceFieldLabel, constraints);
        constraints.gridx = 1;
        baseReproductionChanceField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        baseReproductionChanceField.setText(String.valueOf(subject.baseReproductionChance));
        baseReproductionChanceField.setMinimumSize(minimumFieldSize);
        creaturePane.add(baseReproductionChanceField, constraints);

        // Ferocity
        constraints.gridx = 0;
        constraints.gridy = 12;
        JLabel ferocityFieldLabel = new JLabel("Ferocity (0+): ");
        creaturePane.add(ferocityFieldLabel, constraints);
        constraints.gridx = 1;
        ferocityField = new NumberInputField(environmentPanel.numberInputFieldColumns);
        ferocityField.setText(String.valueOf(subject.ferocity));
        ferocityField.setMinimumSize(minimumFieldSize);
        creaturePane.add(ferocityField, constraints);

        constraints.gridy = 13;
        constraints.gridx = 0;
        constraints.weighty = 1;
        creaturePane.add(new JPanel(), constraints);
        constraints.weighty = 0;
        constraints.gridy = 14;
        saveExit = new JButton("Save and Exit to View Mode");
        saveExit.addActionListener(e->{
            if (saveCreatureSettings()) {
                ViewMode();
            }
        });
        creaturePane.add(saveExit, constraints);

        constraints.gridy = 15;
        noSaveExit = new JButton("Exit to View Mode without saving");
        noSaveExit.addActionListener(e->{subject = null; ViewMode();});
        creaturePane.add(noSaveExit, constraints);

        // Copy and paste buttons
        constraints.gridy = 16;
        copySettings = new JButton("Copy Settings");
        copySettings.addActionListener(e->copyCreatureSettings());
        creaturePane.add(copySettings, constraints);
        constraints.gridx = 1;
        pasteSettings = new JButton("Paste Settings");
        pasteSettings.addActionListener(e->pasteCreatureSettings());
        creaturePane.add(pasteSettings, constraints);

        creaturePane.revalidate();
        creaturePane.repaint();
    }
    public void addNewCreatureButton() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 4;
        constraints.gridx = 1;
        newCreatureButton = new JButton("Create New Creature");
        newCreatureButton.addActionListener(event -> {
            creating = true;
            subject = new Creature();
            mode = EDIT;
            EditMode();
        });
        panel.add(newCreatureButton, constraints);
        panel.revalidate();
    }
    public void removeNewCreatureButton() {
        panel.remove(newCreatureButton);
        panel.revalidate();
        panel.repaint();
    }
    public boolean saveCreatureSettings() {
        try {
            String newName = nameField.getText();
            float newHeatLoss = Float.parseFloat(heatLossField.getText());
            float newHeatGain = Float.parseFloat(heatGainField.getText());
            float newHeat = Float.parseFloat(heatField.getText());
            int newRed = Math.round(Float.parseFloat(redField.getText()));
            int newGreen = Math.round(Float.parseFloat(greenField.getText()));
            int newBlue = Math.round(Float.parseFloat(blueField.getText()));
            float newGreed = Float.parseFloat(greedField.getText());
            float newStomachSize = Float.parseFloat(stomachSizeField.getText());
            float newStomachEfficiency = Float.parseFloat(stomachEfficiencyField.getText());
            float newStomachFill = Float.parseFloat(stomachFillField.getText());
            float newBaseReproductionChance = Float.parseFloat(baseReproductionChanceField.getText());
            float newFerocity = Float.parseFloat(ferocityField.getText());
            if (isNot01(newStomachEfficiency)) {
                throw new IllegalArgumentException("stomachEfficiency !(0-1)");
            }
            if (isNot01(newBaseReproductionChance)) {
                throw new IllegalArgumentException("baseReproductionChance !(0-1)");
            }
            if (!(0 <= newRed )||!(newRed < 256))  {
                throw new IllegalArgumentException("red !(0-255)");
            }
            if (!(0 <= newGreen )||!( newGreen < 256)) {
                throw new IllegalArgumentException("green !(0-255)");
            }
            if (!(0 <= newBlue )||!( newBlue < 256))  {
                throw new IllegalArgumentException("blue !(0-255)");
            }
            if (isNegative(newHeatLoss)||isNegative(newHeatGain)||isNegative(newGreed)||isNegative(newStomachSize)||isNegative(newStomachFill)||isNegative(newBaseReproductionChance)||isNegative(newFerocity)) {
                throw new SignatureException("Signed value in unsigned Container");
            }
            subject.name = newName;
            subject.heatLoss = newHeatLoss;
            subject.heatGain = newHeatGain;
            subject.heat = newHeat;
            subject.red = newRed;
            subject.green = newGreen;
            subject.blue = newBlue;
            subject.greed = newGreed;
            subject.stomachSize = newStomachSize;
            subject.stomachEfficiency=newStomachEfficiency;
            subject.stomachFill=newStomachFill;
            subject.baseReproductionChance = newBaseReproductionChance;
            subject.ferocity = newFerocity;
            subject.control = parent.parent;
            if (!localCreatures.contains(subject) & creating) {
                System.out.println(parent.parent.getCreatures());
                System.out.println(localCreatures);
                parent.parent.addCreature(subject);
                localCreatures.add(subject);
                System.out.println("CreatingTrigger");
                System.out.println(parent.parent.getCreatures());
                System.out.println(localCreatures);
            }
            update(false);
            setNumberOfCreaturesText();
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(window, "Something went wrong. Try checking that the inputs are correct. "+ ex.getClass() + " " + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    public static boolean isNot01(Float number) {return !(0F <= number & number <= 1F);}
    public static boolean isNegative(Float number) {return number < 0;}
    public ArrayList<String> getCreatureNameList() {
        ArrayList<Creature> creatureList = parent.parent.creatures;
        ArrayList<String> creatureNameList = new ArrayList<>();
        for (Creature creature : creatureList) {
            creatureNameList.add(creature.name);
        }
        return creatureNameList;
    }

    public void updateText() {
        setNumberOfCreaturesText();
        if (subject != null) {
            healthLabel.setText(String.valueOf(new BigDecimal(subject.health.toString()).setScale(2, RoundingMode.HALF_UP)));
            stomachFillLabel.setText(String.valueOf(new BigDecimal(subject.stomachFill.toString()).setScale(2, RoundingMode.HALF_UP)));
            heatLabel.setText(String.valueOf(new BigDecimal(subject.heat.toString()).setScale(2, RoundingMode.HALF_UP)));
        }
    }
    public void killCreature(Creature creature) {
        localCreatures.remove(creature);
        if (creature == subject) {
            update(false);

        }
    }
    public void setNumberOfCreaturesText() {
        if (localCreatures.size() != 1) {
            numberOfCreatures.setText(localCreatures.size() + " Creatures");
        } else {
            numberOfCreatures.setText("1 Creature");
        }
    }
    public void copyCreatureSettings() {
        parent.parent.copiedSettings.heatLoss = safeParse(heatLossField.getText());
        parent.parent.copiedSettings.heatGain = safeParse(heatGainField.getText());
        parent.parent.copiedSettings.heat = safeParse(heatField.getText());
        parent.parent.copiedSettings.red = safeRound(safeParse(redField.getText()));
        parent.parent.copiedSettings.green = safeRound(safeParse(greenField.getText()));
        parent.parent.copiedSettings.blue = safeRound(safeParse(blueField.getText()));
        parent.parent.copiedSettings.greed = safeParse(greedField.getText());
        parent.parent.copiedSettings.stomachSize = safeParse(stomachSizeField.getText());
        parent.parent.copiedSettings.stomachFill = safeParse(stomachFillField.getText());
        parent.parent.copiedSettings.stomachEfficiency = safeParse(stomachEfficiencyField.getText());
        parent.parent.copiedSettings.baseReproductionChance = safeParse(baseReproductionChanceField.getText());
        parent.parent.copiedSettings.ferocity = safeParse(ferocityField.getText());
    }
    public void pasteCreatureSettings() {
        if (parent.parent.copiedSettings.heatLoss != null) {
            heatLossField.setText(String.valueOf(parent.parent.copiedSettings.heatLoss));
        }
        if (parent.parent.copiedSettings.heatGain != null) {
            heatGainField.setText(String.valueOf(parent.parent.copiedSettings.heatGain));
        }
        if (parent.parent.copiedSettings.heat != null) {
            heatField.setText(String.valueOf(parent.parent.copiedSettings.heat));
        }
        if (parent.parent.copiedSettings.red != null) {
            redField.setText(String.valueOf(parent.parent.copiedSettings.red));
        }
        if (parent.parent.copiedSettings.green != null) {
            greenField.setText(String.valueOf(parent.parent.copiedSettings.green));
        }
        if (parent.parent.copiedSettings.blue != null) {
            blueField.setText(String.valueOf(parent.parent.copiedSettings.blue));
        }
        if (parent.parent.copiedSettings.greed != null) {
            greedField.setText(String.valueOf(parent.parent.copiedSettings.greed));
        }
        if (parent.parent.copiedSettings.stomachSize != null) {
            stomachSizeField.setText(String.valueOf(parent.parent.copiedSettings.stomachSize));
        }
        if (parent.parent.copiedSettings.stomachFill != null) {
            stomachFillField.setText(String.valueOf(parent.parent.copiedSettings.stomachFill));
        }
        if (parent.parent.copiedSettings.stomachEfficiency != null) {
            stomachEfficiencyField.setText(String.valueOf(parent.parent.copiedSettings.stomachEfficiency));
        }
        if (parent.parent.copiedSettings.baseReproductionChance != null) {
            baseReproductionChanceField.setText(String.valueOf(parent.parent.copiedSettings.baseReproductionChance));
        }
        if (parent.parent.copiedSettings.ferocity != null) {
            ferocityField.setText(String.valueOf(parent.parent.copiedSettings.ferocity));
        }
        creaturePane.revalidate();
        creaturePane.repaint();
        panel.revalidate();
        panel.repaint();
    }
    public Float safeParse(String newFloat) {
        try {
            return Float.parseFloat(newFloat);
        } catch (Exception e) {
            return null;
        }
    }
    public Integer safeRound(Float newInt) {
        try {
            return Math.round(newInt);
        } catch (Exception e) {
            return null;
        }
    }
    public void displayPanelUpdate(Creature newSubject) {
        subject = newSubject;
        subjectNameLabel.setText(subject.name);
        if (mode == VIEW) {
            ViewMode();
        }
    }

}
