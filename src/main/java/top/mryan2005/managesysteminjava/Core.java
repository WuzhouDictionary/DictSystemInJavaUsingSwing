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
        setBounds(0,0,1729,972);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
