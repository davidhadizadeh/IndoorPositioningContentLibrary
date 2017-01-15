package de.hadizadeh.positioning.content;


import java.util.List;

/**
 * Model for holding storing data
 */
public class Content {

    /**
     * Available content types
     */
    public enum ContentType {
        TITLE("TITLE"),
        DESCRIPTION("DESCRIPTION"),
        HTML_TEXT("HTML_TEXT"),
        URL("URL"),
        TEXTFILE("TEXTFILE"),
        HTML_TEXTFILE("HTML_TEXTFILE"),
        IMAGE("IMAGE"),
        AUDIO("AUDIO"),
        MOVIE("MOVIE");

        private final String text;

        private ContentType(final String text) {
            this.text = text;
        }

        /**
         * Converts the current content type to a string
         *
         * @return current content type as string
         */
        @Override
        public String toString() {
            return text;
        }
    }

    private ContentType type;
    private String data;
    private List<String> positions;

    /**
     * Creates a content
     *
     * @param type content type
     * @param data content data
     */
    public Content(ContentType type, String data) {
        this.type = type;
        this.data = data;
    }

    /**
     * Creates a content
     *
     * @param type      content type
     * @param data      content data
     * @param positions connected positions
     */
    public Content(ContentType type, String data, List<String> positions) {
        this.type = type;
        this.data = data;
        this.positions = positions;
    }

    /**
     * Returns the content type
     *
     * @return content type
     */
    public ContentType getType() {
        return type;
    }

    /**
     * Sets the content type
     *
     * @param type content type
     */
    public void setType(ContentType type) {
        this.type = type;
    }

    /**
     * Returns the content data
     *
     * @return content data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the content data
     *
     * @param data content data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Returns the connected positions
     *
     * @return with the content connected positions
     */
    public List<String> getPositions() {
        return positions;
    }

    /**
     * Sets the connected positions
     *
     * @param positions with the content connected positions
     */
    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Content))
            return false;

        Content content = (Content) o;

        if (type != content.type)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
