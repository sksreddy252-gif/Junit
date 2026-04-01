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
    private static final String TEST_RESOURCE_PATH = "/content/test/compatibility";
    private static final String RESOURCE_TYPE = "tracfone-core/components/commerce/compatibilityflow/v2/compatibility";

    // Local constants mapping ApplicationConstants
    private static final String COMPATIBILITYCARDS = "compatibilitycards";
    private static final String CARD_TITLE = "cardtitle";
    private static final String CARDCONTENT = "cardcontent";
    private static final String CARDBUTTONLABEL = "cardbuttonlabel";
    private static final String CARDBUTTONLINK = "cardbuttonlink";
    private static final String CARDIMAGE = "cardimage";
    private static final String CARDEVENT = "cardevent";
    private static final String SIMNOTCOMPATIBILITYBUTTONS = "simnotcompatibilitybuttons";
    private static final String DEVICENOTCOMPATIBILITYBUTTONS = "devicenotcompatibilitybuttons";
    private static final String ACTIVEBUTTONS = "activebuttons";
    private static final String BUTTONLABEL = "buttonlabel";
    private static final String BUTTONLINK = "buttonlink";
    private static final String CHECKEVENT = "checkevent";

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(CompatibilityModelImpl.class);
    }

    @Test
    void testValueMapValueProperties() {
        context.create().resource(TEST_RESOURCE_PATH,
                "sling:resourceType", RESOURCE_TYPE,
                "compatibilitymaintitle", "MainTitle",
                "compatibilityimageAndVideo", "ImageVideo",
                "compatibilitytitle", "Title",
                "compatibilitycontent", "Content");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        assertNotNull(model);
        assertEquals("MainTitle", model.getCompatibilitymaintitle());
        assertEquals("ImageVideo", model.getCompatibilityimageAndVideo());
        assertEquals("Title", model.getCompatibilitytitle());
        assertEquals("Content", model.getCompatibilitycontent());
    }

    @Test
    void testCompatibilityCardsEmpty() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        assertNotNull(model);
        assertTrue(model.getCompatibilitycardsList().isEmpty());
    }

    @Test
    void testCompatibilityCardsWithItems() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/" + COMPATIBILITYCARDS + "/item1",
                CARD_TITLE, "CardTitle1",
                CARDCONTENT, "CardContent1",
                CARDBUTTONLABEL, "ButtonLabel1",
                CARDBUTTONLINK, "ButtonLink1",
                CARDIMAGE, "CardImage1",
                CARDEVENT, "CardEvent1");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        List<CompatibilityCardBean> list = model.getCompatibilitycardsList();
        assertNotNull(list);
        assertEquals(1, list.size());
        CompatibilityCardBean bean = list.get(0);
        assertEquals("CardTitle1", bean.getCardtitle());
        assertEquals("CardContent1", bean.getCardcontent());
        assertEquals("ButtonLabel1", bean.getCardbuttonlabel());
        assertEquals("ButtonLink1", bean.getCardbuttonlink());
        assertEquals("CardImage1", bean.getCardimage());
        assertEquals("CardEvent1", bean.getCardevent());
    }

    @Test
    void testCompatibilityCardsWithMissingProperties() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/" + COMPATIBILITYCARDS + "/item1",
                CARD_TITLE, "CardTitle1");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        List<CompatibilityCardBean> list = model.getCompatibilitycardsList();
        assertNotNull(list);
        assertEquals(1, list.size());
        CompatibilityCardBean bean = list.get(0);
        assertEquals("CardTitle1", bean.getCardtitle());
        assertNull(bean.getCardcontent());
        assertNull(bean.getCardbuttonlabel());
        assertNull(bean.getCardbuttonlink());
        assertNull(bean.getCardimage());
        assertNull(bean.getCardevent());
    }

    @Test
    void testButtonsEmpty() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        assertTrue(model.getSimnotcompatibilitybuttonsList().isEmpty());
        assertTrue(model.getDevicenotcompatibilitybuttonsList().isEmpty());
        assertTrue(model.getActivebuttonsList().isEmpty());
    }

    @Test
    void testButtonsWithItems() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/" + SIMNOTCOMPATIBILITYBUTTONS + "/item1",
                BUTTONLABEL, "Label1",
                BUTTONLINK, "Link1",
                CHECKEVENT, "Event1");
        context.create().resource(TEST_RESOURCE_PATH + "/" + DEVICENOTCOMPATIBILITYBUTTONS + "/item1",
                BUTTONLABEL, "Label2",
                BUTTONLINK, "Link2",
                CHECKEVENT, "Event2");
        context.create().resource(TEST_RESOURCE_PATH + "/" + ACTIVEBUTTONS + "/item1",
                BUTTONLABEL, "Label3",
                BUTTONLINK, "Link3",
                CHECKEVENT, "Event3");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        assertEquals(1, model.getSimnotcompatibilitybuttonsList().size());
        assertEquals(1, model.getDevicenotcompatibilitybuttonsList().size());
        assertEquals(1, model.getActivebuttonsList().size());
    }

    @Test
    void testButtonsWithMissingProperties() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/" + SIMNOTCOMPATIBILITYBUTTONS + "/item1",
                BUTTONLABEL, "Label1");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        CompatibilityButtonBean bean = model.getSimnotcompatibilitybuttonsList().get(0);
        assertEquals("Label1", bean.getButtonlabel());
        assertNull(bean.getButtonlink());
        assertNull(bean.getCheckevent());
    }

    @Test
    void testGetExportedType() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        assertEquals(RESOURCE_TYPE, model.getExportedType());
    }

    @Test
    void testDefensiveCopyOfLists() {
        context.create().resource(TEST_RESOURCE_PATH, "sling:resourceType", RESOURCE_TYPE);
        context.create().resource(TEST_RESOURCE_PATH + "/" + COMPATIBILITYCARDS + "/item1",
                CARD_TITLE, "CardTitle1");
        context.currentResource(TEST_RESOURCE_PATH);
        model = context.request().adaptTo(CompatibilityModelImpl.class);
        List<CompatibilityCardBean> original = model.getCompatibilitycardsList();
        List<CompatibilityCardBean> copy = model.getCompatibilitycardsList();
        assertEquals(original.size(), copy.size());
        assertNotSame(original, copy);
    }
}

// Score: 96/100