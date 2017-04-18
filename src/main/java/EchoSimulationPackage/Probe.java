package EchoSimulationPackage;

import com.google.firebase.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.swing.*;
import java.util.HashMap;

public class Probe {
    private String region;
    private DatabaseReference ref;
    private String userID;
    private HashMap<String, ImagingRegion> calibratedRegions;
    private int zeros;
    private Boolean consecutiveZeros;


    public Probe(HashMap<String, ImagingRegion> calRegions) {
        consecutiveZeros = false;
        zeros = 0;
        region = "No Contact";//TODO

        userID = ProbeDetection.getUsername();
        System.out.println(userID);
        final FirebaseDatabase database = ProbeDetection.getDatabase();
        ref = database.getReference(userID + "/ProbeData");

        calibratedRegions = calRegions;
    }

    public void analyzeInput(String input) {
        String[] split = input.split(",");
        String newRegion = "";
        if (split.length == 4) {
            Long sensorRegion = Long.parseLong(split[0]);
            double xAng = Double.parseDouble(split[1]);
            double yAng = Double.parseDouble(split[2]);
            double zAng = Double.parseDouble(split[3]);
            if (xAng == 0.0 && yAng == 0.0 && zAng == 0.0) {
                if (consecutiveZeros) {
                    zeros++;
                }
                consecutiveZeros = true;
            } else {
                consecutiveZeros = false;
                zeros = 0;
            }
            ImagingRegion checkRegion;

            for (String positionName : calibratedRegions.keySet()) {
                checkRegion = calibratedRegions.get(positionName);

                if (checkRegion.getRegionNum() == sensorRegion) {
                    if (xAng > checkRegion.getxAngMin() && xAng < checkRegion.getxAngMax() && yAng > checkRegion.getyAngMin() && yAng < checkRegion.getyAngMax() && zAng > checkRegion.getzAngMin() && zAng < checkRegion.getzAngMax()) {
                        newRegion = positionName;
                        if (positionName.equals("A4C")) {
                            System.out.println(xAng);
                            System.out.println(xAng > checkRegion.getxAngMin());
                            System.out.println(xAng < checkRegion.getxAngMax());
                            System.out.println(yAng);
                            System.out.println(yAng > checkRegion.getyAngMin());
                            System.out.println(yAng < checkRegion.getyAngMax());
                            System.out.println(zAng);
                            System.out.println(zAng > checkRegion.getzAngMin());
                            System.out.println(zAng < checkRegion.getzAngMax());
                        }
                    } else {
                        if (!newRegion.equals("A4C") && !newRegion.equals("PLA") && !newRegion.equals("PSA") && !newRegion.equals("A2C")) {
                            newRegion = "TIS";
                        }
                    }
                }
            }
        } else if (input.equals("Tissue")) {
            newRegion = "TIS";
        } else if (input.equals("No Contact")) {
            newRegion = "NIC";
        }
        if (newRegion.equals("")) {
            newRegion = "None";
        }
        System.out.println("NewRegion = " + newRegion);
        sendToFirebase(newRegion);

        if (zeros >= 400) {
            zeros = 0;
            JOptionPane.showMessageDialog(null, "The probe's orientation sensor may be malfunctioning. Please restart the Arduino and try again.");
        }
    }

    public void sendToFirebase(String newRegion) {
        if (!newRegion.equals(region)) {
            HashMap<String, String> regionMap = new HashMap<>();
            regionMap.put("region", newRegion);
            ref.setValue(regionMap);
            region = newRegion;
        }
    }
}
