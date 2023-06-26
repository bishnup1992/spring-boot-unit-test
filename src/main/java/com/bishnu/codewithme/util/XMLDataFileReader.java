package com.bishnu.codewithme.util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLDataFileReader {
    public static void main(String[] args) {
        String filename = "C:\\Learning_DOC\\Merchant_Keywords\\LC_1001.xml";

        try {
            // Create a DocumentBuilder instance
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            File xmlFile = new File(filename);
            Document document = builder.parse(xmlFile);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Find the keywords element
            NodeList keywordsList = rootElement.getElementsByTagName("keyword");

            // Extract the data from the keywords element
            for (int i = 0; i < keywordsList.getLength(); i++) {
                Node keywordNode = keywordsList.item(i);
                String keywordValue = keywordNode.getTextContent();
                System.out.println("Keyword: " + keywordValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
