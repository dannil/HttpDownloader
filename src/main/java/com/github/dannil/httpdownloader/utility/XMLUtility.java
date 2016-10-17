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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.dannil.httpdownloader.exception.ParsingException;

/**
 * Class for fetching and manipulate data from XML files.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 0.0.1-SNAPSHOT
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
	 *            the path of the XML file
	 */
	public XMLUtility(final String path) {
		this();
		this.path = path;
	}

	/**
	 * Returns the value for a specific element, as decided by the specified XPath expression.
	 * 
	 * @param expression
	 *            the expression to compute
	 * 
	 * @return the element's value
	 */
	public final String getElementValue(final String expression) {
		final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = domFactory.newDocumentBuilder();
			final Document doc = builder.parse(this.path);

			final XPathFactory xPathfactory = XPathFactory.newInstance();
			final XPath xpath = xPathfactory.newXPath();
			final XPathExpression expr = xpath.compile(expression);
			final String value = (String) expr.evaluate(doc, XPathConstants.STRING);
			return value;
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			throw new ParsingException(e);
		}

	}
}
