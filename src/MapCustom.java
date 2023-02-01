import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapCustom implements ChangeListener {
    MapCustom(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label1 = new JLabel("Difficulty");
        JLabel label2 = new JLabel("Number of countries");
        JSlider diff = new JSlider(0,3,1);
        JSlider countries = new JSlider(3,MapGen.mxC,MapGen.mxC);
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                DIFF.diff=diff.getValue();
                MapGen.countries=countries.getValue();
                SwingUtilities.invokeLater(() -> new Frame());
                frame.setVisible(false);
                frame.dispose();
            }
        });

        diff.setPreferredSize(new Dimension(400,50));
        diff.setPaintTicks(true);
        diff.setSnapToTicks(true);
        diff.setMinorTickSpacing(1);
        diff.setPaintLabels(true);

        countries.setPreferredSize(new Dimension(400,50));
        countries.setPaintTicks(true);
        countries.setSnapToTicks(true);
        countries.setMinorTickSpacing(1);
        countries.setPaintLabels(true);

        //label2.setPreferredSize(new Dimension(400,50));
        panel.add(label1);
        panel.add(diff);
        panel.add(label2);
        panel.add(countries);
        frame.add(panel);
        frame.add(confirm,BorderLayout.SOUTH);
        frame.setSize(400,250);
        frame.setVisible(true);
    }
    @Override
    public void stateChanged(ChangeEvent e) {

    }
    public static void main(String[] arg){
        new MapCustom();
    }
}
