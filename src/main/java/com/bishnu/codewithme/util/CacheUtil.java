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


}
