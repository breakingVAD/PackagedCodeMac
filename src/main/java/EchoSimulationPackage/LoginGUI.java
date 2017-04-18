package EchoSimulationPackage;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    private JFrame frame;
    private JPanel panel;
    private Boolean status;
    private JTextField userNameInput;
    private JLabel lblEmailAddress;
    private JLabel lblPleaseLogIn;
    private JLabel label;
    private JLabel label_1;
    private JPanel panel_1;

    public LoginGUI() {
        status = false;
    }

    public void create() {
        frame = new JFrame("Log In");
        panel = new JPanel();
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{33, 51, 106, 324, 106, 173, -107, -83};
        gbl_panel.rowHeights = new int[]{0, 75, 0, 16, 0, 29, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        panel_1 = new JPanel();
        ImageIcon ic = new ImageIcon(getClass().getResource("/breakingVADicon.png"));
        GridBagConstraints gbc_ic = new GridBagConstraints();
        gbc_ic.fill = GridBagConstraints.NONE;
        gbc_ic.insets = new Insets(0, 0, 0, 5);
        JLabel icon = new JLabel(ic);
        panel_1.add(icon, gbc_ic);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.NONE;
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 1;
        panel.add(panel_1, gbc_panel_1);

        lblPleaseLogIn = new JLabel("Please log in to your Breaking VAD instructor account in order to begin Echocardiogram Simulation.");
        GridBagConstraints gbc_lblPleaseLogIn = new GridBagConstraints();
        gbc_lblPleaseLogIn.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblPleaseLogIn.insets = new Insets(0, 0, 5, 5);
        gbc_lblPleaseLogIn.gridwidth = 4;
        gbc_lblPleaseLogIn.gridx = 2;
        gbc_lblPleaseLogIn.gridy = 3;
        panel.add(lblPleaseLogIn, gbc_lblPleaseLogIn);

        label = new JLabel("");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 6;
        gbc_label.gridy = 3;
        panel.add(label, gbc_label);

        label_1 = new JLabel("");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 7;
        gbc_label_1.gridy = 3;
        panel.add(label_1, gbc_label_1);
        frame.getContentPane().add(panel);

        lblEmailAddress = new JLabel("Email Address:");
        GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
        gbc_lblEmailAddress.anchor = GridBagConstraints.WEST;
        gbc_lblEmailAddress.insets = new Insets(0, 0, 0, 5);
        gbc_lblEmailAddress.gridx = 2;
        gbc_lblEmailAddress.gridy = 5;
        panel.add(lblEmailAddress, gbc_lblEmailAddress);
        userNameInput = new JTextField(20);
        GridBagConstraints gbc_userNameInput = new GridBagConstraints();
        gbc_userNameInput.fill = GridBagConstraints.HORIZONTAL;
        gbc_userNameInput.insets = new Insets(0, 0, 0, 5);
        gbc_userNameInput.gridx = 3;
        gbc_userNameInput.gridy = 5;
        panel.add(userNameInput, gbc_userNameInput);

        JButton login = new JButton("Log in");
        login.setActionCommand("Log in");
        login.addActionListener(new LoginListener());
        GridBagConstraints gbc_login = new GridBagConstraints();
        gbc_login.insets = new Insets(0, 0, 0, 5);
        gbc_login.anchor = GridBagConstraints.NORTHWEST;
        gbc_login.gridx = 4;
        gbc_login.gridy = 5;
        panel.add(login, gbc_login);

        frame.setSize(700,270);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    public JTextField getUserNameInput() {
        return userNameInput;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
