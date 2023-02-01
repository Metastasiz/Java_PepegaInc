import javax.swing.*;
import java.awt.*;

public class MyMap3 extends JPanel {
    static int iconSize = 30;
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int s = MyMouse.selectArea;
        g2.drawLine(0,0,(s+1)*100,(s+1)*100);
    }
}
