package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hitsz.application.*;

public class StartMenu {
    private JButton Simplebutton;
    private JButton middleButton;
    private JButton hardButton;
    private JLabel muscilabel;
    private JComboBox comboBox1;
    private JPanel mainPanel;
    private JComboBox comboBox2;
    private JLabel maplabel;
    private boolean usemusic = true;
    private String mapPath ="src/images/bg.jpg";;
    private Game game;

    public StartMenu() {
        Simplebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releaseGame();
                game = new easyGame(usemusic,mapPath);
                Main.cardPanel.add(game);
                game.action();
                Main.cardLayout.last(Main.cardPanel);
                // 确保游戏面板可以获取焦点
                game.setFocusable(true);
                game.requestFocusInWindow();
            }
        });
        middleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releaseGame();
                game = new middleGame(usemusic,mapPath);
                Main.cardPanel.add(game);
                game.action();
                Main.cardLayout.last(Main.cardPanel);
                // 确保游戏面板可以获取焦点
                game.setFocusable(true);
                game.requestFocusInWindow();
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                releaseGame();
                game = new hardGame(usemusic,mapPath);
                Main.cardPanel.add(game);
                game.action();
                Main.cardLayout.last(Main.cardPanel);
                // 确保游戏面板可以获取焦点
                game.setFocusable(true);
                game.requestFocusInWindow();
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMap = (String) comboBox2.getSelectedItem();
                switch (selectedMap) {
                    case "地图1":
                        mapPath="src/images/bg.jpg";
                        break;
                    case "地图2":
                        mapPath="src/images/bg2.jpg";
                        break;
                    case "地图3":
                        mapPath="src/images/bg3.jpg";
                        break;
                    case "地图4":
                        mapPath="src/images/bg4.jpg";
                        break;
                    case "地图5":
                        mapPath="src/images/bg5.jpg";
                        break;
                    default:
                        mapPath="src/images/bg.jpg";
                        break;
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().equals("打开")) {
                    usemusic = true;
                } else {
                    usemusic = false;
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public  void releaseGame(){
        if (game != null) {
            System.out.println("释放");
            Main.cardPanel.remove(game);
            game = null;
            Main.cardPanel.revalidate();
            Main.cardPanel.repaint();
        }
    }
}
