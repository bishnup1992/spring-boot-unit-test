package com.bishnu.codewithme.util;

public class QueryStringBuilder {
    public static void main(String[] args) {
        // Simulated dynamic values from the request
        String gteValue = "2024-01-01T00:00:00";
        String lteValue = "2024-01-22T23:59:59";
        String oldDocumentStatusValue = "active,inactive"; // Get from request or leave as null
        String documentStatusValue = ""; // Get from request or leave as null

        // Construct the final query string
        String finalQueryString = buildFinalQueryString(gteValue, lteValue, oldDocumentStatusValue, documentStatusValue);

        // Print or use the finalQueryString as needed
        System.out.println("Final Query String: " + finalQueryString);
    }

    // Helper method to build the final query string
    private static String buildFinalQueryString(String gteValue, String lteValue, String oldDocumentStatusValue, String documentStatusValue) {
        // Prepare the components of the query
        String contentTypeQuery = "(ContentType:\"Disclosure Language\" OR ContentType:\"Disclaimer Language\")";
        String subLOBQuery = "subLOB:\"Disclosure Center of Excellence\"";
        String oldDocStatusQuery = buildOldDocStatusQuery(oldDocumentStatusValue);
        String documentStatusQuery = buildDocumentStatusQuery(documentStatusValue);
        String dateRangeQuery = buildDateRangeQuery(gteValue, lteValue);

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

        return finalQueryString.toString();
    }

    // Helper method to build the oldDocumentStatus query part
    private static String buildOldDocStatusQuery(String oldDocumentStatusValue) {
        if (oldDocumentStatusValue != null && !oldDocumentStatusValue.isEmpty()) {
            String[] statuses = oldDocumentStatusValue.split(",");
            StringBuilder queryBuilder = new StringBuilder("(");
            for (String status : statuses) {
                queryBuilder.append("oldDocumentStatus:").append(status).append(" OR ");
            }
            queryBuilder.delete(queryBuilder.length() - 4, queryBuilder.length()); // Remove the last " OR "
            queryBuilder.append(")");
            return queryBuilder.toString();
        }
        return "(oldDocumentStatus:Active OR oldDocumentStatus:InActive)";
    }

    // Helper method to build the documentStatus query part
    private static String buildDocumentStatusQuery(String documentStatusValue) {
        return (documentStatusValue != null && !documentStatusValue.isEmpty())
                ? "documentStatus:\"" + documentStatusValue + "\""
                : "documentStatus:\"Approved Version\"";
    }

    // Helper method to build the date range query part
    private static String buildDateRangeQuery(String gteValue, String lteValue) {
        return "systemDocumentModificationDate GTE \"" + gteValue + "\" AND systemDocumentModificationDate LTE \"" + lteValue + "\"";
    }
}

