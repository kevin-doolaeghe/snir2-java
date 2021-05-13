package doolaeghe_tp1;

import java.util.Scanner;

public class Doolaeghe_tp1 {

    public static void main(String[] args) {
        //Exo 1
        //System.out.println("Hello !");
        
        
        //Exo 2
        System.out.println("Nombre d'arguments : " + args.length);
        for(int i = 0; i < args.length; i++) {
            System.out.println("Argument " + i + " : " + args[i]);
        }
        
        if(args.length > 2) {
            float res = 0;
            
            switch(args[1]) {
                    case "+":
                        res = Float.parseFloat(args[0]) + Float.parseFloat(args[2]);
                        System.out.println("Résultat : " + args[0] + " " + args[1] + " " + args[2] + " = " + res);
                    break;
                    case "-":
                        res = Float.parseFloat(args[0]) - Float.parseFloat(args[2]);
                        System.out.println("Résultat : " + args[0] + " " + args[1] + " " + args[2] + " = " + res);
                    break;
                    case "x":
                        res = Float.parseFloat(args[0]) * Float.parseFloat(args[2]);
                        System.out.println("Résultat : " + args[0] + " " + args[1] + " " + args[2] + " = " + res);
                    break;
                    case "/":
                        res = Float.parseFloat(args[0]) / Float.parseFloat(args[2]);
                        System.out.println("Résultat : " + args[0] + " " + args[1] + " " + args[2] + " = " + res);
                    break;
                    default:
                        res = Float.parseFloat(args[0]) + Float.parseFloat(args[2]);
                        System.out.println("Résultat : " + args[0] + " + " + args[2] + " = " + res);
            }
        } else {
            System.out.println("Pas assez d'arguments pour effectuer un calcul.");
        }
        
        
        /*
        //Exo 3
        //A.
        int i = 1294;
        String str = Integer.toString(i);
        System.out.println(i);
        
        //B.
        int j = 0;
        if(args.length != 0) j = Integer.parseInt(args[0]);
        
        //C.
        float f = 0;
        if(args.length != 0) f = Float.parseFloat(args[0]);
        
        //D.
        System.out.println("Entrez un nom de ville : ");
        */
        
        Scanner sc1 = new Scanner(System.in);
        
        /*
        String str1 = sc1.nextLine();
        System.out.println(str1.toUpperCase());
        
        //E.
        System.out.println("Entrez une chaîne s1 : ");
        String s1 = sc1.nextLine();
        String s2 = sc1.nextLine();
        if(s1.charAt(0) == s2.charAt(0)) System.out.println("Ces deux chaînes commencent par le même caractère : " + s1.charAt(0));
        else System.out.println("Ces deux chaînes ne commencent pas par le même caractère !");
        
        //F.
        System.out.println("s1 == s2 renvoie : " + (s1 == s2));
        System.out.println("s1.equals(s2) renvoie : " + s1.equals(s2));
        System.out.println("s1.compareTo(s2) renvoie : " + s1.compareTo(s2));
        System.out.println("s1.compareToIgnoreCase(s2) renvoie : " + s1.compareToIgnoreCase(s2));
        
        //G.
        if(s1.startsWith(s2)) System.out.println("s1 commence par s2");
        else System.out.println("s1 ne commence pas par s2");
        
        if(s1.endsWith(s2)) System.out.println("s1 finit par s2");
        else System.out.println("s1 ne finit pas par s2");
        
        if(s1.contains(s2)) System.out.println("s1 contient s2");
        else System.out.println("s1 ne contient pas s2");
        
        //H.
        if(s1.contains(s2)) System.out.println("s1 sans s2 : " + s1.replace(s2, ""));
        else System.out.println("s1 ne contient pas s2 : opération impossible !");
        
        //I.
        s1 = s1.intern();
        s2 = s2.intern();
        System.out.println("s1.equals(s2) apprès intern() : " + s1.equals(s2));
        System.out.println("s1 == s2 apprès intern() : " + (s1 == s2));
        */
        
        /*
        //Exo 4
        System.out.println("Combien de nombres aléatoires voulez-vous : ");
        int nbNombres = sc1.nextInt();
        int[] n = new int[nbNombres];
        for(int b = 0; b < nbNombres; b++) {
            n[b] = (int) (Math.random() * 1000);
        }
        
        System.out.println("Etude de 100 000 nombres aléatoires compris entre 1 et " + nbNombres + " : ");
        
        double sum = 0;
        double q = 0;
        double m;
        double et;
        
        for(int b = 0; b < nbNombres; b++) {
            sum += n[b];
            q += n[b] * n[b];
        }
        
        m = sum / nbNombres;
        et = q / nbNombres - m * m;
        et = Math.sqrt(et);
        System.out.println("Moyenne : " + m);
        System.out.println("Ecart-type : " + et);
        */
        
        /*
        //Exo 5
        System.out.println("Entrez le nombre dont vous voulez avoir la factorielle : ");
        int nb = sc1.nextInt();
        Fact fact = new Fact(nb);
        */
        
        //Exo 6
        //Chrono chr = new Chrono();
    }
}
