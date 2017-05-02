package EchoSimulationPackage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

class ProbeDetection {
    private static BufferedReader input;
    private static String inputLine;
    private static SerialConnection serialConnection;
    private static HashMap<String, ImagingRegion> calibratedRegions;
    private static Probe probe;
    private static CalibrationGUI calibrationGUI;
    private static Boolean endSession;
    private static String username;
    private static FirebaseDatabase database;
    private static HashMap<String, HashMap<String, ImagingRegion>> savedCals;
    private static LoginGUI loginGUI;
    private static MainControlGUI mainControlGUI;

    public static void main(String[] args) {
        String osType = System.getProperty("os.name");
        System.out.println(osType);
        char firstLetter = osType.charAt(0);
        if (firstLetter == 'M'){
            try {
                NativeUtils.loadLibraryFromJar("/osx/librxtxSerial.jnilib");
            } catch (IOException e) {
                e.printStackTrace(); // This is probably not the best way to handle exception :-)
            }
        }
        else if(firstLetter == 'W'){
            try {
                NativeUtils.loadLibraryFromJar("/windows/x86_64/libNRJavaSerial.dll");
            } catch (IOException e) {
                e.printStackTrace(); // This is probably not the best way to handle exception :-)
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"This application does not support your operating system, please try again with a different machine.");
        }
        String libPathProperty = System.getProperty("java.library.path");
        System.out.println(libPathProperty);
        FirebaseOptions options;
        try {
            InputStream fileInputStream = ProbeDetection.class.getResourceAsStream("/breaking-vad-online-simulation-firebase-adminsdk-pn399-0fa8b48b27.json");
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(fileInputStream)
                    .setDatabaseUrl("https://breaking-vad-online-simulation.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch(Exception e) {
//            e.printStackTrace();
        }

        database = FirebaseDatabase.getInstance();

        endSession = false;

        loginGUI = new LoginGUI();
        loginGUI.create();

        while (!loginGUI.getStatus()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // Create a listener and controller
        mainControlGUI = new MainControlGUI();
        mainControlGUI.create();

//        serialConnection = new SerialConnection(mainControlGUI);
//        serialConnection.initialize();

        initializeSerial();

        while (!endSession) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        serialConnection.close();
    }

     // [Loaded gnu.io.CommPortIdentifier from file:/Library/Java/Extensions/RXTXcomm.jar]

    static void initializeSerial(){
        serialConnection = new SerialConnection(mainControlGUI);
        serialConnection.initialize();
    }

    static SerialConnection getSerialConnection() {
        return serialConnection;
    }

    static void setCalibratedRegions(HashMap<String, ImagingRegion> calRegions) {
        calibratedRegions = calRegions;
        probe = new Probe(calRegions);
        MainControlGUI.checkCalSet();
    }

    static HashMap<String, ImagingRegion> getCalibratedRegions() {
        return calibratedRegions;
    }

    static Probe getProbe() {
        return probe;
    }

    static CalibrationGUI getCalibrationGUI() {
        return calibrationGUI;
    }

    static void setCalibrationGUI(CalibrationGUI calGUI) {
        calibrationGUI = calGUI;
    }

    static void setEndSession(Boolean bool) {
        endSession = bool;
    }

    static void setInput(BufferedReader in) {
        input = in;
    }

    static BufferedReader getInput() {
        return input;
    }

    public static String getInputLine() {
        return inputLine;
    }

    public static void setInputLine(String inputLine) {
        ProbeDetection.inputLine = inputLine;
    }

    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static HashMap<String, HashMap<String, ImagingRegion>> getSavedCals() {
        return savedCals;
    }

    public static void setSavedCals(HashMap<String, HashMap<String, ImagingRegion>> savedCals) {
        ProbeDetection.savedCals = savedCals;
    }

    public static LoginGUI getLoginGUI() {
        return loginGUI;
    }

    public static void setLoginGUI(LoginGUI loginGUI) {
        ProbeDetection.loginGUI = loginGUI;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ProbeDetection.username = username;
    }
}

