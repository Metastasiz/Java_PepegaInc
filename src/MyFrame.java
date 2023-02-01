import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class MyFrame extends JFrame {
    public static void main(String[] arg){START();}
    public static void START(){new MyFrame();}
    public final static int maxFps = 5;
    public final static int botP = 0, topP = 20, rightP = 250, leftP = 0, textBoxH = 420, initTab = 20, gapH = 5, gapW = 5, imgBtnS = 40;
    public final static int inW = 800, inH = (inW * 3 / 4);
    public final static int gapWCounter = 2, gapHCounter = 1;
    public final static int mxW = inW + rightP + leftP + gapW * gapWCounter, mxH = inH + botP + topP + initTab + gapH * gapHCounter;
    private boolean run = true;
    //
    KeyListener key = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar()=='p'){
                MyClock.runChange();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
    //
    MyMap map = new MyMap();
    public static Map<Integer, MyMap2> mapUI = new HashMap<>();
    //
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel ptsPanel = new JPanel();
    JPanel moniPanel = new JPanel();
    JPanel fpsPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel upPanel = new JPanel();
    JPanel statsPanel = new JPanel();
    JPanel visitPanel = new JPanel();
    JPanel flagPanel = new JPanel();
    JLayeredPane mainMap = new JLayeredPane();
    JLabel flagImg = new JLabel();
    JButton chat = new JButton("stuff");
    JButton menu = new JButton("Menu");
    //
    public static Map<Integer, JPanel> buttonPanel = new HashMap<>() {
        {
            put(0, new JPanel());
            put(1, new JPanel());
            put(2, new JPanel());
            put(3, new JPanel());
            put(4, new JPanel());
            put(5, new JPanel());
            put(6, new JPanel());
            put(7, new JPanel());
            put(8, new JPanel());
            put(9, new JPanel());
        }
    };
    public static Map<Integer, JButton> buttonBtn = new HashMap<>() {
        {
            put(0, new JButton());
            put(1, new JButton());
            put(2, new JButton());
            put(3, new JButton());
            put(4, new JButton());
            put(5, new JButton());
            put(6, new JButton());
            put(7, new JButton());
            put(8, new JButton());
            put(9, new JButton());
        }
    };
    public static Map<Integer, JTextArea> buttonTxt = new HashMap<>() {
        {
            put(0, new JTextArea());
            put(1, new JTextArea());
            put(2, new JTextArea());
            put(3, new JTextArea());
            put(4, new JTextArea());
            put(5, new JTextArea());
            put(6, new JTextArea());
            put(7, new JTextArea());
            put(8, new JTextArea());
            put(9, new JTextArea());
        }
    };
    public static Map<Integer, ImageIcon> buttonImg = new HashMap<>() {
        {
            put(0, new ImageIcon());
            put(1, new ImageIcon());
            put(2, new ImageIcon());
            put(3, new ImageIcon());
            put(4, new ImageIcon());
            put(5, new ImageIcon());
            put(6, new ImageIcon());
            put(7, new ImageIcon());
            put(8, new ImageIcon());
            put(9, new ImageIcon());
        }
    };
    //
    JTabbedPane tab1 = new JTabbedPane();
    JTextArea flagName = new JTextArea();
    JTextArea flagStats = new JTextArea();
    JTextArea flagTrans = new JTextArea();
    JTextArea clock = new JTextArea();
    JTextArea moni = new JTextArea();
    JTextArea pts = new JTextArea();
    JTextArea fps = new JTextArea();
    JTextArea pause = new JTextArea("\"P\" to PAUSE");
    JScrollPane scroll;

    public void INIT_MAP2(){
        for (int i = 0; i < CountryGen.countryInfo.size();i++){
            mapUI.put(i,new MyMap2(i));
            mapUI.get(i).setOpaque(false);
            mapUI.get(i).setBounds(0,0, inW, inH);
        }
    }
    public MyFrame() {
        //
        INIT_MAP2();
        btnImg();
        INIT_BTN();
        INIT_SCROLL();
        //
        moni.setEditable(false);
        pts.setEditable(false);
        fps.setEditable(false);
        pause.setEditable(false);
        scroll = new JScrollPane(flagTrans, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //layout
        mainPanel.setLayout(new BorderLayout(gapW, gapH));
        topPanel.setLayout(new BorderLayout(gapW, gapH));
        topPanel.setPreferredSize(new Dimension(topP, topP));
        rightPanel.setLayout(new BorderLayout(gapW, gapH));
        upPanel.setLayout(new GridLayout(0, 2));
        flagPanel.setLayout(new BorderLayout(gapW, gapH));
        moniPanel.setLayout(new BorderLayout(gapW, gapH));
        fpsPanel.setLayout(new BorderLayout(gapW, gapH));
        ptsPanel.setLayout(new BorderLayout(gapW, gapH));
        //size
        chat.setPreferredSize(new Dimension(botP, botP));
        map.setBounds(0,0, inW, inH);
        map.setOpaque(false);
        //default flag
        moniPanel.setPreferredSize(new Dimension((inW-(menu.getWidth()+clock.getWidth()+gapW*2))/3,topP));
        fpsPanel.setPreferredSize(new Dimension((inW-(menu.getWidth()+clock.getWidth()+gapW*2))/3,topP));
        ptsPanel.setPreferredSize(new Dimension((inW-(menu.getWidth()+clock.getWidth()+gapW*2))/3,topP));
        scroll.setPreferredSize(new Dimension(rightP, textBoxH));
        flagStats.setPreferredSize(new Dimension(rightP, textBoxH));
        flagTrans.setPreferredSize(new Dimension(rightP, textBoxH * 2));
        flagName.setText(CountryGen.locInfo.get(MyMouse.selectArea));
        Image tmp = new ImageIcon("resource/flags/flag00.jpg").getImage();
        flagImg.setIcon(new ImageIcon(tmp.getScaledInstance(rightP, rightP / 2, Image.SCALE_DEFAULT)));
        //tab
        tab1.add("Upgrades", upPanel);
        tab1.add("Stats", statsPanel);
        tab1.add("Visitors", visitPanel);
        //panel management
        mainMap.add(mapUI.get(MyMouse.selectArea));
        mainMap.add(map);
        mainPanel.add(mainMap);
        //mainPanel.add(chat, BorderLayout.PAGE_END);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);
        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        topPanel.add(clock, BorderLayout.LINE_END);
        topPanel.add(menu, BorderLayout.LINE_START);
        topPanel.add(ptsPanel);
        rightPanel.add(tab1);
        rightPanel.add(flagPanel, BorderLayout.PAGE_START);
        flagPanel.add(flagName, BorderLayout.PAGE_START);
        flagPanel.add(flagImg);
        moniPanel.add(moni);
        fpsPanel.add(pause);
        fpsPanel.add(fps,BorderLayout.LINE_END);
        ptsPanel.add(moniPanel,BorderLayout.LINE_START);
        ptsPanel.add(pts);
        ptsPanel.add(fpsPanel,BorderLayout.LINE_END);
        //
        statsPanel.add(flagStats);
        visitPanel.add(scroll);
        //listener
        map.addMouseListener(new MyMouse());
        btnLis();
        //final
        setFocusable(true);
        addKeyListener(key);
        add(mainPanel);
        setSize(mxW, mxH);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        MyClock.runTrue();
        refresh();
    }
    public void refresh() {
        long start, end;
        while (run) {
            while (MyClock.isRun()) {
                start = System.currentTimeMillis();
                clock.setText(MyClock.getTime());
                COUNTRY();
                if (run){mainMap.remove(0);
                mainMap.remove(0);}
                //System.out.println((System.currentTimeMillis() - start) + " for 0 JFrame.");
                start = System.currentTimeMillis();

                mainMap.add(mapUI.get(MyMouse.selectArea));
                mainMap.add(map);
                //System.out.println((System.currentTimeMillis() - start) + " for 1 JFrame.");
                mainPanel.add(mainMap);
                refreshBtnTxt();
                try {
                    Thread.sleep(1000/ maxFps);
                } catch (Exception e) {
                }
                end = (System.currentTimeMillis() - start);
                moni.setText("Moni: $" + Country.gold);
                pts.setText("Time spent: " + MyClock.time*2 + " points");
                //yes im too lazy to think of a point system that would work in this game
                fps.setText("FPS: " + 1000/(end+1));
                if (Country.gameWon()){
                    Highscore.player = JOptionPane.showInputDialog("Please enter your name for the High Score board");
                    Highscore.hs.add(new Hiname(Highscore.player,MyClock.time));
                    Highscore.save();
                    reset();
                } else if (Country.gameOver()) {
                    JOptionPane.showMessageDialog(null,"Everyone is now a Pepega, you have lost");
                    reset();
                }
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
    }

    public void btnLis() {
        //CountryGen.countryInfo.get(MyMouse.selectArea)
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            buttonBtn.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //check if max
                    if (CountryGen.countryInfo.get(MyMouse.selectArea).lvl[finalI] >= 4) return;
                    double price = Upgrade.itemPrice.get(finalI)[CountryGen.countryInfo.get(MyMouse.selectArea).lvl[finalI]];
                    //check if has enough gold
                    if (Country.gold < price) return;
                    //-moni
                    Country.gold -= price;
                    //inc level
                    CountryGen.countryInfo.get(MyMouse.selectArea).lvl[finalI] += 1;
                }
            });
        }
        buttonBtn.get(8).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if max
                if (CountryGen.countryInfo.get(MyMouse.selectArea).lvl[8] >= 1) return;
                double price = Upgrade.itemPrice.get(8)[CountryGen.countryInfo.get(MyMouse.selectArea).lvl[8]];
                //check if has enough gold
                if (Country.gold < price) return;
                //-moni
                Country.gold -= price;
                //++curfew
                CountryGen.countryInfo.get(MyMouse.selectArea).curfew=10;
                //inc level
                CountryGen.countryInfo.get(MyMouse.selectArea).lvl[8] += 1;
            }
        });
        buttonBtn.get(9).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if max
                if (CountryGen.countryInfo.get(MyMouse.selectArea).lvl[9] >= 1) return;
                double price = Upgrade.itemPrice.get(9)[CountryGen.countryInfo.get(MyMouse.selectArea).lvl[9]];
                //check if has enough gold
                if (Country.gold < price) return;
                //-moni
                Country.gold -= price;
                //done
                Highscore.player = JOptionPane.showInputDialog("Please enter your name for the High Score board");
                Highscore.hs.add(new Hiname(Highscore.player,MyClock.time));
                Highscore.save();
                reset();
            }
        });
    }

    public void btnImg() {
        for (Map.Entry<Integer, ImageIcon> e : buttonImg.entrySet()) {
            String btnPath = "resource/icons/i" + e.getKey() + ".png";
            Image tmp = new ImageIcon(btnPath).getImage();
            ImageIcon tmpImg = new ImageIcon(tmp.getScaledInstance(imgBtnS, imgBtnS, Image.SCALE_DEFAULT));
            buttonImg.put(e.getKey(), tmpImg);
        }
    }

    public void COUNTRY() {
        int s = MyMouse.selectArea;
        String flag1 = "resource/flags/flag";
        String flag2 = ".jpg";
        if (s / 10 < 1) flag1 += 0;
        flag1 += s + flag2;
        Image tmp = new ImageIcon(flag1).getImage();
        //
        flagImg.setIcon(new ImageIcon(tmp.getScaledInstance(rightP, rightP / 2, Image.SCALE_DEFAULT)));
        flagName.setText(CountryGen.locInfo.get(s));
        String stats = ""
                + "Total Inhabitants:\n-> " + CountryGen.countryInfo.get(s).inhabitant + " people\n"
                + "\nInfected Inhabitants:\n-> " + CountryGen.countryInfo.get(s).getInf() + "\n"
                + "\nInhabitable Area:\n-> " + CountryGen.countryInfo.get(s).area + " m2\n"
                + "\nEconomical Value:\n-> $" + CountryGen.countryInfo.get(s).wealth + "\n"
                + "\nIncome per Minute:\n-> $" + CountryGen.countryInfo.get(s).getEarn() * 2;
        flagStats.setText(stats);
        //
        int nearbyL, nearbyM;
        if (s == 0) {
            nearbyL = CountryGen.countryInfo.size() - 1;
        } else {
            nearbyL = s - 1;
        }
        if (s == CountryGen.countryInfo.size() - 1) {
            nearbyM = 0;
        } else {
            nearbyM = s + 1;
        }
        String air = "";
        for (Map.Entry<Integer, Country> e : CountryGen.countryInfo.entrySet()) {
            air += "-> From " + e.getValue().name + "\n      " + CountryGen.countryInfo.get(s).air.get(e.getKey()) + " people";
            if (e.getKey() != CountryGen.countryInfo.size() - 1) {
                air += "\n";
            }
        }
        String transportStats = ""
                + "Land Transport:\n"
                + "-> From " + CountryGen.locInfo.get(nearbyL) + "\n      " + CountryGen.countryInfo.get(s).land + " people\n"
                + "\nSea  Transport:\n"
                + "-> From " + CountryGen.locInfo.get(nearbyM) + "\n      " + CountryGen.countryInfo.get(s).sea + " people\n"
                + "\nAir  Transport:\n" + air;
        flagTrans.setText(transportStats);
        //
        flagStats.setEditable(false);
        flagTrans.setEditable(false);
    }

    public void INIT_BTN() {
        JTextArea tmp;
        int s = MyMouse.selectArea;
        Country c = CountryGen.countryInfo.get(s);
        for (int i = 0; i < buttonBtn.size(); i++) {
            String t = "";
            if (i <= 7) {t += "Tier: " + c.lvl[i];
                if (Upgrade.itemPrice.get(i)[c.lvl[i]]!= 0)t+=" at ";}
            if (Upgrade.itemPrice.get(i)[c.lvl[i]]!= 0)t+="$"+Upgrade.itemPrice.get(i)[c.lvl[i]];
            buttonTxt.get(i).setText(t);
            //System.out.println(t);
            tmp = new JTextArea(Upgrade.itemName.get(i));
            tmp.setEditable(false);
            buttonBtn.put(i, new JButton(buttonImg.get(i)));
            buttonPanel.get(i).setLayout(new BorderLayout());
            buttonPanel.get(i).add(buttonTxt.get(i), BorderLayout.PAGE_START);
            buttonPanel.get(i).add(buttonBtn.get(i));
            buttonPanel.get(i).add(tmp, BorderLayout.PAGE_END);
        }
    }
    public void refreshBtnTxt(){
        int s = MyMouse.selectArea;
        Country c = CountryGen.countryInfo.get(s);
        for (int i = 0; i < buttonBtn.size(); i++) {
            String t = "";
            if (i <= 7) {t += "Tier: " + c.lvl[i];
                if (Upgrade.itemPrice.get(i)[c.lvl[i]]!= 0)t+=" at ";}
            if (Upgrade.itemPrice.get(i)[c.lvl[i]]!= 0)t+="$"+Upgrade.itemPrice.get(i)[c.lvl[i]];
            else if (i == 8){t+=c.curfew+" seconds remaining";}
            buttonTxt.get(i).setText(t);
        }
    }
    public void INIT_SCROLL() {
        for (int i = 0; i < buttonBtn.size(); i++) {
            upPanel.add(buttonPanel.get(i));
        }
    }
    public void reset(){
        MyClock.runFalse();
        MyClock.time=0;
        SwingUtilities.invokeLater(() -> new Main());
        setVisible(false);
        dispose();
        run = false;
        Frame.t.stop();
        CountryGen.reset();
        MyMap.reset();
        MapGen.reset();
    }
}