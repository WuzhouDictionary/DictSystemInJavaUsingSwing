package top.mryan2005.managesysteminjava;

import java.io.IOException;
import java.lang.*;
import java.sql.SQLException;

public class SwingBootstrap {

    public static void main(String[] args) throws IOException, SQLException {
        Core core = new Core();
        String a = core.printPath();
        core.buildWindows();
        core.setVisible(true);
    }
}
