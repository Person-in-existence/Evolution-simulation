import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class environmentPanel extends Thread{
    public static final Integer numberInputFieldColumns = 10;
    public controlPanel parent;
    public JFrame window;
    public JPanel panel;
    public NumberInputField predatorChanceField;
    public NumberInputField predatorSightField;
    public NumberInputField predatorLuckField;
    public NumberInputField foodAmountField;
    public NumberInputField mutationChanceField;
    public NumberInputField mutationVariationField;
    public NumberInputField mutationRegressionField;
    public NumberInputField passiveHeatGainField;
    public NumberInputField redCompField;
    public NumberInputField greenCompField;
    public NumberInputField blueCompField;
    public JLabel predatorChanceLabelOut;
    public JLabel predatorSightLabelOut;
    public JLabel predatorLuckLabelOut;
    public JLabel foodAmountLabelOut;
    public JLabel mutationChanceLabelOut;
    public JLabel mutationVariationLabelOut;
    public JLabel mutationRegressionLabelOut;
    public JLabel passiveHeatGainLabelOut;
    public JLabel redCompLabelOut;
    public JLabel greenCompLabelOut;
    public JLabel blueCompLabelOut;
    public JButton confirmButton;
    public JButton creaturePanelButton;
    public JButton runButton;
    public JButton displayPanelButton;

    public void run() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window = new JFrame();
        window.setSize(500, screenSize.height-100);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        URL iconURL = getClass().getResource("/IconEPanel.png");
        // Check if the URL is not null
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            window.setIconImage(icon.getImage());
        } else {
            System.out.println("IconURLWasNull");
        }
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Predator Chance
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel predatorChanceLabel = new JLabel();
        predatorChanceLabel.setText("Predator Chance (0-1): ");
        panel.add(predatorChanceLabel, constraints);

        predatorChanceField = new NumberInputField(numberInputFieldColumns);
        predatorChanceField.setText(String.valueOf(parent.getPredatorChance()));
        constraints.gridx = 1;
        panel.add(predatorChanceField, constraints);

        // Predator Sight
        constraints.gridy = 1; // Move to the next line
        constraints.gridx = 0; // Reset to the first column
        JLabel predatorSightLabel = new JLabel();
        predatorSightLabel.setText("Predator Sight (0-255): ");
        panel.add(predatorSightLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy=1;
        predatorSightField = new NumberInputField(numberInputFieldColumns);
        predatorSightField.setText(String.valueOf(parent.getPredatorSight()));
        panel.add(predatorSightField, constraints);

        // Predator Luck
        constraints.gridy=2;
        constraints.gridx = 0;
        JLabel predatorLuckLabel = new JLabel();
        predatorLuckLabel.setText("Predator Luck (0-1): ");
        panel.add(predatorLuckLabel, constraints);

        constraints.gridx = 1;
        predatorLuckField = new NumberInputField(numberInputFieldColumns);
        predatorLuckField.setText(String.valueOf(parent.getPredatorLuck()));
        panel.add(predatorLuckField, constraints);

        // Food Amount
        constraints.gridy=3;
        constraints.gridx=0;
        JLabel foodAmountLabel = new JLabel();
        foodAmountLabel.setText("Food Amount (0+): ");
        panel.add(foodAmountLabel, constraints);

        constraints.gridx = 1;
        foodAmountField = new NumberInputField(numberInputFieldColumns);
        foodAmountField.setText(String.valueOf(parent.getFoodAmount()));
        panel.add(foodAmountField, constraints);

        // Mutation Chance
        constraints.gridy = 4;
        constraints.gridx = 0;
        JLabel mutationChanceLabel = new JLabel();
        mutationChanceLabel.setText("Mutation Chance (0-1): ");
        panel.add(mutationChanceLabel, constraints);

        constraints.gridx = 1;
        mutationChanceField = new NumberInputField(numberInputFieldColumns);
        mutationChanceField.setText(String.valueOf(parent.getMutationChance()));
        panel.add(mutationChanceField, constraints);

        // Mutation Variation
        constraints.gridy = 5;
        constraints.gridx = 0;
        JLabel mutationVariationLabel = new JLabel();
        mutationVariationLabel.setText("Mutation Variation (0-1*): ");
        panel.add(mutationVariationLabel, constraints);

        constraints.gridx = 1;
        mutationVariationField = new NumberInputField(numberInputFieldColumns);
        mutationVariationField.setText(String.valueOf(parent.getMutationVariation()));
        panel.add(mutationVariationField, constraints);

        // Mutation Regression
        constraints.gridy = 6;
        constraints.gridx = 0;
        JLabel mutationRegressionLabel = new JLabel();
        mutationRegressionLabel.setText("Mutation Regression (0-1): ");
        panel.add(mutationRegressionLabel, constraints);

        constraints.gridx = 1;
        mutationRegressionField = new NumberInputField(numberInputFieldColumns);
        mutationRegressionField.setText(String.valueOf(parent.getMutationRegression()));
        panel.add(mutationRegressionField, constraints);

        // Passive Heat Gain
        constraints.gridy = 7;
        constraints.gridx = 0;
        JLabel passiveHeatGainLabel = new JLabel();
        passiveHeatGainLabel.setText("Passive Heat Gain (-10 - 10*): ");
        panel.add(passiveHeatGainLabel, constraints);
        constraints.gridx = 1;
        passiveHeatGainField = new NumberInputField(numberInputFieldColumns);
        passiveHeatGainField.setText(String.valueOf(parent.getPassiveHeatGain()));
        panel.add(passiveHeatGainField, constraints);

        // Red Comp
        constraints.gridy = 8;
        constraints.gridx = 0;
        JLabel redCompLabel = new JLabel();
        redCompLabel.setText("Red (0-255): ");
        panel.add(redCompLabel, constraints);
        constraints.gridx = 1;
        redCompField = new NumberInputField(numberInputFieldColumns);
        redCompField.setText(String.valueOf(parent.getRedComp()));
        panel.add(redCompField, constraints);

        // Green Comp
        constraints.gridy = 9;
        constraints.gridx = 0;
        JLabel greenCompLabel = new JLabel();
        greenCompLabel.setText("Green (0-255): ");
        panel.add(greenCompLabel, constraints);
        constraints.gridx = 1;
        greenCompField = new NumberInputField(numberInputFieldColumns);
        greenCompField.setText(String.valueOf(parent.getRedComp()));
        panel.add(greenCompField, constraints);

        // Blue Comp
        constraints.gridy = 10;
        constraints.gridx = 0;
        JLabel blueCompLabel = new JLabel();
        blueCompLabel.setText("Blue (0-255):");
        panel.add(blueCompLabel, constraints);
        constraints.gridx = 1;
        blueCompField = new NumberInputField(numberInputFieldColumns);
        blueCompField.setText(String.valueOf(parent.getRedComp()));
        panel.add(blueCompField, constraints);

        // Confirm
        constraints.gridy = 11;
        constraints.gridx = 0;
        confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(100, 20));
        confirmButton.addActionListener(e -> {
            try {
                float newPredatorChance = Float.parseFloat(predatorChanceField.getText());
                int newPredatorSight = Math.round(Float.parseFloat(predatorSightField.getText()));
                float newPredatorLuck = Float.parseFloat(predatorLuckField.getText());
                float newFoodAmount = Float.parseFloat(foodAmountField.getText());
                float newMutationChance = Float.parseFloat(mutationChanceField.getText());
                float newMutationVariation = Float.parseFloat(mutationVariationField.getText());
                float newMutationRegression = Float.parseFloat(mutationRegressionField.getText());
                float newPassiveHeatGain = Float.parseFloat(passiveHeatGainField.getText());
                int newRedComp = Math.round(Float.parseFloat(redCompField.getText()));
                int newGreenComp = Math.round(Float.parseFloat(greenCompField.getText()));
                int newBlueComp = Math.round(Float.parseFloat(blueCompField.getText()));
                if (!(0 <= newRedComp )||!(newRedComp < 256))  {
                    throw new IllegalArgumentException("red !(0-255)");
                }
                if (!(0 <= newGreenComp )||!( newGreenComp < 256)) {
                    throw new IllegalArgumentException("green !(0-255)");
                }
                if (!(0 <= newBlueComp )||!( newBlueComp < 256))  {
                    throw new IllegalArgumentException("blue !(0-255)");
                }
                if (creaturePanel.isNot01(newPredatorChance)) {
                    throw new IllegalArgumentException("predatorChance !(0-1)");
                }
                if (!(0 <= newPredatorSight )||!( newPredatorSight < 256))  {
                    throw new IllegalArgumentException("blue !(0-255)");
                }
                if (creaturePanel.isNot01(newPredatorLuck)) {
                    throw new IllegalArgumentException("predatorLuck !(0-1)");
                }
                if (creaturePanel.isNegative(newFoodAmount)) {
                    throw new IllegalArgumentException("foodAmount !(0+)");
                }
                if (creaturePanel.isNot01(newMutationChance)) {
                    throw new IllegalArgumentException("mutationChance !(0-1)");
                }
                if (creaturePanel.isNot01(newMutationRegression)) {
                    throw new IllegalArgumentException("mutationRegression !(0-1)");
                }

                parent.setPredatorChance(newPredatorChance);
                parent.setPredatorSight(newPredatorSight);
                parent.setPredatorLuck(newPredatorLuck);
                parent.setFoodAmount(newFoodAmount);
                parent.setMutationChance(newMutationChance);
                parent.setMutationVariation(newMutationVariation);
                parent.setMutationRegression(newMutationRegression);
                parent.setPassiveHeatGain(newPassiveHeatGain);
                parent.setRedComp(newRedComp);
                parent.setGreenComp(newGreenComp);
                parent.setBlueComp(newBlueComp);
                parent.parent.displayPanel.update(parent.parent.creatures);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window, "Something went wrong. Try checking that the inputs are correct. The error is: " +ex.getClass()+ ": "+ ex.getCause(), "Error", JOptionPane.WARNING_MESSAGE);
            }
            update();
        });
        panel.add(confirmButton, constraints);
        // Divider
        constraints.gridy = 12;
        constraints.gridx = 0;
        constraints.weighty = 0.03;
        JSeparator divider = new JSeparator();
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.add(divider, constraints);
        constraints.weighty = 0;
        // Displays:

        // Predator Chance
        constraints.gridy = 13;
        JLabel predatorChance = new JLabel();
        predatorChance.setText("Predator Chance: ");
        panel.add(predatorChance, constraints);

        constraints.gridx = 1;
        predatorChanceLabelOut = new JLabel();
        predatorChanceLabelOut.setText(String.valueOf(parent.getPredatorChance()));
        panel.add(predatorChanceLabelOut, constraints);

        // Predator Sight
        constraints.gridy = 14;
        constraints.gridx = 0;
        JLabel predatorSight = new JLabel();
        predatorSight.setText("Predator Sight: ");
        panel.add(predatorSight, constraints);

        constraints.gridx = 1;
        predatorSightLabelOut = new JLabel();
        predatorSightLabelOut.setText(String.valueOf(parent.getPredatorSight()));
        panel.add(predatorSightLabelOut, constraints);

        // Predator Luck
        constraints.gridy = 15;
        constraints.gridx = 0;
        JLabel predatorLuck = new JLabel();
        predatorLuck.setText("Predator Luck: ");
        panel.add(predatorLuck, constraints);

        constraints.gridx = 1;
        predatorLuckLabelOut = new JLabel();
        predatorLuckLabelOut.setText(String.valueOf(parent.getPredatorLuck()));
        panel.add(predatorLuckLabelOut, constraints);

        // Food Amount
        constraints.gridy = 16;
        constraints.gridx = 0;
        JLabel foodAmount = new JLabel();
        foodAmount.setText("Food Amount: ");
        panel.add(foodAmount, constraints);

        constraints.gridx = 1;
        foodAmountLabelOut = new JLabel();
        foodAmountLabelOut.setText(String.valueOf(parent.getFoodAmount()));
        panel.add(foodAmountLabelOut, constraints);

        // Mutation Chance
        constraints.gridy = 17;
        constraints.gridx = 0;
        JLabel mutationChance = new JLabel();
        mutationChance.setText("Mutation Chance: ");
        panel.add(mutationChance, constraints);

        constraints.gridx = 1;
        mutationChanceLabelOut = new JLabel();
        mutationChanceLabelOut.setText(String.valueOf(parent.getMutationChance()));
        panel.add(mutationChanceLabelOut, constraints);

        // Mutation Variation
        constraints.gridy = 18;
        constraints.gridx = 0;
        JLabel mutationVariation = new JLabel();
        mutationVariation.setText("Mutation Variation: ");
        panel.add(mutationVariation, constraints);

        constraints.gridx = 1;
        mutationVariationLabelOut = new JLabel();
        mutationVariationLabelOut.setText(String.valueOf(parent.getMutationVariation()));
        panel.add(mutationVariationLabelOut, constraints);

        // Mutation Regression
        constraints.gridy = 19;
        constraints.gridx = 0;
        JLabel mutationRegression = new JLabel();
        mutationRegression.setText("Mutation Regression: ");
        panel.add(mutationRegression, constraints);

        constraints.gridx = 1;
        mutationRegressionLabelOut = new JLabel();
        mutationRegressionLabelOut.setText(String.valueOf(parent.getMutationRegression()));
        panel.add(mutationRegressionLabelOut, constraints);

        // Passive Heat Gain
        constraints.gridy = 20;
        constraints.gridx = 0;
        JLabel passiveHeatGain = new JLabel();
        passiveHeatGain.setText("Passive Heat Gain: ");
        panel.add(passiveHeatGain, constraints);

        constraints.gridx = 1;
        passiveHeatGainLabelOut = new JLabel();
        passiveHeatGainLabelOut.setText(String.valueOf(parent.getPassiveHeatGain()));
        panel.add(passiveHeatGainLabelOut, constraints);

        // Red Comp
        constraints.gridy = 21;
        constraints.gridx = 0;
        JLabel redComp = new JLabel();
        redComp.setText("Red: ");
        panel.add(redComp, constraints);

        constraints.gridx = 1;
        redCompLabelOut = new JLabel();
        redCompLabelOut.setText(String.valueOf(parent.getRedComp()));
        panel.add(redCompLabelOut, constraints);

        // Green Comp
        constraints.gridy = 22;
        constraints.gridx = 0;
        JLabel greenComp = new JLabel();
        greenComp.setText("Green: ");
        panel.add(greenComp, constraints);

        constraints.gridx = 1;
        greenCompLabelOut = new JLabel();
        greenCompLabelOut.setText(String.valueOf(parent.getGreenComp()));
        panel.add(greenCompLabelOut, constraints);

        // Blue Comp
        constraints.gridy = 23;
        constraints.gridx = 0;
        JLabel blueComp = new JLabel();
        blueComp.setText("Blue: ");
        panel.add(blueComp, constraints);

        constraints.gridx = 1;
        blueCompLabelOut = new JLabel();
        blueCompLabelOut.setText(String.valueOf(parent.getBlueComp()));
        panel.add(blueCompLabelOut, constraints);
        // Run Button
        constraints.gridy = 24;
        constraints.gridx = 0;
        runButton = new JButton("Start");
        runButton.addActionListener(e->toggleRunning());
        panel.add(runButton, constraints);

        // Create a component that will take up all remaining vertical space
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 25;
        panel.add(new JPanel(), constraints);

        constraints.gridy = 26;
        constraints.weighty = 0;
        JLabel asterisk1 = new JLabel();
        asterisk1.setText("* Recommended Range");
        asterisk1.setFont(UIManager.getFont("Label.font").deriveFont(12f));
        panel.add(asterisk1, constraints);

        constraints.gridy = 27;
        window.getContentPane().add(panel);
        window.setTitle("Environment Panel");
        window.setVisible(true);
    }
    public void update() {
        predatorChanceLabelOut.setText(String.valueOf(parent.getPredatorChance()));
        predatorSightLabelOut.setText(String.valueOf(parent.getPredatorSight()));
        predatorLuckLabelOut.setText(String.valueOf(parent.getPredatorLuck()));
        foodAmountLabelOut.setText(String.valueOf(parent.getFoodAmount()));
        mutationChanceLabelOut.setText(String.valueOf(parent.getMutationChance()));
        mutationVariationLabelOut.setText(String.valueOf(parent.getMutationVariation()));
        mutationRegressionLabelOut.setText(String.valueOf(parent.getMutationRegression()));
        passiveHeatGainLabelOut.setText(String.valueOf(parent.getPassiveHeatGain()));
        redCompLabelOut.setText(String.valueOf(parent.getRedComp()));
        greenCompLabelOut.setText(String.valueOf(parent.getGreenComp()));
        blueCompLabelOut.setText(String.valueOf(parent.getBlueComp()));
        predatorChanceField.setText(String.valueOf(parent.getPredatorChance()));
        predatorSightField.setText(String.valueOf(parent.getPredatorSight()));
        predatorLuckField.setText(String.valueOf(parent.getPredatorLuck()));
        foodAmountField.setText(String.valueOf(parent.getFoodAmount()));
        mutationChanceField.setText(String.valueOf(parent.getMutationChance()));
        mutationVariationField.setText(String.valueOf(parent.getMutationVariation()));
        mutationRegressionField.setText(String.valueOf(parent.getMutationRegression()));
        passiveHeatGainField.setText(String.valueOf(parent.getPassiveHeatGain()));
        redCompField.setText(String.valueOf(parent.getRedComp()));
        greenCompField.setText(String.valueOf(parent.getGreenComp()));
        blueCompField.setText(String.valueOf(parent.getBlueComp()));
    }
    public void creaturePanelButton() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 26;
        constraints.weightx = 0;
        constraints.gridx = 1;
        creaturePanelButton = new JButton();
        creaturePanelButton.setText("Open Creature Panel");
        creaturePanelButton.addActionListener(a-> {
            parent.newCreaturePanel();
            panel.remove(creaturePanelButton);
            creaturePanelButton = null;
            panel.revalidate(); // layout components again
            panel.repaint(); // paint components again
        });
        panel.add(creaturePanelButton, constraints);
        panel.revalidate(); // layout components again
        panel.repaint(); // paint components again
        panel.requestFocusInWindow();
    }
    public void toggleRunning() {
        if (parent.getRunning()) {
            runButton.setText("Resume");
            parent.setRunning(false);
        } else {
            runButton.setText("Pause");
            parent.setRunning(true);
        }
    }
    public void displayPanelButton() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 26;
        constraints.weightx = 0;
        constraints.gridx = 2;
        displayPanelButton = new JButton("Open Display Panel");
        displayPanelButton.addActionListener(e -> {
            parent.parent.displayPanel = new displayPanelController();
            parent.parent.displayPanelThread = new Thread(parent.parent.displayPanel);
            parent.parent.displayPanel.parent = parent.parent;
            parent.parent.displayPanelThread.start();
            panel.remove(displayPanelButton);
            displayPanelButton = null;
            panel.repaint();
            panel.requestFocusInWindow();
        });
        panel.add(displayPanelButton, constraints);
        panel.revalidate();
        panel.repaint();
        panel.requestFocusInWindow();
    }
}