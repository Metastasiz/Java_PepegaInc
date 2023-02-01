import javax.swing.*;
import java.util.Map;

public class MyClock {
    static int time = 0;
    private static boolean run = false;
    MyClock(){
        while (true){
            //System.out.println(time);
            while (run){
                try {
                    time++;
                    refresh();
                    if (time%10==0){
                        refresh10();
                    }
                    if (time%30==0){
                        refresh30();
                    }
                    Thread.sleep(1000);
                }
                catch (Exception e){System.out.println(e);}
            }
            try {Thread.sleep(200);}
            catch (Exception e){}
        }
    }
    public void refresh(){
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()){
            e.getValue().simVirus();
            e.getValue().cureVirus();
            e.getValue().wealth();
        }
    }
    public void refresh10(){
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()){
            e.getValue().upLand();
            e.getValue().upSea();
            e.getValue().upAir();
        }
    }
    public void refresh30(){
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()){
            Country.gold+=e.getValue().getEarn();
        }

    }
    public static String getTime(){
        String out = "";
        int sec = time%60, tmp = time/60, min = tmp%60, hour = tmp/60;
        if (hour/10<1)out+="0";
        out+=hour+":";
        if (min/10<1)out+="0";
        out+=min+":";
        if (sec/10<1)out+="0";
        out+=sec;
        return out;
    }
    public static boolean isRun(){
        return run;
    }
    public static void runTrue(){
        run = true;
    }
    public static void runFalse(){
        run = false;
    }
    public static void runChange(){
        run = !run;
    }
    public static void reset(){time=0;}
}
