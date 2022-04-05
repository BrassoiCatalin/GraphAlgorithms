import javafx.scene.shape.Polygon;

import java.awt.*;

import java.awt.geom.*;
import java.awt.event.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;

public class MyPanel extends JPanel {
    public int type;

    private int nodeNr = 1;
    private int node_diam = 30;

    private Vector<Node> listaNoduri = new Vector<>();
    private Vector<Arc> listaArce = new Vector<>();

    private Vector<Vector<Integer>> matriceAdiacenta;

    private Point pointStart = null;
    private Point pointEnd = null;

    private boolean isDragging = false;

    private void generateMatrix() //matrice adiacenta: punctul 4
    {
        matriceAdiacenta = new Vector<>();
        String fileName = "filename.txt";
        FileWriter resultFile;

        try {
            resultFile = new FileWriter(fileName);
            resultFile.write(String.valueOf(listaNoduri.size()) + '\n');
            //Am scris numarul de noduri in fisier, pe prima linie.

            for (int index = 0; index < listaNoduri.size(); index++) {
                Vector<Integer> linieTemporara = new Vector<>();
                //Ne cream o "linie" de matrice, pe care o sa o adaugam in matrice mai tarziu, si care o initializam cu 0.

                for (int index2 = 0; index2 < listaNoduri.size(); index2++) {
                    if (listaNoduri.elementAt(index) == listaNoduri.elementAt(index2)) {
                        linieTemporara.add(1);
                        continue;
                    }
                    //Valori de 1 pe diagonala principala in matrice.

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

            if (type == 0) {
                for (int index = 0; index < matriceAdiacenta.size(); index++) {
                    for (int index2 = index + 1; index2 < matriceAdiacenta.size(); index2++) {
                        if (matriceAdiacenta.elementAt(index).elementAt(index2) == 1)
                            matriceAdiacenta.elementAt(index2).setElementAt(1, index);

                        if (matriceAdiacenta.elementAt(index2).elementAt(index) == 1)
                            matriceAdiacenta.elementAt(index).setElementAt(1, index2);
                    }
                }
            }
            //Aici, in functie de type, se alege sa se construiasca o matrice de graf orientat sau una de graf neorientat

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
            if (type == 0)
                a.drawArc(g);
            else
                drawArrowLine(g, a.getStartX(), a.getStartY(), a.getEndX(), a.getEndY(), 20, 20);
        }

        //deseneaza arcul curent; cel care e in curs de desenare
        if (pointStart != null) {
            g.setColor(Color.RED);
            if (type == 0)
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);//graf neorientat
            else
                drawArrowLine(g, pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, 20, 20);//graf orientat
        }

        //deseneaza lista de noduri
        for (int i = 0; i < listaNoduri.size(); i++) {
            listaNoduri.elementAt(i).drawNode(g, node_diam);
        }
    }
}
