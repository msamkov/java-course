package net.azib.java.lessons.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * TransformerDemo
 *
 * @author anton
 */
public class XSLDemo {
	public static void main(String[] args) throws TransformerFactoryConfigurationError, SAXException, IOException, ParserConfigurationException, TransformerException {
		Transformer serializer = TransformerFactory.newInstance().newTransformer(new StreamSource(DOMDemo.class.getResource("books.xsl").toString()));
		Document doc = DOMDemo.createDocument();		
		serializer.transform(new DOMSource(doc), new StreamResult(System.out));
	}
}
