import java.awt.event.*;
import javax.swing.*;

public class MyMouse implements MouseListener{
    JFrame frame;
    JLabel label;
    public static int selectArea = 0;
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println(e.getX() + " " + e.getY());
        for (int i = 0; i < MyMap.degT.size(); i++){
            int tmpy = e.getY();
            tmpy = tmpy<MapGen.cenY?Math.abs(tmpy-MapGen.cenY):-Math.abs(tmpy-MapGen.cenY);
            int tmpx = e.getX();
            tmpx = tmpx>MapGen.cenX?Math.abs(tmpx-MapGen.cenX):-Math.abs(tmpx-MapGen.cenX);
            double curDeg = Math.atan2(tmpy,tmpx);
            if (curDeg < 0) curDeg += 2*Math.PI;
            if (MyMap.degT.get(i)>MyMap.degB.get(i)){
                //top > bot
                if (MyMap.degT.get(i)>=curDeg && curDeg>=MyMap.degB.get(i)){
                    //System.out.println("Zone " + i + " at " + curDeg);
                    selectArea = i;
                    break;
                }
            } else {
                //top < bot
                if (MyMap.degT.get(i)>=curDeg && curDeg>=0 || Math.PI*2>=curDeg && curDeg>=MyMap.degB.get(i)){
                    //System.out.println("Zone " + i + " at " + curDeg);
                    selectArea = i;
                    break;
                }

            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
