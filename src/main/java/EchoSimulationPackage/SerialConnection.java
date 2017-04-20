package EchoSimulationPackage;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class SerialConnection {
    SerialPort serialPort;
    /** The port we're normally going to use. */
    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            "COM3", // Windows
            "COM6",
            "/dev/tty.usbmodem1411",
            "/dev/tty.usbmodem1421",
    };

    private BufferedReader input;
    private MainControlGUI mainControlGUI;

    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;

    public SerialConnection(MainControlGUI mainGUI) {
        mainControlGUI = mainGUI;
    }

    public void initialize() {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            System.out.println(currPortId.getName());
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            JOptionPane.showMessageDialog( null,"Cannot connect to Device. Please plug in device and retry connection.");
            return;
        }

        try {

            // open serial port, and use class name for the appName.
            System.out.println(portId);
            System.out.println(portId.getName());
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            ProbeDetection.setInput(input);
            mainControlGUI.connected();

            // add event listeners
            serialPort.addEventListener(new ArduinoListener(input, mainControlGUI));
            serialPort.notifyOnDataAvailable(true); //this enables one time notification on first time data is available; not sure what kind of notification?
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            JOptionPane.showMessageDialog( null,"Cannot connect to Arduino. Please restart program to try again.");
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
}


