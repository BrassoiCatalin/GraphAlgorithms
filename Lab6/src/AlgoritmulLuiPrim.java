import java.util.Vector;

public class AlgoritmulLuiPrim {

    static Vector<Vector<Integer>> ProgramPrim(int numarNoduri, Vector<Vector<Integer>> listeAdiacenta, Vector<Vector<Integer>> listeCosturi) {

        int n;
        int inf = Integer.MAX_VALUE;

        Vector<Integer> N = new Vector<Integer>();
        Vector<Vector<Integer>> E = new Vector<Vector<Integer>>();
        Vector<Vector<Integer>> b = new Vector<Vector<Integer>>();
        Vector<Integer> e = new Vector<Integer>();
        Vector<Integer> v = new Vector<Integer>();
        Vector<Integer> N1 = new Vector<Integer>();
        Vector<Integer> N1_ = new Vector<Integer>();
        Vector<Vector<Integer>> A_prim = new Vector<Vector<Integer>>();

        n = numarNoduri;
        b = listeCosturi;
        E = listeAdiacenta;

        N.add(1);
        N1_.add(1);
        v.add(0);

        N1 = new Vector<Integer>();
        A_prim = new Vector<Vector<Integer>>();

        Vector<Integer> vectorAuxiliar = new Vector<Integer>();
        A_prim.add(vectorAuxiliar);
        e.add(0);

        for (int indice = 1; indice < n; indice++) {
            v.add(inf);
            vectorAuxiliar = new Vector<Integer>();
            A_prim.add(vectorAuxiliar);
            e.add(0);
            N.add(indice + 1);
            N1_.add(indice + 1);
        }

        while (N1.size() != N.size()) {
            int y = N1_.get(0);

            for (int x : N1_) {
                if (v.get(y - 1) > v.get(x - 1)) {
                    y = x;
                }
            }

            N1.add(y);
            N1_.remove((Object)y);

            if (y != 1) {
                A_prim.get(y - 1).add(e.get(y - 1));
                A_prim.get(e.get(y - 1) - 1).add(y);
            }

            for (int y_ : E.get(y - 1)) {
                if (N1_.contains(y_) == true) {
                    if (v.get(y_ - 1) > b.get(y - 1).get(y_ - 1)) {
                        v.remove(y_ - 1);
                        v.insertElementAt(b.get(y - 1).get(y_ - 1), y_ - 1);
                        e.remove(y_ - 1);
                        e.insertElementAt(y, y_ - 1);
                    }
                }
            }
        }
        return A_prim;
    }
}