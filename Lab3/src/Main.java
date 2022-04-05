import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static void creareInterfata(){

        Labirint panel = new Labirint();

        Button butonAfisare = new Button();
        butonAfisare.setSize(150, 50);
        butonAfisare.setLabel("Afisare drumuri");
        butonAfisare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.apasat();
            }
        });

        JFrame cadru = new JFrame("Labirint");

        cadru.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cadru.add(butonAfisare);
        cadru.add(panel);
        cadru.setSize(700, 700);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        cadru.setLocation(dim.width/2 - cadru.getSize().width/2, dim.height/2-cadru.getSize().height/2);
        cadru.setVisible(true);

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                creareInterfata();
            }
        });
    }
}
