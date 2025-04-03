package Framework;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XMLReader {
    private String xmlFilePath;

    public XMLReader(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getValueByName(String nameAttribute) {
        return getValueByName("EX", "EX1", nameAttribute);
    }

    public String getValueByName(String tagName, String nameAttribute) {
        return getValueByName("EX", tagName, nameAttribute);
    }

    public String getValueByName(String sectionName, String tagName, String nameAttribute) {
        try {
            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Access the XML file as a stream from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(xmlFilePath);
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + xmlFilePath);
            }
            Document document = builder.parse(inputStream);

            // Get the node list of the specified section
            NodeList sectionNodes = document.getElementsByTagName(sectionName);
            for (int i = 0; i < sectionNodes.getLength(); i++) {
                Element sectionElement = (Element) sectionNodes.item(i);

                // Get the node list of elements within the section
                NodeList elements = sectionElement.getElementsByTagName(tagName);

                // Iterate through the elements to find the one with the specified name attribute
                for (int j = 0; j < elements.getLength(); j++) {
                    Element element = (Element) elements.item(j);
                    String name = element.getAttribute("name");
                    if (name.equals(nameAttribute)) {
                        return element.getAttribute("value");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
