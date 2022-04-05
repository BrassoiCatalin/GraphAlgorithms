import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.util.Scanner;

public class Graf
{
	private static void initUI() 
	{
        JFrame f = new JFrame("Algoritmica Grafurilor");
        
        //sa se inchida aplicatia atunci cand inchid fereastra
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel panel = new MyPanel();

        System.out.println("Introduceti 0 pentru graf neorientat");
        System.out.println("Introduceti 1 pentru graf orientat");

        Scanner s = new Scanner(System.in);
        panel.type = s.nextInt();

        //imi creez ob MyPanel
        f.add(panel);
        
        //setez dimensiunea ferestrei
        f.setSize(600, 400);
        
        //fac fereastra vizibila
        f.setVisible(true);
    }


        public static void main(String[] args) {
            //pornesc firul de executie grafic
            //fie prin implementarea interfetei Runnable, fie printr-un ob al clasei Thread
            SwingUtilities.invokeLater(new Runnable() //new Thread()
            {
                public void run() {
                    initUI();
                }
            });

        }
    }
