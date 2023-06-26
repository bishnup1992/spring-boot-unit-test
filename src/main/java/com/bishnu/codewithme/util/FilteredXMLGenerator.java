package com.bishnu.codewithme.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FilteredXMLGenerator {
    public static void main(String[] args) {
        // Input merchant_language XML content as String
        String merchantLanguage = "<name>Merchant 1</name>\n<short_description>Short description 1</short_description>\n<long_description>Long description 1</long_description>\n<keywords>Keyword 1, Keyword 2</keywords>\n<categories>Category 1, Category 2</categories>";

        // Filter specific XML attributes
        String filteredXML = filterXMLAttributes(merchantLanguage, "name", "short_description", "keywords");

        // Save the filtered XML to merchantData.xml
        String filePath = "C:\\Learning_DOC\\final_xml\\merchantData.xml";
        boolean success = saveXMLToFile(filteredXML, filePath);

        if (success) {
            System.out.println("Filtered XML file created successfully at " + filePath);
        } else {
            System.out.println("Failed to create filtered XML file.");
        }
    }

    private static String filterXMLAttributes(String xmlContent, String... attributes) {
        StringBuilder filteredXMLBuilder = new StringBuilder();
        filteredXMLBuilder.append("<merchants>");
        for (String attribute : attributes) {
            String tag = "<" + attribute + ">";
            int startIndex = xmlContent.indexOf(tag);
            if (startIndex != -1) {
                startIndex += tag.length();
                int endIndex = xmlContent.indexOf("</" + attribute + ">", startIndex);
                if (endIndex != -1) {
                    String value = xmlContent.substring(startIndex, endIndex).trim();
                    filteredXMLBuilder.append("<").append(attribute).append(">").append(value).append("</").append(attribute).append(">\n");
                }
            }
        }
        filteredXMLBuilder.append("</merchants");

        return filteredXMLBuilder.toString();
    }

    private static boolean saveXMLToFile(String xmlContent, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(xmlContent);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
