package top.mryan2005.managesysteminjava;

import java.io.IOException;
import java.lang.*;

public class SwingBootstrap {

    public static void main(String[] args) throws IOException {
        Core core = new Core();
        String a = core.printPath();
        core.buildWindows();
        core.setVisible(true);
    }
}
