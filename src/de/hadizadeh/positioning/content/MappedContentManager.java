package de.hadizadeh.positioning.content;

import de.hadizadeh.positioning.content.exceptions.ContentPersistenceException;
import de.hadizadeh.positioning.controller.MappedPositionManager;
import de.hadizadeh.positioning.model.MappingPoint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Layer above the content manager. Adds methods for managing contents with coordinates.
 */
public class MappedContentManager extends ContentManager {

    /**
     * Creates the mapped content manager.
     *
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public MappedContentManager() throws ContentPersistenceException {
    }

    /**
     * Creates the mapped content manager.
     *
     * @param file File where the contents will be stored
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public MappedContentManager(File file) throws ContentPersistenceException {
        super(file);
    }

    /**
     * Creates the mapped content manager.
     *
     * @param contentPersistenceManager Persistence-Manager
     * @throws ContentPersistenceException if the creation of a persistence file fails
     */
    public MappedContentManager(ContentPersistenceManager contentPersistenceManager) throws ContentPersistenceException {
        super(contentPersistenceManager);
    }

    /**
     * Returns all contents of a point in the coordinate system
     *
     * @param mappingPoint coordinates
     * @return list of matched contents
     */
    public ContentList<Content> getContents(MappingPoint mappingPoint) {
        return contents.get(MappedPositionManager.mappingPointToName(mappingPoint));
    }

    /**
     * Adds multiple positions to multiple content data. The order of both is important (same order)
     *
     * @param contentTypes  content types
     * @param multipleData  content data
     * @param mappingPoints coordinates of the positions
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if positions could not be saved to file
     */
    public boolean addPosition(List<Content.ContentType> contentTypes, List<String> multipleData, List<MappingPoint> mappingPoints) throws ContentPersistenceException {
        List<String> positionNames = new ArrayList<String>();
        for (MappingPoint mappingPoint : mappingPoints) {
            positionNames.add(MappedPositionManager.mappingPointToName(mappingPoint));
        }
        return addPositions(contentTypes, multipleData, positionNames);
    }

    /**
     * Removes a single connected position of a content
     *
     * @param contentType  content type
     * @param data         content data
     * @param mappingPoint coordinates
     * @return true, if the operation was successful, false if it failed
     * @throws ContentPersistenceException if changes could not be saved to file
     */
    public boolean removePosition(Content.ContentType contentType, String data, MappingPoint mappingPoint) throws ContentPersistenceException {
        return removePosition(contentType, data, MappedPositionManager.mappingPointToName(mappingPoint));
    }
}
