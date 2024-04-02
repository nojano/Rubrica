package org.example.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class SigninPage extends JFrame{

    private JTextField signinUsernameField;
    private JPasswordField signinPasswordField;

    public SigninPage(LoginPage login) {
        setTitle("Iscriviti alla Rubrica");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        signinUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        signinPasswordField = new JPasswordField();

        JButton signinButton = new JButton("Iscriviti");

        panel.add(usernameLabel);
        panel.add(signinUsernameField);
        panel.add(passwordLabel);
        panel.add(signinPasswordField);
        panel.add(new JLabel());
        panel.add(signinButton);

        signinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String username = signinUsernameField.getText();
                String password = signinPasswordField.getText();
                for(Utente utente:login.utenti){
                    if(username.equals(utente.getUsername())){
                        JOptionPane.showMessageDialog(null, "Username già usato. Perfavore scegline un altro.");
                        return;
                    }
                }
                Utente utenteCreato = new Utente(username,password);
                login.utenti.add(utenteCreato);
                dispose();
                String filePath = "utenti.txt";
                try (PrintStream printStream = new PrintStream(new FileOutputStream(new File(filePath)))) {
                    for(Utente utente: login.utenti){
                        printStream.println(utente.getUsername()+";"+utente.getPassword());
                    }
                } catch (Exception ex) {
                    System.err.println("Errore durante la scrittura su file: " + ex.getMessage());
                }
            }
        });
        add(panel);
    }
}
