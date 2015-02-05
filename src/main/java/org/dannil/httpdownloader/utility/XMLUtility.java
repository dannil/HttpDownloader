package org.dannil.httpdownloader.utility;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * Class for fetching and manipulate data from XML files.
 * 
 * @author Daniel Nilsson
 */
public final class XMLUtility {

	private String path;

	/**
	 * Default constructor
	 */
	private XMLUtility() {

	}

	/**
	 * Overloaded constructor
	 * 
	 * @param path
	 * 				the path of the XML file
	 */
	public XMLUtility(final String path) {
		this();
		this.path = path;
	}

	/**
	 * Returns the value for a specific element, as decided by the specified XPath expression.
	 * 
	 * @param expression
	 * 					the expression to compute
	 * 
	 * @return the element's value
	 */
	public final String getElementValue(final String expression) {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(this.path);

			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile(expression);
			return (String) expr.evaluate(doc, XPathConstants.STRING);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
