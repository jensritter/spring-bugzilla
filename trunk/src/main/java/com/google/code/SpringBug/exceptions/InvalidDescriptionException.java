package com.google.code.SpringBug.exceptions;

import com.google.code.SpringBug.Bug;

/**
 * This exception is thrown when a {@link Bug} is created with an invalid
 * <code>HashMap</code> -- specifically, one that is missing one or more required
 * key/value pairs.
 * @author Tom
 *
 */
public class InvalidDescriptionException extends BugzillaException {

    /**
     * Eclipse-generated SUID
     */
    private static final long serialVersionUID = 6609902718543473946L;

    /**
     * Default constructor for exception.
     * @param message Which key/value pairing caused the exception to be thrown.
     */
    public InvalidDescriptionException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link InvalidDescriptionException} with the specified
     * summary message and nested exception.
     * @param message A short descriptive message of the error
     * @param cause The nested exception causing this to be thrown
     */
    public InvalidDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

}
