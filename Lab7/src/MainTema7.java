import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainTema7 {

    public static void initializareInterfata() {
        Fereastra fereastra = new Fereastra();
        JFrame cadru = new JFrame("Algoritmica Grafurilor");
        cadru.setSize(1366, 745);
        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(fereastra);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2-cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setLocation(0, 0);
        cadru.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializareInterfata();
            }
        });
    }
}