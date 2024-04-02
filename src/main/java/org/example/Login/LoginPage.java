package org.example.Login;

import org.example.Rubrica.EditorPersona;
import org.example.Rubrica.Persona;
import org.example.Rubrica.RubricaTelefonica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Vector<Utente> utenti = new Vector<>();

    public LoginPage() {
        //CARICO UTENTI ESISTENTI

        String path = "utenti.txt";
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine();
                String[] elementi = riga.split(";");
                System.out.println(elementi[0] +  elementi[1]);
                Utente utente = new Utente(elementi[0], elementi[1]);
                utenti.add(utente);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }

        //INTERFACCIA LOGIN

        setTitle("Login per Rubrica");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton SigninButton = new JButton("Iscriviti");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        //panel.add(new JLabel());
        panel.add(SigninButton);
        panel.add(loginButton);

        SigninButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                openSignin();
            }
        });


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean utenteCorretto=false;
                for(Utente utente:utenti){
                    if(username.equals(utente.getUsername())&&password.equals(utente.getPassword())){
                        utenteCorretto=true;
                    }
                }

                if (utenteCorretto==true) {
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new RubricaTelefonica();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }

    public void openSignin() {
        SigninPage signinPage = new SigninPage(this);
        signinPage.setVisible(true);
    }
}
