package top.mryan2005.managesysteminjava;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Core extends JFrame {

    public String printPath() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println(path);
        return path;
    }

    public Core() {
        super("Wuzhou Dictionary");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon=new ImageIcon(printPath()+"/1.png");
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
        setBounds(0,0,1729,972);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
