import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MyMap extends JPanel {
    final public static int SELECT_SIZE = 20;
    public static Map<Integer, Double> degT = new HashMap<>();
    public static Map<Integer, Double> degB = new HashMap<>();
    static void reset(){
        degT = new HashMap<>();
        degB = new HashMap<>();
    }
    //MapGen map = new MapGen("hex seed here");
    MapGen map = new MapGen();
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < map.map.length; i++){
            for (int j = 0; j < map.map[0].length; j++){
                String out = "#";
                int s = (int)(16*16*map.map[i][j]);
                if (map.map[i][j] < MapGen.SEA_LVL) s = 1;
                String hex1 = Integer.toHexString(s);
                if (hex1.length()<2) hex1 = "0"+hex1;
                out += hex1;
                out += hex1;
                out += hex1;
                //System.out.println(out);
                g2.setPaint(Color.decode(out));
                g2.fillRect(j*MapGen.resX,i*MapGen.resY,MapGen.resX,MapGen.resY);
            }
        }
        g2.setPaint(Color.red);
        g2.drawPolygon(map.objToint(map.mapXres.values().toArray()),map.objToint(map.mapYres.values().toArray()),map.mapX.size());
        g2.setPaint(Color.BLUE);
        for (int i = 0; i < map.mapOX.size(); i++){
            //if (i == 0) break;
            int[] y = map.objToint(map.mapOY.get(i).values().toArray());
            int[] x = map.objToint(map.mapOX.get(i).values().toArray());
            g2.setPaint(caseColor(i));
            g2.fillPolygon(x,y,x.length);
            //not 0
            double tmpy = 0, tmpx = 0;
            double top = 0, bot = 0;
            //System.out.println("ITER"+i);
            if (i != 0){
                tmpy = map.mapOY.get(i).get(map.mapOY.get(i).size()-1);
                tmpx = map.mapOX.get(i).get(map.mapOX.get(i).size()-1);
                tmpy = tmpy<map.cenY?Math.abs(tmpy-map.cenY):-Math.abs(tmpy-map.cenY);
                tmpx = tmpx>map.cenX?Math.abs(tmpx-map.cenX):-Math.abs(tmpx-map.cenX);
                top = Math.atan2(tmpy,tmpx);
                if (top < 0) top += 2*Math.PI;
                //System.out.println("TOP: Y: " + tmpy + " X: " + tmpx + " TOPDEG: " + top);

                tmpy = map.mapOY.get(i).get(map.mapOY.get(i).size()-3);
                tmpx = map.mapOX.get(i).get(map.mapOX.get(i).size()-3);
                tmpy = tmpy<map.cenY?Math.abs(tmpy-map.cenY):-Math.abs(tmpy-map.cenY);
                tmpx = tmpx>map.cenX?Math.abs(tmpx-map.cenX):-Math.abs(tmpx-map.cenX);
                bot = Math.atan2(tmpy,tmpx);
                if (bot < 0) bot += 2*Math.PI;
                //System.out.println("BOT: Y: " + tmpy + " X: " + tmpx + " BOTDEG: " + bot);
            }
            else {
                tmpy = map.mapOY.get(i).get(0);
                tmpx = map.mapOX.get(i).get(0);
                tmpy = tmpy<map.cenY?Math.abs(tmpy-map.cenY):-Math.abs(tmpy-map.cenY);
                tmpx = tmpx>map.cenX?Math.abs(tmpx-map.cenX):-Math.abs(tmpx-map.cenX);
                top = Math.atan2(tmpy,tmpx);
                if (top < 0) top += 2*Math.PI;
                //System.out.println("TOP: Y: " + tmpy + " X: " + tmpx + " TOPDEG: " + top);

                tmpy = map.mapOY.get(i).get(map.mapOY.get(i).size()-2);
                tmpx = map.mapOX.get(i).get(map.mapOX.get(i).size()-2);
                tmpy = tmpy<map.cenY?Math.abs(tmpy-map.cenY):-Math.abs(tmpy-map.cenY);
                tmpx = tmpx>map.cenX?Math.abs(tmpx-map.cenX):-Math.abs(tmpx-map.cenX);
                bot = Math.atan2(tmpy,tmpx);
                if (bot < 0) bot += 2*Math.PI;
                //System.out.println("BOT: Y: " + tmpy + " X: " + tmpx + " BOTDEG: " + bot);
            }
            degT.put(i,top);degB.put(i,bot);
        }
    }
    public void drawNearby(Graphics2D g2){
        for (int i = 0; i < map.mapOX.size(); i++){
            int x = map.mapOX.get(i).get(map.mapOX.get(i).size()/2);
            int y = map.mapOY.get(i).get(map.mapOY.get(i).size()/2);
        }
    }
    public Color caseColor(int i){
        switch (i){
            case 0 : return Color.red;
            case 1 : return Color.yellow;
            case 2 : return Color.blue;
            case 3 : return Color.green;
            case 4 : return Color.pink;
            case 5 : return Color.ORANGE;
            case 6 : return Color.CYAN;
            case 7 : return Color.DARK_GRAY;
            case 8 : return Color.decode("#7347b5");
            case 9 : return Color.decode("#5292a3");
            case 10 : return Color.decode("#68b846");
            case 11 : return Color.decode("#b5366f");
        }
        return Color.decode("#97b536");
    }
}
