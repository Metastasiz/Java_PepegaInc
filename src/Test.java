import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Test extends JPanel{
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.red);
        g2.setStroke(new BasicStroke(50));
        g2.drawLine(10,100,60,150);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke());
        g2.drawArc(10,90,50,40,90,90);
        String path = "resource/icons/t1.png";
        Image tmp = new ImageIcon(path).getImage();
        ImageIcon a = new ImageIcon(tmp.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        a.paintIcon(this,g2,50,100);
    }
}
class mFrame extends JFrame{
    Test test = new Test();
    KeyListener k = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            System.out.println(key);
            System.out.println(e.getKeyChar());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    };
    mFrame(){

        //test.setOpaque(true);
        test.setBounds(0,0,400,400);
        JRadioButton a = new JRadioButton();

        JLabel l1 = new JLabel();
        l1.setOpaque(true);
        l1.setBackground(Color.BLACK);
        l1.setBounds(50,50,100,100);

        JLayeredPane lay = new JLayeredPane();
        lay.setBounds(0,0,400,400);

        lay.add(l1);
        lay.add(test);
        String btn1 = "resource/icons/i0";
        String btn2 = ".png";
        btn1+=btn2;
        Image tmp = new ImageIcon(btn1).getImage();
        ImageIcon imgBtn0 = new ImageIcon(tmp.getScaledInstance(50*2/3,50*2/3,Image.SCALE_DEFAULT));
        JButton button = new JButton("something",imgBtn0);
        add(lay);
        addKeyListener(k);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
    }
    public static void main(String[] arg){
        new mFrame();
    }
}
