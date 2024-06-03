/*
package com.bishnu.codewithme.util;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.InputSource;
public class XMLDocumentParser {


    private Document parseDocument(String base64EncodedResponse) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            // Disable external entity expansion
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Decode the Base64-encoded response
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedResponse);
            String decodedResponse = new String(decodedBytes, StandardCharsets.UTF_8);

            // Create an InputSource from the decoded response
            InputSource inputSource = new InputSource(new StringReader(decodedResponse));

            // Parse the XML and return the Document
            return documentBuilder.parse(inputSource);
        } catch (Exception e) {
            // Handle any exceptions that may occur during parsing
            e.printStackTrace(); // You can log the error or handle it as needed
            return null; // Or return an appropriate value in case of failure
        }
    }

}
*/
