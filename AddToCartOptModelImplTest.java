package com.tracfone.commerce.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddToCartOptModelImplTest {

    @InjectMocks
    private AddToCartOptModelImpl model;

    @Mock
    private TracfoneApiGatewayService tracfoneApiGatewayService;

    @BeforeEach
    void setUp() {
        model.setCategoryType("Phones");
        model.setHeading("Add to Cart");
        model.setCtaText("Buy Now");
        model.setCtaAltText("Buy Now Alt");
        model.setCtaLink("/buy");
        model.setDisclaimer("Disclaimer text");
        model.setFootNote("Footnote text");
        model.setCartOpt("Option1");
        model.setLogoLink("/logo");
        model.setLogoAltText("Logo Alt");
        model.setLogoSubtext("Logo Subtext");
        model.setModalHeading("Modal Heading");
        model.setModalLabel("Modal Label");
        model.setModalCtaText("Modal CTA");
        model.setModalCtaAltText("Modal CTA Alt");
        model.setModalCtaLink("/modal");
        model.setApiDomain("https://api.example.com");
        model.setMarketingIdApiPath("/marketing");
        model.setUpgradeElgApiPath("/upgrade");
        model.setQueryString("/query");
        model.setMarketIdQueryString("?marketId=123");
        model.setUpgradeElgQueryString("?upgrade=true");
        model.setDisableCartOptionsRadio(true);
        model.setUseIncrementalQuantity(false);
        model.setEnableTWP(true);
        model.setTotalItemsList(Arrays.asList("Item1", "Item2"));
    }

    @Test
    void testGetCategoryType() {
        assertEquals("Phones", model.getCategoryType());
    }

    @Test
    void testGetHeading() {
        assertEquals("Add to Cart", model.getHeading());
    }

    @Test
    void testGetCtaText() {
        assertEquals("Buy Now", model.getCtaText());
    }

    @Test
    void testGetCtaAltText() {
        assertEquals("Buy Now Alt", model.getCtaAltText());
    }

    @Test
    void testGetCtaLink() {
        assertEquals("/buy", model.getCtaLink());
    }

    @Test
    void testGetDisclaimer() {
        assertEquals("Disclaimer text", model.getDisclaimer());
    }

    @Test
    void testGetFootNote() {
        assertEquals("Footnote text", model.getFootNote());
    }

    @Test
    void testGetCartOpt() {
        assertEquals("Option1", model.getCartOpt());
    }

    @Test
    void testGetLogoLink() {
        assertEquals("/logo", model.getLogoLink());
    }

    @Test
    void testGetLogoAltText() {
        assertEquals("Logo Alt", model.getLogoAltText());
    }

    @Test
    void testGetLogoSubtext() {
        assertEquals("Logo Subtext", model.getLogoSubtext());
    }

    @Test
    void testGetModalHeading() {
        assertEquals("Modal Heading", model.getModalHeading());
    }

    @Test
    void testGetModalLabel() {
        assertEquals("Modal Label", model.getModalLabel());
    }

    @Test
    void testGetModalCtaText() {
        assertEquals("Modal CTA", model.getModalCtaText());
    }

    @Test
    void testGetModalCtaAltText() {
        assertEquals("Modal CTA Alt", model.getModalCtaAltText());
    }

    @Test
    void testGetModalCtaLink() {
        assertEquals("/modal", model.getModalCtaLink());
    }

    @Test
    void testGetApiDomain() {
        assertEquals("https://api.example.com", model.getApiDomain());
    }

    @Test
    void testGetMarketingIdApiPath() {
        assertEquals("/marketing", model.getMarketingIdApiPath());
    }

    @Test
    void testGetUpgradeElgApiPath() {
        assertEquals("/upgrade", model.getUpgradeElgApiPath());
    }

    @Test
    void testGetQueryString() {
        assertEquals("/query", model.getQueryString());
    }

    @Test
    void testGetMarketIdQueryString() {
        assertEquals("?marketId=123", model.getMarketIdQueryString());
    }

    @Test
    void testGetUpgradeElgQueryString() {
        assertEquals("?upgrade=true", model.getUpgradeElgQueryString());
    }

    @Test
    void testGetDisableCartOptionsRadio() {
        assertTrue(model.getDisableCartOptionsRadio());
    }

    @Test
    void testGetUseIncrementalQuantity() {
        assertFalse(model.getUseIncrementalQuantity());
    }

    @Test
    void testIsEnableTWP() {
        assertTrue(model.isEnableTWP());
    }

    @Test
    void testGetTotalItemsList() {
        List<String> items = model.getTotalItemsList();
        assertEquals(2, items.size());
        assertTrue(items.contains("Item1"));
        assertTrue(items.contains("Item2"));
    }

    @Test
    void testEmptyTotalItemsList() {
        model.setTotalItemsList(Collections.emptyList());
        assertTrue(model.getTotalItemsList().isEmpty());
    }
}