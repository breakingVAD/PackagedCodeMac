package EchoSimulationPackage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PreviousCalibrationsListener implements ActionListener {
    private HashMap<String, HashMap<String, ImagingRegion>> savedCals;

    public PreviousCalibrationsListener() {
        savedCals = ProbeDetection.getSavedCals();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.length() > 5) {
            if (actionCommand.substring(0, 6).equals("Delete")) {
                FirebaseDatabase database = ProbeDetection.getDatabase();
                String userName = ProbeDetection.getUsername();
                DatabaseReference ref = database.getReference(userName + "/SavedCalibrations/");

                HashMap<String, HashMap<String, ImagingRegion>> newSavedCals = ProbeDetection.getSavedCals();
                newSavedCals.remove(actionCommand.substring(7, actionCommand.length()));
                ProbeDetection.setSavedCals(newSavedCals);

                ref.setValue(newSavedCals);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                MainControlGUI.refreshSavedCals();
            } else {
                String calName = actionCommand;
                HashMap<String, ImagingRegion> selectedCal = savedCals.get(calName);
                System.out.println(selectedCal);
                ProbeDetection.setCalibratedRegions(selectedCal);
            }
        } else {
            String calName = actionCommand;
            HashMap<String, ImagingRegion> selectedCal = savedCals.get(calName);
            System.out.println(selectedCal);
            ProbeDetection.setCalibratedRegions(selectedCal);
        }
    }
}
