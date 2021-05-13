package com.kevin.test;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        JFrame fenetre = new JFrame();
        fenetre.setSize(400, 250);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLocationRelativeTo(null);
        fenetre.setContentPane(new Panel());

        //---------------------------------------------------------------------------------

        Scanner s = new Scanner(System.in);
        /*
        int taille = s.nextInt();
        int curTab = 0;
        double index = 2;
        double[] tab = new double[taille];
        boolean premier;

        while(curTab < taille) {
            premier = true;
            for(int i = 0; i < curTab; i++) {
                if(index % tab[i] == 0) premier = false;
            }

            if(premier == true) {
                tab[curTab] = index;
                System.out.println(tab[curTab]);
                curTab++;
            }
            index++;
        }
        */

        //---------------------------------------------------------------------------------

        /*
        System.out.println("Entrez une adresse ip de la forme \"xx.xx.xx.xx\":");
        String ipAddress = s.nextLine();
        boolean joignable;

        try {
            InetAddress inet = InetAddress.getByName(ipAddress);
            joignable = inet.isReachable(10000);
            if(joignable) System.out.println("L'hôte " + ipAddress + " a répondu");
            else System.out.println("L'hôte " + ipAddress + " n'a pas répondu");
        } catch(Exception e) {
            System.out.println("Mauvaise adresse IP !");
        }
        */

        //---------------------------------------------------------------------------------

        System.out.println("Entrez une adresse ip de la forme \"xx.xx.xx.xx\" :");
        String ip = s.nextLine();
        try {
            String[] tabStr = ip.split(".");
            int[] tabInt = new int[3];
        } catch(Exception e) {
            System.out.println("Erreur !");
        }
    }
}
