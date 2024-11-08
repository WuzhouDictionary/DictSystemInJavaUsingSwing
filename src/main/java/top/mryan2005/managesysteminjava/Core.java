package top.mryan2005.managesysteminjava;
import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;
import top.mryan2005.managesysteminjava.SQLs.SQLLinker;
import top.mryan2005.managesysteminjava.Settings.Info;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import static java.lang.Integer.valueOf;

public class Core extends JFrame {

    public Info info = new Info();

    private SQLLinker sql;

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
            JButton jButtonSearch = new JButton("Search");
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
            JButton jButtonViewAll = new JButton("View All");
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
            JDialog jDialog = new JDialog(this, "Admin", true);
            jDialog.setBounds(0, 0, 300, 500);
            jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jDialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            Insets insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = insets;
            JButton jButtonShowAll = new JButton("Show All");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1; // 横占一个单元格
            gbc.gridheight = 1; // 列占一个单元格
            gbc.weightx = 0.0; // 当窗口放大时，长度不变
            gbc.weighty = 0.0; // 当窗口放大时，高度不变
            jDialog.add(jButtonShowAll, gbc);
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
            jButtonLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createLoginWindow(jDialog);
                }
            });
            jDialog.add(jButtonLogin, gbc);
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
                JDialog jDialog1 = new JDialog(this, "Input Your Token", true);
                jDialog1.setSize(200, 300);
                jDialog1.setLayout(new GridBagLayout());
                Insets insets1 = new Insets(10, 10, 10, 10);
                GridBagConstraints gbc1 = new GridBagConstraints();
                gbc1.fill = GridBagConstraints.BOTH;
                gbc1.insets = insets1;
                JButton jButton1 = new JButton("Submit");
                gbc1.gridx = 1;
                gbc1.gridy = 1;
                gbc1.gridwidth = 1; // 横占一个单元格
                gbc1.gridheight = 1; // 列占一个单元格
                gbc1.weightx = 0.0; // 当窗口放大时，长度不变
                gbc1.weighty = 0.0; // 当窗口放大时，高度不变
                jDialog1.add(jButton1, gbc1);
                JLabel YourToken = new JLabel("Your Token: ");
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
        JTextField jTextFieldPassword = new JTextField("", 20);
        jDialogInput.add(jTextFieldPassword, gbc);
        JButton LoginButton = new JButton("Login");
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        }
        if(types[0].matches("A"))
            res = sql.runSQL("SELECT * FROM entry.main WHERE Pronunciation_of_"+place+" LIKE 'a%' ORDER BY id");
        else if(types[0].matches("B"))
            res = sql.runSQL("SELECT * FROM entry.bPart ORDER BY id");
        else if(types[0].matches("C"))
            res = sql.runSQL("SELECT * FROM entry.cPart ORDER BY id");
        else if(types[0].matches("D"))
            res = sql.runSQL("SELECT * FROM entry.dPart ORDER BY id");
        else if(types[0].matches("E"))
            res = sql.runSQL("SELECT * FROM entry.ePart ORDER BY id");
        else if(types[0].matches("F"))
            res = sql.runSQL("SELECT * FROM entry.fPart ORDER BY id");
        else if(types[0].matches("G"))
            res = sql.runSQL("SELECT * FROM entry.gPart ORDER BY id");
        else if(types[0].matches("H"))
            res = sql.runSQL("SELECT * FROM entry.hPart ORDER BY id");
        else if(types[0].matches("I"))
            res = sql.runSQL("SELECT * FROM entry.iPart ORDER BY id");
        else if(types[0].matches("J"))
            res = sql.runSQL("SELECT * FROM entry.jPart ORDER BY id");
        else if(types[0].matches("K"))
            res = sql.runSQL("SELECT * FROM entry.kPart ORDER BY id");
        else if(types[0].matches("L"))
            res = sql.runSQL("SELECT * FROM entry.lPart ORDER BY id");
        else if(types[0].matches("M"))
            res = sql.runSQL("SELECT * FROM entry.mPart ORDER BY id");
        else if(types[0].matches("N"))
            res = sql.runSQL("SELECT * FROM entry.nPart ORDER BY id");
        else if(types[0].matches("O"))
            res = sql.runSQL("SELECT * FROM entry.oPart ORDER BY id");
        else if(types[0].matches("P"))
            res = sql.runSQL("SELECT * FROM entry.pPart ORDER BY id");
        else if(types[0].matches("Q"))
            res = sql.runSQL("SELECT * FROM entry.qPart ORDER BY id");
        else if(types[0].matches("R"))
            res = sql.runSQL("SELECT * FROM entry.rPart ORDER BY id");
        else if(types[0].matches("S"))
            res = sql.runSQL("SELECT * FROM entry.sPart ORDER BY id");
        else if(types[0].matches("T"))
            res = sql.runSQL("SELECT * FROM entry.tPart ORDER BY id");
        else if(types[0].matches("U"))
            res = sql.runSQL("SELECT * FROM entry.uPart ORDER BY id");
        else if(types[0].matches("V"))
            res = sql.runSQL("SELECT * FROM entry.vPart ORDER BY id");
        else if(types[0].matches("W"))
            res = sql.runSQL("SELECT * FROM entry.wPart ORDER BY id");
        else if(types[0].matches("X"))
            res = sql.runSQL("SELECT * FROM entry.xPart ORDER BY id");
        else if(types[0].matches("Y"))
            res = sql.runSQL("SELECT * FROM entry.yPart ORDER BY id");
        else if(types[0].matches("Z"))
            res = sql.runSQL("SELECT * FROM entry.zPart ORDER BY id");
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
        JButton jButton = new JButton("Search");
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
