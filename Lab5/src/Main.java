import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    private static Vector<Vector<Integer>> listeGrafOrientat = new Vector<Vector<Integer>>();
    private static Vector<Vector<Integer>> listeGrafNeorientat = new Vector<Vector<Integer>>();
    private static int numarNoduriGrafOrientat = 0;
    private static int numarNoduriGrafNeorientat = 0;

    private static void citireListeGrafOrientat()
    {
        File fisier = new File("grafOrientat.txt");
        try {
            Scanner citire = new Scanner(fisier);
            numarNoduriGrafOrientat = citire.nextInt();
            for (int linie = 0; linie < numarNoduriGrafOrientat; linie++) {
                Vector<Integer> vectorLinie = new Vector<Integer>();
                int numarNoduriAdiacente = citire.nextInt();
                for (int nod = 0; nod < numarNoduriAdiacente; nod++) {
                    int numarNod = citire.nextInt();
                    vectorLinie.add(numarNod);
                }
                listeGrafOrientat.add(vectorLinie);
            }
            citire.close();
        } catch(Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    private static void citireListeGrafNeorientat()
    {
        File fisier = new File("grafNeorientat.txt");
        try {
            Scanner citire = new Scanner(fisier);
            numarNoduriGrafNeorientat = citire.nextInt();
            for (int linie = 0; linie < numarNoduriGrafNeorientat; linie++) {
                Vector<Integer> vectorLinie = new Vector<Integer>();
                int numarNoduriAdiacente = citire.nextInt();
                for (int nod = 0; nod < numarNoduriAdiacente; nod++) {
                    int numarNod = citire.nextInt();
                    vectorLinie.add(numarNod);
                }
                listeGrafNeorientat.add(vectorLinie);
            }
            citire.close();
        } catch(Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    public static void main (String[] args) {

        citireListeGrafOrientat();
        citireListeGrafNeorientat();
        int nodSursa;
        try {

            System.out.println("Introduceti nodul sursa:");
            Scanner citire = new Scanner(System.in);
            nodSursa = citire.nextInt();
            citire.close();
            if (nodSursa > numarNoduriGrafOrientat) {
                throw new Exception();
            } else {
                ComponenteTareConexe.ProgramCTC(numarNoduriGrafOrientat, nodSursa, listeGrafOrientat);
            }
            if (nodSursa > numarNoduriGrafNeorientat) {
                throw new Exception();
            } else {
                ComponenteConexe.ProgramCC(numarNoduriGrafNeorientat, nodSursa, listeGrafNeorientat);
            }
        } catch (Exception ex) {

            System.out.println("A aparut o eroare!");
        }
    }
}