package top.mryan2005.managesysteminjava;
import top.mryan2005.managesysteminjava.ConnectToNet.POSTAndGET;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Core extends JFrame {

    public void print(String str) {
        System.out.println(str);
    }

    public String printPath() {
        String path = String.valueOf(this.getClass().getClassLoader().getResource(""));
        if(path.matches("file:(.*)/classes/")) {
            path = path.substring(6, path.length() - 15)+"/src/main/resources/top/mryan2005/managesysteminjava/";
        } else if(path == "null") {
            path = "";
        }
        System.out.println(path);
        return path;
    }

    public void buildWindows() {
        ImageIcon icon=new ImageIcon(printPath()+"1.png");
        print(printPath()+"1.png");
        JLabel label=new JLabel(icon);
        label.setBounds(0,0,1729,972);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        ((JPanel)container).setOpaque(false);
        getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("File");
        jMenuBar.add(jMenu);
        JMenuItem jMenuItemExit = new JMenuItem("Exit");
        jMenu.add(jMenuItemExit);
        JMenu jMenu2 = new JMenu("About");
        jMenuBar.add(jMenu2);
        JMenuItem jMenuItemAbout = new JMenuItem("New Issue");
        jMenu2.add(jMenuItemAbout);
        jMenuItemAbout.addActionListener(this::newIssue);
        jMenuItemExit.addActionListener(this::actionPerformed);
        // build a dock bar like Mac OS
        // Create the dock bar
        JPanel dockBar = new JPanel();
        dockBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dockBar.setBackground(new Color(0, 0, 0, 0)); // Transparent background

        // Add buttons to the dock bar
        for (int i = 1; i <= 5; i++) {
            JButton button = new JButton(new ImageIcon(printPath() + "/icon" + i + ".jpg"));
            button.setSize(50, 50);
            button.setContentAreaFilled(false);
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
        jDialog.setBounds(0,0,500,500);
        jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new FlowLayout());
        JLabel jLabel = new JLabel("Title");
        JTextField jTextFieldTitle = new JTextField();
        jTextFieldTitle.setSize(100, 50);
        jTextFieldTitle.setColumns(20);
        JLabel jLabel2 = new JLabel("Body");
        JTextArea jTextAreaBody = new JTextArea();
        jTextAreaBody.setSize(100, 50);
        jTextAreaBody.setColumns(20);
        JButton jButtonSubmit = new JButton("Submit");
        jDialog.add(jLabel);
        jDialog.add(jTextFieldTitle);
        jDialog.add(jLabel2);
        jDialog.add(jTextAreaBody);
        jDialog.add(jButtonSubmit);
        JDialog jDialog1 = new JDialog(this, "请输入验证码", true);
        jDialog1.setLayout(new FlowLayout());
        JLabel jLabelPW = new JLabel("验证码");
        JTextField jTextFieldPW = new JTextField();
        jTextFieldPW.setSize(100, 50);
        jTextFieldPW.setColumns(20);
        JLabel jLabelPW2 = new JLabel("验证码为随机生成的6位数字");
        JButton jButton1 = new JButton("Submit");
        jDialog1.add(jLabelPW);
        jDialog1.add(jTextFieldPW);
        jDialog1.add(jLabelPW2);
        jDialog1.add(jButton1);
        jButtonSubmit.addActionListener(e1 -> {
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
