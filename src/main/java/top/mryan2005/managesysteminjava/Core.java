package top.mryan2005.managesysteminjava;
import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;
import top.mryan2005.managesysteminjava.Settings.Info;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;

import static java.lang.Integer.valueOf;

public class Core extends JFrame {

    public Info info = new Info();

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
        JMenu jMenu1 = new JMenu("Window");
        jMenuBar.add(jMenu1);
        JMenuItem jMenuItemGoBack = new JMenuItem("Go Back");
        jMenu1.add(jMenuItemGoBack);
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
            jDialog.setVisible(true);
        });
        dockBar.add(button2);

        // Add the dock bar to the bottom of the frame
        container.add(dockBar, BorderLayout.WEST);
    }

    public Core() throws IOException {
        super("Wuzhou Dictionary");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1729,972);
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
        jDialog.setBounds(0,0,500,700);
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
        JLabel jLabel2 = new JLabel("Body");
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
        JButton jButtonSubmit1 = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 横占一个单元格
        gbc.gridheight = 1; // 列占一个单元格
        gbc.weightx = 0.0; // 当窗口放大时，长度不变
        gbc.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog.add(jButtonSubmit1, gbc);
        JDialog jDialog1 = new JDialog(this, "请输入验证码", true);
        jDialog1.setLayout(new GridBagLayout());
        Insets insets1 = new Insets(10, 10, 10, 10);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = insets1;
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
        JButton jButton1 = new JButton("Submit");
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        gbc1.gridwidth = 1; // 横占一个单元格
        gbc1.gridheight = 1; // 列占一个单元格
        gbc1.weightx = 0.0; // 当窗口放大时，长度不变
        gbc1.weighty = 0.0; // 当窗口放大时，高度不变
        jDialog1.add(jButton1, gbc1);
        jButtonSubmit1.addActionListener(e1 -> {
            if (jTextFieldTitle.getText().equals("") || jTextAreaBody.getText().equals("")) {
                JOptionPane.showMessageDialog(jDialog1, "Title or Body is empty");
                return;
            }
            jDialog1.setBounds(0,0,500,500);
            jDialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Integer PW = new Random().nextInt(1000000);
            jLabelPW2.setText("验证码为 "+PW.toString());
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
        });
        jDialog.setVisible(true);
    }
}
