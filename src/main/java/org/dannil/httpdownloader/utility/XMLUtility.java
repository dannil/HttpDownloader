package org.dannil.httpdownloader.utility;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public final class XMLUtility {

	private String path;

	private XMLUtility() {
		// Thread.currentThread().getContextClassLoader().getResource("").getPath()
		// + filename
	}

	public XMLUtility(final String path) {
		this();
		this.path = path;
	}

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
