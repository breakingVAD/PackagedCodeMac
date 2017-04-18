package EchoSimulationPackage;

import com.google.firebase.database.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoginListener implements ActionListener {
    private LoginGUI loginGUI;
    boolean retrievedFromFirebase = false;
    public HashMap<String,String> users = new HashMap<String,String>();

    public LoginListener() {
        loginGUI = ProbeDetection.getLoginGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Log in")) {
            //TODO: get log in info from LoginGUI; validate with Firebase? OR just take username not password
            String username = loginGUI.getUserNameInput().getText();
            System.out.println("username: " + username);
            FirebaseDatabase database = ProbeDetection.getDatabase();
            DatabaseReference ref = database.getReference("Users Map");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        HashMap<String,String> emailMap = (HashMap<String,String>) child.getValue();
                        String key = child.getKey();
                        String email = emailMap.get("email");
                        users.put(key,email);
                    }
                    retrievedFromFirebase = true;
                }
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            while(!retrievedFromFirebase) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            if (users.containsValue(username)) {
                for(String user:users.keySet()){
                    if(users.get(user).equals(username)){
                        ProbeDetection.setUsername(user);
                    }
                }
                loginGUI.setStatus(true);
                loginGUI.close();
            } else{
                JOptionPane.showMessageDialog(null, "User is either invalid or isn't signed into web application");
            }
        }
    }
}
