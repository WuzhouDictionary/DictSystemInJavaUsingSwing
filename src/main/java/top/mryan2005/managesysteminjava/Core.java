package top.mryan2005.managesysteminjava;
import top.mryan2005.managesysteminjava.BasicClass.LoginPart;
import top.mryan2005.managesysteminjava.BasicClass.User;
import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;
import top.mryan2005.managesysteminjava.SQLs.SQLLinker;
import top.mryan2005.managesysteminjava.Settings.Info;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import static java.lang.Integer.valueOf;

public class Core extends JFrame {

    public Info info = new Info();

    private SQLLinker sql;

    private LoginPart loginPart;

    private User currentUser;

    private boolean isLogin = false;

    public void print(String str) {
        System.out.println(str);
    }

    public String printPath() {
        String path = String.valueOf(this.getClass().getClassLoader().getResource(""));
        if(path.matches("file:(.*)/classes/")) {
            path = path.substring(6, path.length() - 15)+"src/main/resources/top/mryan2005/managesysteminjava/";
        } else if(path == "null") {
            path = "";
        }
        System.out.println(path);
        return path;
    }

    public void buildWindows() {
        ImageIcon icon=new ImageIcon(printPath()+"1.png");
        JLabel label=new JLabel(icon);
        label.setBounds(0,0,1729,972);
        Container container = getContentPane();
        container.setSize(1729,972);
        container.setLayout(new BorderLayout());
        ((JPanel)container).setOpaque(false);
        getLayeredPane().add(label, valueOf(Integer.MIN_VALUE));
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("File");
        JMenuItem jMenuItemExit = new JMenuItem("Exit");
        jMenu.add(jMenuItemExit);
        jMenuBar.add(jMenu);
        JMenu jMenu2 = new JMenu("About");
        jMenuBar.add(jMenu2);
        JMenuItem jMenuItemNewIssue = new JMenuItem("New Issue");
        jMenu2.add(jMenuItemNewIssue);
        JMenuItem jMenuItemInfo = new JMenuItem("Info");
        jMenu2.add(jMenuItemInfo);
        jMenuItemNewIssue.addActionListener(this::newIssue);
        jMenuItemExit.addActionListener(this::actionPerformed);
        jMenuItemInfo.addActionListener(this::ViewInfo);


        // build a dock bar like macOS
        // Create the dock bar
        JPanel dockBar = new JPanel();
        dockBar.setPreferredSize(new Dimension(80, 0));
        dockBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        dockBar.setBackground(new Color(238, 238, 238, 0)); // Transparent background
        dockBar.setOpaque(true);


        // Add buttons to the dock bar
        JButton button1 = new JButton(new ImageIcon(printPath() + "/icons/1.png"));
        button1.setPreferredSize(new Dimension(50, 50));
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setOpaque(true);
        button1.setVisible(true);
        button1.addActionListener(e -> {
            JDialog jDialog = new JDialog(this, "Dictionary", true);
            jDialog.setBounds(0, 0, 300, 500);
            jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jDialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            Insets insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = insets;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            JButton jButtonSearch = new JButton("搜索");
            jButtonSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createSearchWindow();
                }
            });
            jDialog.add(jButtonSearch, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            JButton jButtonViewAll = new JButton("查看");
            jButtonViewAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createViewWindow();
                }
            });
            jDialog.add(jButtonViewAll, gbc);

            jDialog.setVisible(true);
        });
        dockBar.add(button1);
        button1.requestFocus();

        JButton button2 = new JButton(new ImageIcon(printPath() + "/icons/2.png"));
        button2.setPreferredSize(new Dimension(50, 50));
        button2.setContentAreaFilled(false);
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setOpaque(true);
        button2.setVisible(true);
        button2.addActionListener(e -> {
            final int[] needDelete = {-1};
            final int[] needUpdate = {-1};
            JDialog jDialog = new JDialog(this, "Admin", true);
            jDialog.setBounds(0, 0, 300, 500);
            jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jDialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            Insets insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = insets;
            JButton jButtonShowAll = new JButton("展示所有字词");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonShowAll, gbc);
            jButtonShowAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog jDialog1 = new JDialog(jDialog, "查看所有词条", true);
                    jDialog1.setBounds(0, 0, 800, 500);
                    jDialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jDialog1.setLayout(new FlowLayout());
                    String[] columnNames = {"id", "简体字", "繁体字", "梧州读音", "苍梧石桥读音", "蒙山读音"};
                    List<Object[]> l = new ArrayList<>();
                    try {
                        ResultSet resultSet = sql.runSQL("SELECT * FROM entry.viewAll ORDER BY id");
                        while(resultSet.next()) {
                            l.add(new Object[]{resultSet.getString("id"), resultSet.getString("simplified_Chinese_character"), resultSet.getString("traditional_Chinese_character"), resultSet.getString("Pronunciation_of_Wuzhou"), resultSet.getString("Pronunciation_of_Cangwu_Shiqiao"), resultSet.getString("Pronunciation_of_Mengshan")});
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    JTable jTable = new JTable(l.toArray(new Object[0][0]), columnNames);
                    JScrollPane jScrollPane = new JScrollPane(jTable);
                    jDialog1.add(jScrollPane);
                    jDialog1.setVisible(true);
                }
            });
            JButton jButtonAdd = new JButton("添加词条");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonAdd, gbc);
            jButtonAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog jDialog1 = new JDialog(jDialog);
                    jDialog1.setSize(800, 600);
                    jDialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    jDialog1.setLayout(new GridBagLayout());
                    GridBagConstraints gridBagConstraints = new GridBagConstraints();
                    gridBagConstraints.fill = GridBagConstraints.BOTH;
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.weighty = 1;
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.gridheight = 1;
                    gridBagConstraints.insets = new Insets(5, 5, 5, 5);
                    jDialog1.add(new JLabel("简体中文字符"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldSimplifiedChineseCharacter = new JTextField("" ,100);
                    jDialog1.add(jTextFieldSimplifiedChineseCharacter, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("繁体中文字符"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldTraditionalChineseCharacter = new JTextField("", 100);
                    jDialog1.add(jTextFieldTraditionalChineseCharacter, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("梧州话发音"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldPoWuzhou = new JTextField("", 100);
                    jDialog1.add(jTextFieldPoWuzhou, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("苍梧石桥话发音"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldPoCangwuShiqiao = new JTextField("", 100);
                    jDialog1.add(jTextFieldPoCangwuShiqiao, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("蒙山话发音"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldPoMengshan = new JTextField("", 100);
                    jDialog1.add(jTextFieldPoMengshan, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("同类异文"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldHeterozygousAncientTextsOfTheSameType = new JTextField("", 100);
                    jDialog1.add(jTextFieldHeterozygousAncientTextsOfTheSameType, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("简体部首"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldRadicalSimplified = new JTextField("", 100);
                    jDialog1.add(jTextFieldRadicalSimplified, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 7;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("繁体部首"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldRadicalTraditional = new JTextField("", 100);
                    jDialog1.add(jTextFieldRadicalTraditional, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 8;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("偏旁部首笔画数（简体）"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldTotalNumberOfRadicalStrokesSimplified = new JTextField("", 100);
                    jDialog1.add(jTextFieldTotalNumberOfRadicalStrokesSimplified, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 9;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("偏旁部首笔画数（繁体）"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldTotalNumberOfRadicalStrokesTraditional = new JTextField("", 100);
                    jDialog1.add(jTextFieldTotalNumberOfRadicalStrokesTraditional, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 10;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("笔画数（简体）"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldTotalNumberOfStrokesSimplified = new JTextField("", 100);
                    jDialog1.add(jTextFieldTotalNumberOfStrokesSimplified, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 11;
                    gridBagConstraints.gridwidth = 1;
                    jDialog1.add(new JLabel("笔画数（繁体）"), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridwidth = 3;
                    JTextField jTextFieldTotalNumberOfStrokesTraditional = new JTextField("", 100);
                    jDialog1.add(jTextFieldTotalNumberOfStrokesTraditional, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 12;
                    gridBagConstraints.gridwidth = 1;
                    JButton jButton = new JButton("保存");
                    jDialog1.add(jButton, gridBagConstraints);
                    jButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String simplified_Chinese_character, traditional_Chinese_character, Pronunciation_of_Wuzhou, Pronunciation_of_Cangwu_Shiqiao, Pronunciation_of_Mengshan, Heterozygous_Ancient_Texts_of_the_Same_Type, Radical_simplified, Radical_traditional;
                            int total_number_of_radical_strokes_simplified, total_number_of_radical_strokes_traditional, total_number_of_strokes_simplified, total_number_of_strokes_traditional;
                            simplified_Chinese_character = jTextFieldSimplifiedChineseCharacter.getText();
                            traditional_Chinese_character = jTextFieldTraditionalChineseCharacter.getText();
                            Pronunciation_of_Wuzhou = jTextFieldPoWuzhou.getText();
                            Pronunciation_of_Cangwu_Shiqiao = jTextFieldPoCangwuShiqiao.getText();
                            Pronunciation_of_Mengshan = jTextFieldPoMengshan.getText();
                            Heterozygous_Ancient_Texts_of_the_Same_Type = jTextFieldHeterozygousAncientTextsOfTheSameType.getText();
                            Radical_simplified = jTextFieldRadicalSimplified.getText();
                            Radical_traditional = jTextFieldRadicalTraditional.getText();
                            total_number_of_radical_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesSimplified.getText());
                            total_number_of_radical_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfRadicalStrokesTraditional.getText());
                            total_number_of_strokes_simplified = Integer.parseInt(jTextFieldTotalNumberOfStrokesSimplified.getText());
                            total_number_of_strokes_traditional = Integer.parseInt(jTextFieldTotalNumberOfStrokesTraditional.getText());
                            int maxId = 0;
                            try {
                                ResultSet resultSet = sql.runSQL("SELECT MAX(id) FROM entry.[main]");
                                while(resultSet.next()) {
                                    maxId = resultSet.getInt(1);
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            if(simplified_Chinese_character.equals("") || traditional_Chinese_character.equals("") || Pronunciation_of_Wuzhou.equals("") || Pronunciation_of_Cangwu_Shiqiao.equals("") || Pronunciation_of_Mengshan.equals("") || Heterozygous_Ancient_Texts_of_the_Same_Type.equals("") || Radical_simplified.equals("") || Radical_traditional.equals("")) {
                                JOptionPane.showMessageDialog(jDialog1, "请填写完整");
                                return;
                            }
                            sql.runSQL("INSERT INTO entry.[main] (id, simplified_Chinese_character, traditional_Chinese_character, Pronunciation_of_Wuzhou, Pronunciation_of_Cangwu_Shiqiao, Pronunciation_of_Mengshan, Heterozygous_Ancient_Texts_of_the_Same_Type, Radical_simplified, Radical_traditional, total_number_of_strokes_simplified, total_number_of_strokes_traditional, total_number_of_radical_strokes_simplified, total_number_of_radical_strokes_traditional) VALUES (" + (maxId + 1) + ", '" + simplified_Chinese_character + "', '" + traditional_Chinese_character + "', '" + Pronunciation_of_Wuzhou + "', '" + Pronunciation_of_Cangwu_Shiqiao + "', '" + Pronunciation_of_Mengshan + "', '" + Heterozygous_Ancient_Texts_of_the_Same_Type + "', '" + Radical_simplified + "', '" + Radical_traditional + "', " + total_number_of_strokes_simplified + ", " + total_number_of_strokes_traditional + ", " + total_number_of_radical_strokes_simplified + ", " + total_number_of_radical_strokes_traditional + ")");
                            jDialog1.dispose();
                        }
                    });
                    jDialog1.setVisible(true);
                }
            });

            JButton jButtonDelete = new JButton("删除词条");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonDelete, gbc);
            jButtonDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog jDialog1 = new JDialog(jDialog);
                    JDialog jDialog2 = new JDialog(jDialog1);
                    jDialog1.setSize(800, 600);
                    jDialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    jDialog1.setLayout(new GridBagLayout());
                    jDialog2.setSize(800, 600);
                    jDialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    jDialog2.setLayout(new GridBagLayout());
                    GridBagConstraints gridBagConstraints = new GridBagConstraints();
                    gridBagConstraints.fill = GridBagConstraints.BOTH;
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.weighty = 1;
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.gridheight = 1;
                    JTextField jTextField = new JTextField("", 100);
                    jDialog2.add(jTextField, gridBagConstraints);
                    String[] columnNames = {"id", "简体字", "繁体字", "梧州读音", "苍梧石桥读音", "蒙山读音"};
                    DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames) {
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable jTable = new JTable(model);
                    JScrollPane jScrollPane = new JScrollPane(jTable);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.gridheight = 1;
                    gridBagConstraints.weightx = 0;
                    gridBagConstraints.weighty = 0;
                    jDialog1.add(jScrollPane, gridBagConstraints);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.gridheight = 1;
                    gridBagConstraints.weightx = 1;
                    gridBagConstraints.weighty = 1;
                    JButton jButton = new JButton("搜索");
                    JButton jButton1 = new JButton("删除");
                    jButton1.setEnabled(false);
                    final int[] listSize = {0};
                    jButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            model.setRowCount(0);
                            List<Integer> list = new ArrayList<>();
                            if(jTextField.getText().equals("")) {
                                JOptionPane.showMessageDialog(jDialog2, "请输入要删除的词条");
                                return;
                            }
                            ResultSet resultSet = sql.runSQL("SELECT * FROM entry.[main] WHERE simplified_Chinese_character = '" + jTextField.getText() + "'");
                            try {
                                while(resultSet.next()) {
                                    list.add(resultSet.getInt("id"));
                                }
                                listSize[0] = list.size();
                                List<Object[]> l = new ArrayList<>();
                                for(int i = 0; i < list.size(); i++) {
                                    try {
                                        ResultSet resultSet1 = sql.runSQL("SELECT * FROM entry.[main] WHERE id = " + list.get(i));
                                        while (resultSet1.next()) {
                                            l.add(new Object[]{resultSet1.getInt("id"), resultSet1.getString("simplified_Chinese_character"), resultSet1.getString("traditional_Chinese_character"), resultSet1.getString("Pronunciation_of_Wuzhou"), resultSet1.getString("Pronunciation_of_Cangwu_Shiqiao"), resultSet1.getString("Pronunciation_of_Mengshan")});
                                        }
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                                for(int i = 0; i < l.size(); i++) {
                                    model.addRow(l.get(i));
                                }
                                jButton1.setEnabled(true);
                                needDelete[0] = -1;
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            if(list.size() == 0) {
                                JOptionPane.showMessageDialog(jDialog2, "未找到该词条");
                                return;
                            }
                            jDialog2.dispose();
                        }
                    });
                    jDialog2.add(jButton, gridBagConstraints);
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(new FlowLayout());
                    jButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if(listSize[0] == 0) {
                                JOptionPane.showMessageDialog(jDialog1, "未找到该词条");
                                return;
                            }
                            if(needDelete[0] != -1) {
                                sql.runSQL("DELETE FROM entry.[main] WHERE id = " + needDelete[0]);
                                jDialog1.dispose();
                                needDelete[0] = -1;
                            } else {
                                JOptionPane.showMessageDialog(jDialog1, "请选择要删除的词条");
                            }
                        }
                    });
                    jDialog1.add(jButton1, gridBagConstraints);
                    JButton jButton2 = new JButton("查找");
                    jButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jDialog2.setVisible(true);
                        }
                    });
                    jTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int row = jTable.getSelectedRow();
                            needDelete[0] = (Integer) jTable.getValueAt(row, 0);
                        }
                    });
                    jPanel.add(jButton1);
                    jPanel.add(jButton2);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.gridwidth = 1;
                    gridBagConstraints.gridheight = 1;
                    gridBagConstraints.weightx = 0;
                    gridBagConstraints.weighty = 0;
                    jDialog1.add(jPanel, gridBagConstraints);
                    jDialog1.setVisible(true);
                }
            });
            JButton jButtonUpdate = new JButton("更新词条");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonUpdate, gbc);
            jButtonUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog jDialog1 = new JDialog(jDialog);
                    jDialog1.setSize(800, 600);
                    jDialog1.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    jDialog1.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 1;
                    gbc.weighty = 1;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    DefaultTableModel model = new DefaultTableModel(new Object[0][0], new String[]{"id", "简体字", "繁体字", "梧州读音", "苍梧石桥读音", "蒙山读音"}) {
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable jTable = new JTable(model);
                    JScrollPane jScrollPane = new JScrollPane(jTable);
                    jDialog1.add(jScrollPane, gbc);
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(new FlowLayout());
                    JButton jButton = new JButton("查找");
                    JButton jButton1 = new JButton("更新");
                    jButton1.setEnabled(false);
                    jButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            model.setRowCount(0);
                            List<Integer> list = new ArrayList<>();
                            JDialog jDialog2 = new JDialog(jDialog1);
                            jDialog2.setSize(800, 600);
                            jDialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            jDialog2.setLayout(new FlowLayout());
                            JTextField jTextField = new JTextField("", 20);
                            jDialog2.add(jTextField);
                            JButton jButton = new JButton("搜索");
                            jDialog2.add(jButton);
                            jButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (jTextField.getText().equals("")) {
                                        JOptionPane.showMessageDialog(jDialog2, "请输入要更新的词条");
                                        return;
                                    }
                                    ResultSet resultSet = sql.runSQL("SELECT * FROM entry.[main] WHERE simplified_Chinese_character = '" + jTextField.getText() + "'");
                                    try {
                                        while (resultSet.next()) {
                                            list.add(resultSet.getInt("id"));
                                        }
                                        List<Object[]> l = new ArrayList<>();
                                        for (int i = 0; i < list.size(); i++) {
                                            try {
                                                ResultSet resultSet1 = sql.runSQL("SELECT * FROM entry.[main] WHERE id = " + list.get(i));
                                                while (resultSet1.next()) {
                                                    l.add(new Object[]{resultSet1.getInt("id"), resultSet1.getString("simplified_Chinese_character"), resultSet1.getString("traditional_Chinese_character"), resultSet1.getString("Pronunciation_of_Wuzhou"), resultSet1.getString("Pronunciation_of_Cangwu_Shiqiao"), resultSet1.getString("Pronunciation_of_Mengshan")});
                                                }
                                            } catch (SQLException throwables) {
                                                throwables.printStackTrace();
                                            }
                                        }
                                        for (int i = 0; i < l.size(); i++) {
                                            model.addRow(l.get(i));
                                        }
                                        jButton1.setEnabled(true);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    if (list.size() == 0) {
                                        JOptionPane.showMessageDialog(jDialog2, "未找到该词条");
                                        return;
                                    }
                                    jDialog2.dispose();
                                }
                            });
                            jDialog2.setVisible(true);
                        }
                    });
                    jTable.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int row = jTable.getSelectedRow();
                            needUpdate[0] = (Integer) jTable.getValueAt(row, 0);
                        }
                    });
                    jButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(needUpdate[0] == -1) {
                                JOptionPane.showMessageDialog(jDialog1, "请选择要更新的词条");
                                return;
                            }
                            ResultSet resultSet = sql.runSQL("SELECT * FROM entry.[main] WHERE id = " + needUpdate[0]);
                            try {
                                resultSet.next();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            JDialog jDialog2 = new JDialog(jDialog1);
                            jDialog2.setSize(800, 600);
                            jDialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            jDialog2.setLayout(new GridBagLayout());
                            GridBagConstraints gridBagConstraints = new GridBagConstraints();
                            gridBagConstraints.fill = GridBagConstraints.BOTH;
                            gridBagConstraints.weightx = 1;
                            gridBagConstraints.weighty = 1;
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 0;
                            gridBagConstraints.gridwidth = 1;
                            gridBagConstraints.gridheight = 1;
                            jDialog2.add(new JLabel("简体中文字符"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldSimplifiedChineseCharacter = null;
                            try {
                                jTextFieldSimplifiedChineseCharacter = new JTextField(resultSet.getString("simplified_Chinese_character"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldSimplifiedChineseCharacter, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 1;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("繁体中文字符"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldTraditionalChineseCharacter = null;
                            try {
                                jTextFieldTraditionalChineseCharacter = new JTextField(resultSet.getString("traditional_Chinese_character"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldTraditionalChineseCharacter, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 2;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("梧州话发音"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldPoWuzhou = null;
                            try {
                                jTextFieldPoWuzhou = new JTextField(resultSet.getString("Pronunciation_of_Wuzhou"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldPoWuzhou, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 3;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("苍梧石桥话发音"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldPoCangwuShiqiao = null;
                            try {
                                jTextFieldPoCangwuShiqiao = new JTextField(resultSet.getString("Pronunciation_of_Cangwu_Shiqiao"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldPoCangwuShiqiao, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 4;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("蒙山话发音"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 4;
                            JTextField jTextFieldPoMengshan = null;
                            try {
                                jTextFieldPoMengshan = new JTextField(resultSet.getString("Pronunciation_of_Mengshan"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldPoMengshan, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 5;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("同类异文"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldHeterozygousAncientTextsOfTheSameType = null;
                            try {
                                jTextFieldHeterozygousAncientTextsOfTheSameType = new JTextField(resultSet.getString("Heterozygous_Ancient_Texts_of_the_Same_Type"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldHeterozygousAncientTextsOfTheSameType, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 6;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("简体部首"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldRadicalSimplified = null;
                            try {
                                jTextFieldRadicalSimplified = new JTextField(resultSet.getString("Radical_simplified"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldRadicalSimplified, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 7;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("繁体部首"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldRadicalTraditional = null;
                            try {
                                jTextFieldRadicalTraditional = new JTextField(resultSet.getString("Radical_traditional"), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldRadicalTraditional, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 8;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("偏旁部首笔画数（简体）"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldTotalNumberOfRadicalStrokesSimplified = null;
                            try {
                                jTextFieldTotalNumberOfRadicalStrokesSimplified = new JTextField(String.valueOf(resultSet.getInt("total_number_of_radical_strokes_simplified")), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldTotalNumberOfRadicalStrokesSimplified, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 9;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("偏旁部首笔画数（繁体）"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldTotalNumberOfRadicalStrokesTraditional = null;
                            try {
                                jTextFieldTotalNumberOfRadicalStrokesTraditional = new JTextField(String.valueOf(resultSet.getInt("total_number_of_radical_strokes_traditional")), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldTotalNumberOfRadicalStrokesTraditional, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 10;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("笔画数（简体）"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldTotalNumberOfStrokesSimplified = null;
                            try {
                                jTextFieldTotalNumberOfStrokesSimplified = new JTextField(String.valueOf(resultSet.getInt("total_number_of_strokes_simplified")), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldTotalNumberOfStrokesSimplified, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 11;
                            gridBagConstraints.gridwidth = 1;
                            jDialog2.add(new JLabel("笔画数（繁体）"), gridBagConstraints);
                            gridBagConstraints.gridx = 1;
                            gridBagConstraints.gridwidth = 3;
                            JTextField jTextFieldTotalNumberOfStrokesTraditional = null;
                            try {
                                jTextFieldTotalNumberOfStrokesTraditional = new JTextField(String.valueOf(resultSet.getInt("total_number_of_strokes_traditional")), 100);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            jDialog2.add(jTextFieldTotalNumberOfStrokesTraditional, gridBagConstraints);
                            gridBagConstraints.gridx = 0;
                            gridBagConstraints.gridy = 12;
                            gridBagConstraints.gridwidth = 1;
                            JButton jButton = new JButton("保存");
                            jDialog2.add(jButton, gridBagConstraints);
                            JTextField finalJTextFieldSimplifiedChineseCharacter = jTextFieldSimplifiedChineseCharacter;
                            JTextField finalJTextFieldTraditionalChineseCharacter = jTextFieldTraditionalChineseCharacter;
                            JTextField finalJTextFieldPoWuzhou = jTextFieldPoWuzhou;
                            JTextField finalJTextFieldPoCangwuShiqiao = jTextFieldPoCangwuShiqiao;
                            JTextField finalJTextFieldPoMengshan = jTextFieldPoMengshan;
                            JTextField finalJTextFieldHeterozygousAncientTextsOfTheSameType = jTextFieldHeterozygousAncientTextsOfTheSameType;
                            JTextField finalJTextFieldRadicalSimplified = jTextFieldRadicalSimplified;
                            JTextField finalJTextFieldRadicalTraditional = jTextFieldRadicalTraditional;
                            JTextField finalJTextFieldTotalNumberOfRadicalStrokesSimplified = jTextFieldTotalNumberOfRadicalStrokesSimplified;
                            JTextField finalJTextFieldTotalNumberOfRadicalStrokesTraditional = jTextFieldTotalNumberOfRadicalStrokesTraditional;
                            JTextField finalJTextFieldTotalNumberOfStrokesSimplified = jTextFieldTotalNumberOfStrokesSimplified;
                            JTextField finalJTextFieldTotalNumberOfStrokesTraditional = jTextFieldTotalNumberOfStrokesTraditional;
                            jButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String simplified_Chinese_character, traditional_Chinese_character, Pronunciation_of_Wuzhou, Pronunciation_of_Cangwu_Shiqiao, Pronunciation_of_Mengshan, Heterozygous_Ancient_Texts_of_the_Same_Type, Radical_simplified, Radical_traditional;
                                    int total_number_of_radical_strokes_simplified, total_number_of_radical_strokes_traditional, total_number_of_strokes_simplified, total_number_of_strokes_traditional;
                                    try {
                                        simplified_Chinese_character = finalJTextFieldSimplifiedChineseCharacter.getText();
                                        traditional_Chinese_character = finalJTextFieldTraditionalChineseCharacter.getText();
                                        Pronunciation_of_Wuzhou = finalJTextFieldPoWuzhou.getText();
                                        Pronunciation_of_Cangwu_Shiqiao = finalJTextFieldPoCangwuShiqiao.getText();
                                        Pronunciation_of_Mengshan = finalJTextFieldPoMengshan.getText();
                                        Heterozygous_Ancient_Texts_of_the_Same_Type = finalJTextFieldHeterozygousAncientTextsOfTheSameType.getText();
                                        Radical_simplified = finalJTextFieldRadicalSimplified.getText();
                                        Radical_traditional = finalJTextFieldRadicalTraditional.getText();
                                        total_number_of_radical_strokes_simplified = Integer.parseInt(finalJTextFieldTotalNumberOfRadicalStrokesSimplified.getText());
                                        total_number_of_radical_strokes_traditional = Integer.parseInt(finalJTextFieldTotalNumberOfRadicalStrokesTraditional.getText());
                                        total_number_of_strokes_simplified = Integer.parseInt(finalJTextFieldTotalNumberOfStrokesSimplified.getText());
                                        total_number_of_strokes_traditional = Integer.parseInt(finalJTextFieldTotalNumberOfStrokesTraditional.getText());
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(jDialog2, "请输入正确的数字");
                                        return;
                                    } catch (NullPointerException ex) {
                                        JOptionPane.showMessageDialog(jDialog2, "请填写完整");
                                        return;
                                    }
                                    if (simplified_Chinese_character.equals("") || traditional_Chinese_character.equals("") || Pronunciation_of_Wuzhou.equals("") || Pronunciation_of_Cangwu_Shiqiao.equals("") || Pronunciation_of_Mengshan.equals("") || Heterozygous_Ancient_Texts_of_the_Same_Type.equals("") || Radical_simplified.equals("") || Radical_traditional.equals("")) {
                                        JOptionPane.showMessageDialog(jDialog2, "请填写完整");
                                        return;
                                    }
                                    sql.runSQL("UPDATE entry.[main] SET simplified_Chinese_character = '" + simplified_Chinese_character + "', traditional_Chinese_character = '" + traditional_Chinese_character + "', Pronunciation_of_Wuzhou = '" + Pronunciation_of_Wuzhou + "', Pronunciation_of_Cangwu_Shiqiao = '" + Pronunciation_of_Cangwu_Shiqiao + "', Pronunciation_of_Mengshan = '" + Pronunciation_of_Mengshan + "', Heterozygous_Ancient_Texts_of_the_Same_Type = '" + Heterozygous_Ancient_Texts_of_the_Same_Type + "', Radical_simplified = '" + Radical_simplified + "', Radical_traditional = '" + Radical_traditional + "', total_number_of_strokes_simplified = " + total_number_of_strokes_simplified + ", total_number_of_strokes_traditional = " + total_number_of_strokes_traditional + ", total_number_of_radical_strokes_simplified = " + total_number_of_radical_strokes_simplified + ", total_number_of_radical_strokes_traditional = " + total_number_of_radical_strokes_traditional + " WHERE id = " + needUpdate[0]);
                                    jDialog2.dispose();
                                }
                            });
                            jDialog2.setVisible(true);
                        }
                    });
                    jDialog1.add(jButton, gbc);
                    jPanel.add(jButton1);
                    jPanel.add(jButton);
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    gbc.gridheight = 1;
                    jDialog1.add(jPanel, gbc);
                    jDialog1.setVisible(true);
                }
            });
            jDialog.setVisible(true);
        });
        dockBar.add(button2);

        JButton button3 = new JButton(new ImageIcon(printPath() + "/icons/3.png"));
        button3.setPreferredSize(new Dimension(50, 50));
        button3.setContentAreaFilled(false);
        button3.setBorderPainted(false);
        button3.setFocusPainted(false);
        button3.setOpaque(true);
        button3.setVisible(true);
        button3.addActionListener(e -> {
            JDialog jDialog = new JDialog(this, "Settings", true);
            jDialog.setBounds(0, 0, 300, 500);
            jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jDialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            Insets insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = insets;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            JButton jButtonLogin = new JButton("Login");
            jDialog.add(jButtonLogin, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            JButton jButtonRegister = new JButton("Register");
            JButton jButtonUserInfo = new JButton("用户个人信息");
            JButton jButtonLogout = new JButton("登出");
            jButtonRegister.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createRegisterWindow(jDialog);
                    if(isLogin) {
                        jButtonLogin.setEnabled(false);
                        jButtonRegister.setEnabled(false);
                        jButtonUserInfo.setEnabled(true);
                        jButtonLogout.setEnabled(true);
                    }
                }
            });
            jButtonLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createLoginWindow(jDialog);
                    if(isLogin) {
                        jButtonLogin.setEnabled(false);
                        jButtonRegister.setEnabled(false);
                        jButtonUserInfo.setEnabled(true);
                        jButtonLogout.setEnabled(true);
                    }
                }
            });
            jDialog.add(jButtonRegister, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jButtonUserInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isLogin) {
                        createUserInfoWindow(jDialog);
                    } else {
                        JOptionPane.showMessageDialog(jDialog, "请先登录");
                    }
                }
            });
            jDialog.add(jButtonUserInfo, gbc);
            jButtonLogout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isLogin) {
                        isLogin = false;
                        currentUser = null;
                        jButtonLogin.setEnabled(true);
                        jButtonRegister.setEnabled(true);
                        jButtonUserInfo.setEnabled(false);
                        jButtonLogout.setEnabled(false);
                        JOptionPane.showMessageDialog(jDialog, "Logout Successfully");
                    } else {
                        JOptionPane.showMessageDialog(jDialog, "You are not login");
                    }
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonLogout, gbc);
            jButtonUserInfo.setEnabled(false);
            jButtonLogout.setEnabled(false);
            jDialog.setVisible(true);
        });
        dockBar.add(button3);

        // Add the dock bar to the bottom of the frame
        container.add(dockBar, BorderLayout.WEST);
    }

    public Core() throws IOException, SQLException {
        super("Wuzhou Dictionary");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1729,972);
        sql = new SQLLinker("SQL Server", "127.0.0.1", "1433", "sa", "123456", "wuzhouDict");
        loginPart = new LoginPart(sql);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }


    public void ViewInfo(ActionEvent e) {
        JDialog jDialog = new JDialog(this, "Info", true);
        jDialog.setBounds(0, 0, 300, 500);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        JLabel jLabel = new JLabel("Wuzhou Dictionary");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel, gbc);
        String showedDescription = "<html><body>Description: ";
        for(int i = 0; i < info.Description.length(); i++) {
            if(i % 20 == 0 && i != 0 && info.Description.charAt(i) == ' ') {
                showedDescription += "<br/>";
            }
            showedDescription += info.Description.charAt(i);
        }
        showedDescription += "</body></html>";
        JLabel jLabel2 = new JLabel(showedDescription);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel2, gbc);
        JLabel jLabel3 = new JLabel("Author: " + info.Author);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel3, gbc);
        JLabel jLabel5 = new JLabel("Data Provider: " + info.DataProvider);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel5, gbc);
        JLabel jLabel4 = new JLabel("License: " + info.License);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel4, gbc);
        JLabel jLabel6 = new JLabel("Version: " + info.Version);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel6, gbc);
        jDialog.setVisible(true);
    }

    public void newIssue(ActionEvent e) {
        JDialog jDialog = new JDialog(this, "New Issue", true);
        jDialog.setBounds(0,0,800,700);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        JLabel jLabel = new JLabel("Title");
        JTextField jTextFieldTitle = new JTextField("");
        jTextFieldTitle.setColumns(30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jTextFieldTitle, gbc);
        JLabel jLabel2 = new JLabel("<html><body>" +
                "Body（Body部分支持<br/>Markdown语句，书<br/>写时，请按照<br/>Markdown格式书写</body></html>");
        TextArea jTextAreaBody = new TextArea("**Describe the bug**\n" +
                "A clear and concise description of what the bug is.\n" +
                "\n" +
                "**To Reproduce**\n" +
                "Steps to reproduce the behavior:\n" +
                "1. Go to '...'\n" +
                "2. Click on '....'\n" +
                "3. Scroll down to '....'\n" +
                "4. See error\n" +
                "\n" +
                "**Expected behavior**\n" +
                "A clear and concise description of what you expected to happen.\n" +
                "\n" +
                "**Screenshots**\n" +
                "If applicable, add screenshots to help explain your problem.\n" +
                "\n" +
                "**Desktop (please complete the following information):**\n" +
                " - OS: [e.g. iOS]\n" +
                " - Version [e.g. 22]\n" +
                "\n" +
                "**Additional context**\n" +
                "Add any other context about the problem here.\n", 30, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jTextAreaBody, gbc);
        JComboBox comboBox = new JComboBox();
        String[] selections = {"Public", "Yours"};
        for(String item: selections) {
            comboBox.addItem(item);
        }
        comboBox.setSize(100, 50);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(comboBox, gbc);
        JButton jButtonSubmit1 = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jButtonSubmit1, gbc);
        jButtonSubmit1.addActionListener(e1 -> {
            if (jTextFieldTitle.getText().equals("") || jTextAreaBody.getText().equals("")) {
                JOptionPane.showMessageDialog(jDialog, "Title or Body is empty");
                return;
            }
            if("Public".matches(comboBox.getSelectedItem().toString())) {
                JDialog jDialog1 = new JDialog(this, "请输入验证码", true);
                jDialog1.setLayout(new GridBagLayout());
                jDialog1.setSize(200, 300);
                Insets insets1 = new Insets(10, 10, 10, 10);
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.fill = GridBagConstraints.BOTH;
                gbc1.insets = insets1;
                JButton jButton1 = new JButton("Submit");
                gbc1.gridx = 1;
                gbc1.gridy = 2;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(jButton1, gbc1);
                JLabel jLabelPW = new JLabel("验证码");
                gbc1.gridx = 0;
                gbc1.gridy = 0;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(jLabelPW, gbc1);
                JTextField jTextFieldPW = new JTextField("", 20);
                gbc1.gridx = 1;
                gbc1.gridy = 0;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jTextFieldPW.setSize(100, 50);
                jTextFieldPW.setColumns(20);
                jDialog1.add(jTextFieldPW, gbc1);
                JLabel jLabelPW2 = new JLabel("验证码为随机生成的6位数字");
                gbc1.gridx = 0;
                gbc1.gridy = 1;
                gbc1.gridwidth = 2; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(jLabelPW2, gbc1);
                jDialog1.setBounds(0, 0, 500, 500);
                jDialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                Integer PW = new Random().nextInt(1000000);
                jLabelPW2.setText("验证码为 " + PW.toString());
                jButton1.addActionListener(e11 -> {
                    if (!jTextFieldPW.getText().equals(PW.toString())) {
                        JOptionPane.showMessageDialog(jDialog1, "验证码错误");
                        return;
                    }
                    POSTAndGET postAndGet = new POSTAndGET();
                    postAndGet.newIssue(jTextFieldTitle.getText(), jTextAreaBody.getText(), new String[]{"ReportedBugs"});
                    JOptionPane.showMessageDialog(jDialog1, "Issue Submitted");
                    jDialog1.dispose();
                    jDialog.dispose();
                });
                jDialog1.setVisible(true);
            } else if("Yours".matches(comboBox.getSelectedItem().toString())) {
                JDialog jDialog1 = new JDialog(this, "输入你的Token", true);
                jDialog1.setSize(200, 300);
                jDialog1.setLayout(new GridBagLayout());
                Insets insets1 = new Insets(10, 10, 10, 10);
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.fill = GridBagConstraints.BOTH;
                gbc1.insets = insets1;
                JButton jButton1 = new JButton("提交");
                gbc1.gridx = 1;
                gbc1.gridy = 1;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(jButton1, gbc1);
                JLabel YourToken = new JLabel("你的Token: ");
                gbc1.gridx = 0;
                gbc1.gridy = 0;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(YourToken, gbc1);
                JTextField keyToken = new JTextField("", 20);
                gbc1.gridx = 1;
                gbc1.gridy = 0;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                keyToken.setSize(100, 50);
                keyToken.setColumns(20);
                jButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(keyToken.getText().toString().equals("")) {
                            JOptionPane.showMessageDialog(jDialog1, "请输入Token");
                            return;
                        }
                        POSTAndGET postAndGet = new POSTAndGET(keyToken.getText().toString());
                        postAndGet.newIssue(jTextFieldTitle.getText(), jTextAreaBody.getText(), new String[]{"ReportedBugs"});
                        JOptionPane.showMessageDialog(jDialog1, "Issue Submitted");
                        jDialog1.dispose();
                        jDialog.dispose();
                    }
                });
                jDialog1.add(keyToken, gbc1);
                jDialog1.setVisible(true);
            }
        });
        jDialog.setVisible(true);
    }

    public boolean canLogin() {
        return isLogin;
    }

    public void createRegisterWindow(JDialog owner) {
        JDialog jDialogInput = new JDialog(owner, "注册", true);
        jDialogInput.setBounds(0, 0, 600, 500);
        jDialogInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialogInput.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JLabel jlabelUsername = new JLabel("用户名: ");
        jDialogInput.add(jlabelUsername, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JTextField jTextFieldUsername = new JTextField("", 20);
        jlabelUsername.setSize(100, 50);
        jDialogInput.add(jTextFieldUsername, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JLabel jlabelDisplayName = new JLabel("昵称: ");
        jlabelDisplayName.setSize(100, 50);
        jDialogInput.add(jlabelDisplayName, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JTextField jTextFieldDisplayName = new JTextField("", 20);
        jTextFieldDisplayName.setSize(100, 50);
        jDialogInput.add(jTextFieldDisplayName, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JLabel jlabelPassword = new JLabel("密码: ");
        jlabelPassword.setSize(100, 50);
        jDialogInput.add(jlabelPassword, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JPasswordField jTextFieldPassword = new JPasswordField("", 20);
        jDialogInput.add(jTextFieldPassword, gbc);
        JLabel jlabelPassword2 = new JLabel("再次输入密码: ");
        jlabelPassword2.setSize(100, 50);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(jlabelPassword2, gbc);
        JPasswordField jTextFieldPassword2 = new JPasswordField("", 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(jTextFieldPassword2, gbc);
        JLabel jlabelSex = new JLabel("性别: ");
        jlabelSex.setSize(100, 50);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(jlabelSex, gbc);
        JComboBox jComboBoxSex = new JComboBox();
        String[] selections = new String[2];
        selections[0] = "男";
        selections[1] = "女";
        for(String item: selections) {
            jComboBoxSex.addItem(item);
        }
        jComboBoxSex.setSize(100, 50);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(jComboBoxSex, gbc);
        JButton RegisterButton = new JButton("注册");
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!(jTextFieldPassword.getText().equals(jTextFieldPassword2.getText()))) {
                        JOptionPane.showMessageDialog(jDialogInput, "密码不一致");
                        return;
                    }
                    String registerResult = loginPart.register(jTextFieldUsername.getText(), jTextFieldPassword.getText(), jComboBoxSex.getSelectedItem().toString(), jTextFieldDisplayName.getText());
                    if(registerResult.equals("Success!")) {
                        currentUser = new User();
                        currentUser.loadUser(jTextFieldUsername.getText(), sql);
                        JOptionPane.showMessageDialog(jDialogInput, "注册成功！");
                    } else {
                        JOptionPane.showMessageDialog(jDialogInput, registerResult);
                        return;
                    }
                    isLogin = true;
                    jDialogInput.dispose();
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(RegisterButton, gbc);
        jDialogInput.setVisible(true);
    }

    public void createLoginWindow(JDialog owner) {
        JDialog jDialogInput = new JDialog(owner, "Login", true);
        jDialogInput.setBounds(0, 0, 600, 500);
        jDialogInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialogInput.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JLabel jlabelUsername = new JLabel("Username: ");
        jDialogInput.add(jlabelUsername, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JTextField jTextFieldUsername = new JTextField("", 20);
        jlabelUsername.setSize(100, 50);
        jDialogInput.add(jTextFieldUsername, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JLabel jlabelPassword = new JLabel("Password: ");
        jlabelPassword.setSize(100, 50);
        jDialogInput.add(jlabelPassword, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        JPasswordField jTextFieldPassword = new JPasswordField("", 20);
        jDialogInput.add(jTextFieldPassword, gbc);
        JButton LoginButton = new JButton("Login");
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(loginPart.login(jTextFieldUsername.getText(), jTextFieldPassword.getText())) {
                        isLogin = true;
                        currentUser = new User();
                        currentUser.loadUser(jTextFieldUsername.getText(), sql);
                        jDialogInput.dispose();
                    } else {
                        JOptionPane.showMessageDialog(jDialogInput, "Login Failed");
                    }
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = insets;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogInput.add(LoginButton, gbc);
        jDialogInput.setVisible(true);
    }

    public void createUserInfoWindow(JDialog owner) {
        JDialog jDialogUserInfo = new JDialog(owner, "用户信息", true);
        jDialogUserInfo.setBounds(0, 0, 600, 500);
        jDialogUserInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialogUserInfo.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("<html><body>" +
                "<p style=\"font-size:20px;\">用户名: " + currentUser.getUsername() + "</p><br/>" +
                "<p style=\"font-size:20px;\">昵称: "   + currentUser.getDisplayName() + "</p><br/>" +
                "<p style=\"font-size:20px;\">性别: " + currentUser.getSex() + "</p><br/>" +
                "</body></html>"
        );
        jDialogUserInfo.add(jLabel);
        jDialogUserInfo.setVisible(true);
    }

    public void createViewWindow() {
        final String[] types = {"", ""};
        Object[][] data;
        JDialog jDialogGetWhich = new JDialog(this, "select", true);
        jDialogGetWhich.setBounds(0, 0, 300, 500);
        jDialogGetWhich.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialogGetWhich.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        JComboBox comboBoxPlace = new JComboBox();
        String[] selections1 = {"梧州", "苍梧石桥", "蒙山"};
        for(String item: selections1) {
            comboBoxPlace.addItem(item);
        }
        comboBoxPlace.setSize(100, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogGetWhich.add(comboBoxPlace, gbc);
        JComboBox comboBox = new JComboBox();
        String[] selections = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "All"};
        for(String item: selections) {
            comboBox.addItem(item);
        }
        comboBox.setSelectedItem("All");
        comboBox.setSize(100, 50);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialogGetWhich.add(comboBox, gbc);
        JButton jButtonSubmit = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jButtonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                types[0] = comboBox.getSelectedItem().toString();
                types[1] = comboBoxPlace.getSelectedItem().toString();
                jDialogGetWhich.dispose();
            }
        });
        jDialogGetWhich.add(jButtonSubmit, gbc);
        jDialogGetWhich.setVisible(true);
        JDialog jDialog = new JDialog(this, "View", true);
        jDialog.setBounds(0, 0, 600, 500);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new FlowLayout());
        String[] columnNames = {"id", "简体字", "繁体字", "梧州读音", "苍梧石桥读音", "蒙山读音"};
        List<Object[]> l = new ArrayList<>();
        ResultSet res;
        String place = null;
        if(types[1].matches("梧州")) {
            place = "Wuzhou";
        } else if(types[1].matches("苍梧石桥")) {
            place = "Cangwu_Shiqiao";
        } else if(types[1].matches("蒙山")) {
            place = "Mengshan";
        }
        if(types[0].matches("A"))
            res = sql.runSQL("SELECT * FROM entry.main WHERE Pronunciation_of_"+place+" LIKE 'a%' ORDER BY id");
        else if(types[0].matches("B"))
            res = sql.runSQL("SELECT * FROM entry[main] WHERE Pronunciation_of_"+place+" LIKE 'b%' ORDER BY id");
        else if(types[0].matches("C"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'c%' ORDER BY id");
        else if(types[0].matches("D"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'd%' ORDER BY id");
        else if(types[0].matches("E"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'e%' ORDER BY id");
        else if(types[0].matches("F"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'f%' ORDER BY id");
        else if(types[0].matches("G"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'g%' ORDER BY id");
        else if(types[0].matches("H"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'h%' ORDER BY id");
        else if(types[0].matches("I"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'i%' ORDER BY id");
        else if(types[0].matches("J"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'j%' ORDER BY id");
        else if(types[0].matches("K"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'k%' ORDER BY id");
        else if(types[0].matches("L"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'l%' ORDER BY id");
        else if(types[0].matches("M"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'm%' ORDER BY id");
        else if(types[0].matches("N"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'n%' ORDER BY id");
        else if(types[0].matches("O"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'o%' ORDER BY id");
        else if(types[0].matches("P"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'p%' ORDER BY id");
        else if(types[0].matches("Q"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'q%' ORDER BY id");
        else if(types[0].matches("R"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'r%' ORDER BY id");
        else if(types[0].matches("S"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 's%' ORDER BY id");
        else if(types[0].matches("T"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 't%' ORDER BY id");
        else if(types[0].matches("U"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'u%' ORDER BY id");
        else if(types[0].matches("V"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'v%' ORDER BY id");
        else if(types[0].matches("W"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'w%' ORDER BY id");
        else if(types[0].matches("X"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'x%' ORDER BY id");
        else if(types[0].matches("Y"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'y%' ORDER BY id");
        else if(types[0].matches("Z"))
            res = sql.runSQL("SELECT * FROM entry.[main] WHERE Pronunciation_of_"+place+" LIKE 'z%' ORDER BY id");
        else if(types[0].matches("All"))
            res = sql.runSQL("SELECT * FROM entry.viewAll ORDER BY id");
        else
            res = sql.runSQL("SELECT * FROM entry.viewAll ORDER BY id");
        try {
            if(res != null) {
                while (res.next()) {
                    l.add(new Object[]{res.getString("id"), res.getString("simplified_Chinese_character"), res.getString("traditional_Chinese_character"), res.getString("Pronunciation_of_Wuzhou"), res.getString("Pronunciation_of_Cangwu_Shiqiao"), res.getString("Pronunciation_of_Mengshan")});
                }
                data = new Object[l.size()][6];
                for (int i = 0; i < l.size(); i++) {
                    data[i] = l.get(i);
                }
            } else {
                data = new Object[0][6];
            }
        } catch (SQLException e) {
            data = new Object[0][6];
        }
        JTable jTable = new JTable(data, columnNames);
        JScrollPane scroll = new JScrollPane(jTable);
        scroll.setSize(300, 200);
        jDialog.add(scroll);
        jDialog.setVisible(true);
    }

    public void createSearchWindow() {
        final String[] input = new String[1];
        JDialog jDialog = new JDialog(this, "Search", true);
        jDialog.setBounds(0, 0, 400, 500);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = insets;
        JLabel jLabel = new JLabel("Search");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jLabel, gbc);
        JTextField jTextField = new JTextField("", 20);
        jTextField.setColumns(20);
        jTextField.setSize(100, 50);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jTextField, gbc);
        JButton jButton = new JButton("Go");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input[0] = jTextField.getText();
                jDialog.dispose();
            }
        });
        jDialog.add(jButton, gbc);
        jDialog.setVisible(true);
        if(input[0] == null) {
            return;
        }
        JDialog jDialog1 = new JDialog(this, "Search Result", true);
        jDialog1.setBounds(0, 0, 600, 500);
        jDialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog1.setLayout(new FlowLayout());
        String[] columnNames = {"id", "简体字", "繁体字", "梧州读音", "苍梧石桥读音", "蒙山读音"};
        List<Object[]> l = new ArrayList<>();
        Object[][] data;
        try {
            ResultSet res = sql.runSQL("SELECT * FROM entry.viewAll WHERE simplified_Chinese_character LIKE '%" + input[0] + "%' UNION SELECT * FROM entry.viewAll WHERE traditional_Chinese_character LIKE '%" + input[0] + "%'");
            if (res != null) {
                while (res.next()) {
                    l.add(new Object[]{res.getString("id"), res.getString("simplified_Chinese_character"), res.getString("traditional_Chinese_character"), res.getString("Pronunciation_of_Wuzhou"), res.getString("Pronunciation_of_Cangwu_Shiqiao"), res.getString("Pronunciation_of_Mengshan")});
                }
            }
            data = new Object[l.size()][6];
            for (int i = 0; i < l.size(); i++) {
                data[i] = l.get(i);
            }
        } catch (SQLException e) {
            data = new Object[0][6];
        }
        JTable jTable = new JTable(data, columnNames);
        JScrollPane scroll = new JScrollPane(jTable);
        scroll.setSize(300, 200);
        jDialog1.add(scroll);
        jDialog1.setVisible(true);
    }
}
