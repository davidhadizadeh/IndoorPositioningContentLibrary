/**
 * Created by David Hadizadeh <david@hadizadeh.de>
 * http://hadizadeh.de
 * <p/>
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.md', which is part of this package.
 */

package de.hadizadeh.positioning.content.exceptions;

/**
 * Exception for broken content
 */
public class ContentException extends Exception {

    /**
     * Create Exception
     *
     * @param message Message for the broken content
     */
    public ContentException(String message) {
        super(message);
    }
}
