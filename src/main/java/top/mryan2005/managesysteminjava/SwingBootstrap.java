package top.mryan2005.managesysteminjava;

import java.lang.*;

public class SwingBootstrap {

    public static void main(String[] args) {
        Core core = new Core();
        String a = core.printPath();
        core.buildWindows();
        core.setVisible(true);
    }
}
