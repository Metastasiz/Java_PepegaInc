import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame{
    public final static int mxW = MyFrame.mxW, mxH = MyFrame.mxH;
    JButton play;
    JButton hs;
    JButton exit;
    Main(){
        INIT();
        add(play, BorderLayout.NORTH);
        add(hs);
        add(exit, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void INIT(){
        play = new JButton("New Game");
        play.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                SwingUtilities.invokeLater(() -> new MapCustom());
                setVisible(false);
                dispose();
            }
        });
        hs = new JButton("High Score");
        hs.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                play.setText("Setting score...");
                SwingUtilities.invokeLater(() ->Highscore.show());
            }
        });
        exit = new JButton("Exit");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                play.setText("Quitting the game");
                System.exit(0);
            }
        });
    }
    public static void main(String[] arg){
        Highscore.load();
        new Thread(()->new MyClock()).start();
        SwingUtilities.invokeLater(() ->new Main());
    }
}
