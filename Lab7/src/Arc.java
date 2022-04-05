import java.awt.*;

public class Arc {

    private Nod nodInitial;
    private Nod nodFinal;
    private int flux;
    private int capacitate;
    public boolean vizitat = false;

    public Arc(Nod initial, Nod nodFinal, int flux, int capacitate) {
        this.nodInitial = initial;
        this.nodFinal = nodFinal;
        this.flux = flux;
        this.capacitate = capacitate;
    }

    public Arc(Nod nodInitial, Nod nodFinal) {
        this.nodInitial = nodInitial;
        this.nodFinal = nodFinal;
    }

    public Nod GetNodInitial() {
        return nodInitial;
    }

    public Nod GetNodFinal() {
        return nodFinal;
    }

    public int GetFlux() {
        return flux;
    }

    public int GetCapacitate() {
        return capacitate;
    }

    public void SetFlux(int flux) {
        this.flux = flux;
    }

    public void SetCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public boolean GetVizitat() {
        return vizitat;
    }

    public void SetVizitat(boolean vizitat) {
        this.vizitat = vizitat;
    }

    public void DesenareArc(Graphics grafica) {
        if (nodInitial != null && nodFinal != null) {
            grafica.setColor(Color.BLACK);
            grafica.drawLine(nodInitial.GetCoordonataX() + 10, nodInitial.GetCoordonataY() + 10, nodFinal.GetCoordonataX() + 10, nodFinal.GetCoordonataY() + 10);
            double xCerculet = nodFinal.GetCoordonataX() + 10 - (double) 1 / 6 * (double) (nodFinal.GetCoordonataX() + 10 - nodInitial.GetCoordonataX() - 10);
            double yCerculet = nodFinal.GetCoordonataY() + 10 - (double) 1 / 6 * (double) (nodFinal.GetCoordonataY() + 10 - nodInitial.GetCoordonataY() - 10);
            grafica.setColor(Color.BLACK);
            grafica.fillOval((int) xCerculet - 1, (int) yCerculet - 1, 7, 7);
            grafica.drawOval((int) xCerculet - 1, (int) yCerculet - 1, 7, 7);
            grafica.drawString((Integer.toString(flux) + "," + Integer.toString(capacitate)), (int) (nodFinal.GetCoordonataX() + 10 - (double) 1 / 2 * (double) (nodFinal.GetCoordonataX() + 10 - nodInitial.GetCoordonataX() - 10)), (int) (nodFinal.GetCoordonataY() + 10 - (double) 1 / 2 * (double) (nodFinal.GetCoordonataY() + 10 - nodInitial.GetCoordonataY() - 10)));
        }
    }
}