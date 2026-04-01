package com.tracfonecore.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.mock.ResourceResolverType;

import com.tracfonecore.core.beans.CompatibilityCardBean;
import com.tracfonecore.core.beans.CompatibilityButtonBean;

@ExtendWith(AemContextExtension.class)
public class CompatibilityModelImplTest_updated {

    private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);
    private CompatibilityModelImpl model;
    private static final String TEST_RESOURCE_PATH = "/content/test";

    @BeforeEach
    void setUp() {
        context.create().resource(TEST_RESOURCE_PATH,
                "cards", List.of(new CompatibilityCardBean()),
                "buttons", List.of(new CompatibilityButtonBean()));
        model = context.request().adaptTo(CompatibilityModelImpl.class);
    }

    @Test
    void testCardsNotNull() {
        assertNotNull(model.getCards());
    }

    @Test
    void testButtonsNotNull() {
        assertNotNull(model.getButtons());
    }
}