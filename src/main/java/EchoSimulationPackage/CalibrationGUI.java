package EchoSimulationPackage;

import javax.swing.*;
import java.awt.*;

public class CalibrationGUI {
    private JFrame frame;
    private CalibrationListener calibrationListener;
    private JPanel panel;
    private JLabel instructions;
    private JLabel instructions1;
    private static JTextField textField;
    private String[] positionNames = {"Parasternal (Long Axis)", "Parasternal (Short Axis)", "Apical (4 Chamber)", "Apical (2 Chamber)", "Subxiphoid (4 Chamber/Outlets)", "Suprasternal (Arch)"};
    private String[] positionSensors = {"Parasternal", "Parasternal", "Apical", "Apical", "Subxiphoid", "Suprasternal"};
    private String[] sensorNames = {"Parasternal", "Apical", "Subxiphoid", "Suprasternal"};
    private JPanel panel_1;
    private JProgressBar progressBar;
    private JButton btnFinishCalibration;
    private JPanel panel_2;
    private Boolean status;
    private String currentPosition;
    private JLabel cpLabel;
    private JButton okButton;
    private int currentCal;
    private boolean finishEnabled;
    private JLabel location;

    /**
     * @wbp.parser.entryPoint
     */
    public void create() {
        finishEnabled = false;
        currentPosition = "No Contact";
        currentCal = 0;
        status = true;
        frame = new JFrame("Calibration");
        panel = new JPanel();
        calibrationListener = new CalibrationListener();
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{46, 55, 270, 66, 46, 0};
        gbl_panel.rowHeights = new int[]{0, 44, 36, 32, 0, 39, 46, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 1;

        ImageIcon ic = new ImageIcon(getClass().getResource("/breakingVADicon.png"));
        GridBagConstraints gbc_ic = new GridBagConstraints();
        gbc_ic.fill = GridBagConstraints.NONE;
        gbc_ic.insets = new Insets(0, 0, 0, 5);
        JLabel icon = new JLabel(ic);
        panel_2.add(icon, gbc_ic);
        panel.add(panel_2, gbc_panel_2);


        instructions = new JLabel("Hold probe in correct location and angle for calibration of viewpoint.");
        GridBagConstraints gbc_instructions = new GridBagConstraints();
        gbc_instructions.anchor = GridBagConstraints.SOUTHWEST;
        gbc_instructions.insets = new Insets(0, 0, 5, 5);
        gbc_instructions.gridwidth = 3;
        gbc_instructions.gridx = 1;
        gbc_instructions.gridy = 2;
        panel.add(instructions, gbc_instructions);
        instructions1 = new JLabel("Press \"Set Viewpoint\" to record this position.");
        GridBagConstraints gbc_instructions1 = new GridBagConstraints();
        gbc_instructions1.gridwidth = 3;
        gbc_instructions1.anchor = GridBagConstraints.NORTHWEST;
        gbc_instructions1.insets = new Insets(0, 0, 5, 5);
        gbc_instructions1.gridx = 1;
        gbc_instructions1.gridy = 3;
        panel.add(instructions1, gbc_instructions1);
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 3;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 4;
        panel_1.setBorder(BorderFactory.createTitledBorder(positionNames[0]));
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{42, 150, 129, 0};
        gbl_panel_1.rowHeights = new int[]{45, 40, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        panel.add(panel_1, gbc_panel_1);
        location = new JLabel("Current probe location: ");
        GridBagConstraints gbc_location = new GridBagConstraints();
        gbc_location.anchor = GridBagConstraints.WEST;
        gbc_location.insets = new Insets(0, 0, 5, 5);
        gbc_location.gridx = 1;
        gbc_location.gridy = 0;
        panel_1.add(location, gbc_location);

        cpLabel = new JLabel(currentPosition);
        GridBagConstraints gbc_cpLabel = new GridBagConstraints();
        gbc_cpLabel.anchor = GridBagConstraints.WEST;
        gbc_cpLabel.insets = new Insets(0, 0, 5, 5);
        gbc_cpLabel.gridx = 2;
        gbc_cpLabel.gridy = 0;
        panel_1.add(cpLabel, gbc_cpLabel);

        okButton = new JButton("Set Viewpoint");
        GridBagConstraints gbc_okButton = new GridBagConstraints();
        gbc_okButton.insets = new Insets(0, 0, 0, 5);
        gbc_okButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_okButton.gridx = 1;
        gbc_okButton.gridy = 1;
        panel_1.add(okButton, gbc_okButton);
        if (!currentPosition.equals(positionSensors[0])) {
            okButton.setEnabled(false);
        }
        okButton.setForeground(Color.BLACK);
        okButton.setActionCommand("Calibrate Position");
        okButton.addActionListener(calibrationListener);

        progressBar = new JProgressBar(0,6);
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.gridwidth = 3;
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets(0, 10, 5, 15);
        gbc_progressBar.gridx = 1;
        gbc_progressBar.gridy = 5;
        panel.add(progressBar, gbc_progressBar);

        btnFinishCalibration = new JButton("Finish Calibration");
        btnFinishCalibration.setActionCommand("Finish");
        btnFinishCalibration.addActionListener(calibrationListener);
        btnFinishCalibration.setEnabled(false);
        GridBagConstraints gbc_btnFinishCalibration = new GridBagConstraints();
        gbc_btnFinishCalibration.gridwidth = 2;
        gbc_btnFinishCalibration.anchor = GridBagConstraints.EAST;
        gbc_btnFinishCalibration.insets = new Insets(0, 0, 0, 5);
        gbc_btnFinishCalibration.gridx = 2;
        gbc_btnFinishCalibration.gridy = 6;
        panel.add(btnFinishCalibration, gbc_btnFinishCalibration);

        frame.setSize(530,400);
        frame.setVisible(true);
    }

    public void nextImage(int imageNumber) {
        currentCal = imageNumber;
        panel_1.setBorder(BorderFactory.createTitledBorder(positionNames[imageNumber]));
        progressBar.setValue(imageNumber);
    }

    public void finish() {
        status = false;
        frame.getContentPane().removeAll();
        panel = new JPanel();

        JLabel complete = new JLabel("Calibration Complete. Would you like to save this calibration?");
        JButton saveButton = new JButton("Yes");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(calibrationListener);

        JButton noSaveButton = new JButton("No");
        noSaveButton.setActionCommand("Don't Save");
        noSaveButton.addActionListener(calibrationListener);

        panel.add(complete);
        panel.add(saveButton);
        panel.add(noSaveButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void save() {
        frame.getContentPane().removeAll();
        panel = new JPanel();

        JLabel saveAs = new JLabel("Enter a name for your calibration:");
        textField = new JTextField(20);
        JButton saveAsButton = new JButton("Save");

        saveAsButton.setActionCommand("Save as");
        saveAsButton.addActionListener(calibrationListener);

        panel.add(saveAs);
        panel.add(textField);
        panel.add(saveAsButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    static JTextField getTextField() {
        return textField;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setCurrentPosition(String input) {
        String[] split = input.split(",");
        if (split.length == 4) {
            currentPosition = sensorNames[Integer.parseInt(split[0]) - 1];
        } else {
            currentPosition = input;
        }
        cpLabel.setText(currentPosition);
        if (currentPosition.equals(positionSensors[currentCal]) && !finishEnabled) {
            okButton.setEnabled(true);
        } else {{
            okButton.setEnabled(false);
        }}
    }

    public void enableFinish() {
        progressBar.setValue(6);
        btnFinishCalibration.setEnabled(true);
        panel_1.setEnabled(false);
        okButton.setEnabled(false);
        finishEnabled = true;
        cpLabel.setEnabled(false);
        location.setEnabled(false);
    }

    public void bringToFront() {
        frame.toFront();
        frame.revalidate();
        frame.repaint();
    }
}
