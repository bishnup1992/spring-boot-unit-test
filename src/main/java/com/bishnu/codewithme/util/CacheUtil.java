package com.bishnu.codewithme.util;
import java.util.*;
import java.util.stream.Collectors;

/*

 */
public class CacheUtil {
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public static void main(String[] args) {


    }


    public void refreshPromIdValues(List<CmaReqstDtl> cmaReqDtlsList) {
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();

        List<PromoInfo> promoInfoList = cmaReqDtlsList.stream()
                .flatMap(cmaReqstDtl -> {
                    return IntStream.rangeClosed(1, 9)
                            .mapToObj(i -> {
                                String promoIdAttr = "promoId" + i;
                                String promoId = (String) BeanUtils.getProperty(cmaReqstDtl, promoIdAttr);

                                if (promoId != null && !promoId.isEmpty()) {
                                    return promoInfoCache.computeIfAbsent(promoId, key -> {
                                        List<PromoInfo> selectedPromoInfo = yourMapper.selectPromoInfo(cmaReqstDtl);
                                        return selectedPromoInfo; // Return the whole list
                                    });
                                }
                                return null;
                            })
                            .filter(Objects::nonNull)
                            .flatMap(List::stream); // Flatten the list of lists
                })
                .collect(Collectors.toList());

        if (!promoInfoList.isEmpty()) {
            yourMapper.batchInsertIntoPromoIdDtlsStg(promoInfoList);
        }
    }

}
