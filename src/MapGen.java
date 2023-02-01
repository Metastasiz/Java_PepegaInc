import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapGen {
    //200 full, 10 simple
    static int mul=100;
    static int h = 3*mul, w = 4*mul;
    final static int resX = MyFrame.inW/w, resY = MyFrame.inH/h;
    static int cenX, cenY;
    static int seed;
    static int countries = 13, mxC = 13;
    //Info
    static Map<Integer, Integer> mapX = new HashMap<>();
    static Map<Integer, Integer> mapY = new HashMap<>();
    static Map<Integer, Integer> mapXres = new HashMap<>();
    static Map<Integer, Integer> mapYres = new HashMap<>();
    static Map<Integer, Map<Integer, Integer>> mapOX = new HashMap<>();
    static Map<Integer, Map<Integer, Integer>> mapOY = new HashMap<>();
    CountryGen countryGen;
    //Parametres
    final static double SEA_LVL = .45;
    private int
            prime1 = 514229, prime2 = 149, prime3 = 39, prime4 = 7;
    MapGen(){seed = (int)(Math.random()*prime1);INIT();}
    MapGen(String inputSeed){seed = Integer.parseInt(inputSeed,16)%prime1;INIT();}
    //
    int loopX = 2, loopY = 10;
    double incX = loopX*2*Math.PI/h, incY = loopY*2*Math.PI/w;
    //
    int maxDot = 12, minDot = 4, curDot = seed%maxDot>minDot?seed%maxDot:minDot;
    //
    int mod1=5, mod2=5, mod3=30*curDot;
    int counter = mod1+mod2+mod3;
    int tcounter;
    //
    static void reset(){
        mapX = new HashMap<>();
        mapY = new HashMap<>();
        mapXres = new HashMap<>();
        mapYres = new HashMap<>();
        mapOX = new HashMap<>();
        mapOY = new HashMap<>();
    }
    //
    double[][] map = new double[h][w];
    public void INIT(){
        INIT_MAP();
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                map[i][j]+=mod1*((Math.sin(seed+(incX*j)+(incX*i))+1)/2);
            }
        }
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                map[i][j]+=mod2*((Math.cos(2*seed+(incX*j)+(incX*i))+1)/2);
            }
        }
        for (int k = 0; k < curDot; k++){
            int row = curDot/2<1?1:curDot/2, column = curDot/2<1?1:curDot/2;
            int yCount = k/row;
            int xCount = k%column;
            for (int i = 0; i < h; i++){
                for (int j = 0; j < w; j++){
                    int midY = h/(2*row)+(h*yCount/row), midX = w/(2*column)+(w*xCount/column);
                    int temp = Math.abs(midY-h)>midY?Math.abs(midY-h):midY;
                    double mxY = temp+.5;
                    temp = Math.abs(midX-w)>midX?Math.abs(midX-w):midX;
                    double mxX = temp+.5;
                    double vecY = i-midY+.5, vecX = j-midX+.5;
                    map[i][j]+= mod3 /curDot*(Math.sqrt(vecY*vecY + vecX*vecX)/Math.sqrt(mxY*mxY+mxX*mxX));
                }
            }
        }
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                map[i][j]/=counter;
            }
        }
        assignC();
        countryGen = new CountryGen();
    }
    public static int[] objToint(Object[] obj){
        int[] out = new int[obj.length];
        for (int i = 0; i < obj.length; i++)
            out[i] = (int)obj[i];
        return out;
    }
    public void print(){
        System.out.println(counter);
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    public void INIT_MAP(){
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                map[i][j]=0;
            }
        }
    }
    public void div(){
        cenX = resX*w/2-seed%4;
        cenY = resY*h/2+seed%6;
        int tmp = 0;
        for (int i = 0; i < countries; i++){
            int tmp2 = 0;
            for (int j = 0; tmp < (i+1)*mapX.size()/(countries); j++, tmp++){
                if (!mapOX.containsKey(i)){
                    Map<Integer,Integer> temp = new HashMap<>();
                    temp.put(j,mapX.get(tmp)*resX);
                    mapOX.put(i,temp);
                    temp = new HashMap<>();
                    temp.put(j,mapY.get(tmp)*resY);
                    mapOY.put(i,temp);
                }
                else {
                    mapOX.get(i).put(j,mapX.get(tmp)*resX);
                    mapOY.get(i).put(j,mapY.get(tmp)*resY);
                }
                tmp2 = j;
            }
            mapOX.get(i).put(tmp2+1, cenX);
            mapOY.get(i).put(tmp2+1, cenY);
            if (i != 0){
                if (i != 1){
                    mapOX.get(i).put(tmp2+2,mapOX.get(i-1).get(mapOX.get(i-1).size()-3));
                    mapOY.get(i).put(tmp2+2,mapOY.get(i-1).get(mapOY.get(i-1).size()-3));
                }
                else {
                    mapOX.get(i).put(tmp2 + 2, mapOX.get(i - 1).get(mapOX.get(i-1).size()-2));
                    mapOY.get(i).put(tmp2 + 2, mapOY.get(i - 1).get(mapOY.get(i-1).size()-2));
                }
            }
        }
        if (Frame.debug) {
            for (Map.Entry<Integer, Map<Integer, Integer>> e : mapOX.entrySet())
                System.out.println(Arrays.toString(e.getValue().values().toArray()));
            for (Map.Entry<Integer, Map<Integer, Integer>> e : mapOY.entrySet())
                System.out.println(Arrays.toString(e.getValue().values().toArray()));
        }
    }
    public void assignC(){
        tcounter = 0;
        boolean go = false;
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                if (map[y][x] < SEA_LVL){
                    //
                    if (go) {
                        if (y - mapY.get(tcounter - 1) > 1) {
                            int tmp = mapX.get(tcounter - 1);
                            for (int i = mapY.get(tcounter - 1) + 1; i < y; i++) {
                                for (int j = tmp; j >= 0; j--) {
                                    if (map[i][j] < SEA_LVL) {
                                        mapX.put(tcounter, j);
                                        mapY.put(tcounter++, i);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //
                    mapX.put(tcounter,x);
                    mapY.put(tcounter++,y);
                    go = true;
                    break;
                }
            }
        }
        for (int y = mapY.get(tcounter-1); y < h; y++){
            for (int x = w-1; x >= 0; x--){
                if (map[y][x] < SEA_LVL){
                    mapX.put(tcounter,x);
                    mapY.put(tcounter++,y);
                    break;
                }
            }
        }
        for (int x = mapX.get(tcounter-1); x >= 0; x--){
            for (int y = h-1; y >= 0; y--){
                if (map[y][x] < SEA_LVL){
                    if (mapY.get(tcounter-1) - y > 1){
                        int tmp = mapX.get(tcounter - 1);
                        for (int i = mapY.get(tcounter-1)-1; i >= y; i--){
                            for (int j = tmp; j < w; j++){
                                if (map[i][j] < SEA_LVL){
                                    mapX.put(tcounter,j);
                                    mapY.put(tcounter++,i);
                                    break;
                                }
                            }
                        }
                    }
                    mapX.put(tcounter,x);
                    mapY.put(tcounter++,y);
                    break;
                }
            }
        }
        for (int y = mapY.get(tcounter-1); y >= mapY.get(0); y--){
            for (int x = 0; x < w; x++){
                if (map[y][x] < SEA_LVL){
                    mapX.put(tcounter,x);
                    mapY.put(tcounter++,y);
                    break;
                }
            }
        }
        assignRes();
        div();
    }
    public void assignRes(){
        for (Map.Entry<Integer, Integer> e : mapX.entrySet())
        {mapXres.put(e.getKey(),e.getValue()*resX);}
        for (Map.Entry<Integer, Integer> e : mapY.entrySet())
        {mapYres.put(e.getKey(),e.getValue()*resY);}
    }
    public static int L(Map<Integer, Integer> a, int exception){
        int out = a.get(0);
        for (Map.Entry<Integer, Integer> e : a.entrySet()){
            if (e.getValue() == exception) continue;
            if (e.getValue() > out) out = e.getValue();
        }
        return out;
    }
    public static int L(Map<Integer, Integer> a){
        int out = a.get(0);
        for (Map.Entry<Integer, Integer> e : a.entrySet()){
            if (e.getValue() > out) out = e.getValue();
        }
        return out;
    }
    public static int S(Map<Integer, Integer> a, int exception){
        int out = a.get(0);
        for (Map.Entry<Integer, Integer> e : a.entrySet()){
            if (e.getValue() == exception) continue;
            if (e.getValue() < out) out = e.getValue();
        }
        return out;
    }
    public static int S(Map<Integer, Integer> a){
        int out = a.get(0);
        for (Map.Entry<Integer, Integer> e : a.entrySet()){
            if (e.getValue() < out) out = e.getValue();
        }
        return out;
    }
    public static void main(String[] arg){
        new MapGen().print();
    }
}
