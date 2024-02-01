package com.bishnu.codewithme.util;

import com.google.gson.JsonObject;

public class QueryStringBuilder {
    public static void main(String[] args) {
        // Simulated dynamic values from the request
        String gteValue = "2024-01-01T00:00:00";
        String lteValue = "2024-01-22T23:59:59";
        String oldDocumentStatusValue = "active,inactive"; // Get from request or leave as null
        String documentStatusValue = ""; // Get from request or leave as null

        // Prepare the components of the query
        String contentTypeQuery = "(ContentType:\"Disclosure Language\" OR ContentType:\"Disclaimer Language\")";
        String subLOBQuery = "subLOB:\"Disclosure Center of Excellence\"";
        String oldDocStatusQuery = buildOldDocStatusQuery(oldDocumentStatusValue);
        String documentStatusQuery = buildDocumentStatusQuery(documentStatusValue);
        String dateRangeQuery = buildDateRangeQuery(gteValue, lteValue);

        // Create a JsonObject
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("contentTypeQuery", contentTypeQuery);
        jsonObject.addProperty("subLOBQuery", subLOBQuery);
        if (oldDocStatusQuery != null) {
            jsonObject.addProperty("oldDocStatusQuery", oldDocStatusQuery);
        }
        if (documentStatusQuery != null) {
            jsonObject.addProperty("documentStatusQuery", documentStatusQuery);
        }
        jsonObject.addProperty("dateRangeQuery", dateRangeQuery);

        // Convert JsonObject to String
        String setQuery = jsonObject.toString();

        // Pass the setQuery as an argument to the setQueryMethod
        setQueryMethod(setQuery);
    }

    // Example method where setQuery is used
    private static void setQueryMethod(String setQuery) {
        // Placeholder implementation; replace with actual use of setQuery
        System.out.println("setQueryMethod called with setQuery: " + setQuery);
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
        return null;
    }

    // Helper method to build the documentStatus query part
    private static String buildDocumentStatusQuery(String documentStatusValue) {
        return (documentStatusValue != null && !documentStatusValue.isEmpty())
                ? "documentStatus:\"" + documentStatusValue + "\""
                : null;
    }

    // Helper method to build the date range query part
    private static String buildDateRangeQuery(String gteValue, String lteValue) {
        return "systemDocumentModificationDate GTE \"" + gteValue + "\" AND systemDocumentModificationDate LTE \"" + lteValue + "\"";
    }
}

