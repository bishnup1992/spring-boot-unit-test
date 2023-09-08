package com.bishnu.codewithme.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*

 */
public class CacheUtil {
    public static void main(String[] args) {


    }


    public void refreshPromIdValues(List<CmaReqstDtl> cmaReqDtlsList) {
        List<PromoInfo> promoInfoList = new ArrayList<>();
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();
        ecmsDatabaseMapper.deletePromoIdValues();
        cmaReqDtlsList.forEach(cmaReqstDtl -> {
            processCmaRequestDetails(promoInfoList, promoInfoCache, cmaReqstDtl);
        });

        if (!promoInfoList.isEmpty()) {
            ecmsDatabaseMapper.insertPromoIdValues(promoInfoList);
        }

    }

    private static void processCmaRequestDetails(List<PromoInfo> promoInfoList, Map<String, PromoInfo> promoInfoCache, CmaReqstDtl cmaReqstDtl) {
        for (int i = 1; i <= 9; i++) {
            try {


                String promoIdAttr = "promoId" + i;
                String promoId = BeanUtils.getProperty(cmaReqstDtl, promoIdAttr);
                if (promoId != null && !promoId.isEmpty()) {
                    if (promoInfoCache.containsKey(promoId)) {
                        PromoInfo cachedPromoInfoObj = promoInfoCache.get(promoId);
                        if (cachedPromoInfoObj ! = null && !promoInfo.getPromoId().isEmpty()){
                            break; // Exit the loop when promoId is found
                        }
                    } else {
                        List<PromoInfo> selectedPromoInfo = yourMapper.selectPromoInfo(promoId);
                        if (selectedPromoInfo != null && !selectedPromoInfo.isEmpty()) {
                            promoInfoList.addAll(selectedPromoInfo);
                            selectedPromoInfo.forEach(info -> promoInfoCache.put(info.getPromoId(), info));
                            break; // Exit the loop when promoId is found
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Some error{} ", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }

    }


    // Java 8 Format

    public void refreshPromIdValues(List<CmaReqstDtl> cmaReqDtlsList) {
        List<PromoInfo> promoInfoList = new ArrayList<>();
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();

        cmaReqDtlsList.forEach(cmaReqstDtl -> {
            IntStream.range(1, 10) // Generate a stream of integers from 1 to 9
                    .mapToObj(i -> "promoId" + i) // Map the integer to promoId attribute name
                    .map(promoIdAttr -> {
                        try {
                            return (String) BeanUtils.getProperty(cmaReqstDtl, promoIdAttr);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            // Handle the exception, e.g., log it or take appropriate action
                            e.printStackTrace();
                            return null; // Return null in case of an exception
                        }
                    })
                    .filter(promoId -> promoId != null && !promoId.isEmpty()) // Filter non-null and non-empty promoIds
                    .anyMatch(promoId -> {
                        PromoInfo cachedPromoInfo = promoInfoCache.get(promoId);
                        if (cachedPromoInfo != null) {
                            // Check if promoInfoList already contains cachedPromoInfo
                            if (!promoInfoList.contains(cachedPromoInfo)) {
                                promoInfoList.add(cachedPromoInfo);
                            }
                            return true; // Exit the loop when promoId is found
                        } else {
                            List<PromoInfo> selectedPromoInfo = yourMapper.selectPromoInfo(promoId);
                            if (!selectedPromoInfo.isEmpty()) {
                                promoInfoList.addAll(selectedPromoInfo);
                                selectedPromoInfo.forEach(info -> promoInfoCache.put(info.getPromoId(), info));
                                return true; // Exit the loop when promoId is found
                            }
                        }
                        return false; // Continue to the next promoId if not found
                    });
        });

        // Check if promoInfoList is not empty before batch inserting
        if (!promoInfoList.isEmpty()) {
            yourMapper.batchInsertIntoPromoIdDtlsStg(promoInfoList);
        }
    }



}
