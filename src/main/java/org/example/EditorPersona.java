package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorPersona extends JFrame {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField indirizzoField;
    private JTextField telefonoField;
    private JTextField etaField;

    public EditorPersona(RubricaTelefonica rubrica, Persona persona) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //chiudi ma lascia runnare l'app
        setSize(300, 200);
        JPanel panel = new JPanel(new GridLayout(6, 1));
        JLabel nomeEtichetta = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel cognomeEtichetta = new JLabel("Cognome");
        cognomeField = new JTextField();
        JLabel indirizzoEtichetta = new JLabel(("Indirizzo"));
        indirizzoField = new JTextField();
        JLabel telefonoEtichetta = new JLabel("Telefono");
        telefonoField = new JTextField();
        JLabel etaEtichetta = new JLabel("Età");
        etaField = new JTextField();
        if (persona == null) {
            setTitle("Nuova Persona");
        } else {
            setTitle("Modifica Persona");
            nomeField.setText(persona.getNome());
            cognomeField.setText(persona.getCognome());
            indirizzoField.setText(persona.getIndirizzo());
            telefonoField.setText(persona.getTelefono());
            etaField.setText(String.valueOf(persona.getEta()));
        }

        panel.add(nomeEtichetta);
        panel.add(nomeField);
        panel.add(cognomeEtichetta);
        panel.add(cognomeField);
        panel.add(indirizzoEtichetta);
        panel.add(indirizzoField);
        panel.add(telefonoEtichetta);
        panel.add(telefonoField);
        panel.add(etaEtichetta);
        panel.add(etaField);


        JButton salvaButton = new JButton("Salva");
        salvaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String indirizzo = indirizzoField.getText();
                String telefono = telefonoField.getText();
                if(!StringaSoloNumeri(telefono)){
                    JOptionPane.showMessageDialog(null, "Inserisci un numero di telefono valido.");
                }
                else if(telefono.isEmpty()){
                    telefono = "-1";
                }
                String eta = etaField.getText();
                if(!StringaSoloNumeri(eta)){
                    JOptionPane.showMessageDialog(null, "Inserisci un valore valido per l'età.");
                }
                else if(eta.isEmpty()){
                    eta = "-1";
                }

                if (persona == null) {
                    rubrica.addPersona(new Persona(nome, cognome, indirizzo, telefono, Integer.parseInt(eta)));
                } else {
                    persona.setNome(nome);
                    persona.setCognome(cognome);
                    persona.setIndirizzo(indirizzo);
                    persona.setTelefono(telefono);
                    persona.setEta(Integer.parseInt(eta));
                    rubrica.refreshTable();
                }
                dispose();
            }
        });

        JButton annullaButton = new JButton("Annulla");
        annullaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(salvaButton);
        panel.add(annullaButton);

        add(panel);
    }

    public static boolean StringaSoloNumeri(String str) {
        return str.isEmpty() || str.matches("\\d+");
    }

}
