package com.github.dannil.httpdownloader.utility;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.github.dannil.httpdownloader.exception.ParsingException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Class for fetching and manipulate data from XML files.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class XMLUtility {

    private String path;

    /**
     * Default constructor.
     */
    private XMLUtility() {

    }

    /**
     * Overloaded constructor.
     *
     * @param path
     *            the path of the XML file
     */
    public XMLUtility(String path) {
        this();
        this.path = path;
    }

    /**
     * Returns the value for a specific element, as decided by the specified XPath
     * expression.
     *
     * @param expression
     *            the expression to compute
     *
     * @return the element's value
     */
    public String getElementValue(String expression) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(this.path);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(expression);
            return (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            throw new ParsingException(e);
        }

    }
}
