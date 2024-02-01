package com.bishnu.codewithme.util;

public class QueryStringBuilder {
    public static void main(String[] args) {
        // Prepare the components of the query
        String contentTypeQuery = "(ContentType:\"Disclosure Language\" OR ContentType:\"Disclaimer Language\")";
        String subLOBQuery = "subLOB:\"Disclosure Center of Excellence\"";
        String oldDocStatusQuery = "(oldDocumentStatus:Active OR oldDocumentStatus:InActive)";
        String documentStatusQuery = "documentStatus:\"Approved Version\"";
        String dateRangeQuery = "systemDocumentModificationDate GTE \"2024-01-01T00:00:00\" AND systemDocumentModificationDate LTE \"2024-01-22T23:59:59\"";

        // Construct the final query string
        StringBuilder finalQueryString = new StringBuilder()
                .append(contentTypeQuery)
                .append(" AND ")
                .append(subLOBQuery)
                .append(" AND ")
                .append(oldDocStatusQuery)
                .append(" AND ")
                .append(documentStatusQuery)
                .append(" AND ")
                .append(dateRangeQuery);

        // Set the final query string to the parameter called setQuery
        String setQuery = finalQueryString.toString();

        // Print or use the setQuery parameter as needed
        System.out.println("Final Query String: " + setQuery);
    }
}
