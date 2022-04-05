import java.util.Stack;
import java.util.Vector;

public class ComponenteTareConexe {

    static int inf = Integer.MAX_VALUE;
    static int n;
    static Vector<Integer> N = new Vector<Integer>();

    static Vector<Integer> ProceduraPTDF (int nodSursa, Vector<Vector<Integer>> listeAdiacenta, Vector<Integer> t2_) {

        int s = nodSursa;

        Vector<Boolean> U = new Vector<Boolean>();
        Stack<Integer> V = new Stack<Integer>();
        Vector<Integer> W = new Vector<Integer>();
        Vector<Integer> p = new Vector<Integer>();
        Vector<Integer> t1 = new Vector<Integer>();
        Vector<Integer> t2 = new Vector<Integer>();
        Vector<Vector<Integer>> A = new Vector<Vector<Integer>>();

        A = listeAdiacenta;
        Vector<Integer> listaIndici = new Vector<Integer>();

        for (int indice = 0; indice < n; indice++) {

            U.add(false);
            p.add(0);
            t1.add(inf);
            t2.add(inf);
            listaIndici.add(0);
        }

        int t = 1;
        t1.set(s - 1, 1);
        U.set(s - 1, true);
        V.push(s);
        W = new Vector<Integer>();

        while (W.size() != N.size()) {
            while (V.size() != 0) {
                int x = V.peek();
                boolean ok1 = false;
                for (int indice = listaIndici.get(x - 1); indice < A.get(x - 1).size(); indice++) {
                    if (U.get(A.get(x - 1).get(indice) - 1) == false) {

                        int y = A.get(x - 1).get(indice);
                        U.set(y - 1, true);
                        V.push(y);
                        p.set(y - 1, x);
                        t = t + 1;
                        t1.set(y - 1, t);
                        listaIndici.set(x - 1, indice + 1);
                        ok1 = true;
                        break;
                    }
                }
                if (ok1 == false) {
                    V.pop();
                    W.add(x);
                    t = t + 1;
                    t2.set(x - 1, t);
                }
            }
            for (int indice = 0; indice < n; indice++) {
                if (U.get(indice) == false) {
                    s = indice + 1;
                    for (int x = s; x < n; x++) {
                        if (t2_.get(s - 1) < t2_.get(x) && U.get(x) == false) {
                            s = x + 1;
                        }
                    }
                    U.set(s - 1, true);
                    V.push(s);
                    t = t + 1;
                    t1.set(s - 1, t);
                    break;
                }
            }
        }
        for (int indice = 0; indice < n; indice++) {
            if (t2_.get(indice) == inf) {
                return t2;
            }
        }
        return p;
    }

    static Vector<Vector<Integer>> ProceduraInversare (Vector<Vector<Integer>> A) {

        Vector<Vector<Integer>> listeAdiacenta = new Vector<Vector<Integer>>();
        for (int indice = 0; indice < n; indice++) {
            Vector<Integer> vectorLista = new Vector<Integer>();
            listeAdiacenta.add(vectorLista);
        }
        for (int nod = 0; nod < n; nod++) {
            for (int nodAdiacent = 0; nodAdiacent < A.get(nod).size(); nodAdiacent++) {
                listeAdiacenta.get(A.get(nod).get(nodAdiacent) - 1).add(nod + 1);
            }
        }
        return listeAdiacenta;
    }

    static void ProceduraSeparare (Vector<Integer> p) {

        int c = 1;
        for (int nod = 0; nod < n; nod++) {

            if (p.get(nod) == 0) {

                Vector<Integer> ctc = new Vector<Integer>();
                ctc.add(nod + 1);
                int auxiliar = nod;
                boolean ok = false;
                do {
                    ok = false;
                    for (int nodComponent = 0; nodComponent < n; nodComponent++) {

                        if (p.get(nodComponent) == auxiliar + 1) {

                            auxiliar = nodComponent;
                            ctc.add(nodComponent + 1);
                            ok = true;
                            break;
                        }
                    }
                } while (ok == true);
                System.out.println("Componenta tare conexa " + c + ": " + ctc);
                ctc.clear();
                c++;
            }
        }
    }

    static void ProgramCTC (int numarNoduri, int nodSursa, Vector<Vector<Integer>> listeAdiacenta) {

        Vector<Integer> p = new Vector<Integer>();
        Vector<Integer> t2 = new Vector<Integer>();
        Vector<Vector<Integer>> A = new Vector<Vector<Integer>>();

        n = numarNoduri;
        A = listeAdiacenta;
        for (int indice = 0; indice < n; indice++) {
            N.add(indice + 1);
            t2.add(inf);
        }
        t2 = ProceduraPTDF(nodSursa, A, t2);
        int s = 1;
        for (int x = s; x < n; x++) {

            if (t2.get(s - 1) < t2.get(x)) {
                s = x + 1;
            }
        }
        A = ProceduraInversare(A);
        p = ProceduraPTDF(s, A, t2);
        ProceduraSeparare(p);
    }
}