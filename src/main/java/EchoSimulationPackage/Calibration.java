package EchoSimulationPackage;

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Calibration {
    private HashMap<String, ImagingRegion> calibratedRegions;
    private DatabaseReference ref;
    private String userID;
    private FirebaseDatabase database;
    static Boolean wait;
    private String[] positionCodes = {"PLA", "PSA", "A4C", "A2C", "SX4", "SPA"};

    public Calibration() {
        calibratedRegions = new HashMap<>();
        database = ProbeDetection.getDatabase();
        userID = ProbeDetection.getUsername();
    }

    public void calibrate(String positionCode) {
        String[] split = ProbeDetection.getInputLine().split(",");
        if (split.length == 4) {
            Long region = Long.parseLong(split[0]);
            double xAng = Double.parseDouble(split[1]);
            double yAng = Double.parseDouble(split[2]);
            double zAng = Double.parseDouble(split[3]);

            ImagingRegion imagingRegion = new ImagingRegion(region, xAng, yAng, zAng);
            calibratedRegions.put(positionCode, imagingRegion);
            System.out.println(calibratedRegions);
        }
    }

    public HashMap<String, ImagingRegion> getCalibratedRegions() {
        return calibratedRegions;
    }

    public void sendToFirebase(String saveName) {
        ref = database.getReference(userID + "/SavedCalibrations/" + saveName);
        ref.setValue(calibratedRegions);
        MainControlGUI.refreshSavedCals();
    }

    public HashMap<String, HashMap<String, ImagingRegion>> getCalibrationsFromFirebase() {
        ref = database.getReference(userID + "/SavedCalibrations/");

        HashMap<String, HashMap<String, ImagingRegion>> savedCals = new HashMap<>();
        wait = true;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, ImagingRegion> childMap = new HashMap<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    for (int i=0; i<6; i++) {
                        try {
                            childMap.put(positionCodes[i], new ImagingRegion(((HashMap<String, HashMap<String,Number>>) child.getValue()).get(positionCodes[i])));
                            System.out.println(childMap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    savedCals.put(child.getKey(), childMap);
                    childMap = new HashMap<>();
                }
                wait = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        while(wait) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ProbeDetection.setSavedCals(savedCals);

        return savedCals;
    }
}
