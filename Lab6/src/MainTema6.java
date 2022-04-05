import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class MainTema6 {

    private static Vector<Vector<Integer>> listeAdiacenta = new Vector<Vector<Integer>>();
    private static Vector<Vector<Integer>> matriceCosturi = new Vector<Vector<Integer>>();
    private static int numarNoduri = 0;

    private static void citireListeAdiacenta() {

        File fisier = new File("ListeAdiacenta.txt");
        try {
            Scanner citire = new Scanner(fisier);
            numarNoduri = citire.nextInt();
            for (int linie = 0; linie < numarNoduri; linie++) {
                Vector<Integer> vectorLinie = new Vector<Integer>();
                int numarNoduriAdiacente = citire.nextInt();
                for (int nod = 0; nod < numarNoduriAdiacente; nod++) {
                    int numarNod = citire.nextInt();
                    vectorLinie.add(numarNod);
                }
                listeAdiacenta.add(vectorLinie);
            }
            citire.close();
        } catch(Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    private static void citireMatriceCosturi() {

        File fisier = new File("ListeCosturi.txt");
        try {
            Scanner citire = new Scanner(fisier);
            numarNoduri = citire.nextInt();
            for (int linie = 0; linie < numarNoduri; linie++) {
                Vector<Integer> vectorCost = new Vector<Integer>();
                for (int coloana = 0; coloana < numarNoduri; coloana++) {
                    int cost = citire.nextInt();
                    vectorCost.add(cost);
                }
                matriceCosturi.add(vectorCost);
            }
            citire.close();
        } catch(Exception exceptie) {
            System.out.println("A aparut o eroare!");
        }
    }

    public static void main(String[] args) {

        citireListeAdiacenta();
        citireMatriceCosturi();

        Vector<Vector<Integer>> listeAdiacentaNoi = AlgoritmulLuiPrim.ProgramPrim(numarNoduri, listeAdiacenta, matriceCosturi);
        System.out.println("Listele de adiacenta ale arborelui partial minim sunt: ");
        for (int index = 0; index < listeAdiacentaNoi.size(); index++){
            System.out.println(index + 1 + ": " + listeAdiacentaNoi.get(index));
        }
    }
}


/*
* Progra Dijkstra
*
* Begin
*   W = N;
*   d(S) = 0; p(S) = 0;
*   for toti y din N - {S}
*
*   Begin
*       d(y) = inf;
*       p(y) = 0;
*   End;
*
*   While W != 0 Do
*   Begin
*       selectez nodul x apartine lui W cu d(x) minim;
*       W = W - {x};
*       for y apartine lui W intersectat cu V+(x)
*       Begin
*           if d(x) + b[x, y] < d(y)
*           then Begin
*               d(y) = d(x) + b[x, y];
*               p(y) = x;
*           End;
*       End;
*   End;
* */