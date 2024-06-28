package edu.hitsz.application;

import GUI.StartMenu;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lenovo
 */
public class Main {
    public static final java.awt.CardLayout cardLayout = new java.awt.CardLayout(0, 0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

     public static void main(String[] args) {

         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         JFrame frame = new JFrame("Aircraft War");
         frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
         frame.setResizable(true);
         // 设置窗口的大小和位置,居中放置
         frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                 WINDOW_WIDTH, WINDOW_HEIGHT);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         frame.add(cardPanel);

         StartMenu start = new StartMenu();
         cardPanel.add(start.getMainPanel());
         frame.setVisible(true);
     }
//    public static void main(String[] args) {
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        JFrame frame = new JFrame("Aircraft War");
//        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        frame.setResizable(true);
//        // 设置窗口的大小和位置,居中放置
//        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
//                WINDOW_WIDTH, WINDOW_HEIGHT);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       Game game = new easyGame(true);
//        frame.add(game);
//        game.action();
//
//        // StartMenu start = new StartMenu();
//        // cardPanel.add(start.getMainPanel());
//        frame.setVisible(true);
//    }
}
