import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Fereastra extends JPanel {

    private int diametruNod = 30;
    private Vector<Nod> listaNoduri;
    private Vector<Arc> listaArce;
    private Image img;

    public Fereastra() {
        try {
            img = ImageIO.read(new File("src/Luxembourg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listaNoduri = CitireFisierXML.citireNoduri("src/map.fxml");
        //listaArce = CitireFisierXML.citireLegaturi("src/map.fxml", listaNoduri);
        //CitireFisierXML.citireLegaturi("src/map.fxml", listaNoduri);
    }

    protected void paintComponent(Graphics grafica) {
        super.paintComponent(grafica);
        grafica.drawImage(img, 0, 0, null);
    }
}