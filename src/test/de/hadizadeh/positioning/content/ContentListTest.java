package test.de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.Content;
import de.hadizadeh.positioning.content.ContentList;
import junit.framework.TestCase;

public class ContentListTest extends TestCase {
    private ContentList<Content> contentList;
    private Content description1;
    private Content description2;

    public void setUp() throws Exception {
        super.setUp();
        description1 = new Content(Content.ContentType.DESCRIPTION, "Description1");
        description2 = new Content(Content.ContentType.DESCRIPTION, "Description2");
        contentList = new ContentList<Content>();
        contentList.add(new Content(Content.ContentType.TITLE, "Title"));
        contentList.add(description1);
        contentList.add(description2);
        contentList.add(new Content(Content.ContentType.TEXTFILE, "file.txt"));
    }

    public void testGetContent() throws Exception {
        assertEquals(description1, contentList.getContent(Content.ContentType.DESCRIPTION));
        assertEquals(description1.getData(), contentList.getContent(Content.ContentType.DESCRIPTION).getData());
    }

    public void testGetContents() throws Exception {
        assertEquals(2, contentList.getContents(Content.ContentType.DESCRIPTION).size());
    }
}