import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.swing.JPanel;

public class Labirint extends JPanel {

    enum Culori{
        Alb,
        Albastru,
        Negru,
        Rosu,
        Verde,
        Necolorat
    }//pentru a retine culorile fiecarul nod

    private Vector<Vector<Integer>> matriceLabirint = new Vector<Vector<Integer>>();
    private Vector<Vector<Integer>> matriceNoduri = new Vector<Vector<Integer>>();
    private Vector<Vector<Integer>> listaAdiacenta = new Vector<Vector<Integer>>();
    private Vector<Vector<Culori>> noduriColorate = new Vector<Vector<Culori>>();

    private int numarNod = 0;
    private int nodStart;
    private int nodFinal;
    private boolean apasat = false;

    private Vector<Integer> predecesor = new Vector<Integer>();
    private Vector<Integer> iesireSiIntrareDinLabirint = new Vector<Integer>();

    public Labirint() {

        citireMatrice();
        transformareMatrice();
        construireGraf();
        repaint();
    }

    public void apasat(){

        apasat = true;
        BFS();
    }

    public void citireMatrice(){//se citeste intr-un vector<vector> matricea din fisier text; salvam tot in matriceLabirint

        File fisier = new File ("file.txt");
        try {
            Scanner citireFisier = new Scanner(fisier);

            int numarLinii = citireFisier.nextInt();
            int numarColoane = citireFisier.nextInt();

            for (int indexLinie = 0; indexLinie < numarLinii; indexLinie++) {

                Vector<Integer> vectorLinie = new Vector<Integer>();

                for (int indexColoana = 0; indexColoana < numarColoane; indexColoana++) {

                    int nod = citireFisier.nextInt();
                    vectorLinie.add(nod);
                }
                matriceLabirint.add(vectorLinie);
            }

            citireFisier.close();
        }catch (Exception ex){
            System.out.println("Eroare");
        }
    }

    public void transformareMatrice(){//in fiecare loc din matrice  unde avem !=0, punem nuamrul unui nod; matricea rezultata o salvam in matriceNoduri


        for(int indexLinie = 0; indexLinie < matriceLabirint.size();indexLinie++) {

            Vector<Integer> vectorLinie = new Vector<Integer>();
            Vector<Culori> culoriVector = new Vector<Culori>();

            for(int  indexColoana = 0; indexColoana < matriceLabirint.get(indexLinie).size(); indexColoana++){

                if(matriceLabirint.get(indexLinie).get(indexColoana) != 0){

                    numarNod++;
                    vectorLinie.add(numarNod);
                    if(matriceLabirint.get(indexLinie).get(indexColoana) == 3){
                        nodStart = numarNod;
                        iesireSiIntrareDinLabirint.add(nodStart);
                    }

                    if(matriceLabirint.get(indexLinie).get(indexColoana) == 2){
                        nodFinal = numarNod;
                        iesireSiIntrareDinLabirint.add(nodFinal);
                    }//in vectorul intrare-iesire, am adaugat inrtarea si iesirea din labirint

                }
                else{
                    vectorLinie.add(0);
                }

                culoriVector.add(Culori.Necolorat);
            }
            matriceNoduri.add(vectorLinie);
            noduriColorate.add(culoriVector);
        }
    }

    public void construireGraf(){//din matriceNoduri, construim intr-un vector<vector> matricea de adiacenta

        for(int indexNod = 0; indexNod < numarNod; indexNod++){

            Vector<Integer> vectorAuxiliar = new Vector<Integer>();
            listaAdiacenta.add(vectorAuxiliar);//pt fiecare nod, alocam un vector care v-a retine toate nodurile "vecine"
        }

        for(int indexLinie = 0; indexLinie < matriceNoduri.size(); indexLinie++){

            for(int indexColoana = 0; indexColoana < matriceNoduri.get(indexLinie).size(); indexColoana++){

                if(matriceNoduri.get(indexLinie).get(indexColoana) != 0){

                    int auxiliar = 0, pozitie = 0;

                    if(indexLinie > 0 && matriceNoduri.get(indexLinie - 1).get(indexColoana) != 0){//verifica nodurile incepand cu a doua linie, pentru vecinii de deasupra

                        pozitie = matriceNoduri.get(indexLinie - 1).get(indexColoana) - 1;
                        auxiliar = matriceNoduri.get(indexLinie).get(indexColoana);

                        listaAdiacenta.get(pozitie).add(auxiliar);
                    }

                    if(indexLinie < matriceNoduri.size() - 1 && matriceNoduri.get(indexLinie + 1).get(indexColoana) != 0){//verifica nodurile pana la penultima linie (inclusiv), pentru vecinii de dedesubt

                        pozitie = matriceNoduri.get(indexLinie + 1).get(indexColoana) - 1;
                        auxiliar = matriceNoduri.get(indexLinie).get(indexColoana);

                        listaAdiacenta.get(pozitie).add(auxiliar);
                    }

                    if(indexColoana > 0 && matriceNoduri.get(indexLinie).get(indexColoana - 1) != 0){//verifica nodurile incepand cu a doua coloana, pentru noduri din stanga coloanei respective

                        pozitie = matriceNoduri.get(indexLinie).get(indexColoana - 1) - 1;
                        auxiliar = matriceNoduri.get(indexLinie).get(indexColoana);

                        listaAdiacenta.get(pozitie).add(auxiliar);
                    }

                    if(indexColoana < matriceNoduri.get(indexLinie).size() - 1 && matriceNoduri.get(indexLinie).get(indexColoana + 1) != 0){//verifica nodurile pana la penultima coloana inclusiv, pentru noduri din dreapta coloanei respective

                        pozitie = matriceNoduri.get(indexLinie).get(indexColoana + 1) - 1;
                        auxiliar = matriceNoduri.get(indexLinie).get(indexColoana);

                        listaAdiacenta.get(pozitie).add(auxiliar);
                    }
                }
            }
        }
    }

    public void colorareDrum(int iesire){

        while(iesire != nodStart){

            for(int indexLinie = 0; indexLinie < matriceNoduri.size(); indexLinie++){

                for(int indexColoana = 0; indexColoana < matriceNoduri.get(indexLinie).size(); indexColoana++){

                    if(matriceNoduri.get(indexLinie).get(indexColoana) == iesire){

                        noduriColorate.get(indexLinie).set(indexColoana, Culori.Verde);
                        break;
                    }
                }
            }
            iesire = predecesor.get(iesire - 1);
        }
    }

    public void BFS(){

        int inf = Integer.MAX_VALUE;

        Vector<Boolean> nevizitate = new Vector<Boolean>();
        Vector<Integer> vizitate = new Vector<Integer>();
        Vector<Integer> analizate = new Vector<Integer>();
        Vector<Integer> ordine = new Vector<Integer>();

        for(int index = 0; index < listaAdiacenta.size(); index++){

            predecesor.add(0);
            ordine.add(inf);
            nevizitate.add(false);
        }

        nevizitate.set(nodStart - 1, true);
        vizitate.add(nodStart);
        analizate = new Vector<Integer>();
        ordine.set(nodStart - 1, 0);

        while(vizitate.size() != 0){

            int x = vizitate.firstElement();

            for(int y : listaAdiacenta.get(x - 1)){

                if(nevizitate.get(y - 1) == false){

                    nevizitate.set(y - 1, true);
                    vizitate.add(y);
                    predecesor.set(y - 1, x);
                    ordine.set(y - 1, ordine.get(x - 1) + 1);

                    if(iesireSiIntrareDinLabirint.contains(y)){
                        colorareDrum(y);
                    }
                }
            }

            vizitate.remove(vizitate.firstElement());
            analizate.add(x);
        }
        repaint();
    }

    protected void paintComponent(Graphics grafic){

        super.paintComponent(grafic);

        if(apasat == false){

            for(int indexLinie = 0; indexLinie < matriceLabirint.size(); indexLinie++){

                for(int indexColoana = 0; indexColoana < matriceLabirint.get(indexLinie).size();indexColoana++){

                    if(matriceLabirint.get(indexLinie).get(indexColoana) == 0){

                        noduriColorate.get(indexLinie).set(indexColoana, Culori.Negru);

                        grafic.setColor(Color.black);
                        grafic.fillRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50,50);

                        grafic.setColor((Color.black));
                        grafic.drawRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50,50);
                    }

                    else if(matriceLabirint.get(indexLinie).get(indexColoana) == 2){

                        noduriColorate.get(indexLinie).set(indexColoana, Culori.Rosu);

                        grafic.setColor(Color.red);
                        grafic.fillRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50,50);

                        grafic.setColor((Color.black));
                        grafic.drawRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50,50);

                    }

                    else if(matriceLabirint.get(indexLinie).get(indexColoana) == 3) {

                        noduriColorate.get(indexLinie).set(indexColoana, Culori.Albastru);

                        grafic.setColor(Color.blue);
                        grafic.fillRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50, 50);

                        grafic.setColor((Color.black));
                        grafic.drawRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50, 50);
                    }

                    else if(matriceLabirint.get(indexLinie).get(indexColoana) == 1) {

                        noduriColorate.get(indexLinie).set(indexColoana, Culori.Alb);

                        grafic.setColor(Color.white);
                        grafic.fillRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50, 50);

                        grafic.setColor((Color.black));
                        grafic.drawRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50, 50);
                    }
                }
            }
        }

        else {

            for(int indexLinie = 0; indexLinie < matriceNoduri.size(); indexLinie++){

                for(int indexColoana = 0; indexColoana < matriceNoduri.get(indexLinie).size();indexColoana++){

                    switch(noduriColorate.get(indexLinie).get(indexColoana)){

                        case Alb:
                            grafic.setColor(Color.white);
                            break;

                        case Negru:
                            grafic.setColor(Color.black);
                            break;

                        case Albastru:
                            grafic.setColor(Color.blue);
                            break;

                        case Rosu:
                            grafic.setColor(Color.red);
                            break;

                        case Verde:
                            grafic.setColor(Color.green);
                            break;

                        default:
                            break;
                    }
                    grafic.fillRect(50 * indexColoana + 100, 50 * indexLinie + 100, 50, 50);
                    grafic.setColor(Color.BLACK);
                    grafic.drawRect(50 * indexColoana + 100, 50 * indexLinie + 100,50, 50);
                }
            }
        }
    }
}

