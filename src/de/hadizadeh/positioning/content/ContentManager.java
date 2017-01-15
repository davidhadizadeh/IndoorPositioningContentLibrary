package de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.exceptions.ContentAlreadyExistsException;
import de.hadizadeh.positioning.content.exceptions.ContentPersistenceException;
import de.hadizadeh.positioning.content.exceptions.ContentSameDataException;
import de.hadizadeh.positioning.model.PositionInformation;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Manages all actions for contents
 */
public class ContentManager {
    /**
     * Persistence-Manager
     */
    protected ContentPersistenceManager contentPersistenceManager;
    /**
     * All persisted and loaded content
     */
    protected Map<String, ContentList<Content>> contents;

    /**
     * Creates the content manager
     *
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public ContentManager() throws ContentPersistenceException {
        this(new File("positionContent.xml"));
    }

    /**
     * Creates the content manager
     *
     * @param file File where the contents will be stored
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public ContentManager(File file) throws ContentPersistenceException {
        this(new XMLContentPersistenceManager(file));
    }

    /**
     * Creates the content manager
     *
     * @param contentPersistenceManager Persistence-Manager
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public ContentManager(ContentPersistenceManager contentPersistenceManager) throws ContentPersistenceException {
        this.contentPersistenceManager = contentPersistenceManager;
        contents = this.contentPersistenceManager.getConnectedContents();
    }

    /**
     * Returns all available contents
     *
     * @return list of all contents
     * @throws ContentPersistenceException if content could not be loaded from file
     */
    public ContentList<Content> getAllContents() throws ContentPersistenceException {
        return contentPersistenceManager.getAllContents();
    }

    /**
     * Returns all connected contents of a given position
     *
     * @param positionInformation position
     * @return connected content
     */
    public ContentList<Content> getContents(PositionInformation positionInformation) {
        return contents.get(positionInformation.getName());
    }

    /**
     * Returns all connected contents of all positions
     *
     * @return contents with position names
     * @throws ContentPersistenceException if content could not be loaded from file
     */
    public Map<String, ContentList<Content>> getConnectedContents() throws ContentPersistenceException {
        return contents;
    }

    /**
     * Adds a new content
     *
     * @param contentType content type
     * @param data        content data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if content could not saved to file
     */
    public boolean addContent(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.addContent(contentType, data);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Removes a content from the persisted data
     *
     * @param contentType content type of the content which has to be removes
     * @param data        content data of the content which has to be removes
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if content could not be removed from file
     */
    public boolean removeContent(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.removeContent(contentType, data);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Adds a single position to a content (connects)
     *
     * @param contentType  content type
     * @param data         content data
     * @param positionName name of the position
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if position could not be saved to file
     */
    public boolean addPosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.addPosition(contentType, data, positionName);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Adds multiple positions to multiple content data. The order of both is important (same order)
     *
     * @param contentTypes  content types
     * @param multipleData  content data
     * @param positionNames position names
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if positions could not be saved to file
     */
    public boolean addPositions(List<Content.ContentType> contentTypes, List<String> multipleData, List<String> positionNames) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.addPositions(contentTypes, multipleData, positionNames);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Removes a single connected position of a content
     *
     * @param contentType  content type
     * @param data         content data
     * @param positionName position name
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if changes could not be saved to file
     */
    public boolean removePosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.removePosition(contentType, data, positionName);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Removes all connected positions of a content
     *
     * @param contentType content type
     * @param data        content data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if changes could not be saved to file
     */
    public boolean removeAllPositions(Content.ContentType contentType, String data) throws ContentPersistenceException {
        boolean result = contentPersistenceManager.removeAllPositions(contentType, data);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Updates the type and the data of an existing content.
     *
     * @param oldContentType content type which will be changed
     * @param oldData        content data which will be changed
     * @param newContentType new content type
     * @param newData        new content data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException   if changes could not be saved to file
     * @throws ContentSameDataException      new and old data are same
     * @throws ContentAlreadyExistsException a content with the same new type and data is already existent
     */
    public boolean updateContent(Content.ContentType oldContentType, String oldData, Content.ContentType newContentType, String newData) throws ContentPersistenceException, ContentSameDataException, ContentAlreadyExistsException {
        boolean result = contentPersistenceManager.updateContent(oldContentType, oldData, newContentType, newData);
        contents = contentPersistenceManager.getConnectedContents();
        return result;
    }

    /**
     * Removes all contents
     *
     * @throws ContentPersistenceException if changes could not be saved to file
     */
    public void removeAllContent() throws ContentPersistenceException {
        contentPersistenceManager.removeAllContent();
        contents = contentPersistenceManager.getConnectedContents();
    }

    /**
     * Remove all conncted positions of every content element
     *
     * @throws ContentPersistenceException if changes could not be saved to file
     */
    public void removeAllPositions() throws ContentPersistenceException {
        contentPersistenceManager.removeAllPositions();
        contents = contentPersistenceManager.getConnectedContents();
    }
}
