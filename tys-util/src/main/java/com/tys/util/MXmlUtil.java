package com.tys.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class MXmlUtil {

	/**
	 * 获取xml指定标签名对应的值
	 * 
	 * @param document
	 * @param tagName
	 * @return
	 */
	public static String getXmlValue(Document document, String tagName) {
		String retString = null;
		NodeList list = document.getElementsByTagName(tagName);

		if (list.getLength() > 0) {
			Element element = (Element) list.item(0);
			retString = element.getFirstChild().getNodeValue();
		}
		return retString;
	}

	/**
	 * 将传入的一个XML String转换成一个org.w3c.dom.Document对象返回。
	 * 
	 * @param xmlString
	 *            一个符合XML规范的字符串表达。
	 * @return a Document
	 */
	public static Document parseXMLDocument(String xmlString) {
		if (xmlString == null) {
			throw new IllegalArgumentException();
		}
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(xmlString)));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*
	 * 把dom文件转换为xml字符串
	 */
	public static String toStringFromDoc(Document document) {
		String result = null;
		if (document != null) {
			StringWriter strWtr = new StringWriter();
			StreamResult strResult = new StreamResult(strWtr);
			TransformerFactory tfac = TransformerFactory.newInstance();
			try {
				javax.xml.transform.Transformer t = tfac.newTransformer();
				t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
				// text
//				t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				t.transform(new DOMSource(document.getDocumentElement()), strResult);
			} catch (Exception e) {
				System.err.println("XML.toString(Document): " + e);
			}
			result = strResult.getWriter().toString();
			try {
				strWtr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
