package com.bishnu.codewithme.util;

import java.util.*;

public class MerchantKeywordReader {
    public static void main(String[] args) {
        Map<String, String> merchantLanguageMap = new HashMap<>();
        Map<String, String> merchantKeywordsMap = new HashMap<>();

        // Sample data for merchantLanguageMap
        merchantLanguageMap.put("Merchant1", "<keywords></keywords>");
        merchantLanguageMap.put("Merchant2", "<keywords></keywords>");
        merchantLanguageMap.put("Merchant3", "<keywords></keywords>");

        // Sample data for merchantKeywordsMap
        merchantKeywordsMap.put("Merchant1", "<keywords><keyword>AAA</keyword><keyword>BBB</keyword></keywords>");
        merchantKeywordsMap.put("Merchant2", "<keywords><keyword>ZZZ</keyword><keyword>YYY</keyword></keywords>");

        // Iterate over the merchantLanguageMap
        for (Map.Entry<String, String> entry : merchantLanguageMap.entrySet()) {
            String merchantId = entry.getKey();
            String languageContent = entry.getValue();

            // Check if merchantId exists in merchantKeywordsMap
            if (!merchantKeywordsMap.containsKey(merchantId)) {
                continue; // Skip to next iteration if merchantId not found in merchantKeywordsMap
            }

            String keywordsContent = merchantKeywordsMap.get(merchantId);
            List<String> keywordsList = extractKeywords(keywordsContent);
            List<String> sortedUniqueKeywords = getSortedUniqueKeywords(keywordsList);

            System.out.println("Merchant ID: " + merchantId);
            System.out.println("Language Content: " + languageContent);
            System.out.println("Keywords: " + sortedUniqueKeywords);
            System.out.println();
        }
    }

    // Helper method to extract keywords from the content within <keywords></keywords> tag
    private static List<String> extractKeywords(String content) {
        List<String> keywordsList = new ArrayList<>();

        int startIndex = content.indexOf("<keywords>") + "<keywords>".length();
        int endIndex = content.indexOf("</keywords>");

        String keywordsContent = content.substring(startIndex, endIndex);
        String[] keywordsArray = keywordsContent.split("<keyword>");

        for (String keyword : keywordsArray) {
            if (!keyword.isEmpty()) {
                keywordsList.add(keyword.trim().replace("</keyword>", ""));
            }
        }

        return keywordsList;
    }

    // Helper method to get sorted unique keywords
    private static List<String> getSortedUniqueKeywords(List<String> keywordsList) {
        Set<String> uniqueKeywords = new TreeSet<>(keywordsList);
        return new ArrayList<>(uniqueKeywords);
    }
}

