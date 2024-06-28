package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.hitsz.application.Main;
import edu.hitsz.scorepattern.User;
import edu.hitsz.scorepattern.UserDao;

public class RankList {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnbutton;
    public  String[] columnName ;
    public  ArrayList<User> users;
    public  String[][] tableData;
    public RankList(int kind, UserDao userdao) {
        System.out.println(kind);
        switch (kind) {
            case 0:
                this.headerLabel.setText("简单模式排行榜");
                break;
            case 1:
                this.headerLabel.setText("中等模式排行榜");
                break;
            case 2:
                this.headerLabel.setText("困难模式排行榜");
                break;
            default:
                this.headerLabel.setText("简单模式排行榜");
                break;
        }
        System.out.println("successfully!");
        columnName = new String[]{"名次", "姓名", "成绩", "完成时间"};
         users = userdao.getAllUser();
         tableData = new String[users.size()][4]; // 使用 ArrayList 的 size() 方法获取元素数量
        for (int i = 0; i < users.size(); i++) {
            tableData[i][0] = String.valueOf(i + 1); // 序号
            tableData[i][1] = users.get(i).getUserName(); // 使用 get 方法获取元素
            tableData[i][2] = String.valueOf(users.get(i).getUserScore());
            tableData[i][3] = users.get(i).getUserTime();
        }
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    userdao.DeleteUser(tableData[row][1]);

                    users = userdao.getAllUser();
                    tableData = new String[users.size()][4]; // 使用 ArrayList 的 size() 方法获取元素数量
                    for (int i = 0; i < users.size(); i++) {
                        tableData[i][0] = String.valueOf(i + 1); // 序号
                        tableData[i][1] = users.get(i).getUserName(); // 使用 get 方法获取元素
                        tableData[i][2] = String.valueOf(users.get(i).getUserScore());
                        tableData[i][3] = users.get(i).getUserTime();
                    }
                    DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    scoreTable.setModel(model);
                    tableScrollPanel.setViewportView(scoreTable);
                }
            }
        });
//        returnButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Main.cardLayout.first(Main.cardPanel);
//            }
//        });
        returnbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.first(Main.cardPanel);

            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
