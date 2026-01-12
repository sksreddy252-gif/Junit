package com.example.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.core.models.CompatibilityModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class CompatibilityModelTest {

    private final AemContext context = new AemContext();

    @Test
    void testGetMaintitle() {
        context.create().resource("/content/component",
                "maintitle", "Main Title Value");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertEquals("Main Title Value", model.getMaintitle());
    }

    @Test
    void testGetTitle() {
        context.create().resource("/content/component",
                "title", "Section Title");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertEquals("Section Title", model.getTitle());
    }

    @Test
    void testGetContent() {
        context.create().resource("/content/component",
                "content", "Some content here");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertEquals("Some content here", model.getContent());
    }

    @Test
    void testGetImageAndVideo() {
        context.create().resource("/content/component",
                "imageAndVideo", "image.jpg");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertEquals("image.jpg", model.getImageAndVideo());
    }

    @Test
    void testEmptyCompatibilityCards() {
        context.create().resource("/content/component",
                "compatibilitycards", context.create().resource("/content/component/compatibilitycards"));
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertTrue(model.getCompatibilitycards().isEmpty());
    }

    @Test
    void testCompatibilityCardsWithMultipleItems() {
        context.create().resource("/content/component/compatibilitycards/item1",
                "cardtitle", "Title1",
                "cardcontent", "Content1",
                "cardbuttonlabel", "Label1",
                "cardbuttonlink", "/link1",
                "cardimage", "image1.jpg",
                "cardevent", "event1");
        context.create().resource("/content/component/compatibilitycards/item2",
                "cardtitle", "Title2",
                "cardcontent", "Content2",
                "cardbuttonlabel", "Label2",
                "cardbuttonlink", "/link2",
                "cardimage", "image2.jpg",
                "cardevent", "event2");

        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        List<?> cards = model.getCompatibilitycards();
        assertEquals(2, cards.size());
    }

    @Test
    void testCompatibilityCardsWithMissingOptionalProperties() {
        context.create().resource("/content/component/compatibilitycards/item1",
                "cardtitle", "Title1");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        List<?> cards = model.getCompatibilitycards();
        assertEquals(1, cards.size());
    }

    @Test
    void testEmptySimNotCompatibilityButtons() {
        context.create().resource("/content/component/simnotcompatibilitybuttons");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertTrue(model.getSimnotcompatibilitybuttons().isEmpty());
    }

    @Test
    void testSimNotCompatibilityButtonsWithMultipleItems() {
        context.create().resource("/content/component/simnotcompatibilitybuttons/item1",
                "buttonlabel", "Label1",
                "buttonlink", "/link1",
                "checkevent", "event1");
        context.create().resource("/content/component/simnotcompatibilitybuttons/item2",
                "buttonlabel", "Label2",
                "buttonlink", "/link2",
                "checkevent", "event2");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        List<?> buttons = model.getSimnotcompatibilitybuttons();
        assertEquals(2, buttons.size());
    }

    @Test
    void testEmptyDeviceNotCompatibilityButtons() {
        context.create().resource("/content/component/devicenotcompatibilitybuttons");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertTrue(model.getDevicenotcompatibilitybuttons().isEmpty());
    }

    @Test
    void testDeviceNotCompatibilityButtonsWithMultipleItems() {
        context.create().resource("/content/component/devicenotcompatibilitybuttons/item1",
                "buttonlabel", "Label1",
                "buttonlink", "/link1",
                "checkevent", "event1");
        context.create().resource("/content/component/devicenotcompatibilitybuttons/item2",
                "buttonlabel", "Label2",
                "buttonlink", "/link2",
                "checkevent", "event2");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        List<?> buttons = model.getDevicenotcompatibilitybuttons();
        assertEquals(2, buttons.size());
    }

    @Test
    void testEmptyActiveButtons() {
        context.create().resource("/content/component/activebuttons");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertTrue(model.getActivebuttons().isEmpty());
    }

    @Test
    void testActiveButtonsWithMultipleItems() {
        context.create().resource("/content/component/activebuttons/item1",
                "buttonlabel", "Label1",
                "buttonlink", "/link1",
                "checkevent", "event1");
        context.create().resource("/content/component/activebuttons/item2",
                "buttonlabel", "Label2",
                "buttonlink", "/link2",
                "checkevent", "event2");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        List<?> buttons = model.getActivebuttons();
        assertEquals(2, buttons.size());
    }

    @Test
    void testGetExportedType() {
        context.create().resource("/content/component");
        CompatibilityModel model = context.request().adaptTo(CompatibilityModel.class);
        assertNotNull(model.getExportedType());
    }
}