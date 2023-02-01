import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Highscore extends AbstractTableModel {
    static String player = "blank";
    final static String hsSave = "resource/txt/hsSave.txt";
    static List<Hiname> hs = new ArrayList<>();
    static String[] columnName = {"Time Spent","Player"};
    static void show(){
        JTable table = new JTable();
        table.setModel(new Highscore());
        JScrollPane scroll = new JScrollPane(table);
        JButton button = new JButton("Back");
        JFrame frame = new JFrame();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Main());
                frame.setVisible(false);
                frame.dispose();
            }
        });

        frame.add(scroll);
        frame.add(button,BorderLayout.SOUTH);
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    Highscore(){
        Comparator<Hiname> comp2 = new Comp2();
        Collections.sort(hs,comp2);
    }
    static void load(){
        String temp;
        Integer out;
        try (
                BufferedReader br1 = Files.newBufferedReader(Paths.get(hsSave));
        )
        {
            while ((temp = br1.readLine()) != null) {
                String[] tempArr = temp.split("\\s+");
                try {out = Integer.parseInt(tempArr[1]);}
                catch (Exception e){out = 0;}
                hs.add(new Hiname(tempArr[0],out));
            }
        }
        catch (IOException e) {}
    }
    static void save(){
        new Highscore();
        try {
            FileWriter writer = new FileWriter(hsSave);
            for (int i = 0; i < hs.size(); i++){
                writer.write(hs.get(i).name + " ");
                String a = Integer.toString(hs.get(i).hs);
                writer.write(a);
                writer.write( "\n");
            }
            writer.close();
        } catch (IOException e) {}

    }
    public static void main(String[] arg){
        Highscore.load();
        show();
    }

    @Override
    public int getRowCount() {
        return hs.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
        return hs.get(rowIndex).hs;
        return hs.get(rowIndex).name;
    }
    @Override
    public String getColumnName(int column){
        return columnName[column];
    }
    @Override
    public Class<?> getColumnClass(int columnIndex){
        return getValueAt(0,columnIndex).getClass();
    }
}
class Hiname{
    String name = "BLANK";
    Integer hs = 0;
    Hiname(String a, Integer b){hs =b; name=a;}
}
class Comp2 implements Comparator<Hiname> {
    @Override
    public int compare(Hiname o1, Hiname o2) {
        if (o1.hs!=o2.hs)return o1.hs-o2.hs;
        else return o1.name.compareToIgnoreCase(o2.name);
    }
}
