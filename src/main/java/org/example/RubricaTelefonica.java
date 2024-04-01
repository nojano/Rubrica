package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;

public class RubricaTelefonica extends JFrame {
    private JTable tabella;
    private DefaultTableModel modello; //Modello tabella
    public Vector<Persona> rubrica = new Vector<>();

    public File[] informazioni;

    public RubricaTelefonica() {

        // CARICO I CONTATTI GIÀ PRESENTI NEL FILE RUBRICA NEL VETTORE RUBRICA;

        String folderpath = "informazioni";
        File cartella = new File(folderpath);
        informazioni = cartella.listFiles();
        if (informazioni != null && informazioni.length > 0) {
            for (File file: informazioni){
                try{ Scanner scanner = new Scanner(file);
                    String riga = scanner.nextLine(); // Legge una riga dal file
                    String[] elementi = riga.split(";");
                    Persona persona = new Persona(elementi[0], elementi[1], elementi[2], elementi[3], Integer.parseInt(elementi[4]));
                    rubrica.add(persona);
                    scanner.close();
                }
                catch (FileNotFoundException e){ }
            }
        }

        //CARICO L'INTERFACCIA;

        setTitle("Rubrica Telefonica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //chiudi tutto
        setSize(700, 500);

        modello = new DefaultTableModel();
        modello.addColumn("Nome");
        modello.addColumn("Cognome");
        //modello.addColumn("Indirizzo");
        modello.addColumn("Telefono");
        //modello.addColumn("Età");
        tabella = new JTable(modello); //associo modello alla tabella

        JScrollPane scrollPane = new JScrollPane(tabella); //Rendo dinamico in caso diventi troppo lungo
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton nuovoButton = new JButton("Nuovo");
        JButton modificaButton = new JButton("Modifica");
        JButton eliminaButton = new JButton("Elimina");

        //CARICO IL VETTORE RUBRICA NELL'INTERFACCIA;

        refreshTable();


        nuovoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEditor(null);
            }
        });
        modificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tabella.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona una persona da modificare.");
                } else {
                    openEditor(rubrica.get(row));
                }
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tabella.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona una persona da eliminare.");
                } else {
                    int choice = JOptionPane.showConfirmDialog(null, "Eliminare la persona " +
                            rubrica.get(row).getNome() + " " + rubrica.get(row).getCognome() + "?");
                    if (choice == JOptionPane.YES_OPTION) {
                        File fileDaEliminare = new File("informazioni", rubrica.get(row).getNome() + "." + rubrica.get(row).getCognome()+".txt");
                        fileDaEliminare.delete();
                        rubrica.remove(row);
                        refreshTable();
                    }
                }

            }
        });

        buttonPanel.add(nuovoButton);
        buttonPanel.add(modificaButton);
        buttonPanel.add(eliminaButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);


    }

    public void openEditor(Persona persona) {
        EditorPersona editor = new EditorPersona(this, persona);
        editor.setVisible(true);
    }

    public void addPersona(Persona persona) {
        rubrica.add(persona);
        refreshTable();
    }

    public void refreshTable() {
        modello.setRowCount(0);
        for (Persona persona : rubrica) {
            modello.addRow(new Object[]{persona.getNome(), persona.getCognome(), persona.getTelefono()});
        }
    }
}
