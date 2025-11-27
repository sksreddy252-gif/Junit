package com.mycompany.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mycompany.core.models.impl.AddToCartOptModelImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.ResourceResolverType;

@ExtendWith(AemContextExtension.class)
class AddToCartOptModelImplTest {

    private static final String TEST_RESOURCE_PATH = "/content/test/addtocartopt";
    private static final String RESOURCE_TYPE = "mycompany/components/addtocartopt";

    private AemContext context;
    private AddToCartOptModelImpl model;

    @BeforeEach
    void setUp(AemContext context) {
        this.context = context;
        context.addModelsForClasses(AddToCartOptModelImpl.class);
    }

    @Test
    void testValueMapValueGetters() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE,
                "title", "Sample Title",
                "description", "Sample Description",
                "ctaText", "Add to Cart");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(AddToCartOptModelImpl.class);

        assertNotNull(model);
        assertEquals("Sample Title", model.getTitle());
        assertEquals("Sample Description", model.getDescription());
        assertEquals("Add to Cart", model.getCtaText());
    }

    @Test
    void testEmptyChildList() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(AddToCartOptModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getOptions();
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    void testChildListWithItems() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/options/option1",
                "label", "Option 1",
                "value", "opt1");
        context.create().resource(TEST_RESOURCE_PATH + "/options/option2",
                "label", "Option 2",
                "value", "opt2");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(AddToCartOptModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getOptions();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Option 1", items.get(0).getLabel());
        assertEquals("opt1", items.get(0).getValue());
        assertEquals("Option 2", items.get(1).getLabel());
        assertEquals("opt2", items.get(1).getValue());
    }

    @Test
    void testChildListWithMissingProperties() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/options/option1",
                "label", "Option 1");
        context.create().resource(TEST_RESOURCE_PATH + "/options/option2",
                "value", "opt2");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(AddToCartOptModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getOptions();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Option 1", items.get(0).getLabel());
        assertNull(items.get(0).getValue());
        assertNull(items.get(1).getLabel());
        assertEquals("opt2", items.get(1).getValue());
    }

    @Test
    void testGetExportedType() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(AddToCartOptModelImpl.class);

        assertNotNull(model);
        assertEquals(RESOURCE_TYPE, model.getExportedType());
    }
}