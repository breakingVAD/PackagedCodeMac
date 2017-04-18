package EchoSimulationPackage;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class MainControlGUI {
    private static JFrame frame;
    private static Calibration calibration;
    private static JPanel panel2;
    private static JLabel instructions;
    private static GridBagConstraints gbc_instructions;
    private static JButton btnRefresh;
    private static GridBagConstraints gbc_btnRefresh;
    private static JLabel lblX;
    private static GridBagConstraints gbc_lblX;
    private static JPanel panel;
    private static ImageIcon check;

    public MainControlGUI() {
        frame = new JFrame("Echo Simulation Control Panel");
        calibration = new Calibration();
    }

    public void create() {
        JLabel connecting = new JLabel("Connecting...");
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("End Session");
        cancel.addActionListener(new ButtonListener());
        JPanel panel = new JPanel();

        panel.add(connecting);
        panel.add(cancel);
        frame.getContentPane().add(panel);

        frame.setSize(800,500);
        frame.setVisible(true);
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void connected() {
        frame.getContentPane().removeAll();

        panel = new JPanel();

        ButtonListener listener = new ButtonListener();

        frame.getContentPane().add(panel, BorderLayout.WEST);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{50, 28, 35, 413, 50};
        gbl_panel.rowHeights = new int[]{0, 75, 0, 12, 34, 63, 0, 67, 16, 0, 50};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 1;

        ImageIcon ic = new ImageIcon(getClass().getResource("/breakingVADicon.png"));
        GridBagConstraints gbc_ic = new GridBagConstraints();
        gbc_ic.fill = GridBagConstraints.NONE;
        gbc_ic.anchor = GridBagConstraints.NORTHWEST;
        gbc_ic.insets = new Insets(0, 0, 0, 5);
        JLabel icon = new JLabel(ic);
        panel_1.add(icon, gbc_ic);
        panel.add(panel_1, gbc_panel_1);

        check = new ImageIcon(getClass().getResource("/check.png"));
        JLabel lblC = new JLabel(check);
        GridBagConstraints gbc_lblC = new GridBagConstraints();
        gbc_lblC.insets = new Insets(0, 0, 5, 5);
        gbc_lblC.gridx = 1;
        gbc_lblC.gridy = 3;
        panel.add(lblC, gbc_lblC);

        JLabel connected = new JLabel("Connected to Arduino.");
        GridBagConstraints gbc_connected = new GridBagConstraints();
        gbc_connected.gridwidth = 2;
        gbc_connected.insets = new Insets(0, 0, 5, 5);
        gbc_connected.anchor = GridBagConstraints.WEST;
        gbc_connected.gridx = 2;
        gbc_connected.gridy = 3;
        panel.add(connected, gbc_connected);

        ImageIcon x = new ImageIcon(getClass().getResource("/x.png"));
        lblX = new JLabel(x);
        gbc_lblX = new GridBagConstraints();
        gbc_lblX.anchor = GridBagConstraints.NORTH;
        gbc_lblX.insets = new Insets(0, 0, 5, 5);
        gbc_lblX.gridx = 1;
        gbc_lblX.gridy = 4;
        panel.add(lblX, gbc_lblX);

        JLabel lblValidCalibrationChosen = new JLabel("Valid Calibration Chosen.");
        GridBagConstraints gbc_lblValidCalibrationChosen = new GridBagConstraints();
        gbc_lblValidCalibrationChosen.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblValidCalibrationChosen.gridwidth = 2;
        gbc_lblValidCalibrationChosen.insets = new Insets(0, 0, 5, 5);
        gbc_lblValidCalibrationChosen.gridx = 2;
        gbc_lblValidCalibrationChosen.gridy = 4;
        panel.add(lblValidCalibrationChosen, gbc_lblValidCalibrationChosen);

        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createTitledBorder("Create New Calibration"));
        GridBagConstraints gbc_panel1 = new GridBagConstraints();
        gbc_panel1.gridwidth = 3;
        gbc_panel1.fill = GridBagConstraints.BOTH;
        gbc_panel1.insets = new Insets(0, 0, 5, 5);
        gbc_panel1.gridx = 1;
        gbc_panel1.gridy = 5;
        GridBagLayout gbl_panel1 = new GridBagLayout();
        gbl_panel1.columnWidths = new int[]{359, 100, 0};
        gbl_panel1.rowHeights = new int[]{29, 0};
        gbl_panel1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel1.setLayout(gbl_panel1);

        JButton calibrate = new JButton("Calibrate");
        calibrate.setActionCommand("Calibrate");
        calibrate.addActionListener(listener);
        JLabel calinstruction = new JLabel("Set correct probe positions for each of 6 available viewpoints.");
        GridBagConstraints gbc_calinstruction = new GridBagConstraints();
        gbc_calinstruction.anchor = GridBagConstraints.WEST;
        gbc_calinstruction.insets = new Insets(0, 0, 0, 5);
        gbc_calinstruction.gridx = 0;
        gbc_calinstruction.gridy = 0;
        panel1.add(calinstruction, gbc_calinstruction);

        GridBagConstraints gbc_calibrate = new GridBagConstraints();
        gbc_calibrate.anchor = GridBagConstraints.NORTHWEST;
        gbc_calibrate.insets = new Insets(0, 0, 0, 5);
        gbc_calibrate.gridx = 1;
        gbc_calibrate.gridy = 0;
        panel1.add(calibrate);
        panel.add(panel1, gbc_panel1);

        panel2 = new JPanel();
        GridBagLayout gbl_panel2 = new GridBagLayout();
        gbl_panel2.columnWidths = new int[]{164, 0, 31, 169, 31, 0};
        gbl_panel2.rowHeights = new int[]{29, 23, 0, 0, 0, 0};
        gbl_panel2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel2.setLayout(gbl_panel2);

        panel2.setBorder(BorderFactory.createTitledBorder("Use Previous Calibration"));
        GridBagConstraints gbc_panel2 = new GridBagConstraints();
        gbc_panel2.gridwidth = 3;
        gbc_panel2.fill = GridBagConstraints.BOTH;
        gbc_panel2.insets = new Insets(0, 0, 5, 5);
        gbc_panel2.gridx = 1;
        gbc_panel2.gridy = 7;
        panel.add(panel2, gbc_panel2);

        instructions = new JLabel("Please select from the saved calibrations below:");
        gbc_instructions = new GridBagConstraints();
        gbc_instructions.gridwidth = 4;
        gbc_instructions.anchor = GridBagConstraints.NORTHWEST;
        gbc_instructions.insets = new Insets(0, 0, 5, 5);
        gbc_instructions.gridx = 0;
        gbc_instructions.gridy = 0;
        panel2.add(instructions, gbc_instructions);

        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("End Session");
        closeButton.addActionListener(listener);
        GridBagConstraints gbc_closeButton = new GridBagConstraints();
        gbc_closeButton.anchor = GridBagConstraints.NORTHEAST;
        gbc_closeButton.insets = new Insets(0, 0, 5, 5);
        gbc_closeButton.gridx = 3;
        gbc_closeButton.gridy = 8;
        panel.add(closeButton, gbc_closeButton);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setActionCommand("Refresh");
        btnRefresh.addActionListener(listener);
        btnRefresh.setForeground(new Color(0, 128, 0));
        gbc_btnRefresh = new GridBagConstraints();
        gbc_btnRefresh.insets = new Insets(0, 0, 5, 0);
        gbc_btnRefresh.gridx = 4;
        gbc_btnRefresh.gridy = 0;
        panel2.add(btnRefresh, gbc_btnRefresh);

        refreshSavedCals();

        frame.setSize(650,650);
        frame.setVisible(true);
    }

    public void disconnected() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        JLabel connected = new JLabel("Arduino has been disconnected.");
        JButton close = new JButton("Close");
        close.setActionCommand("End Session");
        close.addActionListener(new ButtonListener());

        panel.add(connected);
        panel.add(close);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    static void close() {
        frame.dispose();
        ProbeDetection.setEndSession(true);
    }

    static void refreshSavedCals() {
        panel2.removeAll();
        panel2.add(instructions, gbc_instructions);
        panel2.add(btnRefresh, gbc_btnRefresh);

        HashMap<String, HashMap<String, ImagingRegion>> savedCals = calibration.getCalibrationsFromFirebase();

        int iter = 0;
        for (String key : savedCals.keySet()) {
            JButton button = new JButton(key);
            GridBagConstraints gbc_btnSavedcal = new GridBagConstraints();
            gbc_btnSavedcal.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnSavedcal.insets = new Insets(0, 0, 5, 5);
            gbc_btnSavedcal.gridx = 3* (iter % 2);
            gbc_btnSavedcal.gridy = Math.floorDiv(iter, 2) + 1;
            button.setActionCommand(key);
            button.addActionListener(new PreviousCalibrationsListener());
            panel2.add(button, gbc_btnSavedcal);

            JButton deleteBtn = new JButton("DELETE");
            deleteBtn.setForeground(Color.RED);
            deleteBtn.setActionCommand("Delete " + key);
            deleteBtn.addActionListener(new PreviousCalibrationsListener());
            GridBagConstraints gbc_deleteBtn = new GridBagConstraints();
            gbc_deleteBtn.insets = new Insets(0, 0, 5, 5);
            gbc_deleteBtn.gridx = gbc_btnSavedcal.gridx + 1;
            gbc_deleteBtn.gridy = Math.floorDiv(iter, 2) + 1;;
            panel2.add(deleteBtn, gbc_deleteBtn);
            iter++;
        }
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public static void checkCalSet() {
        panel.remove(lblX);
        JLabel lblC2 = new JLabel(check);
        panel.add(lblC2, gbc_lblX);
        panel.revalidate();
        panel.repaint();
        frame.setVisible(true);
    }
}
