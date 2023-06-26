package com.bishnu.codewithme.util;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class XMLFileReader {
    public static void main(String[] args) {
        String parentFolder = "C:\\Learning_DOC\\Merchant_Language";
       // String childFolder = "C:\\Learning_DOC\\Merchant_Keywords";
        String filename = "LC_1001.xml";

        try {
            // Read the parent file
            File parentFile = new File(parentFolder, filename);
            FileReader parentReader = new FileReader(parentFile);
            BufferedReader parentBufferedReader = new BufferedReader(parentReader);

            StringBuilder parentContent = new StringBuilder();
            String parentLine;
            while ((parentLine = parentBufferedReader.readLine()) != null) {
                parentContent.append(parentLine).append("\n");
            }

            parentBufferedReader.close();
            parentReader.close();

            // Read the child file
            String childContent = extractContent("C:\\Learning_DOC\\Merchant_Keywords\\LC_1001.xml");
            String finalContent = parentContent.toString()
                    .replaceAll("<keywords></keywords>", "<keywords>"+childContent+"</keywords>");

            // Replace the parent file with the child file's keywords
            FileWriter parentWriter = new FileWriter("C:\\Learning_DOC\\Merchant_Language\\final.xml");
            parentWriter.write(finalContent);
            parentWriter.close();

            System.out.println("Parent file updated: " + parentFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractContent(String filename) {
        String keywordsContent = null;
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
            NodeList keywordsList = rootElement.getElementsByTagName("keywords");

            // Extract the content from the keywords element as a String
            if (keywordsList.getLength() > 0) {
                Element keywordsElement = (Element) keywordsList.item(0);
                keywordsContent = getInnerXml(keywordsElement);
                System.out.println("Keywords Content: " + keywordsContent);
            } else {
                System.out.println("No keywords found in the XML file.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keywordsContent;
    }

    // Helper method to extract inner XML content as a String
    // Helper method to extract inner XML content as a String
    private static String getInnerXml(Node node) {
        DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer lsSerializer = lsImpl.createLSSerializer();

        // Configure the serializer to exclude the XML declaration
        DOMConfiguration domConfig = lsSerializer.getDomConfig();
        LSOutput lsOutput = lsImpl.createLSOutput();
        lsOutput.setEncoding("UTF-8"); // Set the desired encoding
        domConfig.setParameter("xml-declaration", false);

        NodeList childNodes = node.getChildNodes();
        StringBuilder xmlContent = new StringBuilder();
        for (int i = 0; i < childNodes.getLength(); i++) {
            xmlContent.append(lsSerializer.writeToString(childNodes.item(i)));
        }
        return xmlContent.toString();
    }
}
