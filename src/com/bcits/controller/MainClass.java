package com.bcits.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MainClass {
  public static void main(String[] args) throws IOException, ParserConfigurationException,
      org.xml.sax.SAXException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
    factory.setValidating(false);
    factory.setNamespaceAware(true);
    factory.setFeature("http://xml.org/sax/features/namespaces", false);
    factory.setFeature("http://xml.org/sax/features/validation", false);
    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    DocumentBuilder parser = factory.newDocumentBuilder();
File f=new File("C:\\Users\\user\\Desktop\\MIP_uploads\\201809\\20180902\\12246042_2018-09-02.xml");
    Document document = parser.parse(f);

    NodeList sections = document.getElementsByTagName("D1");
    int numSections = sections.getLength();
    for (int i = 0; i < numSections; i++) {
      Element section = (Element) sections.item(i); // A <sect1>

      Node title = section.getFirstChild();
      while (title != null && title.getNodeType() != Node.ELEMENT_NODE)
        title = title.getNextSibling();

      if (title != null)
        System.out.println(title.getFirstChild().getNodeValue());
    }
  }
}