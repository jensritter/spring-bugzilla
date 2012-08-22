package org.jens.SpringBug.j2bugzilla.exceptions;

/**
 * The <code>NoSuchIdException</code> is thrown when a bug cannot be matched against
 * an id provided by the API consumer. 
 * 
 * @author Tom
 */
public class NoSuchIdException extends BugzillaException {

    /**
     * Eclipse-generated SUID
     */
    private static final long serialVersionUID = -4475321452047333909L;

    /**
     * Creates a new {@link NoSuchIdException} with the specified message and wrapped
     * inner exception
     * @param message A short, descriptive message of the error
     * @param cause The exception which caused this one to be thrown
     */
    public NoSuchIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
