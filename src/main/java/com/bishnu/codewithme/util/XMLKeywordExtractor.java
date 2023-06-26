package com.bishnu.codewithme.util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLKeywordExtractor {
    public static void main(String[] args) {
        String filename = "C:\\Learning_DOC\\Merchant_Keywords\\LC_1001.xml";

        try {
            // Create a DocumentBuilder instance
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            Document document = builder.parse(new File(filename));

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Find the keywords element
            NodeList keywordsList = rootElement.getElementsByTagName("keywords");

            // Extract the content from the keywords element
            if (keywordsList.getLength() > 0) {
                Element keywordsElement = (Element) keywordsList.item(0);
                String keywordsContent = keywordsElement.getTextContent();
                System.out.println("Keywords: " + keywordsContent);
            } else {
                System.out.println("No keywords found in the XML file.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

