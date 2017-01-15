/**
 * Created by David Hadizadeh <david@hadizadeh.de>
 * http://hadizadeh.de
 * <p/>
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.md', which is part of this package.
 */

package de.hadizadeh.positioning.content.exceptions;

/**
 * Exception for multiple contents with same data
 */
public class ContentSameDataException extends ContentException {

    /**
     * Create Exception
     *
     * @param message Message for the contents with same data
     */
    public ContentSameDataException(String message) {
        super(message);
    }
}
