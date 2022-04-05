import java.awt.*;
import java.util.Vector;

public class Nod {

    private int numarNod;
    private int coordonataX;
    private int coordonataY;
    private Vector<Nod> listaLegaturi = new Vector<>();
    private Vector<Integer> costuri = new Vector<>();

    public Nod() {}

    public Nod(Nod nod) {
        this.coordonataX = nod.coordonataX;
        this.coordonataY = nod.coordonataY;
        this.numarNod = nod.numarNod;
    }

    public Nod(int numarNod) {
        this.numarNod = numarNod;
    }

    public Nod(int coordonataX, int coordonataY, int numarNod) {
        this.coordonataX = coordonataX;
        this.coordonataY = coordonataY;
        this.numarNod = numarNod;
    }


    public int GetNumarNod() {
        return numarNod;
    }

    public int GetCoordonataX() {
        return coordonataX;
    }

    public int GetCoordonataY() {
        return coordonataY;
    }

    public void addLegatura (Nod n, int cost) {
        listaLegaturi.add(n);
        costuri.add(cost);
    }

    public void DesenareNod(Graphics grafica, int diametruNod) {
        grafica.setColor(Color.WHITE);
        grafica.setFont(new Font("TimesRoman", Font.BOLD, 15));
        grafica.fillOval(coordonataX, coordonataY, diametruNod, diametruNod);
        grafica.setColor(Color.BLACK);
        grafica.drawOval(coordonataX, coordonataY, diametruNod, diametruNod);
        if(numarNod < 10) {
            grafica.drawString(((Integer) numarNod).toString(), coordonataX + 10, coordonataY + 20);
        } else {
            grafica.drawString(((Integer) numarNod).toString(), coordonataX + 8, coordonataY + 20);
        }
    }
}