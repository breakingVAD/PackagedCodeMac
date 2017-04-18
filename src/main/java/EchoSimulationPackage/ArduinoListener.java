package EchoSimulationPackage;

import gnu.io.*;

import java.io.BufferedReader;

public class ArduinoListener implements SerialPortEventListener {
    private BufferedReader input;
    private Probe probe;
    private MainControlGUI mainControlGUI;

    public ArduinoListener(BufferedReader in, MainControlGUI main) {
        input = in;
        mainControlGUI = main;
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                System.out.println(inputLine);
                ProbeDetection.setInputLine(inputLine);
                probe = ProbeDetection.getProbe();
                CalibrationGUI calibrationGUI = ProbeDetection.getCalibrationGUI();
                if (calibrationGUI != null) {
                    if (calibrationGUI.getStatus()) {
                        ProbeDetection.getCalibrationGUI().setCurrentPosition(inputLine);
                    }
                }
                if (probe != null) {
                    probe.analyzeInput(inputLine);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }
}
