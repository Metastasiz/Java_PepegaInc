import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Upgrade {
    static Map<Integer, double[]> itemMod = new HashMap<>(){
        {
            //mask
            put(0,new double[]{1,2,4,8,12});
            //hand
            put(1,new double[]{0,4,8,12,16});
            //temp
            put(2,new double[]{0,0.025,0.05,0.075,0.1,});
            //blood
            put(3,new double[]{0.0,0.05,0.10,0.15,0.20});
            //staff
            put(4,new double[]{0,10,100,1000,10000});
            //hospital
            put(5,new double[]{0,1,2,3,4});
            //hygiene
            put(6,new double[]{2,4,6,8,10});
            //detection
            put(7,new double[]{0,1.4,1,0.6,0.2});
            //curfew
            put(8,new double[]{0,1});
            //vaccine
            put(9,new double[]{0,1});
        }
    };
    static Map<Integer, int[]> itemPrice = new HashMap<>(){
        {
            put(0,new int[]{400,800,1200,800,0});
            put(1,new int[]{1000,1200,1400,1600,0});
            put(2,new int[]{1000,1300,1600,2000,0});
            put(3,new int[]{2500,3000,3500,4000,0});
            put(4,new int[]{3000,7000,12000,18000,0});
            put(5,new int[]{5000,15000,30000,50000,0});
            put(6,new int[]{800,1000,1200,1400,1600});
            put(7,new int[]{3000,3500,4000,4500,0});
            put(8,new int[]{5000,0});
            put(9,new int[]{99999,0});
        }
    };
    //
    final static Map<Integer, String> itemName = new HashMap<Integer,String>(){{
        put(0,"Medical Mask");
        put(1,"Hand Sanitiser");
        put(2,"Temperature Check");
        put(3,"Blood Test Check");
        put(4,"Medical Staff");
        put(5,"Hospital");
        put(6,"Hygiene");
        put(7,"Infection Detection");
        put(8,"Temporary Curfew");
        put(9,"Vaccine from Bill G.");
    }};
    static Map<Integer, JButton> itemButton = new HashMap<>();
}
class DIFF{
    static int diff = 1;
    final static double[][] SCALE = {
            //scalePop, scaleWealth, scaleEarn, scaleSave, scaleResearch, scaleVirus
            {0.8,1.5,800,1.5,14},
            {1.0,1.0,1000,1.0,10},
            {1.2,0.7,2000,0.7,8},
            {1.5,0.3,3000,0.3,5}
    };
    public static double scalePop(){return SCALE[diff][0];}
    public static double scaleWealth(){return SCALE[diff][1];}
    public static double scaleEarn(){return SCALE[diff][2];}
    public static double scaleSave(){return SCALE[diff][3];}
    public static double scaleVirus(){return SCALE[diff][4];}
}
