package org.example;

import org.example.Login.LoginPage;
import org.example.Rubrica.RubricaTelefonica;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }
}