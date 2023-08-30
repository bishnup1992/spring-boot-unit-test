package com.bishnu.codewithme.util;
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

        // Iterate through cmaReqDtlsList using Java 8 forEach
        cmaReqDtlsList.forEach(cmaReqstDtl -> {
            // Iterate through multiple promoId attributes using IntStream
            IntStream.rangeClosed(1, 9)
                    .mapToObj(i -> "promoId" + i) // Create promoId attribute names
                    .map(promoIdAttr -> (String) BeanUtils.getProperty(cmaReqstDtl, promoIdAttr))
                    .filter(promoId -> promoId != null && !promoId.isEmpty())
                    .forEach(promoId -> {
                        PromoInfo cachedPromoInfo = promoInfoCache.get(promoId);
                        if (cachedPromoInfo != null) {
                            promoInfoList.add(cachedPromoInfo);
                        } else {
                            List<PromoInfo> selectedPromoInfo = yourMapper.selectPromoInfo(promoId); // Pass promoId as String
                            selectedPromoInfo.forEach(info -> promoInfoCache.put(info.getPromoId(), info));
                            promoInfoList.addAll(selectedPromoInfo);
                        }
                    });
        });

        // Check if promoInfoList is not empty before batch inserting
        if (!promoInfoList.isEmpty()) {
            yourMapper.batchInsertIntoPromoIdDtlsStg(promoInfoList);
        }
    }

}
