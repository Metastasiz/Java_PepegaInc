import javax.swing.*;
import java.awt.*;

public class MyMap2 extends JPanel {
    static int iconSize = 30;
    private int s;
    MyMap2(int s){this.s = s;}
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < MapGen.mapOX.size(); i++){
            //init
            String path = "resource/icons/t2.png";
            Image tmp = new ImageIcon(path).getImage();
            ImageIcon a = new ImageIcon(tmp.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
            //get mid x and y
            int size = MapGen.mapOX.get(i).size();
            int midX = MapGen.mapOX.get(i).get(size/5);
            int midY = MapGen.mapOY.get(i).get(size/5);
        a.paintIcon(this,g2,midX,midY);
        }
        //lower
        //init
        String path = "resource/icons/t0.png";
        Image tmp = new ImageIcon(path).getImage();
        ImageIcon a = new ImageIcon(tmp.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
        //get x and y, lower
        int select = s==0?CountryGen.countryInfo.size()-1:s-1;
        int size = MapGen.mapOX.get(select).size();
        int midX = MapGen.mapOX.get(select).get(size/2);
        int midY = MapGen.mapOY.get(select).get(size/2);
        a.paintIcon(this,g2,midX,midY);
        //higher
        //init
        path = "resource/icons/t1.png";
        tmp = new ImageIcon(path).getImage();
        a = new ImageIcon(tmp.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
        //get x and y, higher
        select = s==CountryGen.countryInfo.size()-1?0:s+1;
        size = MapGen.mapOX.get(select).size();
        midX = MapGen.mapOX.get(select).get(size/2);
        midY = MapGen.mapOY.get(select).get(size/2);
        a.paintIcon(this,g2,midX,midY);

    }
}
