package com.bishnu.codewithme;

import com.bishnu.codewithme.util.CacheUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CacheUtilTest {
    @InjectMocks
    private CacheUtil cacheUtil;

    @Mock
    private YourMapper yourMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRefreshPromIdValues_WithMatchingPromoInfo() {
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("promoId1");

        PromoInfo promoInfo = new PromoInfo();
        promoInfo.setPromoId("promoId1");
        promoInfo.setPromoRateTp("RateType1");

        when(yourMapper.selectPromoInfo(any())).thenReturn(Collections.singletonList(promoInfo));

        yourClass.refreshPromIdValues(Collections.singletonList(cmaReqstDtl));

        verify(yourMapper, times(1)).batchInsertIntoPromoIdDtlsStg(Collections.singletonList(promoInfo));
    }

    @Test
    public void testRefreshPromIdValues_WithNonMatchingPromoInfo() {
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("promoId1");

        when(yourMapper.selectPromoInfo(any())).thenReturn(Collections.emptyList());

        yourClass.refreshPromIdValues(Collections.singletonList(cmaReqstDtl));

        verify(yourMapper, never()).batchInsertIntoPromoIdDtlsStg(any());
    }

    @Test
    public void testRefreshPromIdValues_WithEmptyCmaReqDtlsList() {
        yourClass.refreshPromIdValues(Collections.emptyList());

        verify(yourMapper, never()).batchInsertIntoPromoIdDtlsStg(any());
    }

    @Test
    public void testRefreshPromIdValues_WithNullPromoId() {
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1(null);

        yourClass.refreshPromIdValues(Collections.singletonList(cmaReqstDtl));

        verify(yourMapper, never()).batchInsertIntoPromoIdDtlsStg(any());
    }

    @Test
    public void testRefreshPromIdValues_WithEmptyPromoId() {
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("");

        yourClass.refreshPromIdValues(Collections.singletonList(cmaReqstDtl));

        verify(yourMapper, never()).batchInsertIntoPromoIdDtlsStg(any());
    }

    @Test
    public void testRefreshPromIdValues_WithMultiplePromoIds() {
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("promoId1");
        cmaReqstDtl.setPromoId2("promoId2");

        PromoInfo promoInfo1 = new PromoInfo();
        promoInfo1.setPromoId("promoId1");
        promoInfo1.setPromoRateTp("RateType1");

        PromoInfo promoInfo2 = new PromoInfo();
        promoInfo2.setPromoId("promoId2");
        promoInfo2.setPromoRateTp("RateType2");

        when(yourMapper.selectPromoInfo(any())).thenReturn(Collections.singletonList(promoInfo1), Collections.singletonList(promoInfo2));

        yourClass.refreshPromIdValues(Collections.singletonList(cmaReqstDtl));

        verify(yourMapper, times(1)).batchInsertIntoPromoIdDtlsStg(Collections.singletonList(promoInfo1));
        verify(yourMapper, times(1)).batchInsertIntoPromoIdDtlsStg(Collections.singletonList(promoInfo2));
    }
}
