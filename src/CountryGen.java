import java.util.HashMap;
import java.util.Map;

public class CountryGen {
    private static int seed = MapGen.seed;
    private static int countries = MapGen.countries;
    public static Map<Integer, Country> countryInfo = new HashMap<>();
    public static Map<Integer, String> locInfo = new HashMap<>(){
        {
            put(0,"Trumpland");
            put(1,"Snusland");
            put(2,"Wodkaland");
            put(3,"Motherland");
            put(4,"North Korea");
            put(5,"Weebland");
            put(6,"China numba wan");
            put(7,"Gachiland");
            put(8,"Downunder");
            put(9,"Uganda");
            put(10,"Wakanda");
            put(11,"Zimbabwe");
            put(12,"PlaceHolder");
        }
    };
    static void reset(){
        countryInfo = new HashMap<>();
    }
    static int totalArea = 0;
    CountryGen(){
        INIT();
    }
    public void INIT(){
        for (int i = 0; i < countries; i++){
            Country tmp = new Country(i,locInfo.get(i),(int)getSizeCountry(i));
            countryInfo.put(i,tmp);
            totalArea+=(int)getSizeCountry(i);
            if (i == 0) countryInfo.get(i).infected=1;
        }
        for (int i = 0; i < countries; i++){
            countryInfo.get(i).INIT();
        }
    }
    public static int getPop(){
        int out = 0;
        for (int i = 0; i < countries; i++){
            out+=countryInfo.get(i).inhabitant;
        }
        return out;
    }
    public double getSizeCountry(int i){
        int x1;
        int x2;
        int y1;
        int y2;
        if (i != 0){
            x1 = MapGen.mapOX.get(i).get(MapGen.mapOX.get(i).size()-1);
            x2 = MapGen.mapOX.get(i).get(MapGen.mapOX.get(i).size()-3);
            y1 = MapGen.mapOY.get(i).get(MapGen.mapOY.get(i).size()-1);
            y2 = MapGen.mapOY.get(i).get(MapGen.mapOY.get(i).size()-3);
        }
        else{
            x1 = MapGen.mapOX.get(i).get(0);
            x2 = MapGen.mapOX.get(i).get(MapGen.mapOX.get(i).size()-2);
            y1 = MapGen.mapOY.get(i).get(0);
            y2 = MapGen.mapOY.get(i).get(MapGen.mapOY.get(i).size()-2);
        }
        int x = Math.abs(x1-x2);
        int y = Math.abs(y1-y2);
        double base = Math.sqrt(x*x+y*y);
        int xh = Math.abs((x1+x2)/2-MapGen.cenX);
        int yh = Math.abs((y1+y2)/2-MapGen.cenY);
        double height = Math.sqrt(xh*xh+yh*yh);
        return base*height/2;
    }
}
class Country{
    Map<Integer, Integer> air = new HashMap<>();
    //
    static int gold = 100000000;
    int key;
    double spreadRate = Math.pow(2,(1/DIFF.scaleVirus()));
    double spreadTopup = 1;
    int mxPop = 1000000;
    int mxWealth = 20000000;
    String name;
    int inhabitant;
    int infected = 0;
    int wealth;
    int area;
    int virusMax = 100, dodgeChance = 0;
    int infP = 0;
    //
    int curfew = 0;
    double transMx = 1.2, transMn = 0.7;
    int land, sea;
    int[] lvl = {0,0,0,0,0,0,0,0,0,0};
    Country(int k, String n, int a){key=k;name=n; area=a;}
    public void INIT(){
        inhabitant = (int)(DIFF.scalePop()*area/CountryGen.totalArea*mxPop);
        wealth = (int)(DIFF.scaleWealth()*area/CountryGen.totalArea*mxWealth+ inhabitant /DIFF.scaleEarn());
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()){
            air.put(e.getKey(),0);
        }
    }
    public void wealth(){
        wealth = (int)(DIFF.scaleWealth()*area/CountryGen.totalArea*mxWealth+ inhabitant /DIFF.scaleEarn());
    }
    public void simVirus(){
        if (curfew>0){curfew--;return;}
        lvl[8]=0;
        double mask = Upgrade.itemMod.get(0)[lvl[0]];
        double hand = Upgrade.itemMod.get(1)[lvl[1]];
        infP = infected;
        int virus = (int)(Math.random()*(virusMax-mask-hand));
        double dodge = Upgrade.itemMod.get(6)[lvl[6]];
        if (virus < dodgeChance+dodge) return;
        if (infected>=inhabitant){infected=inhabitant;return;}
        int tmp = (int)(infected*spreadRate);
        if (tmp==infected){
            spreadTopup*=spreadRate;
            if (spreadTopup >= 2){infected*=spreadTopup;spreadTopup-=(int)spreadTopup;spreadTopup+=1;}
        }
        else {infected*=spreadRate;}
    }
    public void cureVirus(){
        if (infected <= 0){return;}
        double staff = Upgrade.itemMod.get(4)[lvl[4]];
        if (infected-staff < 0) {infected = 0;gold+=staff*DIFF.scaleSave();return;}
        infected-=staff;
        double hospital = Upgrade.itemMod.get(5)[lvl[5]];
        if (infected-(inhabitant*hospital/100) < 0) infected = 0;
        else {infected-=(inhabitant*hospital/100);}
        gold += (staff+(inhabitant*hospital/100))*DIFF.scaleSave();
    }
    public int getEarn(){
        return (int)(inhabitant/DIFF.scaleEarn());
    }
    public void inChange(int a){
        inhabitant +=a;
    }
    public void viChange(int a){
        infected += a;
    }
    //1% Land, from l or -1
    public void upLand(){
        int from;
        if (key == 0){from=CountryGen.countryInfo.size()-1;}
        else from=key-1;
        //parametres
        if (infected/inhabitant > .3){
            land = 0;
            return;
        }
        //cal
        double mod = (Math.random()*(transMx-transMn))+transMn;
        int out = (int)(mod*CountryGen.countryInfo.get(from).inhabitant/100);
        land=out;
        //inh changes
        inChange(out);
        CountryGen.countryInfo.get(from).inChange(-out);
        //virus changes
        double viChance = Math.random()*(Upgrade.itemMod.get(2)[lvl[2]]+Upgrade.itemMod.get(3)[lvl[3]]);
        double viMod = 1-viChance;
        double per = 100*CountryGen.countryInfo.get(from).infected/CountryGen.countryInfo.get(from).inhabitant;
        int vi = (int)(out*per*viMod/100);
        viChange(vi);
        CountryGen.countryInfo.get(from).viChange(-vi);
    }
    //1% Sea, from m or +1
    public void upSea(){
        int from;
        if (key == CountryGen.countryInfo.size()-1){from=0;}
        else from=key+1;
        //parametres
        if (infected/inhabitant > .5){
            sea = 0;
            return;
        }
        //cal
        double mod = (Math.random()*(transMx-transMn))+transMn;
        int out = (int)(mod*CountryGen.countryInfo.get(from).inhabitant/100);
        sea=out;
        //changes
        inChange(out);
        CountryGen.countryInfo.get(from).inChange(-out);
        //virus changes
        double viChance = Math.random()*(Upgrade.itemMod.get(2)[lvl[2]]+Upgrade.itemMod.get(3)[lvl[3]]);
        double viMod = 1-viChance;
        double per = 100*CountryGen.countryInfo.get(from).infected/CountryGen.countryInfo.get(from).inhabitant;
        int vi = (int)(out*per*viMod/100);
        viChange(vi);
        CountryGen.countryInfo.get(from).viChange(-vi);
    }
    //0.2% Air, all, this to all
    public void upAir(){
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()){
            //parametres
            if (infected/inhabitant > .2){
                air.put(e.getKey(),0);
                continue;
            }
            //cal
            double mod = (Math.random()*(transMx-transMn))+transMn;
            int out = (int)((mod)*(2*e.getValue().inhabitant/1000));
            air.put(e.getKey(),out);
            //changes
            inChange(out);
            e.getValue().inChange(-out);
            //virus changes
            double viChance = Math.random()*(Upgrade.itemMod.get(2)[lvl[2]]+Upgrade.itemMod.get(3)[lvl[3]]);
            double viMod = 1-viChance;
            double per = 100*e.getValue().infected/e.getValue().inhabitant;
            int vi = (int)(out*per*viMod/100);
            viChange(vi);
            e.getValue().viChange(-vi);
        }
    }
    public String getInf(){
        //
        if (lvl[7]==0){return"NOT RESEARCH";}
        if (inhabitant==infected){return ""+infected+" people";}
        double tmp = Upgrade.itemMod.get(7)[lvl[7]];
        double lo = 1-tmp/2;
        double hi = 1+tmp/2;
        double mod = Math.random()*(hi-lo)+lo;
        infP*=(spreadRate*mod);
        infP=infP>inhabitant?inhabitant:infP;
        //System.out.println(mod + " " + infP + " " + infected);
        return ""+infP+" people";
    }
    public static boolean gameOver(){
        for (int i = 0; i < CountryGen.countryInfo.size(); i++){
            if (CountryGen.countryInfo.get(i).infected<CountryGen.countryInfo.get(i).inhabitant)return false;
        }
        return true;
    }
    public static boolean gameWon(){
        for (int i = 0; i < CountryGen.countryInfo.size(); i++){
            if (CountryGen.countryInfo.get(i).infected!=0)return false;
        }
        return true;
    }
}