package de.hadizadeh.positioning.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages lists of content elements
 *
 * @param <Content> content element
 */
public class ContentList<Content> extends ArrayList<Content> {

    /**
     * Returns a single content of the given type
     *
     * @param contentType content type
     * @return content or null, if there is no content of the given type
     */
    public Content getContent(de.hadizadeh.positioning.content.Content.ContentType contentType) {
        int index = this.indexOf(new de.hadizadeh.positioning.content.Content(contentType, null));
        if (index != -1) {
            return this.get(index);
        }
        return null;
    }

    /**
     * Returns all contents of a given type
     *
     * @param contentType content type for filtering
     * @return list of contents which match the filter
     */
    public List<Content> getContents(de.hadizadeh.positioning.content.Content.ContentType contentType) {
        List<Content> contents = new ArrayList<Content>();
        for (Content content : this) {
            if (((de.hadizadeh.positioning.content.Content) content).getType().equals(contentType)) {
                contents.add(content);
            }
        }
        return contents;
    }
}
