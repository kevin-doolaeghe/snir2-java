package doolaeghe_tp1;

import java.math.BigInteger;

public class Fact {
    
    public Fact(int n) {
        //A.
        //factorielle1(n);
        
        //B.
        /*
        double fact = 1;
        double fact_temp;
        int i = 2;
        do {
            fact_temp = fact;
            fact *= i++;
        } while(fact > fact_temp);
        System.out.println("Valeur max de n : " + (i - 2));
        */
        
        //C.
        //factorielle2(n);
        //factorielle3(n);
        
        //D.
        //Non
    }
    
    static long factorielle1(int n) {
        long fact = 1;
        for(int i = 0; n - i > 0; i++) {
            fact *= n - i;
        }
        
        System.out.println("Factorielle de " + n + " = " + fact);
        return fact;
    }
    
    static BigInteger factorielle2(int n) {
        BigInteger fact = BigInteger.ONE;
        for(int i = 0; n - i > 0; i++) {
            fact = fact.multiply(BigInteger.valueOf(n - i));
        }
        
        System.out.println("Factorielle de " + n + " = " + fact);
        return fact;
    }
    
    static double factorielle3(int n) {
        double fact = 1;
        for(int i = 0; n - i > 0; i++) {
            fact *= n - i;
        }
        
        System.out.println("Factorielle de " + n + " = " + fact);
        return fact;
    }
}
