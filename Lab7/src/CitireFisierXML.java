import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class CitireFisierXML {

    public static Vector<Nod> citireNoduri(String file) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(file));

            Vector<Nod> noduri = new Vector<>();
            NodeList nodeList = document.getElementsByTagName("node");

            int n = nodeList.getLength();
            for (int i = 0; i < n; i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());

                    int x = Integer.parseInt(node.getAttributes().getNamedItem("latitude").getNodeValue());

                    int y = Integer.parseInt(node.getAttributes().getNamedItem("longitude").getNodeValue());
                    System.out.println("id: " + id + " - lat: " + x + " long: " + y);
                    noduri.add(new Nod((int) ((x - 4945029) * 0.01861), (int) ((y - 573929) * 0.00894), id));
                }
            }

            return noduri;


        } catch (ParserConfigurationException | SAXException | IOException e) {

        }
        return null;
    }

    public static Vector<Arc> citireLegaturi(String file, Vector<Nod> nodes) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(file));

            NodeList nodeList = document.getElementsByTagName("arc");

            Vector<Arc> legaturi = new Vector<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    int from = Integer.parseInt(node.getAttributes().getNamedItem("from").getNodeValue());
                    int to = Integer.parseInt(node.getAttributes().getNamedItem("to").getNodeValue());
                    int length = Integer.parseInt(node.getAttributes().getNamedItem("length").getNodeValue());

                    nodes.elementAt(from).addLegatura(nodes.elementAt(to), length);
                }
            }

            return legaturi;

        } catch (ParserConfigurationException | SAXException | IOException e) {

        }
        return null;
    }
}
