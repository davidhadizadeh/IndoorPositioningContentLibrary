package test.de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.Content;
import de.hadizadeh.positioning.content.ContentManager;
import de.hadizadeh.positioning.model.PositionInformation;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ContentManagerTest extends TestCase {
    private ContentManager contentManager;

    public void setUp() throws Exception {
        super.setUp();
        contentManager = new ContentManager();
        contentManager.removeAllContent();
        contentManager.addContent(Content.ContentType.TITLE, "title");
        contentManager.addContent(Content.ContentType.DESCRIPTION, "description");
        contentManager.addContent(Content.ContentType.TEXTFILE, "file.txt");
        contentManager.addPosition(Content.ContentType.DESCRIPTION, "description", "position");
    }

    public void testGetAllContents() throws Exception {
        assertEquals(3, contentManager.getAllContents().size());
    }

    public void testGetContents() throws Exception {
        assertNull(contentManager.getContents(new PositionInformation("id", null)));
    }

    public void testAddContent() throws Exception {
        assertTrue(contentManager.addContent(Content.ContentType.DESCRIPTION, "description1"));
    }

    public void testRemoveContent() throws Exception {
        assertTrue(contentManager.removeContent(Content.ContentType.DESCRIPTION, "description"));
    }

    public void testAddPosition() throws Exception {
        assertTrue(contentManager.addPosition(Content.ContentType.DESCRIPTION, "description", "position1"));
    }

    public void testAddPositions() throws Exception {
        List<Content.ContentType> types = new ArrayList<Content.ContentType>() {{
            add(Content.ContentType.DESCRIPTION);
        }};
        List<String> data = new ArrayList<String>() {{
            add("description");
        }};
        List<String> positions = new ArrayList<String>() {{
            add("position1");
        }};
        assertTrue(contentManager.addPositions(types, data, positions));
    }

    public void testRemovePosition() throws Exception {
        assertTrue(contentManager.removePosition(Content.ContentType.DESCRIPTION, "description", "position"));
    }

    public void testRemoveAllPositions() throws Exception {
        assertTrue(contentManager.removeAllPositions(Content.ContentType.DESCRIPTION, "description"));
    }

    public void testUpdateContent() throws Exception {
        assertTrue(contentManager.updateContent(Content.ContentType.DESCRIPTION, "description", Content.ContentType.TITLE, "title2"));
    }

    public void testRemoveAllContent() throws Exception {
        contentManager.removeAllContent();
        assertEquals(0, contentManager.getAllContents().size());
    }

}