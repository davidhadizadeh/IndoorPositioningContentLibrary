/**
 * Created by David Hadizadeh <david@hadizadeh.de>
 * http://hadizadeh.de
 * <p/>
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.md', which is part of this package.
 */

package de.hadizadeh.positioning.content.exceptions;

/**
 * Exception for duplicate content
 */
public class ContentAlreadyExistsException extends ContentException {

    /**
     * Create Exception
     *
     * @param message Message for the duplicate content
     */
    public ContentAlreadyExistsException(String message) {
        super(message);
    }
}
