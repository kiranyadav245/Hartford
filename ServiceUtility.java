package com.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ServiceUtility {
	public static void main(String[] args) throws Exception {
		ServiceUtility serviceUtility = new ServiceUtility();
		Scanner scanner = new Scanner(new File("C:/Selenium/request.xml"));
		//file to String
		String xml = scanner
				.useDelimiter("\\Z").next();
		//string to org.w3c.dom.Document
		Document document = serviceUtility.convertStringToDocument(xml);

		// xpath to find in org.w3c.dom.Document object
		String elementName = "ns0271:ProductCode";
//		getting element value from org.w3c.dom.Document using element id
		String nodeValueByElementName = serviceUtility.getNodeValueByElementName(document, elementName);
		System.out.println(nodeValueByElementName);
//		setting element value in org.w3c.dom.Document using element id
		document = serviceUtility.setNodeValueByElementName(document, elementName, "Kiran");
//		converting org.w3c.dom.Document to String 
		String documentToString = serviceUtility.convertDocumentToString(document);
		System.out.println(documentToString);
//		writing updated org.w3c.dom.Document to file
		File f = serviceUtility.convertStringToFile(documentToString);
		System.out.println(f);

	}

	public File convertStringToFile(String documentToString) {
		File fold = new File("C:/Selenium/request.xml");
		fold.delete();
		File fnew = new File("C:/Selenium/request1.xml");
		System.out.println(documentToString);

		try {
			FileWriter f2 = new FileWriter(fnew, false);
			f2.write(documentToString);
			f2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fnew;
	}

	public Document setNodeValueByElementName(Document document,
			String elementName, String value) {
		Node elementByTag = getElementByTagName(document, elementName);
		if (elementByTag != null) {
			elementByTag.setTextContent(value);
		}
		return document;
	}

	public String getNodeValueByElementName(Document document,
			String elementName) {
		Node elementByTag = getElementByTagName(document, elementName);
		if (elementByTag != null) {
			return elementByTag.getTextContent();
		}
		return null;
	}

	public static org.w3c.dom.Node getElementByTagName(Document document,
			String elementName) {
		NodeList nodeList = document.getElementsByTagName("*");
		for (int i = 0; i < nodeList.getLength(); i++) {
			org.w3c.dom.Node node = nodeList.item(i);
			System.out.println(node.getNodeName());
			if (elementName.equalsIgnoreCase(node.getNodeName())) {
				return node;
			}
		}
		return null;
	}

	public String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
