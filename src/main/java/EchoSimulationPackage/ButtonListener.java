package EchoSimulationPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Calibrate")) {
            CalibrationGUI calibrationGUI = new CalibrationGUI();
            ProbeDetection.setCalibrationGUI(calibrationGUI);
            calibrationGUI.create();
        } else if (command.equals("End Session")) {
            MainControlGUI.close();
        } else if (command.equals("Refresh")) {
            MainControlGUI.refreshSavedCals();
        }
    }
}
