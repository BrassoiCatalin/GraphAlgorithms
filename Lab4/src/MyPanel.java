import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.shape.Polygon;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class MyPanel extends JPanel implements ActionListener{

    private int nodeNr = 0;
    private int node_diam = 30;

    private Vector<Node> listaNoduri = new Vector<>();
    private Vector<Arc> listaArce = new Vector<>();

    private Vector<Vector<Integer>> matriceAdiacenta;

    private Point pointStart = null;
    private Point pointEnd = null;

    private boolean isDragging = false;

    JButton butonST = new JButton("Sortati topologic");
    private Vector<Vector<Integer>> listaAdiacenta = new Vector<Vector<Integer>>();

    private void generateMatrix() //matrice adiacenta: punctul 4
    {
        matriceAdiacenta = new Vector<>();
        String fileName = "filename.txt";
        FileWriter resultFile;

        try {
            resultFile = new FileWriter(fileName);

            for (int index = 0; index < listaNoduri.size(); index++) {
                Vector<Integer> linieTemporara = new Vector<>();
                //Ne cream o "linie" de matrice, pe care o sa o adaugam in matrice mai tarziu, si care o initializam cu 0.

                for (int index2 = 0; index2 < listaNoduri.size(); index2++) {

                    boolean foundArc = false;
                    for (Arc arc : listaArce) {
                        if (arc.getStartX() == (listaNoduri.elementAt(index).getCoordX() + node_diam / 2)
                                && arc.getStartY() == (listaNoduri.elementAt(index).getCoordY() + node_diam / 2)
                                && arc.getEndX() == (listaNoduri.elementAt(index2).getCoordX() + node_diam / 2)
                                && arc.getEndY() == (listaNoduri.elementAt(index2).getCoordY() + node_diam / 2)) {
                            foundArc = true;
                            break;
                        }
                        //Daca se potrivesc coordonatele dintre cele doua noduri si posibilul arc dintre ele,
                        // conditia devine true + se iese din for.
                    }

                    if (foundArc) {
                        linieTemporara.add(1);
                    } else {
                        linieTemporara.add(0);
                    }
                    //In functie de conditie, in linia de matrice se adauga 1 daca avem arc (adica daca se
                    // potrivesc coordonatele cum am explicat mai sus) sau 0 in caz contrar.
                }

                matriceAdiacenta.add(linieTemporara);
                //In matricea de adiacenta se adauga linieTemporala.
            }

            for (int index = 0; index < matriceAdiacenta.size(); index++) {
                for (int index2 = 0; index2 < matriceAdiacenta.size(); index2++) {
                    resultFile.write(matriceAdiacenta.elementAt(index).elementAt(index2) + " ");
                }
                //Scriem in fisier tot ce avem in matricea de adiacenta.

                resultFile.write('\n');
                //Adaugam linie noua la fiecare sfarsit de linie de matrice
            }

            resultFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyPanel() {

        butonST.setBackground(Color.GREEN);
        butonST.setSize(50, 50);
        this.add(butonST);
        butonST.addActionListener(this);

        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            //evenimentul care se produce la apasarea mouse-ului
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
            }

            //evenimentul care se produce la eliberarea mouse-ului
            public void mouseReleased(MouseEvent e) {
                if (!isDragging) {
                    if (nodeOverlap(e.getX(), e.getY()) == null) {
                        addNode(e.getX(), e.getY());
                        generateMatrix();
                    }
                } else {
                    if (nodeOverlap(pointStart.x, pointStart.y) != null && nodeOverlap(pointEnd.x, pointEnd.y) != null) {
                        Point startPoint = new Point(nodeOverlap(pointStart.x, pointStart.y).getCoordX() + node_diam / 2, nodeOverlap(pointStart.x, pointStart.y).getCoordY() + node_diam / 2);
                        Point endPoint = new Point(nodeOverlap(pointEnd.x, pointEnd.y).getCoordX() + node_diam / 2, nodeOverlap(pointEnd.x, pointEnd.y).getCoordY() + node_diam / 2);
                        //arc doar intre doua noduri: punctul 3

                        Arc arc = new Arc(startPoint, endPoint);
                        listaArce.add(arc);
                        generateMatrix();

                    }
                }
                pointStart = null;
                isDragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            //evenimentul care se produce la drag&drop pe mouse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                repaint();
            }
        });


    }

    //metoda care ajuta sa nu se suprapuna nodurile: punctul 2
    private Node nodeOverlap(int x, int y) {
        for (int index = 0; index < listaNoduri.size(); index++) {

            int coordX = listaNoduri.elementAt(index).getCoordX();
            int coordY = listaNoduri.elementAt(index).getCoordY();

            if (x <= coordX + node_diam
                    && x >= coordX - node_diam
                    && y <= coordY + node_diam
                    && y >= coordY - node_diam) {
                return listaNoduri.elementAt(index);
            }
        }
        return null;
    }

    //metoda care se apeleaza la eliberarea mouse-ului
    private void addNode(int x, int y) {
        Node node = new Node(x, y, nodeNr); //coordonatele + diametru sa nu intersecteze alt nod
        listaNoduri.add(node);
        nodeNr++;
        repaint();
    }

    //sageata pentru graf orientat: punctul 5
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.setColor(Color.BLUE);
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    //se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);

        for (Arc a : listaArce) {
                drawArrowLine(g, a.getStartX(), a.getStartY(), a.getEndX(), a.getEndY(), 20, 20);
        }

        //deseneaza arcul curent; cel care e in curs de desenare
        if (pointStart != null) {
            g.setColor(Color.RED);
                drawArrowLine(g, pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, 20, 20);//graf orientat
        }

        //deseneaza lista de noduri
        for (int i = 0; i < listaNoduri.size(); i++) {
            listaNoduri.elementAt(i).drawNode(g, node_diam);
        }
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == butonST){

            dinMatriceInLista();

            if(!ciclic())
                ProgramST();
            else
                System.out.println("Graful are cicluri! Nu se poate aplica sortare topologica!");
        }
    }

    public Vector<Vector<Integer>> dinMatriceInLista(){

        int lenght = matriceAdiacenta.elementAt(0).size();
        listaAdiacenta = new Vector<Vector<Integer>>(lenght);

        for(int index=0;index<lenght;index++){

            listaAdiacenta.add(new Vector<Integer>());
        }

        for(int index1 = 0; index1 < lenght; index1++){

            for(int index2 = 0;index2 < lenght; index2++){

                if(matriceAdiacenta.get(index1).get(index2) == 1){

                    listaAdiacenta.get(index1).add(index2);
                }
            }
        }
        return listaAdiacenta;
    }

    public void ProgramDFS(int nod, boolean vizitat[], Stack<Integer> stiva){

        vizitat[nod] = true;
        int index;

        Iterator<Integer> it = listaAdiacenta.get(nod).iterator();

        while(it.hasNext()){

            index = it.next();
            if(!vizitat[index])
                ProgramDFS(index, vizitat, stiva);
        }
        stiva.push(nod);
    }

    public void ProgramST(){

        Stack<Integer> stiva = new Stack<Integer>();

        boolean vizitat[] = new boolean[nodeNr];
        for(int index = 0; index < nodeNr; index++){
            vizitat[index] = false;
        }

        for(int index = 0; index < nodeNr; index++){
            if(vizitat[index] == false){
                ProgramDFS(index, vizitat, stiva);
            }
        }

        while(!stiva.empty()){
            System.out.print(stiva.pop() + " ");
        }
        System.out.println();
    }

    private boolean verificaCiclicitate(int index, boolean[] vizitat, boolean[] stack){

        if(stack[index])
            return true;
        if(vizitat[index])
            return false;

        vizitat[index] = true;
        stack[index] = true;

        List<Integer> copil = listaAdiacenta.get(index);
        for(Integer c: copil){

            if(verificaCiclicitate(c, vizitat, stack)){

                return true;
            }
        }
        stack[index] = false;

        return false;
    }

    private boolean ciclic(){

        boolean[] vizitat = new boolean[nodeNr];
        boolean[] stack = new boolean[nodeNr];

        for(int index = 0; index < nodeNr; index++){

            if(verificaCiclicitate(index, vizitat, stack)){
                return true;
            }
        }
        return false;
    }
}

