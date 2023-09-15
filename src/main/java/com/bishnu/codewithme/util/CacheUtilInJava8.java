package com.bishnu.codewithme.util;

public class CacheUtilInJava8 {

    private void processPromoInfo(List<PromoInfo> promoInfoList, Map<String, List<PromoInfo>> promoInfoCache, CmaReqDtl cmaRequestDtl) {
        IntStream.rangeClosed(1, 9)
                .mapToObj(i -> "promoId" + i)
                .map(promoIdAttr -> {
                    try {
                        return BeanUtils.getProperty(cmaRequestDtl, promoIdAttr);
                    } catch (Exception e) {
                        log.error("processPromoInfoDetails got exception {}", e.getMessage());
                        return null;
                    }
                })
                .filter(StringUtils::isNotEmpty)
                .anyMatch(promoId -> {
                    if (promoInfoCache.containsKey(promoId)) {
                        List<PromoInfo> promoInfos = promoInfoCache.get(promoId);
                        if (CollectionUtils.isNotEmpty(promoInfos)) {
                            return true;
                        }
                    } else {
                        List<PromoInfo> promoInfos = cplanDatabaseMapper.getPromoInfo(promoId);
                        if (CollectionUtils.isNotEmpty(promoInfos)) {
                            promoInfoList.addAll(promoInfos);
                            promoInfoCache.put(promoId, promoInfos);
                            return true;
                        } else {
                            log.debug("processPromoInfoDetails No PromoInfo Found For the Id {}", promoId);
                        }
                    }
                    return false;
                });
    }

}
