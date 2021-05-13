package doolaeghe_tp1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Chrono {
    
    public Chrono() {
        //A.
        a();
        
        //B.
        b();
        
        //C.
        c();
        
        //D.
        d();
        
        //E.
        e();
    }
    
    void a() {
        System.out.println("Le temps écoulé depuis le 1 janvier 1970 est : " + System.currentTimeMillis());
    }
    
    void b() {
        Date d = new Date();
        System.out.println(d);
    }
    
    void c() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.MONTH) + " - " + c.get(Calendar.YEAR));
    }
    
    void d() {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd MMMMM yyyy HH:mm");
        System.out.println(f.format(d));
    }
    
    void e() {
        long dep, fin;
        
        System.out.println("Envoyer un message pour démarrer : ");
        Scanner s = new Scanner(System.in);
        s.next();
        dep = System.currentTimeMillis();
        
        System.out.println("Envoyer un message pour finir : ");
        s.next();
        fin = System.currentTimeMillis();
        
        double tps = ((double) (fin - dep)) / 1000;
        System.out.println("Le temps entre les deux messages est : " + tps);
    }
}
