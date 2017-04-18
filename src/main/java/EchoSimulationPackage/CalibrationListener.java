package EchoSimulationPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CalibrationListener implements ActionListener{
    private Calibration calibration;
    private CalibrationGUI calibrationGUI;
    private int calCounter;
    private String[] positionCodes = {"PLA", "PSA", "A4C", "A2C","SX4", "SPA"};

    public CalibrationListener() {
        calibration = new Calibration();
        calibrationGUI = ProbeDetection.getCalibrationGUI();
        calCounter = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Calibrate Position")) {
            calibration.calibrate(positionCodes[calCounter]);
            if (calCounter < 5) {
                calibrationGUI.nextImage(++calCounter);
            } else {
                calCounter = 0;
                calibrationGUI.enableFinish();
            }
        } else if (command.equals("Finish")) {
            calibrationGUI.finish();
            ProbeDetection.setCalibratedRegions(calibration.getCalibratedRegions());
            calibrationGUI.bringToFront();
        } else if (command.equals("Don't Save")) {
            calibrationGUI.close();
        } else if (command.equals("Save")) {
            calibrationGUI.save();
        } else if (command.equals("Save as")) {
            HashMap<String, HashMap<String, ImagingRegion>> savedCals = ProbeDetection.getSavedCals();
            int numSavedCals = savedCals.size();
            if (numSavedCals < 10) {
                String text = CalibrationGUI.getTextField().getText();
                if (!(text.contains(".") || text.contains("#") || text.contains("$") || text.contains("[") || text.contains("]"))) {
                    if (!savedCals.containsKey(text)) {
                        if (!text.equals("")) {
                            calibration.sendToFirebase(text);
                            calibrationGUI.close();
                        } else {
                            JOptionPane.showMessageDialog(null, "Please enter a name before saving.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You already have a calibration saved under this name. Please choose a new name.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "\"Invalid calibration name. Paths must be non-empty strings and can't contain \".\", \"#\", \"$\", \"[\", or \"]\"");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You already have the maximum number of calibrations saved. Please delete one before saving another.");
            }

        }
    }

}
