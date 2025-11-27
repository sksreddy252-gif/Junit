package com.mycompany.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.ResourceResolverType;

@ExtendWith(AemContextExtension.class)
class CompatibilityModelImplTest {

    private static final String TEST_RESOURCE_PATH = "/content/test/compatibility";
    private static final String RESOURCE_TYPE = "mycompany/components/compatibility";

    private CompatibilityModelImpl model;

    @BeforeEach
    void setUp(AemContext context) {
        context.addModelsForClasses(CompatibilityModelImpl.class);
    }

    @Test
    void testValueMapValueGetters(AemContext context) {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE,
                "title", "Compatibility Title",
                "description", "Compatibility Description");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);

        assertNotNull(model);
        assertEquals("Compatibility Title", model.getTitle());
        assertEquals("Compatibility Description", model.getDescription());
    }

    @Test
    void testEmptyChildList(AemContext context) {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getCompatibleItems();
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    void testChildListWithItems(AemContext context) {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/items/item1",
                "name", "Item 1",
                "code", "code1");
        context.create().resource(TEST_RESOURCE_PATH + "/items/item2",
                "name", "Item 2",
                "code", "code2");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getCompatibleItems();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("code1", items.get(0).getCode());
        assertEquals("Item 2", items.get(1).getName());
        assertEquals("code2", items.get(1).getCode());
    }

    @Test
    void testChildListWithMissingProperties(AemContext context) {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/items/item1",
                "name", "Item 1");
        context.create().resource(TEST_RESOURCE_PATH + "/items/item2",
                "code", "code2");

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);

        assertNotNull(model);
        List<?> items = model.getCompatibleItems();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertNull(items.get(0).getCode());
        assertNull(items.get(1).getName());
        assertEquals("code2", items.get(1).getCode());
    }

    @Test
    void testGetExportedType(AemContext context) {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE);

        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);

        assertNotNull(model);
        assertEquals(RESOURCE_TYPE, model.getExportedType());
    }
}