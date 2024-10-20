package top.mryan2005.managesysteminjava;
import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.lang.Integer.valueOf;

public class Core extends JFrame {

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
        JMenuItem jMenuItemAbout = new JMenuItem("New Issue");
        jMenu2.add(jMenuItemAbout);
        jMenuItemAbout.addActionListener(this::newIssue);
        jMenuItemExit.addActionListener(this::actionPerformed);
        // build a dock bar like macOS
        // Create the dock bar
        JPanel dockBar = new JPanel();
        dockBar.setPreferredSize(new Dimension(0, 100));
        dockBar.setSize(120, 80);
        dockBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dockBar.setBackground(new Color(0, 0, 0, 20)); // Transparent background
        dockBar.setOpaque(true);
        // Add buttons to the dock bar
        for (int i = 1; i <= 5; i++) {
            JButton button = new JButton(new ImageIcon(printPath() + "/icons/" + i + ".png"));
            button.setSize(50, 50);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setVisible(true);
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button " + finalI + " clicked");
                }
            });
            dockBar.add(button);
        }

        // Add the dock bar to the bottom of the frame
        container.add(dockBar, BorderLayout.SOUTH);
    }

    public Core() {
        super("Wuzhou Dictionary");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1729,972);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
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
            Integer PW = new java.util.Random().nextInt(1000000);
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
