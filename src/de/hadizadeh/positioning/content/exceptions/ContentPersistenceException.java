/**
 * Created by David Hadizadeh <david@hadizadeh.de>
 * http://hadizadeh.de
 * <p/>
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.md', which is part of this package.
 */

package de.hadizadeh.positioning.content.exceptions;

/**
 * Exception for problems while persisting content data
 */
public class ContentPersistenceException extends ContentException {

    /**
     * Create Exception
     *
     * @param message Message for the problem while persisting content data
     */
    public ContentPersistenceException(String message) {
        super(message);
    }
}
