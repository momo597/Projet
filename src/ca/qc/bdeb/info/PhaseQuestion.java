/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.newdawn.slick.Input;

/**
 *
 * @author Utilisateur
 */
public class PhaseQuestion extends JFrame {

    JPanel Panneau = new JPanel(new GridLayout(2, 1));
    JPanel choix = new JPanel(new GridLayout(2, 2));
    JPanel hautMenu = new JPanel(new BorderLayout());
    JPanel centre = new JPanel(new BorderLayout());
    JLabel question;
    JLabel etiquettePointsTemps = new JLabel();

    Timer timer;
    JButton btn1 = new JButton("choix A");
    JButton btn2 = new JButton("choix B");
    JButton btn3 = new JButton("choix C");
    JButton btn4 = new JButton("choix D");
    JButton plusTemps = new JButton("+ temps");
    JButton moinsChoix = new JButton("50/50");
    ArrayList<JButton> tabChoix = new ArrayList<JButton>();
    int seconde;
    int reponse;
    int points = 0;
    boolean abiliteUtilise = false;
    boolean vraiFaux;
//Dans le cas d'un vrai ou faux Vrai et Faux seront entrer pour choix1 et choix2 et null pour  choix3 et choix4

    public PhaseQuestion(String Question, int reponse, String choix1, String choix2, String choix3, String choix4, boolean vraiFaux, int temps) {
        question = new JLabel(Question);
        this.reponse = reponse;
        btn1 = new JButton(choix1);
        btn2 = new JButton(choix2);
        btn3 = new JButton(choix3);
        btn4 = new JButton(choix4);
        this.seconde = temps;
        this.vraiFaux = vraiFaux;
        setTitle("Phase de Questions");
        setSize(1000, 1000);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (seconde <= 0) {

                    timer.stop();
                    mauvaiseReponse();
                     JOptionPane.showMessageDialog(null, "Temps ecoule");
                    

                } else {
                    seconde--;
                }
                etiquettePointsTemps.setText(String.format("%1d:%2d", seconde / 60, seconde % 60) + "                                                                                                                                                                                     points=" + points);
            }
        });
        timer.start();
        if (!vraiFaux) {
            hautMenu.add(plusTemps, BorderLayout.WEST);
            hautMenu.add(moinsChoix, BorderLayout.EAST);
        }
        centre.add(etiquettePointsTemps, BorderLayout.SOUTH);
        centre.add(question, BorderLayout.NORTH);
        hautMenu.add(centre, BorderLayout.CENTER);
        tabChoix.add(btn1);
        tabChoix.add(btn2);

        if (!vraiFaux) {
            tabChoix.add(btn3);
            tabChoix.add(btn4);
        }
        plusTemps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                seconde += 30;
                abiliteUtilise = true;
            }
        });
        moinsChoix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Random r = new Random();
                int reponseEnlever1 = -1;
                int reponseEnlever2 = -1;

                do {
                    do {
                        reponseEnlever1 = r.nextInt(4);
                    } while (reponseEnlever1 == reponse);
                    reponseEnlever2 = r.nextInt(4);
                } while (reponseEnlever2 == reponse || reponseEnlever2 == reponseEnlever1);

                choix.remove(reponseEnlever1);
                choix.remove(reponseEnlever2);
                abiliteUtilise = true;
            }
        });
        reponse();
        JOptionPane.showMessageDialog(null, choix.getComponentCount());
        Panneau.add(hautMenu);
        Panneau.add(choix);
        this.add(Panneau);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

    }

    public void reponse() {

        for (int i = 0; i < tabChoix.size(); i++) {
            tabChoix.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    JButton btn = (JButton) ae.getSource();
                    verifierReponse(tabChoix.indexOf(btn));
                }
            });

            choix.add(tabChoix.get(i));
        }
    }

    public void verifierReponse(int i) {

        if (reponse == i) {
            bonneReponse();

        } else {

            mauvaiseReponse();
        }

    }

    private void bonneReponse() {
        JOptionPane.showMessageDialog(null, "bonneReponse");
        if (abiliteUtilise|| vraiFaux) {
            points += 50;
        } else {
            points += 150;
        }
    }

    private void mauvaiseReponse() {
        JOptionPane.showMessageDialog(null, "mauvaiseReponse");
    }
}
