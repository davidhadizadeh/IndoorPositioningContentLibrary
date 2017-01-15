package de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.exceptions.ContentAlreadyExistsException;
import de.hadizadeh.positioning.content.exceptions.ContentPersistenceException;
import de.hadizadeh.positioning.content.exceptions.ContentSameDataException;

import java.util.List;
import java.util.Map;

public interface ContentPersistenceManager {
    /**
     * Returns all persisted contents
     *
     * @return persisted contents
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    ContentList<Content> getAllContents() throws ContentPersistenceException;

    /**
     * Returns all connected contents with positions
     *
     * @return contents with position names
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    Map<String, ContentList<Content>> getConnectedContents() throws ContentPersistenceException;

    /**
     * Adds a content
     *
     * @param contentType
     * @param data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean addContent(Content.ContentType contentType, String data) throws ContentPersistenceException;

    /**
     * Removes a content
     *
     * @param contentType
     * @param data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean removeContent(Content.ContentType contentType, String data) throws ContentPersistenceException;

    /**
     * Adds a single position to a content (connects)
     *
     * @param contentType  content type
     * @param data         content data
     * @param positionName name of the position
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean addPosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException;

    /**
     * Adds multiple positions to multiple content data. The order of both is important (same order)
     *
     * @param contentTypes  content types
     * @param multipleData  content data
     * @param positionNames position names
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean addPositions(List<Content.ContentType> contentTypes, List<String> multipleData, List<String> positionNames) throws ContentPersistenceException;

    /**
     * Removes a single connected position of a content
     *
     * @param contentType  content type
     * @param data         content data
     * @param positionName position name
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean removePosition(Content.ContentType contentType, String data, String positionName) throws ContentPersistenceException;

    /**
     * Removes all connected positions of a content
     *
     * @param contentType content type
     * @param data        content data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    boolean removeAllPositions(Content.ContentType contentType, String data) throws ContentPersistenceException;

    /**
     * Updates the type and the data of an existing content.
     *
     * @param oldContentType content type which will be changed
     * @param oldData        content data which will be changed
     * @param newContentType new content type
     * @param newData        new content data
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException   error while accessing to the persistence file
     * @throws ContentSameDataException      new and old data are same
     * @throws ContentAlreadyExistsException a content with the same new type and data is already existent
     */
    boolean updateContent(Content.ContentType oldContentType, String oldData, Content.ContentType newContentType, String newData) throws ContentPersistenceException, ContentSameDataException, ContentAlreadyExistsException;

    /**
     * Removes all contents
     *
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    void removeAllContent() throws ContentPersistenceException;

    /**
     * Remove all conncted positions of every content element
     *
     * @throws ContentPersistenceException error while accessing to the persistence file
     */
    void removeAllPositions() throws ContentPersistenceException;
}
