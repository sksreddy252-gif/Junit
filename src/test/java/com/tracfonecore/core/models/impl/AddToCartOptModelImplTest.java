package com.tracfonecore.core.models.impl;

import com.tracfonecore.core.constants.CommerceConstants;
import com.tracfonecore.core.services.ApplicationConfigService;
import com.tracfonecore.core.services.BrandSpecificConfigService;
import com.tracfonecore.core.services.PurchaseFlowConfigService;
import com.tracfonecore.core.services.TracfoneApiGatewayService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddToCartOptModelImplTest {

    @Mock
    private Page currentPage;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private Resource resource;
    @Mock
    private ResourceResolver resolver;
    @Mock
    private TracfoneApiGatewayService tracfoneApiService;
    @Mock
    private ApplicationConfigService applicationConfigService;
    @Mock
    private PurchaseFlowConfigService purchaseFlowConfigService;
    @Mock
    private BrandSpecificConfigService brandSpecificConfigService;
    @Mock
    private ValueMap valueMap;
    @Mock
    private PageManager pageManager;

    @InjectMocks
    private AddToCartOptModelImpl model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(resource.getResourceResolver()).thenReturn(resolver);
        when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
    }

    @Test
    void testGettersAndSetters() {
        model.setHeading("Heading");
        assertEquals("Heading", model.getHeading());

        model.setCtaText("CTA");
        assertEquals("CTA", model.getCtaText());

        model.setCtaAltText("AltText");
        assertEquals("AltText", model.getCtaAltText());

        model.setCtaLink("/path");
        when(resolver.map("/path")).thenReturn("/mappedPath");
        assertEquals("/mappedPath", model.getCtaLink());
    }

    @Test
    void testQueryStringMethods() {
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(applicationConfigService.getHomePageLevel()).thenReturn(1);
        when(tracfoneApiService.getRefillPlanTypeFacet()).thenReturn("facet");
        when(tracfoneApiService.getOfferHppTrueFacet()).thenReturn("offerFacet");
        when(tracfoneApiService.getMultiMonthTabletFacet()).thenReturn("tabletFacet");

        assertTrue(model.getMarketIdQueryString().contains(CommerceConstants.BRANDNAME));
        assertTrue(model.getUpgradeElgQueryString().contains(CommerceConstants.BRANDNAME));
        assertTrue(model.getResourceMgmtQuery().contains(CommerceConstants.RESOURCE_CATEGORY));
    }

    @Test
    void testGlobalCallingCardQuantity_Default() {
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.containsKey(CommerceConstants.GLOBAL_CALLING_CARD_QUANTITY)).thenReturn(false);
        when(currentPage.getParent()).thenReturn(currentPage);
        when(currentPage.getParent().getProperties()).thenReturn(valueMap);
        when(valueMap.containsKey(anyString())).thenReturn(false);

        assertEquals(1, model.getGlobalCallingCardQuantity());
    }

    @Test
    void testTotalItemsList() {
        when(currentPage.getProperties()).thenReturn(valueMap);
        when(valueMap.containsKey(CommerceConstants.GLOBAL_CALLING_CARD_QUANTITY)).thenReturn(true);
        when(valueMap.get(CommerceConstants.GLOBAL_CALLING_CARD_QUANTITY, Integer.class)).thenReturn(3);

        List<Integer> items = model.getTotalItemsList();
        assertEquals(List.of(1, 2, 3), items);
    }

    @Test
    void testEnableTWPFlag() {
        assertFalse(model.isEnableTWP());
    }
}