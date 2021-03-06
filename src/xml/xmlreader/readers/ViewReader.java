package xml.xmlreader.readers;

import exceptions.GeneralXMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.xmlreader.interfaces.ViewReaderInterface;
import xml.xmlreader.interfaces.XMLGeneratorInterface;
import xml.xmlreader.interfaces.XMLParseInterface;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewReader implements ViewReaderInterface {

    private static Document myDocument;

    private static final String WIDTH_TAG = "Width";
    private static final String HEIGHT_TAG = "Height";
    private static final String TITLE_TAG = "Title";
    private static final String AUTHORS_TAG = "Authors";

    private static final String ICON_BUNDLE = "IconBundle";
    private static final String ERROR_STYLSHEET = "ErrorStyleSheet";

    private static final String LANGUAGE_TAG = "Language";
    private static final String STYLESHEET_TAG = "Stylesheet";

    /**
     * Create the reader from string
     * @param file is the file pointer to make the reader from
     * @throws GeneralXMLException if the GeneratorInterface cannot parse the file (non xml)
     */
    public ViewReader(File file) throws GeneralXMLException {
        myDocument = XMLGeneratorInterface.createDocument(file);
    }

    /**
     * Create the reader from string
     * @param file is the string of path to make the reader from
     * @throws GeneralXMLException if the GeneratorInterface cannot parse the file (non xml)
     */
    public ViewReader(String file) throws GeneralXMLException {
        myDocument = XMLGeneratorInterface.createDocument(new File(file));
    }

    /**
     * Get dimensions of the screen
     * @return width
     */
    @Override
    public int getScreenWidth() {
        return Integer.parseInt(XMLParseInterface.getSingleTag(myDocument, WIDTH_TAG));
    }

    /**
     * Get dimensions of the screen
     * @return height
     */
    @Override
    public int getScreenHeight() {
        return Integer.parseInt(XMLParseInterface.getSingleTag(myDocument, HEIGHT_TAG));
    }

    /**
     * Get string type parameters governing the game
     * @return game title
     */
    @Override
    public String getTitle() {
        return XMLParseInterface.getSingleTag(myDocument, TITLE_TAG);
    }

    /**
     * Get string type parameters governing the game
     * @return game authors
     */
    @Override
    public String getAuthors() {
        return XMLParseInterface.getSingleTag(myDocument, AUTHORS_TAG);
    }

    /**
     * Get game view construct params
     * @return list of languages
     */
    @Override
    public List<String> getLanguages() {
        NodeList nodeList = XMLParseInterface.getNodeList(myDocument, LANGUAGE_TAG);
        return parseNodeListForContent(nodeList);
    }

    /**
     * Get game view construct params
     * @return list of stylesheets
     */
    @Override
    public List<String> getStylesheets() {
        NodeList nodeList = XMLParseInterface.getNodeList(myDocument, STYLESHEET_TAG);
        return parseNodeListForContent(nodeList);
    }

    /**
     * Return icon bundle tag
     * @return
     */
    @Override
    public String getIconBundle() {
        return XMLParseInterface.getSingleTag(myDocument, ICON_BUNDLE);
    }

    /**
     * Reteurn error stylesheet tag
     * @return
     */
    @Override
    public String getErrorStylesheet() {
        return XMLParseInterface.getSingleTag(myDocument, ERROR_STYLSHEET);
    }

    private List<String> parseNodeListForContent(NodeList nodeList) {
        List<String> list = new ArrayList<>();
        for (int index = 0; index < nodeList.getLength(); index ++) {
            Node n = nodeList.item(index);
            Element e = (Element) n;
            list.add(e.getTextContent());
        }
        return list;
    }

}
