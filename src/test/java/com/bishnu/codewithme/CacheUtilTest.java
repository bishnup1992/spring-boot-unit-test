/*
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
    private YourClass yourClass; // Replace YourClass with the actual class name containing the methods.

    @Mock
    private EcmsDatabaseMapper ecmsDatabaseMapper; // Replace with the actual database mapper interface.

    @Mock
    private YourMapper yourMapper; // Replace with the actual mapper interface.

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRefreshPromIdValues_WithEmptyCmaReqDtlsList() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Verify that deletePromoIdValues and insertPromoIdValues are never called when the list is empty
        verify(ecmsDatabaseMapper, never()).deletePromoIdValues();
        verify(ecmsDatabaseMapper, never()).insertPromoIdValues(anyList());
    }

    @Test
    public void testRefreshPromIdValues_WithCachedPromoInfo() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();
        // Add CmaReqstDtl objects to the list for testing

        List<PromoInfo> promoInfoList = new ArrayList<>();
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();

        // Mock yourMapper.selectPromoInfo to return a non-empty list
        when(yourMapper.selectPromoInfo(anyString())).thenReturn(new ArrayList<>());

        // Add a PromoInfo to the cache
        PromoInfo cachedPromoInfo = new PromoInfo();
        cachedPromoInfo.setPromoId("cachedPromoId");
        promoInfoCache.put("cachedPromoId", cachedPromoInfo);

        // Set one of the CmaReqstDtl objects to have "cachedPromoId" as a promoId
        CmaReqstDtl cmaReqstDtlWithCachedPromoId = new CmaReqstDtl();
        cmaReqstDtlWithCachedPromoId.setPromoId1("cachedPromoId");
        cmaReqDtlsList.add(cmaReqstDtlWithCachedPromoId);

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Verify that deletePromoIdValues and insertPromoIdValues are called once
        verify(ecmsDatabaseMapper, times(1)).deletePromoIdValues();
        verify(ecmsDatabaseMapper, times(1)).insertPromoIdValues(promoInfoList);

        // Verify that the cached PromoInfo is not added again to promoInfoList
        verify(yourMapper, never()).selectPromoInfo(eq("cachedPromoId"));
    }

    @Test
    public void testRefreshPromIdValues_WithEmptySelectedPromoInfo() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();
        // Add CmaReqstDtl objects to the list for testing

        List<PromoInfo> promoInfoList = new ArrayList<>();
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();

        // Mock yourMapper.selectPromoInfo to return an empty list
        when(yourMapper.selectPromoInfo(anyString())).thenReturn(new ArrayList<>());

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Verify that deletePromoIdValues and insertPromoIdValues are called once
        verify(ecmsDatabaseMapper, times(1)).deletePromoIdValues();
        verify(ecmsDatabaseMapper, times(1)).insertPromoIdValues(promoInfoList);
    }

    @Test
    public void testRefreshPromIdValues_WithMultiplePromoIds() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();

        // Create multiple CmaReqstDtl objects with different promoIds
        CmaReqstDtl cmaReqstDtl1 = new CmaReqstDtl();
        cmaReqstDtl1.setPromoId1("promoId1");
        cmaReqstDtl1.setPromoId2("promoId2");
        cmaReqstDtl1.setPromoId3("promoId3");
        cmaReqstDtl1.setPromoId4("promoId4");
        cmaReqstDtl1.setPromoId5("promoId5");
        cmaReqstDtl1.setPromoId6("promoId6");
        cmaReqstDtl1.setPromoId7("promoId7");
        cmaReqstDtl1.setPromoId8("promoId8");
        cmaReqstDtl1.setPromoId9("promoId9");

        CmaReqstDtl cmaReqstDtl2 = new CmaReqstDtl();
        cmaReqstDtl2.setPromoId1("promoId10");
        cmaReqstDtl2.setPromoId2("promoId11");
        cmaReqstDtl2.setPromoId3("promoId12");
        cmaReqstDtl2.setPromoId4("promoId13");
        cmaReqstDtl2.setPromoId5("promoId14");
        cmaReqstDtl2.setPromoId6("promoId15");
        cmaReqstDtl2.setPromoId7("promoId16");
        cmaReqstDtl2.setPromoId8("promoId17");
        cmaReqstDtl2.setPromoId9("promoId18");

        cmaReqDtlsList.add(cmaReqstDtl1);
        cmaReqDtlsList.add(cmaReqstDtl2);

        List<PromoInfo> promoInfoList = new ArrayList<>();
        Map<String, PromoInfo> promoInfoCache = new HashMap<>();

        // Mock yourMapper.selectPromoInfo to return a non-empty list
        when(yourMapper.selectPromoInfo(anyString())).thenReturn(new ArrayList<>());

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Verify that deletePromoIdValues and insertPromoIdValues are called once
        verify(ecmsDatabaseMapper, times(1)).deletePromoIdValues();
        verify(ecmsDatabaseMapper, times(1)).insertPromoIdValues(promoInfoList);

        // Verify that the method correctly processes all promoIds from both CmaReqstDtl objects
        verify(yourMapper, times(18)).selectPromoInfo(anyString());
    }

    @Test
    public void testRefreshPromIdValues_EmptyInputList() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();
        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Assert that neither deletePromoIdValues nor insertPromoIdValues are called
        verify(ecmsDatabaseMapper, never()).deletePromoIdValues();
        verify(ecmsDatabaseMapper, never()).insertPromoIdValues(anyList());
    }

    @Test
    public void testRefreshPromIdValues_EmptyPromoInfoList() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("promoId1");
        cmaReqDtlsList.add(cmaReqstDtl);

        // Mock yourMapper.selectPromoInfo to return an empty list
        when(yourMapper.selectPromoInfo(anyString())).thenReturn(Collections.emptyList());

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Assert that neither deletePromoIdValues nor insertPromoIdValues are called
        verify(ecmsDatabaseMapper, never()).deletePromoIdValues();
        verify(ecmsDatabaseMapper, never()).insertPromoIdValues(anyList());
    }

    @Test
    public void testRefreshPromIdValues_ExceptionHandling() {
        List<CmaReqstDtl> cmaReqDtlsList = new ArrayList<>();
        CmaReqstDtl cmaReqstDtl = new CmaReqstDtl();
        cmaReqstDtl.setPromoId1("promoId1");
        cmaReqDtlsList.add(cmaReqstDtl);

        // Mock BeanUtils.getProperty to throw an exception
        when(BeanUtils.getProperty(any(), anyString())).thenThrow(new RuntimeException("Property access error"));

        yourClass.refreshPromIdValues(cmaReqDtlsList);

        // Assert that neither deletePromoIdValues nor insertPromoIdValues are called
        verify(ecmsDatabaseMapper, never()).deletePromoIdValues();
        verify(ecmsDatabaseMapper, never()).insertPromoIdValues(anyList());
    }

}
*/
